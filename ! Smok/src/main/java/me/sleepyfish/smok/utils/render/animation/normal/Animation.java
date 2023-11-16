package me.sleepyfish.smok.utils.render.animation.normal;

import me.sleepyfish.smok.utils.misc.Timer;

// Class from SMok Client by SleepyFish
public abstract class Animation {

    public Timer timer = new Timer();
    protected int duration;
    protected double endPoint;
    protected Direction direction;

    public Animation(int duration, double endPoint) {
        this.duration = duration;
        this.endPoint = endPoint;
        this.direction = Direction.FORWARDS;
    }

    public Animation(int ms, double endPoint, Direction direction) {
        this.duration = ms;
        this.endPoint = endPoint;
        this.direction = direction;
    }

    public boolean isDone(Direction direction) {
        return this.isDone() && this.direction.equals(direction);
    }

    public double getLinearOutput() {
        return 1.0 - (double) this.timer.getTime() / (double) this.duration * this.endPoint;
    }

    public void reset() {
        this.timer.reset();
    }

    public boolean isDone() {
        return this.timer.delay(this.duration);
    }

    protected boolean correctOutput() {
        return false;
    }

    public double getValue() {
        if (this.direction == Direction.FORWARDS) {
            return this.isDone() ? this.endPoint : this.getEquation((double) this.timer.getTime()) * this.endPoint;
        } else if (this.isDone()) {
            return 0.0;
        } else {
            return this.correctOutput() ? this.getEquation((double) Math.min(this.duration, Math.max(0L, (long) this.duration - this.timer.getTime()))) * this.endPoint : (1.0 - this.getEquation((double) this.timer.getTime())) * this.endPoint;
        }
    }

    public void setValue(double value) {
        this.endPoint = value;
    }

    protected abstract double getEquation(double var1);

    public double getEndPoint() {
        return this.endPoint;
    }

    public void setEndPoint(double endPoint) {
        this.endPoint = endPoint;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        if (this.direction != direction) {
            this.direction = direction;
            this.timer.setTime(System.currentTimeMillis() - ((long) this.duration - Math.min((long) this.duration, this.timer.getTime())));
        }
    }

    public void changeDirection() {
        this.setDirection(this.direction.opposite());
    }
}