package ambiencemod.ambience.patches;

import ambiencemod.ambience.sounds.AmbientManager;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.gfx.forms.presets.PauseMenuForm;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = PauseMenuForm.class, name = "setupMainForm", arguments = {})
public class PauseMenuPatch {
    @Advice.OnMethodExit
    static void onExit() {
//        AmbientManager.stopGlobalTracks();
        System.out.println("Called setupMainForm");
    }
}