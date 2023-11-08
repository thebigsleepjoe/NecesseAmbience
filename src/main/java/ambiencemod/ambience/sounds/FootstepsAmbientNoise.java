package ambiencemod.ambience.sounds;

public class FootstepsAmbientNoise extends PositionalAmbientNoise {
    public FootstepsAmbientNoise() {
        super(SoundChance.OFTEN, 1.0f, 0.8f, 1.3f);

        this.addSoundPath("footsteps/DirtFootstep1.ogg");
    }
}
