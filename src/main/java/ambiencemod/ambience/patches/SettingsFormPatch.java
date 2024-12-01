package ambiencemod.ambience.patches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.gfx.forms.components.FormContentBox;
import necesse.gfx.forms.components.FormFlow;
import necesse.gfx.forms.components.localComponents.FormLocalSlider;
import necesse.gfx.forms.presets.SettingsForm;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = SettingsForm.class, name = "updateSoundHeight", arguments = {})
public class SettingsFormPatch {
    private static FormLocalSlider ambienceVolume;
    private static FormLocalSlider footstepVolume;

    @Advice.OnMethodEnter
    static boolean onEnter(@Advice.This SettingsForm form, @Advice.FieldValue FormContentBox soundContent, @Advice.FieldValue FormFlow soundFlow) {
        soundFlow.next(2);
        SettingsFormPatch.ambienceVolume = (FormLocalSlider)soundContent.addComponent((FormLocalSlider)soundFlow.nextY(new FormLocalSlider("settingsui", "ambiencevolume", 10, 0, 50, 0, 100, soundContent.getWidth() - 20), 15));
        SettingsFormPatch.ambienceVolume.onChanged((e) -> {
//            Settings.musicVolume = ((FormSlider)e.from).getPercentage();
//            SoundManager.updateVolume();
//            form.setSaveActive(true);
            System.out.println(e.from.getPercentage());
        });

        System.out.println(soundContent.equals(null)+ "|" + soundFlow.equals(null));
        System.out.println("onEnter!!!");

        form.updateSoundHeight();

        return false;
    }

    @Advice.OnMethodExit
    static void onExit(@Advice.This SettingsForm form, @Advice.FieldValue FormContentBox soundContent, @Advice.FieldValue FormFlow soundFlow) {
        System.out.println("onExit!!!!!");
    }

}
