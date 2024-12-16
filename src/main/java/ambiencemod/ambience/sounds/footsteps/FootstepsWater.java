package ambiencemod.ambience.sounds.footsteps;

import ambiencemod.ambience.sounds.FootstepsAmbient;
import ambiencemod.ambience.sounds.SoundChance;

public class FootstepsWater extends FootstepsAmbient {
    public FootstepsWater() {
        this.setPitchRange(0.8f, 1.2f);
        this.setMinTicksBetweenPlays(25);
        this.addSoundPath("footsteps/Swim1.ogg");
        this.addSoundPath("footsteps/Swim2.ogg");
        this.addSoundPath("footsteps/Swim3.ogg");
        this.addSoundPath("footsteps/Swim4.ogg");
        this.addSoundPath("footsteps/Swim5.ogg");
        this.addSoundPath("footsteps/Swim6.ogg");
        this.addSoundPath("footsteps/Swim7.ogg");
    }
}
