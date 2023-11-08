package ambiencemod.ambience.sounds;

import ambiencemod.AmbienceMod;
import necesse.engine.util.GameRandom;

public class FootstepsAmbientNoise extends PositionalAmbientNoise {
    public FootstepsAmbientNoise() {
        super();
        this.SetChance(SoundChance.ALWAYS);
        this.SetVolume(0.3f);
        this.SetPitchRange(0.6f, 0.8f);
        this.SetMinTicksBetweenPlays(0); // This is managed elsewhere.

        // Add the sound paths
        this.addSoundPath("footsteps/DirtFootstep1.ogg");
    }

    public float getChanceFromSpeedPct(float speedPct) {
        if (speedPct < 0.05) {
            return SoundChance.NEVER.getChance();
        }
        else if (speedPct < 0.1) {
            return SoundChance.ALMOST_NEVER.getChance();
        }
        else if (speedPct < 0.25) {
            return SoundChance.RARELY.getChance();
        }
        else if (speedPct < 0.5) {
            return SoundChance.FREQUENT.getChance();
        }
        else if (speedPct < 0.75) {
            return SoundChance.OFTEN.getChance();
        }
        else if (speedPct < 0.9) {
            return SoundChance.VERY_OFTEN.getChance();
        }
        else {
            return SoundChance.ALWAYS.getChance();
        }
    }

    public void playSound(float x, float y, float mobSpeedPct) {
        if (GameRandom.globalRandom.getChance(this.getChanceFromSpeedPct(mobSpeedPct)))
            super.playSound(x, y);
    }
}
