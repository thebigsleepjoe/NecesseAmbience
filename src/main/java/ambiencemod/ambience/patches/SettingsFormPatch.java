package ambiencemod.ambience.patches;

import necesse.engine.GlobalData;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;
import necesse.gfx.forms.components.FormComponent;
import necesse.gfx.forms.components.FormContentBox;
import necesse.gfx.forms.components.FormSlider;
import necesse.gfx.forms.components.localComponents.FormLocalSlider;
import necesse.gfx.forms.events.FormEventListener;
import necesse.gfx.forms.events.FormInputEvent;
import necesse.gfx.forms.position.FormPositionContainer;
import necesse.gfx.forms.presets.SettingsForm;
import net.bytebuddy.asm.Advice;

import java.io.File;

@ModMethodPatch(target = SettingsForm.class, name = "updateSoundForm", arguments = {})
public class SettingsFormPatch {
    public static FormLocalSlider ambienceSlider;
    public static FormLocalSlider footstepsSlider;

    public static float ambienceVolumePct = 0.5f;
    public static float footstepsVolumePct = 0.5f;

    public static FormEventListener<FormInputEvent<FormSlider>> onCustomSliderChanged = e -> {
        float pct = e.from.getPercentage();
        if (ambienceSlider.equals(e.from)) {
            ambienceVolumePct = pct;
        } else {
            footstepsVolumePct = pct;
        }

        saveCustomSettings();
    };

    public static SaveData getCustomSettings() {
        SaveData customSettings = new SaveData("AMBIENTSOUND");
        customSettings.addFloat("ambienceVolumePct", ambienceVolumePct);
        customSettings.addFloat("footstepsVolumePct", footstepsVolumePct);

        return customSettings;
    }

    public static String customSavePath() {
        return GlobalData.cfgPath() + "ambientsound.cfg";
    }

    public static void saveCustomSettings() {
        File file = new File(customSavePath());
        getCustomSettings().saveScript(file);
    }

    // safely wraps loadCustomData method
    public static void loadCustomSettings() {
        File file = new File(customSavePath());
        if (file.exists()) {
            loadCustomData(new LoadData(file));
        } else {
            System.out.println("Ambience: Error loading settings--file doesn't exist--some settings may be reset");
            saveCustomSettings();
        }
    }

    // loads data straight into the variables here
    public static void loadCustomData(LoadData data) {
        ambienceVolumePct = data.getFloat("ambienceVolumePct");
        footstepsVolumePct = data.getFloat("footstepsVolumePct");
    }

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
                (int) (ambienceVolumePct * 100f),
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
                (int) (footstepsVolumePct * 100f),
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