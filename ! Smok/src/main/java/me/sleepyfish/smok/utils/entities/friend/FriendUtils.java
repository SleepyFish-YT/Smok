package me.sleepyfish.smok.utils.entities.friend;

import net.minecraft.entity.Entity;
import java.util.ArrayList;

// Class from SMok Client by SleepyFish
public class FriendUtils {

    private static final ArrayList<Entity> renderFriends = new ArrayList<>();
    private static final ArrayList<Entity> ignoreFriends = new ArrayList<>();

    public static ArrayList<Entity> getRenderFriends() {
        return renderFriends;
    }

    public static ArrayList<Entity> getIgnoreFriends() {
        return ignoreFriends;
    }

    public static boolean ignoreFriend(Entity entity) {
        return getRenderFriends().contains(entity);
    }

}