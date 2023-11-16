package me.sleepyfish.smok.utils.entities;

import me.sleepyfish.smok.Smok;

public class SoundUtils {

    static boolean allow = false;

    public static void inject() {
        allow = true;
    }

    public static void unInject() {
        allow = false;
    }

    public static void playSound(String name, float volume, float pitch) {
        if (allow) Smok.inst.mc.thePlayer.playSound(name, volume, pitch);
    }

    public static void playEnableSound() {
        playSound("random.click", 1.0F, 0.8F);
    }

    public static void playDisableSound() {
        playSound("random.click", 1.0F, 0.7F);
    }

}