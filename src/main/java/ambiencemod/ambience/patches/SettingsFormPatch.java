package ambiencemod.ambience.patches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.gfx.forms.components.FormComponent;
import necesse.gfx.forms.components.FormContentBox;
import necesse.gfx.forms.components.FormFlow;
import necesse.gfx.forms.components.FormSlider;
import necesse.gfx.forms.components.localComponents.FormLocalSlider;
import necesse.gfx.forms.events.FormEventListener;
import necesse.gfx.forms.events.FormInputEvent;
import necesse.gfx.forms.position.FormPositionContainer;
import necesse.gfx.forms.presets.SettingsForm;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = SettingsForm.class, name = "updateSoundForm", arguments = {})
public class SettingsFormPatch {
    public static FormLocalSlider ambienceSlider;
    public static FormLocalSlider footstepsSlider;
    public static FormEventListener<FormInputEvent<FormSlider>> onCustomSliderChanged = e -> {
        System.out.println("Changed custom value to " + e.from.getValue());
    };

    @Advice.OnMethodExit
    static void onExit(
            @Advice.FieldValue("soundContent") FormContentBox soundContent,
            @Advice.FieldValue("musicVolume") FormLocalSlider musicVolume,
            @Advice.FieldValue(value = "soundContentHeight", readOnly = false) int soundContentHeight
    ) {

        // Create the custom slider (set the Y value later)
        ambienceSlider = soundContent.addComponent(new FormLocalSlider(
                "settingsui",
                "ambiencevolume",
                10,
                0,
                50,
                0,
                100,
                soundContent.getWidth() - 20
        ));
        ambienceSlider.onChanged(onCustomSliderChanged); // Can't use lambdas inside patch methods


        // Create the custom slider (set the Y value later)
        footstepsSlider = soundContent.addComponent(new FormLocalSlider(
                "settingsui",
                "footstepsvolume",
                10,
                0,
                50,
                0,
                100,
                soundContent.getWidth() - 20
        ));
        footstepsSlider.onChanged(onCustomSliderChanged); // Can't use lambdas inside patch methods

        int addedHeight = ambienceSlider.getTotalHeight() + footstepsSlider.getTotalHeight() + 30;

        // Move everything underneath the "Music Volume" element down to make room for the new sliders
        for (FormComponent component : soundContent.getComponents()) {
            if (!(component instanceof FormPositionContainer)) {
                continue;
            }
            FormPositionContainer comp = (FormPositionContainer) component;
            if (comp.getY() > musicVolume.getY()) {
                comp.setY(comp.getY() + addedHeight);
            }
        }

        // Move the new slider into position and update the content height
        int maxY = (int) musicVolume.getBoundingBox().getMaxY();
        ambienceSlider.setY(maxY + 15);
        footstepsSlider.setY(maxY + ambienceSlider.getTotalHeight() + 30);

        // Adjust original content height to account for changes
        soundContentHeight += addedHeight;
    }
}