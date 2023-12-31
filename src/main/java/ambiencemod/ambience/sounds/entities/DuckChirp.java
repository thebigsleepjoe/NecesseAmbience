package ambiencemod.ambience.sounds.entities;

import ambiencemod.ambience.sounds.SoundChance;

public class DuckChirp extends MobChirp {

    public DuckChirp() {
        super();
        this.setVolume(0.4f);

        this.setChance(SoundChance.FREQUENT);
        this.setPitchRange(0.5f, 1.5f);

        this.addSoundPath("birds/Duck1.ogg");
        this.addSoundPath("birds/Duck2.ogg");
        this.addSoundPath("birds/Duck3.ogg");
        this.addSoundPath("birds/Duck4.ogg");
        this.addSoundPath("birds/Duck5.ogg");
    }
}
