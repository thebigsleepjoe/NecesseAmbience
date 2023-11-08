package ambiencemod.ambience.sounds;

import necesse.engine.GameInfo;
import necesse.engine.Screen;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;

public final class AmbientManager {
    FootstepsAmbientNoise footstepsAmbientNoise;

    public AmbientManager() {
        footstepsAmbientNoise = new FootstepsAmbientNoise();
    }

    public float getMobSpeedPct(Mob mob) {
        return (mob.getCurrentSpeed() / mob.getSpeed());
    }

    public void manageMobFootstepSounds(Mob mob) {
        // Execute this code once per 0.3 seconds
        if (AmbientManager.getTick() % AmbientManager.secondsToTicks(0.3f) != 0) {
            return;
        }
        if (mob.isFlying()) return;

        float mobSpeedPct = getMobSpeedPct(mob);
        if (mobSpeedPct > 0.1f) {
            footstepsAmbientNoise.playSound(mob.x, mob.y, mobSpeedPct);
        }
    }

    public void onPlayerTick(PlayerMob playerMob) {
        this.manageMobFootstepSounds(playerMob);
    }

    public static float ticksToSeconds(int tick) {
        return (tick / 20.0f);
    }

    public static int secondsToTicks(float seconds) {
        return (int)(seconds * 20.0f);
    }

    public static long getTick() {
        return Screen.tickManager.getTotalTicks();
    }

}

