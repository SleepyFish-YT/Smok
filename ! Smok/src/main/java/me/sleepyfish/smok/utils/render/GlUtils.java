package me.sleepyfish.smok.utils.render;

import me.sleepyfish.smok.Smok;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.GL11;

// Class from SMok Client by SleepyFish
public class GlUtils {

    public static void setAlphaLimit(float limit) {
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, (float) ((double) limit * 0.01));
    }

    public static Framebuffer createFrameBuffer(Framebuffer framebuffer) {
        if (framebuffer != null && framebuffer.framebufferWidth == Smok.inst.mc.displayWidth && framebuffer.framebufferHeight == Smok.inst.mc.displayHeight) {
            return framebuffer;
        } else {
            if (framebuffer != null) {
                framebuffer.deleteFramebuffer();
            }

            return new Framebuffer(Smok.inst.mc.displayWidth, Smok.inst.mc.displayHeight, true);
        }
    }

    public static void bindTexture(int texture) {
        GL11.glBindTexture(3553, texture);
    }

    public static void startScale(float scale) {
        GL11.glPushMatrix();
        GL11.glScaled(scale, scale, scale);
    }

    public static void startScale(float x, float y, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0.0F);
        GlStateManager.scale(scale, scale, 1.0F);
        GlStateManager.translate(-x, -y, 0.0F);
    }

    public static void startScale(float x, float y, float width, float height, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((x + x + width) / 2.0F, (y + y + height) / 2.0F, 0.0F);
        GlStateManager.scale(scale, scale, 1.0F);
        GlStateManager.translate(-(x + x + width) / 2.0F, -(y + y + height) / 2.0F, 0.0F);
    }

    public static void stopScale() {
        GlStateManager.popMatrix();
    }

    public static void fixDepth() {
        GlStateManager.disableDepth();
        GL11.glDisable(2929);
    }

    public static void enableSeeThru() {
        GL11.glDisable(2929);
        GlStateManager.disableAlpha();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
    }

    public static void disableSeeThru() {
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        GL11.glEnable(2929);
    }

    public static void startScissors() {
        Smok.inst.mc.getFramebuffer().bindFramebuffer(false);
        StencilUtils.checkSetupFBO(Smok.inst.mc.getFramebuffer());
        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 1);
        GL11.glStencilOp(GL11.GL_REPLACE, GL11.GL_REPLACE, GL11.GL_REPLACE);
        GL11.glColorMask(false, false, false, false);
    }

    public static void readScissors(int ref) {
        GL11.glColorMask(true, true, true, true);
        GL11.glStencilFunc(GL11.GL_EQUAL, ref, 1);
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
    }

    public static void endScissors() {
        GL11.glDisable(GL11.GL_STENCIL_TEST);
    }

    public static void enableChamsSee() {
        GL11.glEnable(32823);
        GL11.glPolygonOffset(0.0F, -1100000.0F);
    }

    public static void disableChamsSee() {
        GL11.glDisable(32823);
        GL11.glPolygonOffset(0.0F, 1100000.0F);
    }

}