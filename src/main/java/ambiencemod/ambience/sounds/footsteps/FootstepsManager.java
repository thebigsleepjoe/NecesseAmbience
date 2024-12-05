package ambiencemod.ambience.sounds.footsteps;

import ambiencemod.ambience.sounds.FootstepsAmbient;
import necesse.engine.localization.message.GameMessage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.level.gameTile.*;

import java.util.HashMap;

public class FootstepsManager {
    public HashMap<Class<? extends GameTile>, FootstepsAmbient> tileHashMap = new HashMap<>();
    public HashMap<Class<? extends GameTile>, Boolean> playerTiles = new HashMap<>();
    public FootstepsGrass grassy;
    public FootstepsStone stony;
    public FootstepsWater watery;
    public FootstepsSand sandy;
    public FootstepsWood woody;

    public FootstepsManager() {
        // GRASSY
        this.grassy = new FootstepsGrass();
        tileHashMap.put(GrassTile.class, grassy);
        tileHashMap.put(OvergrownPlainsGrassTile.class, grassy);
        tileHashMap.put(FarmlandTile.class, grassy);
        tileHashMap.put(SimpleTerrainTile.class, grassy);
        tileHashMap.put(SwampGrassTile.class, grassy);
        tileHashMap.put(PlainsGrassTile.class, grassy);

        // STONY
        this.stony = new FootstepsStone();
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
    }

    public void onFootstep(Mob mob) {
        GameTile tile = mob.getLevel().getTile(mob.getTileX(), mob.getTileY());
        Class<? extends GameTile> tclass = tile.getClass();

        FootstepsAmbient match = this.tileHashMap.get(tclass);
        Boolean isPlayerTile = this.playerTiles.get(tclass);

        if (mob.isPlayer) {
            System.out.println("Player tile name: " + tile.getDisplayName() + " | " + tile.getClass().getName());
        }

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
