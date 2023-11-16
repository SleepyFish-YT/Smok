package me.sleepyfish.smok.utils.render.animation.normal;

// Class from SMok Client by SleepyFish
public enum Direction {
    FORWARDS,
    BACKWARDS;

    public Direction opposite() {
        return this == FORWARDS ? BACKWARDS : FORWARDS;
    }

}