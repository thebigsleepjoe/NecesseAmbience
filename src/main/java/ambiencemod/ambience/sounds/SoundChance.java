package ambiencemod.ambience.sounds;

public enum SoundChance {
    ALWAYS(1.0f),
    VERY_OFTEN(0.85f),
    OFTEN(0.75f),
    FREQUENT(0.5f),
    RARELY(0.25f),
    ALMOST_NEVER(0.1f),
    NEVER(0.0f);

    private final float chance;

    SoundChance(float chance) {
        this.chance = chance;
    }

    public float getChance() {
        return chance;
    }
}
