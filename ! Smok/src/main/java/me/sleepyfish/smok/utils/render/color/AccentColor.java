package me.sleepyfish.smok.utils.render.color;

import java.awt.Color;

// Class from SMok Client by SleepyFish
public class AccentColor {
   private final int id;
   private final Color color1;
   private final Color color2;
   private final boolean isSpecial;

   public AccentColor(int id, Color color1, Color color2) {
      this.id = id;
      this.color1 = color1;
      this.color2 = color2;
      this.isSpecial = false;
   }

   public AccentColor(int id, Color color1) {
      this.id = id;
      this.color1 = color1;
      this.color2 = color1;
      this.isSpecial = true;
   }

   public boolean isSpecial() {
      return this.isSpecial;
   }

   public int getID() {
      return this.id;
   }

   public Color getColor1() {
      return this.color1;
   }

   public Color getColor2() {
      return this.color2;
   }
}
