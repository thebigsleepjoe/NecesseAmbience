package ambiencemod.ambience.sounds.chirps;

import ambiencemod.ambience.sounds.AmbientManager;
import ambiencemod.ambience.sounds.SoundChance;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.friendly.critters.BirdMob;

public class BirdChirp extends MobChirp {
    public BirdChirp() {
        super();
        this.setMaxVolume(0.5f);
        this.setChance(SoundChance.OFTEN);

        this.addSoundPath("birds/Cardinal1.ogg");
        this.addSoundPath("birds/Cardinal2.ogg");
        this.addSoundPath("birds/Cardinal3.ogg");
        this.addSoundPath("birds/Cardinal4.ogg");
        this.addSoundPath("birds/Generic1.ogg");
    }

    @Override
    protected void registerChirps() {
        AmbientManager.chirpMap.put(BirdMob.class, this);
    }

    public boolean isAirborne(Mob mob) {
        return (mob.isFlying() && mob.getFlyingHeight() > 40);
    }

    public boolean isOnGroundRandom(Mob mob) {
        if (!this.isAirborne(mob)) {
            return (GameRandom.globalRandom.getChance(SoundChance.ALMOST_NEVER.getChance()));
        }
        return false;
    }

    @Override
    public boolean shouldPlaySound(Mob mob) {
        return (super.shouldPlaySound(mob) && (this.isOnGroundRandom(mob) || this.isAirborne(mob)));
    }
}
