package ambiencemod.ambience.sounds.environments;

import java.util.ArrayList;
import java.util.HashMap;

import ambiencemod.ambience.sounds.GlobalAmbient;
import ambiencemod.ambience.sounds.PositionalAmbient;
import necesse.engine.util.GameRandom;

public abstract class Environment {
    // A pretty name for this environment.
    public String name;
    // The biomes this environment applies to.
    private HashMap<String, Boolean> biomes = new HashMap<String, Boolean>();
    // The constant ambient tracks that can play in this environment.
    private GlobalAmbient constantAmbience;
    // The occasional (global) ambient tracks that can play in this environment.
    private GlobalAmbient occasionalAmbience;
    // The positional ambience tracks that can at random positions around the player in this environment.
    private PositionalAmbient positionalAmbience;

    public Environment(String name) {
        this.name = name;
    }

    public void addBiome(String biome) {
        biomes.put(biome, true);
    }

    public boolean hasBiome(String biome) {
        return biomes.containsKey(biome);
    }

    public void setConstantAmbience(GlobalAmbient constantAmbience) {
        this.constantAmbience = constantAmbience;
    }

    public void setOccasionalAmbience(GlobalAmbient occasionalAmbience) {
        this.occasionalAmbience = occasionalAmbience;
    }

    public void setPositionalAmbience(PositionalAmbient positionalAmbience) {
        this.positionalAmbience = positionalAmbience;
    }

    public void playConstantAmbience() {
        if (constantAmbience != null) {
            constantAmbience.playSound();
        }
    }

    public void playOccasionalAmbience() {
        if (occasionalAmbience != null) {
            occasionalAmbience.playSound();
        }
    }

    public void playPositionalAmbience() {
        if (positionalAmbience != null) {
            positionalAmbience.playSound();
        }
    }
}
