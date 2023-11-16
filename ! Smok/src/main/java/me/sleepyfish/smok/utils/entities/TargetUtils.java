package me.sleepyfish.smok.utils.entities;

import me.sleepyfish.smok.utils.misc.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

// Class from SMok Client by SleepyFish
public class TargetUtils {

   static Minecraft mc = Minecraft.getMinecraft();
   static Timer timer = new Timer();
   static Entity target;

   public static void onUpdate() {
      if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit != null) {
         if (mc.objectMouseOver.entityHit instanceof EntityPlayer) {
            target = mc.objectMouseOver.entityHit;
            timer.reset();
         }
      } else if (timer.delay(2500L)) {
         target = null;
         timer.reset();
      }

      if (target != null) {
         if (target.isDead) {
            target = null;
         }

         if (mc.thePlayer.isDead) {
            target = null;
         }

         if (target != null && target.isInvisible()) {
            target = null;
         }

         if (mc.thePlayer != null && target != null && target.getDistanceToEntity(mc.thePlayer) > 12.0F) {
            target = null;
         }
      }
   }

   public static Entity getTarget() {
      return target;
   }

   public static void setTarget(Entity target) {
      TargetUtils.target = target;
   }

   public static boolean inTab(Entity entity) {
      if (mc.getCurrentServerData() != null) {
         for (NetworkPlayerInfo item : mc.getNetHandler().getPlayerInfoMap()) {
            if (item != null && item.getGameProfile() != null && item.getGameProfile().getName().contains(entity.getName())) {
               return true;
            }
         }
      }

      return false;
   }

}