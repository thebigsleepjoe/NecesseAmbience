package ambiencemod.ambience.sounds.footsteps;

import ambiencemod.ambience.sounds.AmbientManager;
import ambiencemod.ambience.sounds.FootstepsAmbient;
import necesse.entity.mobs.Mob;
import necesse.level.gameTile.*;
import necesse.level.maps.Level;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FootstepsManager {
    public HashMap<Class<? extends GameTile>, FootstepsAmbient> tileHashMap = new HashMap<>();
    public HashMap<Class<? extends GameTile>, Boolean> playerTiles = new HashMap<>();
    public HashMap<Mob, Float> footstepTime = new HashMap<>();
    public FootstepsGrass grassy;
    public FootstepsStone stony;
    public FootstepsWater watery;
    public FootstepsSand sandy;
    public FootstepsWood woody;
    public FootstepsMud muddy;
    public FootstepsSnow snowy;

    public FootstepsManager() {
        // GRASSY
        this.grassy = new FootstepsGrass();
        tileHashMap.put(GrassTile.class, grassy);
        tileHashMap.put(OvergrownPlainsGrassTile.class, grassy);
        tileHashMap.put(SimpleTerrainTile.class, grassy);
        tileHashMap.put(SwampGrassTile.class, grassy);
        tileHashMap.put(PlainsGrassTile.class, grassy);

        // STONY
        this.stony = new FootstepsStone();
        tileHashMap.put(CrystalTile.class, stony);
        tileHashMap.put(DeepRockTile.class, stony);
        tileHashMap.put(DeepSandstoneTile.class, stony);
        tileHashMap.put(DeepSnowRockTile.class, stony);
        tileHashMap.put(DeepSwampRockTile.class, stony);
        tileHashMap.put(DungeonFloorTile.class, stony);
        tileHashMap.put(EmptyTile.class, stony);
        tileHashMap.put(IceTile.class, stony);
        tileHashMap.put(DeepIceTile.class, stony);
        tileHashMap.put(MoonPath.class, stony);
        tileHashMap.put(RockTile.class, stony);
        tileHashMap.put(SandBrickTile.class, stony);
        tileHashMap.put(SandstoneTile.class, stony);
        tileHashMap.put(SnowRockTile.class, stony);
        tileHashMap.put(SpiderNestTile.class, stony);
        tileHashMap.put(SwampRockTile.class, stony);

        this.woody = new FootstepsWood();
        playerTiles.put(SimpleFloorTile.class, true);
        playerTiles.put(SimpleTiledFloorTile.class, true);
        playerTiles.put(EdgedTiledTexture.class, true);
        playerTiles.put(PathTiledTile.class, true);

        // WATER
        this.watery = new FootstepsWater();
        tileHashMap.put(LiquidTile.class, watery);
        tileHashMap.put(WaterTile.class, watery);

        // SANDY/GRAVELLY
        this.sandy = new FootstepsSand();
        tileHashMap.put(SandTile.class, sandy);
        tileHashMap.put(CrystalGravelTile.class, sandy);
        tileHashMap.put(GravelTile.class, sandy);
        tileHashMap.put(SandGravelTile.class, sandy);

        // SNOWY
        this.snowy = new FootstepsSnow();
        tileHashMap.put(SnowTile.class, snowy);

        // MUDDY
        this.muddy = new FootstepsMud();
        tileHashMap.put(MudTile.class, muddy);
        tileHashMap.put(SlimeLiquidTile.class, muddy);
        tileHashMap.put(FarmlandTile.class, muddy);
    }

    private void validateFootstepTime() throws ConcurrentModificationException {
        // FIXME: This throws a CME. I should probably fix it in the long run,
        // FIXME: but I kinda don't care enough to ATM. -tbsj
        final float cleanupTime = 10.0f;
        final float timeNow = AmbientManager.getTimeSecs();

        Iterator<Map.Entry<Mob, Float>> iterator = this.footstepTime.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Mob, Float> entry = iterator.next();
            Mob mob = entry.getKey();
            float time = entry.getValue();
            boolean oldEnough = timeNow - time > cleanupTime;
            if (oldEnough || mob == null || mob.isDisposed()) {
                iterator.remove();
            }
        }
    }

    public void onGameSecondTick() {
        try {
            this.validateFootstepTime();
        } catch (ConcurrentModificationException e) {
            System.err.println("[Ambience] CME thrown...");
        }
    }

    public void onMobTick(Mob mob) {
        if (mob == null) return;
        if (mob.isFlying()) return;

        final float mobSpeedPct = AmbientManager.getMobSpeedPct(mob);
        if (mobSpeedPct < 0.1f) return;

        float seconds = AmbientManager.getTimeSecs();
        // ideal rate is 3 steps/sec for PlayerMob, but may be higher or lower depending on speed
        float speedFactorSecs = 0.33f / mobSpeedPct;
        float lastTimeSecs = this.footstepTime.getOrDefault(mob, 0.0f);
        float timeSinceSecs = seconds - lastTimeSecs;

        if (timeSinceSecs < speedFactorSecs) return;

        this.footstepTime.put(mob, seconds);
        this.onFootstep(mob);
    }

    public void onFootstep(Mob mob) {
        Level level = mob.getLevel();
        if (level == null) return;

        GameTile tile = level.getTile(mob.getTileX(), mob.getTileY());
        Class<? extends GameTile> tclass = tile.getClass();

        FootstepsAmbient match = this.tileHashMap.get(tclass);
        Boolean isPlayerTile = this.playerTiles.getOrDefault(tclass, false);

//        if (mob.isPlayer) {
//            System.out.println("Player tile name: " + tile.getDisplayName() + " | " + tile.getClass().getName());
//        }

        if (match == null) {
            if (isPlayerTile != true) return;
            this.onPlayerTile(mob, tile);
            return;
        }

        match.playSound(
                mob.x,
                mob.y
        );
    }

    public void onPlayerTile(Mob mob, GameTile tile) {
        // name is like 'tile.woodfloor' or 'tile.woodpathtile'
        String name = tile.getStringID();

        if (name.contains("wood")) {
            this.woody.playSound(mob.x, mob.y);
            return;
        }

        // ... maybe more to come, but default to stony.

        this.stony.playSound(mob.x, mob.y);
    }

}
