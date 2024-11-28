package ambiencemod.ambience.sounds;

import necesse.engine.Screen;
import necesse.engine.network.client.Client;
import necesse.engine.sound.SoundEffect;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameSound.GameSound;
import java.util.ArrayList;

public abstract class PositionalAmbient {
    ArrayList<GameSound> sounds;
    float volume = 1.0f;
    float pitchRangeLow = 1.0f;
    float pitchRangeHigh = 1.1f;
    SoundChance chance = SoundChance.ALWAYS;
    int minTicksBetweenPlays = 0;
    long lastPlayTick = 0;

    public PositionalAmbient() {
        this.sounds = new ArrayList<>();
    }

    public void setVolume(float v) {
        this.volume = v;
    }

    public void setPitchRange(float low, float high) {
        this.pitchRangeLow = low;
        this.pitchRangeHigh = high;
    }

    public void setChance(SoundChance c) {
        this.chance = c;
    }

    public void setMinTicksBetweenPlays(int ticks) {
        this.minTicksBetweenPlays = ticks;
    }

    public void addSound(GameSound sound) {
        this.sounds.add(sound);
    }

    public void addSoundPath(String soundPath) {
        this.addSound(GameSound.fromFile(soundPath));
    }

    public GameSound getRandomSound() throws RuntimeException {
        if (this.sounds.size() == 0) {
            throw new RuntimeException("No sounds added to PositionalAmbientNoise");
        } else if (this.sounds.size() == 1) {
            // basic optimization
            return this.sounds.get(0);
        }
        return this.sounds.get(GameRandom.globalRandom.getIntBetween(0, this.sounds.size() - 1));
    }

    public void playSound(float x, float y) {
        // Test if we succeed the min ticks between plays
        if (this.minTicksBetweenPlays != 0) {
            if (AmbientManager.getTick() - this.lastPlayTick < this.minTicksBetweenPlays) {
                return;
            } else {
                this.lastPlayTick = AmbientManager.getTick();
            }
        }
        // Test if we succeed the chance roll
        if (this.chance != SoundChance.ALWAYS
                && (GameRandom.globalRandom.getFloatBetween(0.0f, 1.0f) > this.chance.getChance())) {
            return;
        }
        // Play the sound
        Screen.playSound(this.getRandomSound(), SoundEffect.effect(x, y)
                .volume(this.volume)
                .pitch(GameRandom.globalRandom.getFloatBetween(this.pitchRangeLow, this.pitchRangeHigh)));
    }

    // Play a sound at a random position around the client.
    public void playSound() {
        Client client = AmbientManager.getClient();
        PlayerMob player = client.getPlayer();
        if (player == null || player.isDisposed())
            return;
        
        // Pick a point on a circle around the radius
        final float RADIUS = GameRandom.globalRandom.getFloatBetween(300.0f, 600.0f);
        final float ANGLE = GameRandom.globalRandom.getFloatBetween(0.0f, 360.0f);
        final float x = player.x + (float) Math.cos(ANGLE) * RADIUS;
        final float y = player.y + (float) Math.sin(ANGLE) * RADIUS;
        this.playSound(x, y);
    }
}
