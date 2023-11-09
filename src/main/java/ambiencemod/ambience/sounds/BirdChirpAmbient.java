package ambiencemod.ambience.sounds;

public class BirdChirpAmbient extends GlobalAmbient {

    public BirdChirpAmbient() {
        super();

        this.setVolume(0.2f);
        this.setWaitUntilDone(true);
        this.setPitchRange(0.7f, 1.3f);
        this.setFadeInTime(1.0f);
        this.setTimeBetweenRepeats(0.0f);

        this.addSoundPath("global/BirdChirp1.ogg");
    }

}
