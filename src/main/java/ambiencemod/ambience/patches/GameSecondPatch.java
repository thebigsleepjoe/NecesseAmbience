package ambiencemod.ambience.patches;

import ambiencemod.AmbientMod;
import ambiencemod.ambience.sounds.AmbientManager;
import necesse.engine.gameLoop.ClientGameLoop;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import net.bytebuddy.asm.Advice;

// hook onto client game's update function to tick ambience

@ModMethodPatch(target = ClientGameLoop.class, name = "update", arguments = {})
public class GameSecondPatch {
    public static long lastExecMS = 0;
    @Advice.OnMethodEnter()
    static boolean onEnter() {
        if (AmbientMod.shouldDisable()) return false;
        AmbientManager.tick++;
        long curTime = System.currentTimeMillis();
        if (curTime - lastExecMS > 1000) {
            lastExecMS = curTime;
            if (AmbientManager.isInGame()) AmbientMod.ambientManager.onGameSecondTick();
        }
        return false; // return false to not disrupt other code execution
    }

}
