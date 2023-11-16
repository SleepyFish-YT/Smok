package me.sleepyfish.smok.utils.anticheat;

import java.util.ArrayList;
import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.utils.anticheat.impl.Invalid;
import me.sleepyfish.smok.utils.anticheat.impl.Movement;
import me.sleepyfish.smok.utils.entities.BotUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;

// Class from SMok Client by SleepyFish
public class CheckManager {

    private final ArrayList<Check> checks;

    public CheckManager() {
        this.checks = new ArrayList<>();
        this.checks.add(new Movement());
        this.checks.add(new Invalid());
    }

    public void onTickMain() {
        for (EntityPlayer player : Smok.inst.mc.theWorld.playerEntities) {
            if (BotUtils.isBot(player)) {
                return;
            }

            for (Check check : this.checks) {
                if (check.enabled) {
                    if (check.timesFailed > 99) {
                        check.timesFailed = 0;
                    }

                    if (!player.isDead && !player.isSpectator()) {
                        check.checkTick(player);
                    }
                }
            }
        }

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

}