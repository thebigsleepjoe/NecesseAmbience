package ambiencemod.ambience.sounds.entities;

import ambiencemod.ambience.sounds.SoundChance;

public class SheepChirp extends MobChirp {

    public SheepChirp() {
        super();
        this.setVolume(0.3f);

        this.setChance(SoundChance.RARELY);

        this.addSoundPath("sheep/Sheep1.ogg");
        this.addSoundPath("sheep/Sheep2.ogg");
        this.addSoundPath("sheep/Sheep3.ogg");
        this.addSoundPath("sheep/Sheep4.ogg");
        this.addSoundPath("sheep/Sheep5.ogg");
        this.addSoundPath("sheep/Sheep6.ogg");
        this.addSoundPath("sheep/Sheep7.ogg");
        this.addSoundPath("sheep/Sheep8.ogg");
    }
}
