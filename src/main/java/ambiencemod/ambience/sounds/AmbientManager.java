package ambiencemod.ambience.sounds;

import necesse.engine.GlobalData;
import necesse.engine.Screen;
import necesse.engine.network.client.Client;
import necesse.engine.state.MainGame;
import necesse.engine.state.State;
import necesse.engine.util.GameMath;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;

public final class AmbientManager {
    FootstepsAmbientNoise footstepsAmbientNoise;

    public AmbientManager() {
        footstepsAmbientNoise = new FootstepsAmbientNoise();
    }

    public float getMobSpeedPct(Mob mob) {
        //return (mob.getCurrentSpeed() / mob.getSpeed());
        return (mob.getCurrentSpeed() / 35.0f); // 35.0f is baseline human speed, makes footstep speed more consistent. basically 2 step sounds per second
    }

    public void manageMobFootstepSounds(Mob mob) {
        // Execute this code once per 0.3 seconds
        if (AmbientManager.getTick() % AmbientManager.secondsToTicks(0.4f) != 0) {
            return;
        }
        if (mob.isFlying() || mob.inLiquid()) return;

        float mobSpeedPct = getMobSpeedPct(mob);
        if (mobSpeedPct > 0.1f) {
            System.out.println("Playing footstep for " + mob.getDisplayName());
            footstepsAmbientNoise.playSound(mob.x, mob.y, mobSpeedPct);
        }
    }

    public boolean isInGame() {
        State currentState = GlobalData.getCurrentState();
        return (currentState instanceof MainGame);
    }

    public Client getClient() {
        State currentState = GlobalData.getCurrentState();
        if (currentState instanceof MainGame) {
            return ((MainGame)currentState).getClient();
        }
        return null;
    }
    public PlayerMob getLocalPlayer() {
        Client client = getClient();
        if (client == null) return null;
        return client.getPlayer();
    }

    public static float distance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }


    public void onMobTick(Mob mob) {
        if (!isInGame()) return;
        PlayerMob ply = this.getLocalPlayer();
        if (ply == mob) { // end early, no need to calculate distance to self
            this.manageMobFootstepSounds(mob);
            return;
        }
        if (ply == null) return;
        float distTo = distance(mob.x, mob.y, ply.x, ply.y);
        if (distTo < 800) { // Prevent overwhelming the sound engine
            this.manageMobFootstepSounds(mob);
        }
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

