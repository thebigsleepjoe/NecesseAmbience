package ambiencemod.ambience.sounds;

import ambiencemod.ambience.sounds.chirps.*;
import ambiencemod.ambience.sounds.footsteps.FootstepsManager;
import ambiencemod.ambience.sounds.global.BirdAmbient;
import ambiencemod.ambience.sounds.global.CaveAmbient;
import ambiencemod.ambience.sounds.global.ForestNightAmbient;
import ambiencemod.ambience.sounds.global.WindAmbient;
import necesse.engine.GlobalData;
import necesse.engine.network.client.Client;
import necesse.engine.state.MainGame;
import necesse.engine.state.State;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;

import java.util.ArrayList;
import java.util.HashMap;

public final class AmbientManager {
    public static long tick = 0;
    public static final ArrayList<GlobalAmbient> ambientTracks = new ArrayList<>();
    public static final HashMap<Class<? extends Mob>, MobChirp> chirpMap = new HashMap<>();
    FootstepsManager footstepsManager;

    public AmbientManager() {
        this.footstepsManager = new FootstepsManager();
        loadChirps();
        loadAmbient();
    }

    private void loadChirps() {
        // all chirp classes add themselves to chirpMap
        new BirdChirp();
        new CowChirp();
        new SheepChirp();
        new DuckChirp();
    }

    private void loadAmbient() {
        // all ambient classes add themselves to ambientTracks
        new WindAmbient();
        new CaveAmbient();
        new BirdAmbient();
        new ForestNightAmbient();
    }

    public static float getMobSpeedPct(Mob mob) {
        //return (mob.getCurrentSpeed() / mob.getSpeed());
        return (mob.getCurrentSpeed() / 40.0f);
    }

    public static void stopGlobalTracks() {
        for (GlobalAmbient track : ambientTracks) {
            track.stopPlayer();
        }
    }

    private static void updateAmbientTracks(PlayerMob ply) {
        for (GlobalAmbient track : ambientTracks) {
            if (track.canRun(ply)) {
                track.update(ply);
            } else {
                track.stopPlayer();
            }
        }
    }

    private void manageMobChirps(Mob mob) {
        MobChirp chirp = chirpMap.get(mob.getClass());

        if (chirp == null) return;

        chirp.playSound(mob.x, mob.y);
    }

    public static boolean isInGame() {
        State currentState = GlobalData.getCurrentState();
        return (currentState instanceof MainGame);
    }

    public static Client getClient() {
        State currentState = GlobalData.getCurrentState();
        if (currentState instanceof MainGame) {
            return ((MainGame) currentState).getClient();
        }
        return null;
    }
    
    public static PlayerMob getLocalPlayer() {
        Client client = getClient();
        if (client == null) return null;
        return client.getPlayer();
    }

    public static float distance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public void onMobTick(Mob mob) {
        if (!isInGame()) return;
        PlayerMob ply = AmbientManager.getLocalPlayer();
        if (ply == null) return;

        if (ply == mob) { // end early, no need to calculate distance to self
            this.footstepsManager.onMobTick(mob);
            return;
        }

        if (!mob.isSamePlace(ply)) return;

        float distTo = distance(mob.x, mob.y, ply.x, ply.y);

        if (distTo < 800) { // Prevent overwhelming the sound engine
            this.footstepsManager.onMobTick(mob);
            this.manageMobChirps(mob);
        }
    }

    public static float getTimeSecs() {
        return System.nanoTime() / 1_000_000_000.0f;
    }

    public static float ticksToSeconds(int tick) {
        return (tick / 20.0f);
    }

    public static int secondsToTicks(float seconds) {
        return (int)(seconds * 20.0f);
    }

    // FIXME: might be broken....???
    public static long getTick() {
        return AmbientManager.tick;
    }

    public void onGameSecondTick() {
        PlayerMob ply = AmbientManager.getLocalPlayer();
        this.updateAmbientTracks(ply);
        this.footstepsManager.onGameSecondTick();
    }
}

