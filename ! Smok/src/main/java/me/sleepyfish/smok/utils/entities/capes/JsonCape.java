package me.sleepyfish.smok.utils.entities.capes;

import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.util.ResourceLocation;

public class JsonCape extends Cape {
    private final String displayName;
    private final String name;
    private final int frameNumber;
    private final ConcurrentLinkedQueue<ResourceLocation> locations;
    private final boolean animated;
    private final int delay;

    public JsonCape(String displayName, String name, int frameAmount, int delay, ConcurrentLinkedQueue<ResourceLocation> locations) {
        super(name, locations.peek(), locations.peek());
        this.displayName = displayName;
        this.name = name;
        this.frameNumber = frameAmount;
        this.locations = locations;
        this.animated = locations.size() >= 2;
        this.delay = delay;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getName() {
        return this.name;
    }

    public int getFrameNumber() {
        return this.frameNumber;
    }

    public ConcurrentLinkedQueue<ResourceLocation> getLocations() {
        return this.locations;
    }

    public boolean isAnimated() {
        return this.animated;
    }

    public int getDelay() {
        return this.delay;
    }

}