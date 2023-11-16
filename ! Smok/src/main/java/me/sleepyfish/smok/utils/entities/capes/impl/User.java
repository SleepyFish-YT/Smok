package me.sleepyfish.smok.utils.entities.capes.impl;

import java.util.UUID;
import net.minecraft.util.ResourceLocation;

public class User {
    public UUID uuid;
    public ResourceLocation location;

    public User(UUID uuid, ResourceLocation location) {
        this.uuid = uuid;
        this.location = location;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public ResourceLocation getLocation() {
        return this.location;
    }

}