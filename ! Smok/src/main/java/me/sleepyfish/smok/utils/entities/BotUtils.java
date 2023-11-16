package me.sleepyfish.smok.utils.entities;

import net.minecraft.entity.Entity;

// Class from SMok Client by SleepyFish
public class BotUtils {

    public static boolean isBot(Entity target) {
        return target.getUniqueID().version() == 2
            || target.ticksExisted > 99999
            || target.getName().contains("-")
            || target.getName().contains("[")
            || target.getName().contains("]")
            || target.getName().contains(" ")
            || target.getName().length() <= 2;
    }

}