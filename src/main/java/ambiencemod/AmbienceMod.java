package ambiencemod;

import necesse.engine.modLoader.annotations.ModEntry;

@ModEntry
public class AmbienceMod {

    public void init() {
        System.out.println("Hello world from ambience!");
    }

    public void initResources() {
        // TODO: Load resources
        // ExampleMob.texture = GameTexture.fromFile("mobs/examplemob");
    }

    public void postInit() {
    }

}
