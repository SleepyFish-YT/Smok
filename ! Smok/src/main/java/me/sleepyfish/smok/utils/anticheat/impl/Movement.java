package me.sleepyfish.smok.utils.anticheat.impl;

import me.sleepyfish.smok.utils.anticheat.Check;
import me.sleepyfish.smok.utils.entities.Utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;

// Class from SMok Client by SleepyFish
public class Movement extends Check {

    public Movement() {
        super("Movement", "Movement checks", 1.2);
    }

    public void checkTick(EntityPlayer player) {
        if (!player.capabilities.isFlying) {
            if (ACUtils.inBlock(player, Blocks.water) && !player.isWet() && !player.isInWater()) {
                this.fail(player);
            }

            if (ACUtils.inBlock(player, Blocks.web) && (Math.abs(player.motionZ) > 0.30000001192092896 || Math.abs(player.motionX) > 0.30000001192092896)) {
                this.fail(player);
            }

            if (player.isSprinting()) {
                if (ACUtils.isBlocking(player)) {
                    this.fail(player);
                }

                if (player.isSneaking()) {
                    this.fail(player);
                }

                if (player.isEating()) {
                    this.fail(player);
                }

                if (ACUtils.justGotHurt(player)) {
                    this.fail(player);
                }

                if (ACUtils.isWalingForward(player) || ACUtils.isWalingBackward(player)) {
                    this.fail(player);
                }

                if (player.isCollidedHorizontally) {
                    this.fail(player);
                }
            }

            if (player.onGround) {
                if (ACUtils.isBlocking(player) && Math.abs(player.motionX) > 0.07999999821186066) {
                    this.fail(player);
                }

                if (!Utils.isMoving() && (float)Math.round(player.motionY) != 0.0F) {
                    this.fail(player);
                }

                if (player.isSneaking()) {
                    if (!player.velocityChanged && ACUtils.justGotHurt(player)) {
                        this.fail(player);
                    }

                    if (player.motionZ > 0.05000000074505806) {
                        this.fail(player);
                    }
                }

                if (player.fallDistance != 0.0F) {
                    this.fail(player);
                }
            } else if (Math.abs(player.motionY) > 1.4) {
                this.fail(player);
            }

            if (Math.abs(player.rotationPitch) > 90.0F) {
                this.fail(player);
            } else if ((float)Math.round(player.motionX) > 5.0F || (float)Math.round(player.motionY) > 5.0F || (float)Math.round(player.motionZ) > 5.0F) {
                this.fail(player);
            }
        } else if (!player.isSpectator()) {
            if (!player.capabilities.isCreativeMode) {
                this.fail(player);
            }

            if (player.fallDistance != 0.0F) {
                this.fail(player);
            }

            if (ACUtils.getBlock(player) != Blocks.air) {
                this.fail(player);
            }
        }

    }
}