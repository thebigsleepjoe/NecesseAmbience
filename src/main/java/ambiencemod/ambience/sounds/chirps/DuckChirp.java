package ambiencemod.ambience.sounds.chirps;

import ambiencemod.ambience.sounds.AmbientManager;
import ambiencemod.ambience.sounds.SoundChance;
import necesse.entity.mobs.friendly.critters.DuckMob;

public class DuckChirp extends MobChirp {

    public DuckChirp() {
        super();
        this.setMaxVolume(0.4f);

        this.setPitchRange(0.5f, 1.5f);

        this.addSoundPath("birds/Duck1.ogg");
        this.addSoundPath("birds/Duck2.ogg");
        this.addSoundPath("birds/Duck3.ogg");
        this.addSoundPath("birds/Duck4.ogg");
        this.addSoundPath("birds/Duck5.ogg");
    }

    @Override
    protected void registerChirps() {
        AmbientManager.chirpMap.put(DuckMob.class, this);
    }
}
