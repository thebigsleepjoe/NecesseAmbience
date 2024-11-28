package ambiencemod.ambience.sounds;

import necesse.engine.sound.GlobalSoundEffect;
import necesse.engine.sound.SoundManager;
import necesse.engine.sound.SoundPlayer;
import necesse.engine.util.GameRandom;

public class GlobalAmbient extends PositionalAmbient {

    public SoundPlayer soundPlayer;
    public boolean waitUntilDone = false;
    public float fadeInTime = 0.0f;
    public float timeBetweenRepeats = 0.0f;
    private boolean wasJustPlayed = false;
    private float justPlayedTime = 0.0f;

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

    protected void setTimeBetweenRepeats(float v) {
        this.timeBetweenRepeats = v;
    }
}
