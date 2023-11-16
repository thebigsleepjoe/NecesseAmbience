package ambiencemod.ambience.sounds.environments;

import java.util.HashMap;
import ambiencemod.ambience.sounds.AmbientManager;
import ambiencemod.ambience.sounds.GlobalAmbient;
import ambiencemod.ambience.sounds.PositionalAmbient;
import necesse.entity.mobs.PlayerMob;
import necesse.level.maps.biomes.Biome;

public abstract class Environment {
    // A pretty name for this environment.
    public String name;
    // The biomes this environment applies to.
    private HashMap<Class<? extends Biome>, Boolean> biomes = new HashMap<Class<? extends Biome>, Boolean>();
    // The constant ambient tracks that can play in this environment.
    private GlobalAmbient constantAmbience;
    // The occasional (global) ambient tracks that can play in this environment.
    private GlobalAmbient occasionalAmbience;
    // The positional ambience tracks that can at random positions around the player in this environment.
    private PositionalAmbient positionalAmbience;

    public Environment(String name) {
        this.name = name;
    }

    public void addBiome(Class<? extends Biome> biome) {
        biomes.put(biome, true);
    }

    public boolean hasBiome(Class<? extends Biome> biome) {
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

    public boolean isPlayerInEnvironment() {
        PlayerMob playerMob = AmbientManager.getLocalPlayer();
        if (playerMob == null || playerMob.isDisposed())
            return false;
        
        Class<? extends Biome> biome = playerMob.getLevel().biome.getClass();
        return hasBiome(biome);
    }

    public void manageEnvironmentIfApplicable() {
        if (isPlayerInEnvironment()) {
            playConstantAmbience();
            playOccasionalAmbience();
            playPositionalAmbience();
        }
    }
}
