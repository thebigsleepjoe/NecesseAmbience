package ambiencemod.ambience.sounds;

public class WindAmbient extends GlobalAmbient{

    public WindAmbient() {
        super();

        this.setVolume(0.2f);
        this.setWaitUntilDone(true);
        this.setPitchRange(0.9f, 1.4f);
        this.setFadeInTime(1.0f);
        this.setTimeBetweenRepeats(5.0f);

        this.addSoundPath("global/Wind1.ogg");
    }
}
