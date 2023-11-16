package me.sleepyfish.smok.utils.render.animation.simple;

import me.sleepyfish.smok.rats.impl.visual.GuiModule;
import me.sleepyfish.smok.utils.render.animation.normal.Animation;
import me.sleepyfish.smok.utils.render.animation.normal.impl.EaseInOutQuad;
import me.sleepyfish.smok.utils.render.animation.normal.impl.DecelerateAnimation;

// Class from SMok Client by SleepyFish
public class AnimationUtils {

    public static float calculateCompensation(float target, float current, double speed, long delta) {
        float diff = current - target;
        double add = (double) delta * (speed / 50.0);

        if ((double) diff > speed) {
            if ((double) current - add > (double) target) {
                current = (float) ((double) current - add);
            } else {
                current = target;
            }
        } else if ((double) diff < -speed) {
            if ((double) current + add < (double) target) {
                current = (float) ((double) current + add);
            } else {
                current = target;
            }
        } else {
            current = target;
        }

        return current;
    }

    public static Animation getAnimation() {
        switch (GuiModule.animationMode.getMode().name()) {
            case "Centered":
                return new EaseInOutQuad(450, 1.0);

            case "Decelerate":
                return new DecelerateAnimation(450, 1.0);

            case "None":
                return new EaseInOutQuad(1, 1.0);
        }

        return null;
    }

}