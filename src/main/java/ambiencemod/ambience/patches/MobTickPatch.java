package ambiencemod.ambience.patches;

import ambiencemod.AmbientMod;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.entity.mobs.Mob;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = Mob.class, name = "serverTick", arguments = {})
public class MobTickPatch {

    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    static boolean onEnter(@Advice.This Mob mob) {
        if (AmbientMod.shouldDisable()) return false;
        AmbientMod.ambientManager.onMobTick(mob);
        return false; // return false to not disrupt other code execution
    }

}
