package me.sleepyfish.smok.utils.render.animation.simple;

// Class from SMok Client by SleepyFish
public class SimpleAnimation {

    private float value;
    private long lastMS;

    public SimpleAnimation(float value) {
        this.value = value;
        this.lastMS = System.currentTimeMillis();
    }

    public void setAnimation(float value, double speed) {
        long currentMS = System.currentTimeMillis();
        long delta = currentMS - this.lastMS;
        this.lastMS = currentMS;
        double deltaValue = 0.0;

        if (speed > 28.0) {
            speed = 28.0;
        }

        if (speed != 0.0) {
            deltaValue = (double) (Math.abs(value - this.value) * 0.35F) / (10.0 / speed);
        }

        this.value = AnimationUtils.calculateCompensation(value, this.value, deltaValue, delta);
    }

    public float getValue() {
        return this.value;
    }

    public void setValue(float value) {
        this.value = value;
    }

}