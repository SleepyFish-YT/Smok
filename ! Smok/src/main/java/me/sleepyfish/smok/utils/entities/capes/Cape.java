package me.sleepyfish.smok.utils.entities.capes;

import net.minecraft.util.ResourceLocation;

// Class from SMok Client by SleepyFish
public class Cape {

    private final String name;
    private final ResourceLocation file;
    private final ResourceLocation preview;

    public Cape(String name, ResourceLocation file, ResourceLocation preview) {
        this.name = name;
        this.file = file;
        this.preview = preview;
    }

    public static Cape fromName(String name) {
        return null;
    }

    public String getName() {
        return name;
    }

    public ResourceLocation getFile() {
        return file;
    }

    public ResourceLocation getPreview() {
        return preview;
    }

}