package me.sleepyfish.smok.utils.render;

import java.awt.Color;

import me.sleepyfish.smok.rats.impl.visual.GuiModule;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.utils.render.color.ColorUtils;
import me.sleepyfish.smok.utils.render.shader.ShaderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

// Class from SMok Client by SleepyFish
public class RoundedUtils {

    private static final ShaderUtils roundedGradientShader = new ShaderUtils(ClientUtils.path + "/shaders/roundedRectGradient.frag");
    public static ShaderUtils roundedOutlineShader = new ShaderUtils(ClientUtils.path + "/shaders/roundRectOutline.frag");
    public static ShaderUtils roundedShader = new ShaderUtils(ClientUtils.path + "/shaders/roundedRect.frag");

    public static void drawGradientRoundLR(float x, float y, float width, float height, float radius, Color color1, Color color2) {
        drawGradientRound(x, y, width, height, radius, color1, color2, color2, color1);
    }

    public static void drawRound(float x, float y, float width, float height, float radius, Color color) {
        if (!GuiModule.rounded.isEnabled()) {
            radius = 0;
        }

        ColorUtils.clearColor();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        roundedShader.init();
        setupRoundedRectUniforms(x, y, width, height, radius, roundedShader);
        roundedShader.setUniformf("color", (float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F, (float) color.getAlpha() / 255.0F);
        ShaderUtils.drawQuads(x - 1.0F, y - 1.0F, width + 2.0F, height + 2.0F);
        roundedShader.unload();
        GlStateManager.disableBlend();
    }

    public static void drawGradientRound(float x, float y, float width, float height, float radius, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight) {
        if (!GuiModule.rounded.isEnabled()) {
            radius = 0;
        }

        ColorUtils.clearColor();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        roundedGradientShader.init();
        setupRoundedRectUniforms(x, y, width, height, radius, roundedGradientShader);
        roundedGradientShader.setUniformf("color1", (float) topLeft.getRed() / 255.0F, (float) topLeft.getGreen() / 255.0F, (float) topLeft.getBlue() / 255.0F, (float) topLeft.getAlpha() / 255.0F);
        roundedGradientShader.setUniformf("color2", (float) bottomRight.getRed() / 255.0F, (float) bottomRight.getGreen() / 255.0F, (float) bottomRight.getBlue() / 255.0F, (float) bottomRight.getAlpha() / 255.0F);
        roundedGradientShader.setUniformf("color3", (float) bottomLeft.getRed() / 255.0F, (float) bottomLeft.getGreen() / 255.0F, (float) bottomLeft.getBlue() / 255.0F, (float) bottomLeft.getAlpha() / 255.0F);
        roundedGradientShader.setUniformf("color4", (float) topRight.getRed() / 255.0F, (float) topRight.getGreen() / 255.0F, (float) topRight.getBlue() / 255.0F, (float) topRight.getAlpha() / 255.0F);
        ShaderUtils.drawQuads(x - 1.0F, y - 1.0F, width + 2.0F, height + 2.0F);
        roundedGradientShader.unload();
        GlStateManager.disableBlend();
    }

    public static void drawRoundOutline(float x, float y, float width, float height, float radius, float outlineThickness, Color color, Color outlineColor) {
        if (!GuiModule.rounded.isEnabled()) {
            radius = 0;
        }

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        roundedOutlineShader.init();
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        setupRoundedRectUniforms(x, y, width, height, radius, roundedOutlineShader);
        roundedOutlineShader.setUniformf("outlineThickness", outlineThickness * (float) sr.getScaleFactor());
        roundedOutlineShader.setUniformf("color", (float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F, (float) color.getAlpha() / 255.0F);
        roundedOutlineShader.setUniformf("outlineColor", (float) outlineColor.getRed() / 255.0F, (float) outlineColor.getGreen() / 255.0F, (float) outlineColor.getBlue() / 255.0F,  (float) outlineColor.getAlpha() / 255.0F);
        ShaderUtils.drawQuads(x - (2.0F + outlineThickness), y - (2.0F + outlineThickness), width + 4.0F + outlineThickness * 2.0F, height + 4.0F + outlineThickness * 2.0F);
        roundedOutlineShader.unload();
    }

    private static void setupRoundedRectUniforms(float x, float y, float width, float height, float radius, ShaderUtils textShader) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        textShader.setUniformf("location", x * (float) sr.getScaleFactor(), (float) Minecraft.getMinecraft().displayHeight - height * (float) sr.getScaleFactor() - y * (float) sr.getScaleFactor());
        textShader.setUniformf("rectSize", width * (float) sr.getScaleFactor(), height * (float) sr.getScaleFactor());
        textShader.setUniformf("radius", radius * (float) sr.getScaleFactor());
    }

}