package ambiencemod.ambience.sounds.environments;

import java.util.HashMap;
import ambiencemod.ambience.sounds.AmbientManager;
import ambiencemod.ambience.sounds.GlobalAmbient;
import ambiencemod.ambience.sounds.PositionalAmbient;
import necesse.entity.mobs.PlayerMob;
import necesse.level.maps.biomes.Biome;

public abstract class Environment {
    /** A pretty name for this environment. */
    public String name;
    /** The biomes this environment applies to. */
    private HashMap<Class<? extends Biome>, Boolean> biomes = new HashMap<Class<? extends Biome>, Boolean>();
    /** The constant ambient tracks that can play in this environment. */
    private GlobalAmbient background;
    /** Day/night ambience */
    private GlobalAmbient dayAmbient;
    private GlobalAmbient nightAmbient;
    /** The positional ambience tracks that can at random positions around the player in this environment. */
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

    public void setBackground(GlobalAmbient constantAmbience) {
        this.background = constantAmbience;
    }

    public void setDayAmbience(GlobalAmbient dayAmbience) {
        this.dayAmbient = dayAmbience;
    }

    public void setNightAmbience(GlobalAmbient nightAmbience) {
        this.nightAmbient = nightAmbience;
    }

    public void setPositionalAmbience(PositionalAmbient positionalAmbience) {
        this.positionalAmbience = positionalAmbience;
    }

    public void playConstantAmbience() {
        if (background != null) {
            background.playSound();
        }
    }

    public boolean isDay() {
        PlayerMob playerMob = AmbientManager.getLocalPlayer();
        if (playerMob == null || playerMob.isDisposed())
            return false;
        return !playerMob.getWorldEntity().isNight();
    }

    public boolean isNight() {
        return !isDay();
    }

    public void playTimeSpecificAmbience() {
        if (isDay()) {
            if (dayAmbient != null) {
                dayAmbient.playSound();
            }
        } else if (isNight()) {
            if (nightAmbient != null) {
                nightAmbient.playSound();
            }
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

    /**
     * Play all sounds that are applicable to this environment.
     */
    public void playEnvironmentSoundsIfApplicable() {
        if (isPlayerInEnvironment()) {
            playConstantAmbience();
            playTimeSpecificAmbience();
            playPositionalAmbience();
        }
    }
}
