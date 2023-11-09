package ambiencemod.ambience.sounds;

import necesse.engine.Screen;
import necesse.engine.sound.GlobalSoundEffect;
import necesse.engine.sound.SoundPlayer;
import necesse.engine.util.GameRandom;

public class GlobalAmbient extends PositionalAmbient {

    public SoundPlayer soundPlayer;
    public boolean waitUntilDone = false;

    public GlobalAmbient() {}

    public void setWaitUntilDone(boolean wait) {
        this.waitUntilDone = wait;
    }

    public void playSound() {
        // Test if we're already playing audio (if applicable).
        if (this.waitUntilDone && soundPlayer.isPlaying()) return;

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
        soundPlayer = Screen.playSound(this.getRandomSound(), GlobalSoundEffect.globalEffect()
                        .volume(this.volume)
                        .pitch(GameRandom.globalRandom.getFloatBetween(this.pitchRangeLow, this.pitchRangeHigh)));
    }
}
