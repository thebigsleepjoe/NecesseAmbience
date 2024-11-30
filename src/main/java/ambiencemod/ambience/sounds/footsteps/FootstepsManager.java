package ambiencemod.ambience.sounds.footsteps;

import ambiencemod.ambience.sounds.FootstepsAmbient;
import necesse.entity.mobs.Mob;
import necesse.level.gameTile.*;

import java.util.HashMap;

public class FootstepsManager {
    HashMap<Class<? extends GameTile>, FootstepsAmbient> tileHashMap = new HashMap<>();

    public FootstepsManager() {
        // GRASSY
        FootstepsGrass grassy = new FootstepsGrass();
        tileHashMap.put(GrassTile.class, grassy);
        tileHashMap.put(OvergrownPlainsGrassTile.class, grassy);
        tileHashMap.put(FarmlandTile.class, grassy);
        tileHashMap.put(SimpleTerrainTile.class, grassy);
        tileHashMap.put(SwampGrassTile.class, grassy);
        tileHashMap.put(PlainsGrassTile.class, grassy);

        // STONY
        FootstepsStone stony = new FootstepsStone();
        tileHashMap.put(DeepRockTile.class, stony);
        tileHashMap.put(DeepSandstoneTile.class, stony);
        tileHashMap.put(DeepSnowRockTile.class, stony);
        tileHashMap.put(DeepSwampRockTile.class, stony);
        tileHashMap.put(DungeonFloorTile.class, stony);
        tileHashMap.put(EdgedTiledTexture.class, stony);
        tileHashMap.put(EmptyTile.class, stony);
        tileHashMap.put(IceTile.class, stony);
        tileHashMap.put(DeepIceTile.class, stony);
        tileHashMap.put(MoonPath.class, stony);
        tileHashMap.put(RockTile.class, stony);
        tileHashMap.put(SandBrickTile.class, stony);
        tileHashMap.put(SandstoneTile.class, stony);
        tileHashMap.put(SimpleFloorTile.class, stony);
        tileHashMap.put(SimpleTiledFloorTile.class, stony);
        tileHashMap.put(SnowRockTile.class, stony);
        tileHashMap.put(SpiderNestTile.class, stony);
        tileHashMap.put(SwampRockTile.class, stony);
    }

    public void onFootstep(Mob mob) {
        GameTile tile = mob.getLevel().getTile(mob.getTileX(), mob.getTileY());
        FootstepsAmbient match = this.tileHashMap.get(tile.getClass());

        if (match == null) return; // No tile

        match.playSound(
                mob.x,
                mob.y
        );
    }

}
