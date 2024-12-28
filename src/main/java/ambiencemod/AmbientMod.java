package ambiencemod;

import ambiencemod.ambience.patches.SettingsFormPatch;
import ambiencemod.ambience.sounds.AmbientManager;
import necesse.engine.GlobalData;
import necesse.engine.modLoader.annotations.ModEntry;

@ModEntry
public class AmbientMod {
    public static AmbientManager ambientManager;
    private static boolean isDisabled = false;
    private boolean hasWarned = false;

    private void warn() {
        if (hasWarned) return;
        System.err.println("[NecesseAmbience] This mod is NOT intended for dedicated servers. It will now attempt to self-disable, but for your sake, please do not put this on a server! To use, please install the mod on your local machine and join the server on your modded client.");
        this.hasWarned = true;
    }

    public static boolean shouldDisable() {
        return (isDisabled || GlobalData.isServer());
    }

    public void init() {
        if (this.shouldDisable()) {
            this.warn();
            isDisabled = true;
            return;
        }
        SettingsFormPatch.loadCustomSettings();
        System.out.println("[NecesseAmbience] Initialized");
    }

    public void initResources() {
        if (this.shouldDisable()) {
            this.warn();
            isDisabled = true;
            return;
        }
        ambientManager = new AmbientManager(); // This will also load our resources.
    }

    public void postInit() {
    }

}
