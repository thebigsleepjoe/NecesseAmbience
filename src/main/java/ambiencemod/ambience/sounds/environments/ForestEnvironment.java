package ambiencemod.ambience.sounds.environments;

import ambiencemod.ambience.sounds.global.BirdChirpAmbient;
import ambiencemod.ambience.sounds.global.WindAmbient;
import necesse.level.maps.biomes.forest.ForestBiome;

public class ForestEnvironment extends Environment {
    /**
     * An ensemble of birds chirping, and wind blowing, complimented by crickets chirping at night.
     */
    public ForestEnvironment() {
        super("Forest");

        // Biomes
        addBiome(ForestBiome.class);

        // Sounds
        WindAmbient windAmbient = new WindAmbient();
        BirdChirpAmbient birdChirpAmbient = new BirdChirpAmbient();

        setBackground(windAmbient);
        setDayAmbience(birdChirpAmbient);
    }
}
