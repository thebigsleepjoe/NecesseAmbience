package ambiencemod.ambience.sounds;

import necesse.engine.sound.GlobalSoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.sound.SoundPlayer;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;
import necesse.level.maps.Level;
import necesse.level.maps.biomes.Biome;
import net.bytebuddy.utility.nullability.MaybeNull;

import java.util.ArrayList;

public class GlobalAmbient extends PositionalAmbient {

    public ArrayList<Class<? extends Biome>> biomes = new ArrayList<>();

    @MaybeNull
    public SoundPlayer soundPlayer;
    public boolean waitUntilDone = false;
    public float fadeInTime = 0.0f;
    public float timeBetweenRepeats = 0.0f;
    private boolean wasJustPlayed = false;
    private float justPlayedTime = 0.0f;
    public boolean mufflesIndoors = true;
    private boolean isMuffled = false;

    public GlobalAmbient() {}

    public void setFadeInTime(float time) {
        this.fadeInTime = time;
    }
    public void setWaitUntilDone(boolean wait) {
        this.waitUntilDone = wait;
    }

    private float getCurrentTimeSecs() {
        return ((float)System.currentTimeMillis() / 1000.0f);
    }

    public void playSound() {
        // Test if we're already playing audio (if applicable).
        if (this.waitUntilDone && this.soundPlayer != null && (!this.soundPlayer.isDone() && !this.soundPlayer.isDisposed())) {
            this.wasJustPlayed = true;
            this.justPlayedTime = this.getCurrentTimeSecs();
            return;
        }
        if (wasJustPlayed && (this.getCurrentTimeSecs() - this.justPlayedTime) < this.timeBetweenRepeats) {
            return;
        } else {
            this.wasJustPlayed = false; // Reset the flag
        }

        // Test if we succeed the min ticks between plays
        if (this.minTicksBetweenPlays != 0) {
            if (AmbientManager.getTick() - this.lastPlayTick < this.minTicksBetweenPlays) {
                return;
            } else {
                this.lastPlayTick = AmbientManager.getTick();
            }
        }
        // Test if we succeed the chance roll
        if (this.chance != SoundChance.ALWAYS && (GameRandom.globalRandom.getFloatBetween(0.0f, 1.0f) > this.chance.getChance())) {
            return;
        }
        // Play the sound
        soundPlayer = SoundManager.playSound(this.getRandomSound(), GlobalSoundEffect.globalEffect()
                        .volume(this.volume)
                        .pitch(GameRandom.globalRandom.getFloatBetween(this.pitchRangeLow, this.pitchRangeHigh)))
                        .fadeIn(this.fadeInTime);
    }

    public void startOrContinue() {
        if (this.soundPlayer != null && this.soundPlayer.isPlaying()) return;

        this.playSound();
    }

    public void stopPlaying() {
        if (this.soundPlayer == null) return;
        if (!this.soundPlayer.isPlaying()) return;

        this.destroyPlayer();
    }

    public void handleMuffle(PlayerMob ply) {
        if (!this.mufflesIndoors) return;
        if (this.soundPlayer == null) return;
        if (!this.soundPlayer.isPlaying()) return;

        Level lvl = ply.getLevel();
        if (lvl == null) return;

        final boolean shouldMuffle = !lvl.isOutside(ply.getTileX(), ply.getTileY());

        if (shouldMuffle == this.isMuffled) return; // No need to do anything. We are where we need to be.

        final float gainAmount = 5.0f; // I think this is dB

        this.isMuffled = shouldMuffle;
        this.soundPlayer.alSetGain(shouldMuffle ? -gainAmount : 0.0f);
    }

    public boolean canRun(PlayerMob ply) {
        throw new RuntimeException("Must override GlobalAmbient canRun function");
    }

    public void destroyPlayer() {
        if (this.soundPlayer == null) return;
        this.soundPlayer.pause();
        this.soundPlayer.dispose();
        this.soundPlayer = null;
        this.isMuffled = false;
    }

    public void update(PlayerMob ply) {
        if (this.soundPlayer == null) return;

        this.handleMuffle(ply);

        if (!this.soundPlayer.isDone() || !this.soundPlayer.isPlaying()) return;

        this.destroyPlayer();
    }

    public boolean isInBiomes(PlayerMob ply) {
        Level lvl = ply.getLevel();
        Biome biome = lvl.biome;

        for (Class<? extends Biome> aClass : biomes) {
            if (biome.getClass().equals(aClass)) return true;
        }

        return false;
    }

    protected void setTimeBetweenRepeats(float v) {
        this.timeBetweenRepeats = v;
    }
}
