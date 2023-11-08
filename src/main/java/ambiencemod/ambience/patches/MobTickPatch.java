package ambiencemod.ambience.patches;

import ambiencemod.AmbienceMod;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = Mob.class, name = "serverTick", arguments = {})
public class MobTickPatch {

    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    static boolean onEnter(@Advice.This Mob mob) {
        AmbienceMod.ambientManager.onMobTick(mob);
        return false; // return false to not disrupt other code execution
    }

}
