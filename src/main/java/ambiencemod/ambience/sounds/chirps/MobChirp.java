package ambiencemod.ambience.sounds.chirps;

import ambiencemod.AmbientMod;
import ambiencemod.ambience.sounds.AmbientManager;
import ambiencemod.ambience.sounds.PositionalAmbient;
import ambiencemod.ambience.sounds.SoundChance;
import necesse.engine.network.client.Client;
import necesse.engine.sound.gameSound.GameSound;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;

// This class manages automatically playing "chirps" for animals nearby the player.
public abstract class MobChirp extends PositionalAmbient {

    public MobChirp() {
        super();

        this.setVolume(0.4f);
        this.setPitchRange(0.8f, 1.2f);
        this.setMinTicksBetweenPlays(AmbientManager.secondsToTicks(5.0f));
        this.setChance(SoundChance.ALMOST_NEVER);
    }

    @Override
    public float getVolumeModPct() {
        // overridden because this otherwise gets scaled with the footsteps volume slider.
       return 1.0f;
    }

    public boolean shouldPlaySound(Mob mob) {
        return true;
    }

    public static float distToClient(Client client, Mob mob) {
        final float FALLBACK = 999999.0f;
        if (client == null || mob == null) return FALLBACK;
        PlayerMob playerMob = client.getPlayer();
        if (playerMob == null) return FALLBACK;
        return (float)playerMob.getPositionPoint().distance(mob.getPositionPoint());
    }

    public void onMobTick(Mob mob) {
        Client client = AmbientMod.ambientManager.getClient();
        if (client == null) return;
        if (distToClient(client, mob) > 512.0f) return;

        if (shouldPlaySound(mob)) {
            playSound(mob.x, mob.y);
        }
    }
}
