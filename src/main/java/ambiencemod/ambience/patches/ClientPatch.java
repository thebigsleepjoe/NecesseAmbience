package ambiencemod.ambience.patches;

import ambiencemod.AmbientMod;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.client.Client;
import necesse.entity.mobs.PlayerMob;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.utility.nullability.MaybeNull;

@ModMethodPatch(target = Client.class, name = "tick", arguments = {})
public class ClientPatch {
    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    static boolean onEnter(@Advice.This Client client) {
        PlayerMob mob = client.getPlayer();
        AmbientMod.ambientManager.onMobTick(mob);
        AmbientMod.ambientManager.onTick();
        return false; // return false to not disrupt other code execution
    }

}
