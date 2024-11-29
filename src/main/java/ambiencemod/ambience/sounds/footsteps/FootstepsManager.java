package ambiencemod.ambience.sounds.footsteps;

import ambiencemod.ambience.sounds.FootstepsAmbient;
import necesse.entity.mobs.Mob;
import necesse.level.gameTile.FarmlandTile;
import necesse.level.gameTile.GameTile;
import necesse.level.gameTile.GrassTile;

import java.util.HashMap;

public class FootstepsManager {
    HashMap<Class<? extends GameTile>, FootstepsAmbient> tileHashMap = new HashMap<>();

    public FootstepsManager() {
        // GRASSY
        FootstepsGrass grassy = new FootstepsGrass();
        tileHashMap.put(GrassTile.class, grassy);
        tileHashMap.put(FarmlandTile.class, grassy);
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
