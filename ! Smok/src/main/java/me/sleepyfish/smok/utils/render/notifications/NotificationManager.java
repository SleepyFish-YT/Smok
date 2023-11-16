package me.sleepyfish.smok.utils.render.notifications;

import java.util.concurrent.LinkedBlockingQueue;

// Class from SMok Client by SleepyFish
public class NotificationManager {
   public static final LinkedBlockingQueue<Notification> pending = new LinkedBlockingQueue<>();
   public static Notification current = null;

   public static void show(Notification notification) {
      pending.add(notification);
   }

   public static void update() {
      if (current != null && !current.isShown()) {
         current = null;
      }

      if (current == null && !pending.isEmpty()) {
         current = pending.poll();
         current.show();
      }
   }

   public static void render() {
      update();
      if (current != null) {
         current.render();
      }
   }
}
