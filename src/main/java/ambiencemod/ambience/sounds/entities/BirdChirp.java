package ambiencemod.ambience.sounds.entities;

import necesse.entity.mobs.Mob;

public class BirdChirp extends MobChirp {
    public BirdChirp() {
        super();
        this.setVolume(0.5f);

        this.addSoundPath("birds/Cardinal1.ogg");
        this.addSoundPath("birds/Cardinal2.ogg");
        this.addSoundPath("birds/Cardinal3.ogg");
        this.addSoundPath("birds/Cardinal4.ogg");
        this.addSoundPath("birds/Generic1.ogg");
    }

    @Override
    public boolean shouldPlaySound(Mob mob) {
        return (mob.isFlying() && mob.getFlyingHeight() > 40);
    }
}
