package ambiencemod.ambience.sounds;

public enum SoundChance {
    ALWAYS(1.0f),
    OFTEN(0.75f),
    SOMETIMES(0.5f),
    RARELY(0.25f),
    NEVER(0.0f);

    private final float chance;

    SoundChance(float chance) {
        this.chance = chance;
    }

    public float getChance() {
        return chance;
    }
}
