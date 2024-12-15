package ambiencemod.ambience.sounds.global;

import ambiencemod.ambience.sounds.GlobalAmbient;
import necesse.entity.mobs.PlayerMob;
import necesse.level.maps.Level;

public class WindAmbient extends GlobalAmbient {

    public WindAmbient() {
        super();

        this.setMaxVolume(0.15f);
        this.setWaitUntilDone(true);
        this.setPitchRange(0.8f, 1.3f);
        this.setFadeInTime(2.0f);
        this.setTimeBetweenRepeats(5.0f);

        this.addSoundPath("global/Wind1.ogg");
        this.addSoundPath("global/Wind2.ogg");
    }

    public boolean canRun(PlayerMob ply) {
        Level lvl = ply.getLevel();

        if (lvl == null) return false;

        return (!lvl.isCave);
        // if (!lvl.isOutside(ply.getTileX(), ply.getTileY())) return false;
    }
}
