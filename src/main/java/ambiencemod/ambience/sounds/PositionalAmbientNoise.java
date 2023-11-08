package ambiencemod.ambience.sounds;

import necesse.engine.Screen;
import necesse.engine.sound.SoundEffect;
import necesse.engine.util.GameRandom;
import necesse.gfx.gameSound.GameSound;
import java.util.ArrayList;

public abstract class PositionalAmbientNoise {
    ArrayList<GameSound> sounds;
    float volume = 1.0f;
    float pitchRangeLow = 1.0f;
    float pitchRangeHigh = 1.1f;
    SoundChance chance = SoundChance.ALWAYS;

    public PositionalAmbientNoise(SoundChance chance, float volume, float pitchRangeLow, float pitchRangeHigh) {
        this.volume = volume;
        this.pitchRangeLow = pitchRangeLow;
        this.pitchRangeHigh = pitchRangeHigh;
        this.chance = chance;
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
        if (this.chance != SoundChance.ALWAYS && (GameRandom.globalRandom.getFloatBetween(0.0f, 1.0f) > this.chance.getChance())) {
            return;
        }
        Screen.playSound(this.getRandomSound(), SoundEffect.effect(x, y)
                .volume(this.volume)
                .pitch(GameRandom.globalRandom.getFloatBetween(this.pitchRangeLow, this.pitchRangeHigh)));
    }
}
