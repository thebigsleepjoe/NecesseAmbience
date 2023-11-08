package ambiencemod.ambience.patches;

import ambiencemod.AmbienceMod;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = PlayerMob.class, name = "serverTick", arguments = {})
public class PlayerTickPatch {

    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    static boolean onEnter(@Advice.This PlayerMob mob) {
        AmbienceMod.ambientManager.onMobTick(mob);
        return false; // return false to not disrupt other code execution
    }

}
