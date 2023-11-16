package me.sleepyfish.smok.utils.entities;

import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.utils.misc.MathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;

// Class from SMok Client by SleepyFish
public class RotsManager {

    public float yaw;
    public float pitch;

    public boolean renderRayTrace;
    private boolean rotating;

    public BlockPos rayTracePos;
    public Entity rayTraceEntity;

    public void startRotate() {
        this.rotating = true;
    }

    public void stopRotate() {
        this.rotating = false;
        this.yaw = Smok.inst.mc.thePlayer.rotationYaw;
        this.pitch = Smok.inst.mc.thePlayer.rotationPitch;
    }

    public boolean isRotating() {
        return this.rotating;
    }

    public void setRotations(float yaw, float pitch) {
        this.yaw = yaw;

        // net.wrongturn fix
        if (pitch > 90.0F) {
            this.pitch = 89.0F;
        } else {
            this.pitch = pitch;
        }
    }

    public float smoothRotation(float from, float to, float speed) {
        float angle = MathUtils.wrapAngleToCustom_float(to - from, 180.0F);
        if (angle > speed) {
            angle = speed;
        }

        if (angle < -speed) {
            angle = -speed;
        }

        return from + angle;
    }

    public float getSensitivity() {
        float sensitivity = Smok.inst.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        return sensitivity * sensitivity * sensitivity * 8.0F * 0.15F;
    }

    public void rayTrace(Entity target) {
        this.rayTraceEntity = target;

        if (this.isRotating()) {
            Smok.inst.mc.pointedEntity = this.rayTraceEntity;
            Smok.inst.mc.objectMouseOver.entityHit = this.rayTraceEntity;
        }
    }

    public void rayTrace(float yaw, float pitch) {
        Smok.inst.mc.thePlayer.rayTrace(yaw, pitch);
    }

    public void unInject() {
        this.stopRotate();
        this.yaw = 0.0F;
        this.pitch = 0.0F;
        this.rayTracePos = null;
        this.rayTraceEntity = null;
        this.renderRayTrace = false;
        Smok.inst.rotManager = null;
    }

}