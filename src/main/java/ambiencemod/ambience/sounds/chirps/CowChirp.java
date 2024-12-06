package ambiencemod.ambience.sounds.chirps;

import ambiencemod.ambience.sounds.AmbientManager;
import ambiencemod.ambience.sounds.SoundChance;
import necesse.entity.mobs.friendly.CowMob;

public class CowChirp extends MobChirp {

    public CowChirp() {
        super();
        this.setVolume(0.3f);

        this.setChance(SoundChance.RARELY);

        this.addSoundPath("cows/Cow1.ogg");
        this.addSoundPath("cows/Cow2.ogg");
        this.addSoundPath("cows/Cow3.ogg");
        this.addSoundPath("cows/Cow4.ogg");
        this.addSoundPath("cows/Cow5.ogg");
    }

    @Override
    protected void registerChirps() {
        AmbientManager.chirpMap.put(CowMob.class, this);
    }
}
