package ambiencemod.ambience.patches;

import ambiencemod.AmbientMod;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.entity.mobs.PlayerMob;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = PlayerMob.class, name = "clientTick", arguments = {})
public class PlayerTickPatch {

    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    static boolean onEnter(@Advice.This PlayerMob mob) {
        AmbientMod.ambientManager.onMobTick(mob);
        return false; // return false to not disrupt other code execution
    }

}
