package me.sleepyfish.smok.utils.misc;

// Class from SMok Client by SleepyFish
public class Timer {

   public long lastMS = System.currentTimeMillis();

   public void reset() {
      this.lastMS = System.currentTimeMillis();
   }

   public boolean delay(long nextDelay) {
      return System.currentTimeMillis() - this.lastMS >= nextDelay;
   }

   public long getTime() {
      return System.currentTimeMillis() - this.lastMS;
   }

   public void setTime(long time) {
      this.lastMS = time;
   }

   public boolean cpsTimer(int min, int max) {
      return this.delay(1000L / MathUtils.randomInt(min, max));
   }

}