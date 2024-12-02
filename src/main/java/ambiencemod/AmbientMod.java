package ambiencemod;

import ambiencemod.ambience.patches.SettingsFormPatch;
import ambiencemod.ambience.sounds.AmbientManager;
import necesse.engine.modLoader.annotations.ModEntry;

@ModEntry
public class AmbientMod {
    public static AmbientManager ambientManager;

    public void init() {
        System.out.println("AmbienceMod initialized");
        SettingsFormPatch.loadCustomSettings();
    }

    public void initResources() {
        ambientManager = new AmbientManager(); // This will also load our resources.
    }

    public void postInit() {
    }

}
