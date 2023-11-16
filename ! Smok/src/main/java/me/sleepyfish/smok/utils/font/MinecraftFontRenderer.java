package me.sleepyfish.smok.utils.font;

import java.awt.Color;
import java.awt.Font;
import me.sleepyfish.smok.utils.render.GlUtils;
import me.sleepyfish.smok.utils.render.color.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;

public class MinecraftFontRenderer extends CFont {

    CFont.CharData[] boldChars = new CFont.CharData[256];
    CFont.CharData[] italicChars = new CFont.CharData[256];
    CFont.CharData[] boldItalicChars = new CFont.CharData[256];

    FontRenderer fr;
    String colorcodeIdentifiers;
    DynamicTexture texBold;
    DynamicTexture texItalic;
    DynamicTexture texItalicBold;
    int[] colorCode;

    public MinecraftFontRenderer(Font font) {
        super(font, true, true);
        this.fr = Minecraft.getMinecraft().fontRendererObj;
        this.colorcodeIdentifiers = "0123456789abcdefklmnor";
        this.colorCode = new int[32];
        this.setupMinecraftColorcodes();
        this.setupBoldItalicIDs();
    }

    public void drawStringWithShadow(String text, double x2, double y2, Color color) {
        this.drawString(text, x2, y2, color, false, 8.3F);
    }

    public int drawString(String text, double x, double y, Color color) {
        return (int) this.drawString(text, x, y, color, false, 8.3F);
    }

    public int drawString(String text, double x, double y) {
        return (int) this.drawString(text, x, y, Color.white, false, 8.3F);
    }

    public int drawString(String text, double x, double y, Color color, boolean shadow) {
        return (int) this.drawString(text, x, y, color, shadow, 8.3F);
    }

    public void drawStringWithClientColor(String text, double x, double y, int opacity, boolean shadow) {
        double xTmp = x;
        boolean hasReachedSS = false;
        boolean hasFinished = false;
        int i = 0;
        char[] var13 = text.toCharArray();
        int var14 = var13.length;

        for (int var15 = 0; var15 < var14; ++var15) {
            char textChar = var13[var15];
            String tmp = String.valueOf(textChar);
            if (Character.toString(textChar).equalsIgnoreCase("Ã¯Â¿Â½Ã¯Â¿Â½")) {
                hasReachedSS = true;
            }

            if (!hasReachedSS) {
                if (shadow) {
                    this.drawStringWithShadow(tmp, xTmp, y, ColorUtils.getClientColor(i, opacity));
                } else {
                    this.drawString(tmp, xTmp, y, ColorUtils.getClientColor(i, opacity));
                }

                xTmp += this.getStringWidth(String.valueOf(textChar));
                text = text.substring(1);
            } else if (!hasFinished) {
                this.drawString(text, xTmp, y, Color.black);
                hasFinished = true;
            }

            i -= 20;
        }

    }

    public void drawStringWithClientColor(String text, double x, double y, boolean shadow) {
        this.drawStringWithClientColor(text, x, y, 255, shadow);
    }

    public int drawPassword(String text, double x2, float y2, Color color) {
        return (int) this.drawString(text.replaceAll("\\.", "."), x2, (double) y2, color, false, 8.0F);
    }

    public float drawString(String text, double x, double y, Color color, boolean shadow, float kerning) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        if (text == null) {
            return 0.0F;
        } else {
            int co = color.getRGB();
            if (shadow) {
                co = (color.getRGB() & 16579836) >> 2 | color.getRGB() & -16777216;
            }

            FontUtils.inject();
            CFont.CharData[] currentData = this.charData;
            float alpha = (float) (co >> 24 & 255) / 255.0F;
            x = (x - 1.0) * (double) sr.getScaleFactor();
            y = (y - 3.0) * (double) ((float) sr.getScaleFactor()) - 0.2;
            GL11.glPushMatrix();
            GL11.glScaled(1.0 / (double) sr.getScaleFactor(), 1.0 / (double) sr.getScaleFactor(), 1.0 / (double) sr.getScaleFactor());
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            ColorUtils.setColor(new Color(co));
            GlStateManager.enableTexture2D();
            GlStateManager.bindTexture(this.tex.getGlTextureId());
            GlUtils.bindTexture(this.tex.getGlTextureId());
            GlStateManager.enableBlend();

            for (int index = 0; index < text.length(); ++index) {
                char character = text.charAt(index);
                if (character == 167) {
                    int colorIndex = 21;

                    try {
                        colorIndex = this.colorcodeIdentifiers.indexOf(text.charAt(index + 1));
                    } catch (Exception e) {
                    }

                    if (colorIndex < 16) {
                        GlStateManager.bindTexture(this.tex.getGlTextureId());
                        currentData = this.charData;
                        if (colorIndex < 0) {
                            colorIndex = 15;
                        }

                        if (shadow) {
                            colorIndex += 16;
                        }

                        ColorUtils.setColor(new Color(this.colorCode[colorIndex]), alpha);
                    } else {
                        ColorUtils.setColor(color);
                        GlStateManager.bindTexture(this.tex.getGlTextureId());
                        currentData = this.charData;
                    }

                    ++index;
                } else if (character < currentData.length) {
                    this.drawLetter(x, y, currentData, character);
                    x += (double) ((float) currentData[character].width - kerning + (float) this.charOffset);
                }
            }

            GlStateManager.disableBlend();
            GL11.glHint(3155, 4352);
            GlUtils.stopScale();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            return (float) x / 2.0F;
        }
    }

    private void drawLetter(double x, double y, CFont.CharData[] currentData, char character) {
        GL11.glBegin(4);
        this.drawChar(currentData, character, (float) x, (float) y);
        GL11.glEnd();
    }

    public double getStringWidth(String text) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        if (text == null) {
            return 0.0;
        } else {
            float width = 0.0F;
            CFont.CharData[] currentData = this.charData;

            for (int index = 0; index < text.length(); ++index) {
                char character = text.charAt(index);
                if (character == 167) {
                    ++index;
                } else if (character < currentData.length) {
                    width += (float) currentData[character].width - 8.3F + (float) this.charOffset;
                }
            }

            return (double) width / (double) sr.getScaleFactor();
        }
    }

    public double getStringWidth(String text, float kerning) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        if (text == null) {
            return 0.0;
        } else {
            float width = 0.0F;
            CFont.CharData[] currentData = this.charData;

            for (int index = 0; index < text.length(); ++index) {
                char c = text.charAt(index);
                if (c == 167) {
                    ++index;
                } else if (c < currentData.length) {
                    width += (float) currentData[c].width - kerning + (float) this.charOffset;
                }
            }

            return (double) width / (double) sr.getScaleFactor();
        }
    }

    public double getHeight() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        return (double) (this.fontHeight - 8) / (double) sr.getScaleFactor();
    }

    public void setFont(Font font) {
        super.setFont(font);
        this.setupBoldItalicIDs();
    }

    public void setAntiAlias(boolean antiAlias) {
        super.setAntiAlias(antiAlias);
        this.setupBoldItalicIDs();
    }

    public void setFractionalMetrics(boolean fractionalMetrics) {
        super.setFractionalMetrics(fractionalMetrics);
        this.setupBoldItalicIDs();
    }

    private void setupBoldItalicIDs() {
        this.texBold = this.setupTexture(this.font.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldChars);
        this.texItalic = this.setupTexture(this.font.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
        this.texItalicBold = this.setupTexture(this.font.deriveFont(3), this.antiAlias, this.fractionalMetrics, this.boldItalicChars);
    }

    private void setupMinecraftColorcodes() {
        for (int index = 0; index < 32; ++index) {
            int noClue = (index >> 3 & 1) * 85;
            int red = (index >> 2 & 1) * 170 + noClue;
            int green = (index >> 1 & 1) * 170 + noClue;
            int blue = (index & 1) * 170 + noClue;
            if (index == 6) {
                red += 85;
            }

            if (index >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }

            this.colorCode[index] = (red & 255) << 16 | (green & 255) << 8 | blue & 255;
        }

    }

}