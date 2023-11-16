package me.sleepyfish.smok.utils.entities;

import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.utils.misc.MathUtils;
import me.sleepyfish.smok.utils.render.color.ColorUtils;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.Display;

// Class from SMok Client by SleepyFish
public class Utils {

    public static boolean isSameTeamAs(EntityLivingBase entity) {
        return entity.getTeam() == Smok.inst.mc.thePlayer.getTeam();
    }

    public static boolean isMoving() {
        return Smok.inst.mc.thePlayer.moveForward != 0.0F || Smok.inst.mc.thePlayer.moveStrafing != 0.0F;
    }

    public static boolean isMovingForward() {
        return Smok.inst.mc.thePlayer.moveForward > 0.0F;
    }

    public static boolean isMovingBackwards() {
        return Smok.inst.mc.thePlayer.moveForward < 0.0F;
    }

    public static boolean canLegitWork() {
        if (ColorUtils.isGooberDate() || !Display.isActive() || !Display.isVisible() || !Smok.inst.injected) return false;
        return Smok.inst.mc.thePlayer != null && !Smok.inst.mc.thePlayer.isDead && !Smok.inst.mc.thePlayer.isSpectator() && Smok.inst.mc.theWorld != null && Smok.inst.mc.currentScreen == null;
    }

    public static ItemStack getCurrentItem() {
        return Smok.inst.mc.thePlayer.getCurrentEquippedItem() == null ? new ItemStack(Blocks.air) : Smok.inst.mc.thePlayer.getCurrentEquippedItem();
    }

    public static boolean holdingBlock() {
        Item item = getCurrentItem().getItem();
        if (item == null) return false;
        String itemName = getCurrentItem().getDisplayName().toLowerCase();
        return item instanceof ItemBlock && !itemName.equals("tnt") && !(itemName.contains("sand")
                && itemName.contains(" stone")) && !itemName.contains("portal frame");
    }

    public static boolean holdingWeapon() {
        Item item = getCurrentItem().getItem();
        if (item == null) return false;
        return item instanceof ItemSword || item instanceof ItemAxe || item instanceof ItemShears;
    }

    public static boolean holdingPickaxe() {
        Item item = getCurrentItem().getItem();
        if (item == null) return false;
        return item instanceof ItemPickaxe;
    }

    public static boolean holdingAxe() {
        Item item = getCurrentItem().getItem();
        if (item == null) return false;
        return item instanceof ItemAxe;
    }

    public static boolean holdingShears() {
        Item item = getCurrentItem().getItem();
        if (item == null) return false;
        return item instanceof ItemShears;
    }

    public static boolean canNotBePlaced(BlockPos blockPos, EnumFacing facing) {
        Block block = getBlock(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        if (Smok.inst.mc.theWorld.canBlockBePlaced(block, blockPos, false, facing, Smok.inst.mc.thePlayer, getCurrentItem())) {
            return true;
        } else {
            return block != Blocks.air && (block == Blocks.lava || block == Blocks.water || block == Blocks.flowing_lava || block == Blocks.flowing_water);
        }
    }

    public static Block getBlock(double x, double y, double z) {
        return getBlock(new BlockPos(x, y, z));
    }

    public static Block getBlock(BlockPos blockPos) {
        return Smok.inst.mc.theWorld.getBlockState(blockPos).getBlock();
    }

    public static boolean overAir(double distance) {
        return Smok.inst.mc.theWorld.isAirBlock(new BlockPos(MathHelper.floor_double(Smok.inst.mc.thePlayer.posX), MathHelper.floor_double(Smok.inst.mc.thePlayer.posY - distance), MathHelper.floor_double(Smok.inst.mc.thePlayer.posZ)));
    }

    public static boolean overAirCustom(double distanceX, double distanceY, double distanceZ) {
        return Smok.inst.mc.theWorld.isAirBlock(new BlockPos(MathHelper.floor_double(Smok.inst.mc.thePlayer.posX - distanceX), MathHelper.floor_double(Smok.inst.mc.thePlayer.posY - distanceY), MathHelper.floor_double(Smok.inst.mc.thePlayer.posZ - distanceZ)));
    }

    public static boolean inInv() {
        return Smok.inst.mc.currentScreen instanceof GuiInventory;
    }

    public static boolean inGui() {
        return Smok.inst.mc.currentScreen != null;
    }

    public static void shiftClick(int slot) {
        Smok.inst.mc.playerController.windowClick(Smok.inst.mc.thePlayer.inventoryContainer.windowId, slot, 0, 1, Smok.inst.mc.thePlayer);
    }

    public static ItemStack changeToBlock(boolean silent) {
        for (int i = 0; i < 9; ++i) {
            ItemStack iS = Smok.inst.mc.thePlayer.inventory.getStackInSlot(i);

            if (iS != null && iS.getItem() instanceof ItemBlock) {
                boolean anvil = iS.getItem() instanceof ItemAnvilBlock;
                String n = iS.getDisplayName().toLowerCase();
                if (anvil || n.contains("sand") || n.startsWith("anvil") || n.contains("slab") || n.startsWith("lilly") || n.contains("sapling") || n.contains("chest") || n.contains("web"))
                    continue;

                if (!silent) {
                    Smok.inst.mc.thePlayer.inventory.currentItem = i;
                } else {
                    Smok.inst.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(i));
                }

                return Smok.inst.mc.thePlayer.inventory.getStackInSlot(i);
            }
        }

        return null;
    }

    public static boolean changeToPaper(boolean silent) {
        for(int i = 0; i < 9; ++i) {
            ItemStack iS = Smok.inst.mc.thePlayer.inventory.getStackInSlot(i);
            if (iS != null && iS.getDisplayName().toLowerCase().contains("play agai")) {
                if (!silent) {
                    Smok.inst.mc.thePlayer.inventory.currentItem = i;
                } else {
                    Smok.inst.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(i));
                }

                return true;
            }
        }

        return false;
    }

    public static void changeToPearl(boolean silent) {
        for (int i = 0; i < 9; ++i) {
            ItemStack iS = Smok.inst.mc.thePlayer.inventory.getStackInSlot(i);
            if (iS != null && iS.getItem() instanceof ItemEnderPearl) {
                if (!silent) {
                    Smok.inst.mc.thePlayer.inventory.currentItem = i;
                } else {
                    Smok.inst.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(i));
                }
            }
        }
    }

    public static ItemStack changeToBlockItem(boolean silent) {
        for(int i = 0; i < 9; ++i) {
            ItemStack iS = Smok.inst.mc.thePlayer.inventory.getStackInSlot(i);
            if (iS != null && iS.getItem() instanceof ItemBlock) {
                boolean anvil = iS.getItem() instanceof ItemAnvilBlock;
                String n = iS.getDisplayName().toLowerCase();
                if (!anvil && !n.contains("sand") && !n.startsWith("anvil") && !n.contains("slab") && !n.startsWith("lilly") && !n.contains("sapling") && !n.contains("chest") && !n.contains("web")) {
                    if (!silent) {
                        Smok.inst.mc.thePlayer.inventory.currentItem = i;
                        return getCurrentItem();
                    }

                    Smok.inst.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(i));
                    return Smok.inst.mc.thePlayer.inventory.getStackInSlot(i);
                }
            }
        }

        return null;
    }

    public static boolean changeToBlockBool(boolean silent) {
        for (int i = 0; i < 9; ++i) {
            ItemStack iS = Smok.inst.mc.thePlayer.inventory.getStackInSlot(i);

            if (iS != null && iS.getItem() instanceof ItemBlock) {
                boolean anvil = iS.getItem() instanceof ItemAnvilBlock;
                String n = iS.getDisplayName().toLowerCase();
                if (anvil || n.contains("sand") || n.startsWith("anvil") || n.contains("slab") || n.startsWith("lilly") || n.contains("sapling") || n.contains("chest") || n.contains("web"))
                    continue;

                if (!silent) {
                    Smok.inst.mc.thePlayer.inventory.currentItem = i;
                } else {
                    Smok.inst.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(i));
                }

                return true;
            }
        }

        return false;
    }

    public static class Combat {

        public static float fovToEntity(Entity target) {
            double x = target.posX - Smok.inst.mc.thePlayer.posX;
            double z = target.posZ - Smok.inst.mc.thePlayer.posZ;
            double w = Math.atan2(x, z) * 57.2957795;
            return (float) (w * -1.0);
        }

        public static boolean isInFov(Entity target, float fov) {
            fov = (float) ((double) fov * 0.5);
            double v = ((double) (Smok.inst.mc.thePlayer.rotationYaw - fovToEntity(target)) % 360.0 + 540.0) % 360.0 - 180.0;
            return v > 0.0 && v < (double) fov || (double) (-fov) < v && v < 0.0;
        }

        public static boolean inRange(Entity target, double range) {
            if (target != null && !target.isDead && !target.isInvisibleToPlayer(Smok.inst.mc.thePlayer)) {
                return (double) Smok.inst.mc.thePlayer.getDistanceToEntity(target) < range;
            } else {
                return false;
            }
        }

        public static float[] getBlockRotations(BlockPos blockPos, EnumFacing facing) {
            double x = (double) blockPos.getX() + 0.5 - Smok.inst.mc.thePlayer.posX + (double) facing.getFrontOffsetX() / 2.0;
            double y = Smok.inst.mc.thePlayer.posY + (double) Smok.inst.mc.thePlayer.getEyeHeight() - ((double) blockPos.getY() + 0.5);
            double z = (double) blockPos.getZ() + 0.5 - Smok.inst.mc.thePlayer.posZ + (double) facing.getFrontOffsetZ() / 2.0;
            float yaw = (float) (Math.atan2(z, x) * 180.0 / MathUtils.PI) - 90.0F;
            float pitch = (float) (Math.atan2(y, MathHelper.sqrt_double(x * x + z * z)) * 180.0 / MathUtils.PI);

            if (yaw < 0.0F) {
                yaw += 360.0F;
            }

            return new float[]{yaw, pitch};
        }

        public static BlockData getBlockData(BlockPos pos) {
            if (Utils.getBlock(pos.add(0, -1, 0)) != Blocks.air) {
                return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
            } else if (Utils.getBlock(pos.add(-1, 0, 0)) != Blocks.air) {
                return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
            } else if (Utils.getBlock(pos.add(1, 0, 0)) != Blocks.air) {
                return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
            } else if (Utils.getBlock(pos.add(0, 0, -1)) != Blocks.air) {
                return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
            } else {
                return Utils.getBlock(pos.add(0, 0, 1)) != Blocks.air ? new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH) : null;
            }
        }

        public static BlockData getBlockDataFixed(BlockPos pos) {
            if (Utils.getBlock(pos.add(0, -1, 0)) != Blocks.air) {
                return new BlockData(pos.add(0, 0, 0), EnumFacing.UP);
            } else if (Utils.getBlock(pos.add(-1, 0, 0)) != Blocks.air) {
                return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
            } else if (Utils.getBlock(pos.add(1, 0, 0)) != Blocks.air) {
                return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
            } else if (Utils.getBlock(pos.add(0, 0, -1)) != Blocks.air) {
                return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
            } else {
                return Utils.getBlock(pos.add(0, 0, 1)) != Blocks.air ? new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH) : null;
            }
        }

        public static float[] getEntityRotations(Entity target) {
            if (target == null) {
                return null;
            } else {
                double diffX = target.posX - Smok.inst.mc.thePlayer.posX;
                double diffY;
                if (target instanceof EntityLivingBase) {
                    EntityLivingBase x = (EntityLivingBase) target;
                    diffY = x.posY + (double) x.getEyeHeight() * 0.9 - (Smok.inst.mc.thePlayer.posY + (double) Smok.inst.mc.thePlayer.getEyeHeight());
                } else {
                    diffY = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0 - (Smok.inst.mc.thePlayer.posY + (double) Smok.inst.mc.thePlayer.getEyeHeight());
                }

                double diffZ = target.posZ - Smok.inst.mc.thePlayer.posZ;
                float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0 / MathUtils.PI) - 90.0F;
                float pitch = (float) (-(Math.atan2(diffY, MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ)) * 180.0 / MathUtils.PI));

                return new float[]{Smok.inst.mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Smok.inst.mc.thePlayer.rotationYaw), Smok.inst.mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Smok.inst.mc.thePlayer.rotationPitch)};
            }
        }
    }

    public static class Npc {

        private static EntityOtherPlayerMP npc;

        public static EntityOtherPlayerMP getNpc() {
            return npc;
        }

        public static void spawn() {
            if (Smok.inst.mc.thePlayer != null) {
                npc = new EntityOtherPlayerMP(Smok.inst.mc.theWorld, Smok.inst.mc.thePlayer.getGameProfile());
                npc.copyLocationAndAnglesFrom(Smok.inst.mc.thePlayer);
                npc.setRotationYawHead(Smok.inst.mc.thePlayer.rotationYawHead);
                npc.setSprinting(Smok.inst.mc.thePlayer.isSprinting());
                npc.setSneaking(Smok.inst.mc.thePlayer.isSneaking());
                npc.setInvisible(Smok.inst.mc.thePlayer.isInvisible());
                ClientUtils.addDebug("Spawned npc: " + npc.getName());
                Smok.inst.mc.theWorld.addEntityToWorld(npc.getEntityId(), npc);
            }
        }

        public static void kill() {
            if (npc != null) {
                Smok.inst.mc.theWorld.removeEntityFromWorld(npc.getEntityId());
                ClientUtils.addDebug("Killed npc: " + npc.getName());
                npc = null;
            }
        }
    }

    public static class BlockData {
        public final BlockPos pos;
        public final EnumFacing face;

        public BlockData(BlockPos pos, EnumFacing face) {
            this.pos = pos;
            this.face = face;
        }
    }

}