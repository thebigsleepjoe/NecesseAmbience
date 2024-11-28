package ambiencemod.ambience.sounds.global;

import ambiencemod.ambience.sounds.GlobalAmbient;
import necesse.entity.mobs.PlayerMob;
import necesse.level.maps.Level;
import necesse.level.maps.biomes.forest.ForestBiome;
import necesse.level.maps.biomes.forest.ForestVillageBiome;
import necesse.level.maps.biomes.forest.PlainsVillageBiome;
import necesse.level.maps.biomes.pirate.PirateVillageBiome;
import necesse.level.maps.biomes.plains.PlainsBiome;
import necesse.level.maps.biomes.swamp.SwampBiome;

public class BirdChirpAmbient extends GlobalAmbient {

    public BirdChirpAmbient() {
        super();

        this.biomes.add(ForestBiome.class);
        this.biomes.add(ForestVillageBiome.class);
        this.biomes.add(PlainsVillageBiome.class);
        this.biomes.add(PirateVillageBiome.class);
        this.biomes.add(PlainsBiome.class);
        this.biomes.add(SwampBiome.class);

        this.setVolume(0.2f);
        this.setWaitUntilDone(true);
        this.setPitchRange(0.7f, 1.3f);
        this.setFadeInTime(1.0f);
        this.setTimeBetweenRepeats(0.0f);

        this.addSoundPath("global/BirdChirp1.ogg");
    }

    public boolean canRun(PlayerMob ply) {
        Level lvl = ply.getLevel();

        if (lvl.isCave) return false;
        if (lvl.getWorldEntity().isNight()) return false;
        // if (!lvl.isOutside(ply.getTileX(), ply.getTileY())) return false;
        if (!this.isInBiomes(ply)) return false;

        return true;
    }

}
