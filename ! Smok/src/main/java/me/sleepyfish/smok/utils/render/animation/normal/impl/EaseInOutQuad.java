package me.sleepyfish.smok.utils.render.animation.normal.impl;

import me.sleepyfish.smok.utils.render.animation.normal.Animation;

// Class from SMok Client by SleepyFish
public class EaseInOutQuad extends Animation {

    public EaseInOutQuad(int ms, double endPoint) {
        super(ms, endPoint);
    }

    @Override
    protected double getEquation(double x) {
        double x1 = x / (double) this.duration;
        return x1 < 0.5 ? 2.0 * Math.pow(x1, 2.0) : 1.0 - Math.pow(-2.0 * x1 + 2.0, 2.0) / 2.0;
    }

}