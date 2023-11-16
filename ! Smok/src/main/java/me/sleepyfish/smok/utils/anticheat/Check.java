package me.sleepyfish.smok.utils.anticheat;

import java.util.ArrayList;
import java.util.Iterator;
import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.utils.anticheat.impl.Invalid;
import me.sleepyfish.smok.utils.anticheat.impl.Movement;
import me.sleepyfish.smok.utils.entities.BotUtils;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.utils.misc.Timer;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;

// Class from SMok Client by SleepyFish
public abstract class Check {

    private String name;
    private String desc;
    private double ver;
    private Timer timer;
    public boolean enabled;
    public int timesFailed;

    public Check(String name, String desc, double ver) {
        this.name = name;
        this.desc = desc;
        this.ver = ver;
        this.enabled = true;
        this.timer = new Timer();
        this.timesFailed = 0;
    }

    public void kill() {
        this.name = "";
        this.desc = "";
        this.ver = 0;
        this.enabled = false;
        this.timer = null;
        this.timesFailed = 0;
    }

    public abstract void checkTick(EntityPlayer var1);

    public void toggle() {
        this.enabled = !this.enabled;
    }

    public void fail(EntityPlayer player) {
        if (this.timer.delay(500L)) {
            ++this.timesFailed;
            ClientUtils.addMessage(EnumChatFormatting.WHITE + "Detector: " + player.getName() + EnumChatFormatting.RED + " failed " + EnumChatFormatting.WHITE + this.getName() + EnumChatFormatting.GRAY + " [x" + this.timesFailed + "]" + EnumChatFormatting.WHITE + ".");
            this.timer.reset();
        }
    }

    public String getName() {
        return this.name;
    }

    public String getDesc() {
        return this.desc;
    }

    public double getVer() {
        return this.ver;
    }

    public static class ACUtils {

        public static boolean isWalingForward(EntityPlayer player) {
            return player.moveForward == 1.0F;
        }

        public static boolean isWalingBackward(EntityPlayer player) {
            return player.moveForward == -1.0F;
        }

        public static boolean justGotHurt(EntityPlayer player) {
            return player.hurtTime != 0;
        }

        public static boolean inBlock(EntityPlayer player, Block block) {
            return getBlock(player) == block;
        }

        public static Block getBlock(EntityPlayer player) {
            return Smok.inst.mc.theWorld.getBlockState(player.getPosition()).getBlock();
        }

        public static boolean isBlocking(EntityPlayer player) {
            return player.isBlocking() && player.isUsingItem();
        }
    }

    public static class Manager {

        private final ArrayList<Check> checks = new ArrayList<>();

        public Manager() {
            this.checks.add(new Movement());
            this.checks.add(new Invalid());
        }

        public void onTickMain() {
            for (EntityPlayer player : Smok.inst.mc.theWorld.playerEntities) {
                if (BotUtils.isBot(player))
                    return;

                for (Check check : this.checks) {
                    if (check.enabled) {
                        if (check.timesFailed > 99)
                            check.timesFailed = 0;

                        if (!player.isDead && !player.isSpectator())
                            check.checkTick(player);
                    }
                }
            }

        }

        public void unInject() {
            this.checks.forEach(Check::kill);
            this.checks.clear();
        }
    }

}