package ambiencemod.ambience;

import ambiencemod.AmbienceMod;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.entity.mobs.PlayerMob;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = PlayerMob.class, name = "serverTick", arguments = {})
public class PlayerTickPatch {

    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    static boolean onEnter(@Advice.This PlayerMob playerMob) {
        AmbienceMod.ambientManager.onPlayerTick(playerMob);
        return false; // return false to not disrupt other code execution
    }

}
