package ambiencemod.ambience.sounds.footsteps;

import ambiencemod.ambience.sounds.FootstepsAmbient;

public class FootstepsSnow extends FootstepsAmbient {
    public FootstepsSnow() {
        this.addSoundPath("footsteps/Snow-01.ogg");
        this.addSoundPath("footsteps/Snow-02.ogg");
        this.addSoundPath("footsteps/Snow-03.ogg");
        this.addSoundPath("footsteps/Snow-04.ogg");
        this.addSoundPath("footsteps/Snow-05.ogg");
        this.addSoundPath("footsteps/Snow-06.ogg");
        this.addSoundPath("footsteps/Snow-07.ogg");
        this.addSoundPath("footsteps/Snow-08.ogg");
        this.addSoundPath("footsteps/Snow-09.ogg");
        this.addSoundPath("footsteps/Snow-10.ogg");

        this.setMaxVolume(0.7f);
        this.setPitchRange(0.9f, 1.1f);
    }
}
