package ambiencemod.ambience.sounds.chirps;

import ambiencemod.ambience.sounds.SoundChance;

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
}
