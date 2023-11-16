package me.sleepyfish.smok.utils.anticheat.impl;

import me.sleepyfish.smok.utils.anticheat.Check;
import net.minecraft.entity.player.EntityPlayer;

// Class from SMok Client by SleepyFish
public class Invalid extends Check {

    public Invalid() {
        super("Invalid Rotation", "Checks invalid rotations", 1.0);
    }

    public void checkTick(EntityPlayer player) {
        if (Math.abs(player.rotationPitch) > 90.0F) {
            this.fail(player);
        }
    }

}