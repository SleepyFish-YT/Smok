package me.sleepyfish.smok.utils.render.color;

import me.sleepyfish.smok.Smok;

import java.awt.Color;
import java.util.ArrayList;

// Class from SMok Client by SleepyFish
public class ColorManager {

   private final ArrayList<AccentColor> colorModes = new ArrayList<>();
   private AccentColor colorMode;

   public ColorManager() {
      this.getColorModes().add(new AccentColor(1, new Color(78, 139, 255), new Color(0, 48, 98))); // blueberry
      this.getColorModes().add(new AccentColor(2, new Color(244, 123, 255), new Color(254, 240, 45))); // fruit
      this.getColorModes().add(new AccentColor(3, new Color(5, 135, 65), new Color(158, 227, 191))); // mint
      this.getColorModes().add(new AccentColor(4, new Color(152, 114, 206), new Color(90, 47, 141))); // lavender
      this.getColorModes().add(new AccentColor(5, new Color(253, 145, 21), new Color(245, 106, 230))); // pink
      this.getColorModes().add(new AccentColor(6, new Color(210, 39, 48), new Color(79, 13, 26))); // devil
      this.getColorModes().add(new AccentColor(7, new Color(61, 79, 143), new Color(1, 19, 63))); // nightsky
      this.getColorModes().add(new AccentColor(8, new Color(23, 157, 206), new Color(185, 225, 4))); // lemon
      this.getColorModes().add(new AccentColor(9, new Color(33, 212, 253), new Color(183, 33, 255))); // purple blue
      this.getColorModes().add(new AccentColor(10, new Color(251, 109, 32), new Color(190, 53, 38))); // orange
      this.getColorModes().add(new AccentColor(11, new Color(237, 133, 211), new Color(28, 166, 222))); // tena
      this.getColorModes().add(new AccentColor(12, new Color(192, 95, 216), new Color(74, 0, 224))); // amin
      this.getColorModes().add(new AccentColor(13, new Color(26, 33, 42), new Color(232, 234, 240))); // black white
      this.getColorModes().add(new AccentColor(14, new Color(0xFF7F00FF), new Color(0xFFE100FF))); // purple
      this.getColorModes().add(new AccentColor(15, new Color(0xFFFF8008), new Color(0x151515))); // duckhub
      this.getColorModes().add(new AccentColor(16, new Color(0x3047FF), new Color(0x0033FF))); // water
      this.getColorModes().add(new AccentColor(17, new Color(0xFF0000), new Color(0x000DFF))); // police
      this.getColorModes().add(new AccentColor(18, new Color(0x0), new Color(0x0))); // black
      this.getColorModes().add(new AccentColor(19, Color.white, Color.white)); // white
      this.getColorModes().add(new AccentColor(20, new Color(4049313), new Color(0xFF35B791, true))); // vape
      this.getColorModes().add(new AccentColor(21, new Color(4049313))); // rainbow

      this.setColorMode(6);
   }

   public AccentColor getColorMode() {
      return this.colorMode;
   }

   public void setColorMode(int id) {
      this.colorMode = this.getColorModeByID(id);
   }

   public ArrayList<AccentColor> getColorModes() {
      return this.colorModes;
   }

   public AccentColor getColorModeByID(int id) {
      return this.colorModes.stream().filter(color -> color.getID() == id).findFirst().orElse(null);
   }

   public void unInject() {
      this.colorMode = null;
      this.colorModes.clear();
      Smok.inst.colManager = null;
   }

}