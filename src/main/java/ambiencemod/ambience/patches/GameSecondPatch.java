package ambiencemod.ambience.patches;

import ambiencemod.AmbientMod;
import ambiencemod.ambience.sounds.AmbientManager;
import necesse.engine.GameWindow;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = GameWindow.class, name = "endSceneDraw", arguments = {})
public class GameSecondPatch {
    public static long lastExecMS = 0;
    @Advice.OnMethodEnter()
    static boolean onEnter(@Advice.This GameWindow window) {
        long curTime = System.currentTimeMillis();
        if (curTime - lastExecMS > 1000) {
            lastExecMS = curTime;
            if (AmbientManager.isInGame()) AmbientMod.ambientManager.onGameSecondTick();
        }
        return false; // return false to not disrupt other code execution
    }

}
