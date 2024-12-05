package ambiencemod.ambience.sounds;

import ambiencemod.ambience.patches.SettingsFormPatch;
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
        if (this.chance != SoundChance.ALWAYS && (GameRandom.globalRandom.getFloatBetween(0.0f, 1.0f) > this.chance.getChance())) {
            return;
        }

        if (this.soundPlayer != null && this.isPlaying() && !this.isDone()) {
            return;
        }

        this.soundPlayer = SoundManager.playSound(this.getRandomSound(), GlobalSoundEffect.globalEffect()
                        .volume(this.volume)
                        .pitch(GameRandom.globalRandom.getFloatBetween(this.pitchRangeLow, this.pitchRangeHigh)))
                        .fadeIn(this.fadeInTime);
    }

    public float getVolumeModPct() {
        return SettingsFormPatch.ambienceVolumePct;
    }

    // NOTE: this is a fn because the SoundPlayer class does NOT yield the correct value of .isDone().
    // Because of this, we must check manually how many seconds remain in the clip.
    public boolean isDone() {
        return this.soundPlayer == null ? false : this.soundPlayer.getSecondsLeft() < 0.1f;
    }

    public boolean isPlaying() {
        return this.soundPlayer == null ? false: this.soundPlayer.isPlaying();
    }

    public void restartPlayer() {
        if (this.soundPlayer == null) return;

        this.soundPlayer.setPosition(0.0f);
    }

    public void stopPlayer() {
        if (this.soundPlayer == null || this.soundPlayer.isDisposed()) return;

        // we dispose here instead of just restartPlayer because it will allow a new sound to play
        this.soundPlayer.dispose();
        this.soundPlayer = null;
    }

    public void handleMuffle(PlayerMob ply) {
        if (!this.mufflesIndoors) return;
        if (this.soundPlayer == null) return;
        if (!this.isPlaying()) return;

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

    @Deprecated /* this shouldn't be used unless in very specific circumstances */
    public void destroyPlayer() {
        if (this.soundPlayer == null) return;
        this.soundPlayer.pause();
        this.soundPlayer.dispose();
        this.soundPlayer = null;
        this.isMuffled = false;
    }

    public void update(PlayerMob ply) {
        // note: this is only called if canRun() == true
        if (this.soundPlayer == null) {
            this.playSound();
            return;
        }

        this.handleMuffle(ply);

        final boolean finished = !this.isPlaying() || this.isDone();

        if (finished) {
            this.playSound();
        }
    }

    public boolean isInBiomes(PlayerMob ply) {
        Level lvl = ply.getLevel();
        Biome biome = lvl.biome;

        for (Class<? extends Biome> aClass : biomes) {
            if (biome.getClass().equals(aClass)) return true;
        }

        return false;
    }

    @Deprecated
    protected void setTimeBetweenRepeats(float v) {
    }
}
