package ambiencemod.ambience.sounds.global;

import ambiencemod.ambience.sounds.GlobalAmbient;
import necesse.entity.mobs.PlayerMob;
import necesse.level.maps.Level;

public class CaveAmbient extends GlobalAmbient {

    public CaveAmbient() {
        super();

        this.setVolume(1.0f);
        this.setWaitUntilDone(true);
        this.setPitchRange(0.9f, 1.4f);
        this.setFadeInTime(3.0f);
        this.setTimeBetweenRepeats(30.0f);

        this.addSoundPath("global/Cave-01.ogg");
        this.addSoundPath("global/Cave-02.ogg");
        this.addSoundPath("global/DeepCave-01.ogg");
        this.addSoundPath("global/DeepCave-02.ogg");
    }

    public boolean canRun(PlayerMob ply) {
        Level lvl = ply.getLevel();

        if (lvl == null) return false;

        return (lvl.isCave);
    }
}
