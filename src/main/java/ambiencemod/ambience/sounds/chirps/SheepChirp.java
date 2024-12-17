package ambiencemod.ambience.sounds.chirps;

import ambiencemod.ambience.sounds.AmbientManager;
import ambiencemod.ambience.sounds.SoundChance;
import necesse.entity.mobs.friendly.SheepMob;

public class SheepChirp extends MobChirp {

    public SheepChirp() {
        super();
        this.setMaxVolume(0.3f);

        this.addSoundPath("sheep/Sheep1.ogg");
        this.addSoundPath("sheep/Sheep2.ogg");
        this.addSoundPath("sheep/Sheep3.ogg");
        this.addSoundPath("sheep/Sheep4.ogg");
        this.addSoundPath("sheep/Sheep5.ogg");
        this.addSoundPath("sheep/Sheep6.ogg");
        this.addSoundPath("sheep/Sheep7.ogg");
        this.addSoundPath("sheep/Sheep8.ogg");
    }

    @Override
    protected void registerChirps() {
        AmbientManager.chirpMap.put(SheepMob.class, this);
    }
}
