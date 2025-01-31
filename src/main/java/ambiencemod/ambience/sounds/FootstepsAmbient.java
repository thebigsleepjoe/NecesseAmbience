package ambiencemod.ambience.sounds;

import necesse.engine.util.GameRandom;

public abstract class FootstepsAmbient extends PositionalAmbient {
    public FootstepsAmbient() {
        super();
        this.setChance(SoundChance.ALWAYS);
        this.setMaxVolume(1.0f);
        this.setPitchRange(0.8f, 1.2f);
        this.setMinTicksBetweenPlays(0); // This is managed elsewhere.

        // Add the sound paths
        // this.addSoundPath("footsteps/Grass1.ogg");
    }

    public float getChanceFromSpeedPct(float speedPct) {
        if (speedPct < 0.05) {
            return SoundChance.NEVER.getChance();
        }
        else if (speedPct < 0.1) {
            return SoundChance.RARELY.getChance();
        }
        else if (speedPct < 0.25) {
            return SoundChance.FREQUENT.getChance();
        }
        else if (speedPct < 0.5) {
            return SoundChance.OFTEN.getChance();
        }
        else if (speedPct < 0.75) {
            return SoundChance.VERY_OFTEN.getChance();
        }
        else if (speedPct < 0.9) {
            return SoundChance.ALWAYS.getChance();
        }
        else {
            return SoundChance.ALWAYS.getChance();
        }
    }
}
