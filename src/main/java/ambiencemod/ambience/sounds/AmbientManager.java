package ambiencemod.ambience.sounds;

import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.PlayerMob;

public final class AmbientManager {
    FootstepsAmbientNoise footstepsAmbientNoise;

    public AmbientManager() {
        footstepsAmbientNoise = new FootstepsAmbientNoise();
    }

    public void onPlayerTick(PlayerMob playerMob) {
        // TODO: Implement this with footstep sounds.
    }
}

