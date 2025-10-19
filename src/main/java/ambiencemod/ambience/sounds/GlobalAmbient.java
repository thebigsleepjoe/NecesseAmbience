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
    protected boolean isMuffled = false;
    protected final float lerpRate = 1.0f / 20.0f;
    protected float targetVolume = 0.0f;
    protected float volume = 0.0f;

    public GlobalAmbient() {
        AmbientManager.ambientTracks.add(this);
    }

    @Deprecated
    public void setFadeInTime(float time) {
        this.fadeInTime = time;
    }

    public void setWaitUntilDone(boolean wait) {
        this.waitUntilDone = wait;
    }

    public float getAdjustedMaxVolume() {
        return this.maxVolume * this.getVolumeSetting();
    }

    public float getTargetVolume() {
        return this.targetVolume;
    }

    public void setTargetVolume(float x) {
        this.targetVolume = x;
    }

    public void resetVolume() {
        this.volume = 0.0f;
    }

    private void tickVolume() {
        if (!this.isPlaying()) {
            this.resetVolume();
            return;
        }

        assert this.soundPlayer != null;
        float timeLeft = this.soundPlayer.getSecondsLeft();
        float timeLeftModifier = timeLeft < 4.0f ? timeLeft / 4.0f : 1.0f; // little extra time buffer just in case

        float pausedModifier = AmbientManager.isPaused() ? 0.0f : 1.0f;
        this.setTargetVolume(pausedModifier * timeLeftModifier * this.getAdjustedMaxVolume() * (this.isMuffled ? 0.3f : 1.0f));

        this.volume = lerp(this.volume, this.getTargetVolume(), this.lerpRate);
        this.soundPlayer.effect.volume(this.volume);
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

        this.resetVolume();
        this.soundPlayer = SoundManager.playSound(this.getRandomSound(), GlobalSoundEffect.globalEffect()
                        .volume(0.0f) // this is managed externally
                        .pitch(GameRandom.globalRandom.getFloatBetween(this.pitchRangeLow, this.pitchRangeHigh)));
    }

    private static float lerp(float a, float b, float t) {
        return a + t * (b - a);
    }

    public void onTick(PlayerMob ply) {
        if (ply == null) return;

        this.isMuffled = this.getShouldMuffle(ply);
        this.tickVolume();

        if (this.soundPlayer == null || this.soundPlayer.effect == null) return;
        this.soundPlayer.effect.volume(this.getVolume());
    }

    public float getVolume() {
        return this.volume;
    }

    public float getVolumeSetting() {
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
        this.resetVolume();
    }

    public boolean getShouldMuffle(PlayerMob ply) {
        if (!this.mufflesIndoors) return false;
        if (this.soundPlayer == null) return false;
        if (this.soundPlayer.effect == null) return false;
        if (!this.isPlaying()) return false;

        Level lvl = ply.getLevel();
        if (lvl == null) return false;

        return !lvl.isOutside(ply.getTileX(), ply.getTileY());
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
        this.resetVolume();
    }

    public void update(PlayerMob ply) {
        // note: this is only called if canRun() == true
        if (this.soundPlayer == null) {
            this.playSound();
            return;
        }

        final boolean finished = !this.isPlaying() || this.isDone();

        if (finished) {
            this.playSound();
        }
    }

    public boolean isInBiomes(PlayerMob ply) {
        Level lvl = ply.getLevel();
        Biome biome = lvl.getBiome(ply.getTileX(), ply.getTileY());

        for (Class<? extends Biome> aClass : biomes) {
            if (biome.getClass().equals(aClass)) return true;
        }

        return false;
    }

    @Deprecated
    protected void setTimeBetweenRepeats(float v) {
    }
}
