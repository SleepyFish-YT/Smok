package me.sleepyfish.smok.utils.render;

import java.awt.Color;

import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.rats.impl.visual.GuiModule;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.utils.misc.MathUtils;
import me.sleepyfish.smok.utils.font.FontUtils;
import me.sleepyfish.smok.utils.render.color.ColorUtils;
import me.sleepyfish.smok.utils.render.shader.ShaderUtils;
import me.sleepyfish.smok.utils.render.color.NewColorUtils;

import net.minecraft.entity.Entity;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import org.lwjgl.opengl.GL11;

// Class from SMok Client by SleepyFish
public class RenderUtils {

    public static void drawShadow(float x, float y, float width, float height, float radius) {
        RoundedUtils.drawRound(x - 2, y - 2, width + 4, height + 4, radius + 2, new Color(0, 0, 0, 85));
        RoundedUtils.drawRound(x - 4, y - 4, width + 8, height + 8, radius + 4, new Color(0, 0, 0, 45));
    }

    public static void drawAuthor(int x, int y) {
        if (GuiModule.renderShadows.isEnabled()) {
            RenderUtils.drawShadow((float) x / 2.0F - ((float) FontUtils.r20.getStringWidth("SleepyFish") / 2.0F + 2.0F), (float) y - 68.0F, (float) FontUtils.r20.getStringWidth("SleepyFish") + 4.0F, 20.0F, 2.0F);
            ColorUtils.clearColor();
        }

        RoundedUtils.drawRound((float) x / 2.0F - ((float) FontUtils.r20.getStringWidth("SleepyFish") / 2.0F + 2.0F), (float) y - 68.0F, (float) FontUtils.r20.getStringWidth("SleepyFish") + 4.0F, 20.0F, 2.0F, NewColorUtils.backgroundColor.darker());
        RoundedUtils.drawGradientRoundLR((float) x / 2.0F - (float) FontUtils.r20.getStringWidth("SleepyFish") / 2.0F, (float) y - 64.0F, (float) FontUtils.r20.getStringWidth("SleepyFish"), 1.0F, 2.0F, NewColorUtils.clientColorDefIndex, ColorUtils.getClientColor(2000));
        FontUtils.r20.drawStringWithShadow("SleepyFish", (double) ((float) x / 2.0F) - FontUtils.r20.getStringWidth("SleepyFish") / 2.0, (float) y - 58.0F, NewColorUtils.clientColorDefIndex);
    }

    public static void drawVersion(int x, int y) {
        if (GuiModule.renderShadows.isEnabled()) {
            RenderUtils.drawShadow((float) (x - 62), (float) (y - 33), 63.0F, 27.0F, 2.0F);
            ColorUtils.clearColor();
        }

        RoundedUtils.drawRound((float) (x - 62), (float) (y - 33), 63.0F, 27.0F, 2.0F, NewColorUtils.backgroundColor.darker());
        RoundedUtils.drawGradientRoundLR((float) (x - 60), (float) (y - 30), 61.0F, 1.0F, 2.0F, NewColorUtils.clientColorDefIndex, ColorUtils.getClientColor(2000));
        FontUtils.r20.drawStringWithShadow("Server: v" + Smok.inst.getServerVersion(), ((float) x - 60.0F), ((float) y - 25.0F), NewColorUtils.clientColorDefIndex);
        FontUtils.r20.drawStringWithShadow("Client: v" + Smok.inst.getClientVersion(), ((float) x - 60.0F), ((float) y - 15.0F), NewColorUtils.clientColorDefIndex.darker());
    }

    public static void drawRect(float x, float y, float width, float height, Color c) {
        float alpha = (float) (c.getRGB() >> 24 & 0xFF) / 255.0F;
        float red = (float) (c.getRGB() >> 16 & 0xFF) / 255.0F;
        float green = (float) (c.getRGB() >> 8 & 0xFF) / 255.0F;
        float blue = (float) (c.getRGB() & 0xFF) / 255.0F;

        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(7);
        GL11.glVertex2d((x + width), y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, (y + height));
        GL11.glVertex2d((x + width), (y + height));
        GL11.glEnd();
        GlUtils.stopScale();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void drawRect(float x, float y, float width, float height) {
        drawRect(x, y, width, height, Color.white);
    }

    public static void drawImage(String image, int x, int y, int width, int height) {
        drawImageWC(image, x, y, width, height, null);
    }

    public static void drawImageWC(String image, int x, int y, int width, int height, Color color) {
        GL11.glDisable(2929);
        GlStateManager.enableBlend();
        GL11.glDepthMask(false);
        ColorUtils.clearColor();

        if (color != null)
            ColorUtils.setColor(color);

        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        ResourceLocation res = new ResourceLocation(ClientUtils.path + "/" + image);
        Smok.inst.mc.getTextureManager().bindTexture(res);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, (float) width, (float) height);

        if (color != null)
            ColorUtils.clearColor();

        ColorUtils.clearColor();
        GL11.glDepthMask(true);
        GlStateManager.disableBlend();
        GL11.glEnable(2929);
    }

    public static void drawScaledCustomSizeModalRect(double x, double y, float u, float v, int uWidth, int vHeight, double width, double height, float tileWidth, float tileHeight) {
        Tessellator tess = Tessellator.getInstance();
        WorldRenderer wR = tess.getWorldRenderer();
        wR.begin(7, DefaultVertexFormats.POSITION_TEX);
        wR.pos(x, y + height, 0.0).tex((u / tileWidth), ((v + (float) vHeight) / tileHeight)).endVertex();
        wR.pos(x + width, y + height, 0.0).tex(((u + (float) uWidth) / tileWidth), ((v + (float) vHeight) / tileHeight)).endVertex();
        wR.pos(x + width, y, 0.0).tex(((u + (float) uWidth) / tileWidth), (v / tileHeight)).endVertex();
        wR.pos(x, y, 0.0).tex((u / tileWidth), (v / tileHeight)).endVertex();
        tess.draw();
    }

    private static ShaderUtils roundedTexturedShader = new ShaderUtils(ClientUtils.path + "/shaders/roundRectTextured.frag");

    public static void drawRoundTextured(float x, float y, float width, float height, float radius, float alpha) {
        ColorUtils.clearColor();
        roundedTexturedShader.init();
        roundedTexturedShader.setUniformi("textureIn", 0);
        setupRoundedRectUniforms(x, y, width, height, radius, roundedTexturedShader);
        roundedTexturedShader.setUniformf("alpha", alpha);
        ShaderUtils.drawQuads(x - 1, y - 1, width + 2, height + 2);
        roundedTexturedShader.unload();
        GlStateManager.disableBlend();
    }

    private static void setupRoundedRectUniforms(float x, float y, float width, float height, float radius, ShaderUtils roundedTexturedShader) {
        ScaledResolution sr = new ScaledResolution(Smok.inst.mc);
        roundedTexturedShader.setUniformf("location", x * sr.getScaleFactor(),
                (Smok.inst.mc.displayHeight - (height * sr.getScaleFactor())) - (y * sr.getScaleFactor()));
        roundedTexturedShader.setUniformf("rectSize", width * sr.getScaleFactor(), height * sr.getScaleFactor());
        roundedTexturedShader.setUniformf("radius", radius * sr.getScaleFactor());
    }

    public static void drawTargetCapsule(Entity entity, double rad, float ticks, float width, Color c) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        //GL11.glEnable(2832);
        GL11.glEnable(3042);

        GL11.glBlendFunc(770, 771);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glHint(3153, 4354);
        GL11.glDepthMask(false);

        GlStateManager.alphaFunc(GL11.GL_GREATER, 0F);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GlStateManager.disableCull();

        GL11.glLineWidth(width);
        GL11.glBegin(GL11.GL_TRIANGLE_STRIP);

        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * ticks - Smok.inst.mc.getRenderManager().viewerPosX;
        final double y = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * ticks - Smok.inst.mc.getRenderManager().viewerPosY + Math.sin(System.currentTimeMillis() / 2E+2) + 1);
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * ticks - Smok.inst.mc.getRenderManager().viewerPosZ;

        for (float i = 0; i < MathUtils.PI * 2; i += MathUtils.PI * 2 / 9.0F) { // - MathUtils.PI * 2 / 64.0F / 18.0F
            final double vecX = x + rad * Math.cos(i);
            final double vecZ = z + rad * Math.sin(i);

            GlStateManager.color(c.getRed() / 254F, c.getGreen() / 254F, c.getBlue() / 254F, 0);
            GL11.glVertex3d(vecX, y - Math.cos(System.currentTimeMillis() / 2E+2) / 2F, vecZ);
            GlStateManager.color(c.getRed() / 254F, c.getGreen() / 254F, c.getBlue() / 254F, 0.85F);
            GL11.glVertex3d(vecX, y, vecZ);

            GL11.glColor4f(255F, 255F, 255F, 20F);
        }

        GL11.glEnd();
        GL11.glShadeModel(GL11.GL_FLAT);

        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GlStateManager.enableCull();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);

        GL11.glDisable(2848);
        GL11.glDisable(2848);
        //GL11.glEnable(2832);
        GL11.glEnable(3553);

        GlUtils.stopScale();
    }

    public static void drawAroundAxisbox(AxisAlignedBB box, float r, float g, float b) {
        Tessellator tess = Tessellator.getInstance();
        WorldRenderer wr = tess.getWorldRenderer();
        float alpha = 0.25F;

        // Top
        wr.begin(7, DefaultVertexFormats.POSITION_COLOR);
        wr.pos(box.minX, box.maxY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.maxY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.maxY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.maxY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.maxY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.maxY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.maxY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.maxY, box.minZ).color(r, g, b, alpha).endVertex();
        tess.draw();

        // Top
        wr.begin(7, DefaultVertexFormats.POSITION_COLOR);
        wr.pos(box.minX, box.minY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.minY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.minY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.minY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.minY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.minY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.minY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.minY, box.minZ).color(r, g, b, alpha).endVertex();
        tess.draw();

        // Right
        wr.begin(7, DefaultVertexFormats.POSITION_COLOR);
        wr.pos(box.minX, box.minY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.maxY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.minY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.maxY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.minY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.maxY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.minY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.maxY, box.maxZ).color(r, g, b, alpha).endVertex();
        tess.draw();

        // Right
        wr.begin(7, DefaultVertexFormats.POSITION_COLOR);
        wr.pos(box.maxX, box.maxY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.minY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.maxY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.minY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.maxY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.minY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.maxY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.minY, box.maxZ).color(r, g, b, alpha).endVertex();
        tess.draw();

        // Left
        wr.begin(7, DefaultVertexFormats.POSITION_COLOR);
        wr.pos(box.minX, box.minY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.maxY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.minY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.maxY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.minY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.maxY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.minY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.maxY, box.minZ).color(r, g, b, alpha).endVertex();
        tess.draw();

        // Left
        wr.begin(7, DefaultVertexFormats.POSITION_COLOR);
        wr.pos(box.minX, box.maxY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.minY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.maxY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.minX, box.minY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.maxY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.minY, box.minZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.maxY, box.maxZ).color(r, g, b, alpha).endVertex();
        wr.pos(box.maxX, box.minY, box.maxZ).color(r, g, b, alpha).endVertex();
        tess.draw();
    }

    public static class ItemRenderer {

        public static void render(ItemStack iS, int x, int y) {
            if (iS != null) {
                Smok.inst.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                Smok.inst.mc.getRenderItem().renderItemIntoGUI(iS, x, y);
            }
        }

    }

}