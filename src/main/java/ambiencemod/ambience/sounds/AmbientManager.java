package ambiencemod.ambience.sounds;

import ambiencemod.ambience.sounds.chirps.BirdChirp;
import ambiencemod.ambience.sounds.chirps.CowChirp;
import ambiencemod.ambience.sounds.chirps.DuckChirp;
import ambiencemod.ambience.sounds.chirps.SheepChirp;
import ambiencemod.ambience.sounds.footsteps.FootstepsManager;
import ambiencemod.ambience.sounds.global.BirdChirpAmbient;
import ambiencemod.ambience.sounds.global.WindAmbient;
import necesse.engine.GlobalData;
import necesse.engine.network.client.Client;
import necesse.engine.state.MainGame;
import necesse.engine.state.State;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.friendly.CowMob;
import necesse.entity.mobs.friendly.SheepMob;
import necesse.entity.mobs.friendly.critters.BirdMob;
import necesse.entity.mobs.friendly.critters.DuckMob;

import java.util.ArrayList;

public final class AmbientManager {
    public static long tick = 0;
    ArrayList<GlobalAmbient> ambientTracks = new ArrayList<GlobalAmbient>();
    FootstepsManager footstepsManager;

    // Locational animal noises
    BirdChirp birdChirp;
    CowChirp cowChirp;
    SheepChirp sheepChirp;
    DuckChirp duckChirp;

    public AmbientManager() {
        this.footstepsManager = new FootstepsManager();

        // Locational animal chirps
        birdChirp = new BirdChirp();
        cowChirp = new CowChirp();
        sheepChirp = new SheepChirp();
        duckChirp = new DuckChirp();

       this.ambientTracks.add(new WindAmbient());
       this.ambientTracks.add(new BirdChirpAmbient());
    }

    public float getMobSpeedPct(Mob mob) {
        //return (mob.getCurrentSpeed() / mob.getSpeed());
        return (mob.getCurrentSpeed() / 35.0f); // 35.0f is baseline human speed, makes footstep speed more consistent. basically 2x step sounds per second
    }

    private void manageAmbientTracks(PlayerMob ply) {
        for (GlobalAmbient track : this.ambientTracks) {
            if (track.canRun(ply)) {
                track.update(ply);
            } else {
                track.stopPlayer();
            }
        }
    }

    private void manageMobFootstepSounds(Mob mob) {
        // Execute this code once per 0.3 seconds
        if (AmbientManager.getTick() % AmbientManager.secondsToTicks(0.4f) != 0) {
            return;
        }
        if (mob.isFlying() || mob.inLiquid()) return;

        float mobSpeedPct = getMobSpeedPct(mob);
        if (mobSpeedPct > 0.1f) {
            this.footstepsManager.onFootstep(mob);
            // System.out.println("Playing footstep for " + mob.getDisplayName());
        }
    }

    private void manageMobChirps(Mob mob) {
        // While this code may look unappealing and non-modular, it's a much simpler way to implement sounds.
        if (mob instanceof BirdMob) {
            birdChirp.onMobTick(mob);
        }else if (mob instanceof CowMob) {
            cowChirp.onMobTick(mob);
        }else if (mob instanceof SheepMob) {
            sheepChirp.onMobTick(mob);
        }else if (mob instanceof DuckMob) {
            duckChirp.onMobTick(mob);
        }
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
            this.manageMobFootstepSounds(mob);
            return;
        }

        if (!mob.isSamePlace(ply)) return;

        float distTo = distance(mob.x, mob.y, ply.x, ply.y);

        if (distTo < 800) { // Prevent overwhelming the sound engine
            this.manageMobFootstepSounds(mob);
            this.manageMobChirps(mob);
        }
    }

    public static float ticksToSeconds(int tick) {
        return (tick / 20.0f);
    }

    public static int secondsToTicks(float seconds) {
        return (int)(seconds * 20.0f);
    }

    public static long getTick() {
        return AmbientManager.tick;
    }

    public void onGameSecondTick() {
        PlayerMob ply = AmbientManager.getLocalPlayer();
        this.manageAmbientTracks(ply);
    }
}

