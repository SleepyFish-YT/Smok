package me.sleepyfish.smok.utils.render.color;

import java.awt.Color;
import java.time.Month;
import java.time.LocalDate;
import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.utils.misc.MathUtils;
import me.sleepyfish.smok.utils.render.animation.simple.SimpleAnimation;
import net.minecraft.client.renderer.GlStateManager;

// Class from SMok Client by SleepyFish
public class ColorUtils {

    public static Color vapeBackgroundColor = new Color(0xFF1A191A);
    public static Color vapeIconColor = new Color(0xFF777777);

    public static SimpleAnimation[] animation = new SimpleAnimation[]{
            new SimpleAnimation(0.0F), new SimpleAnimation(0.0F),
            new SimpleAnimation(0.0F), new SimpleAnimation(0.0F),
            new SimpleAnimation(0.0F), new SimpleAnimation(0.0F),
            new SimpleAnimation(0.0F), new SimpleAnimation(0.0F),
            new SimpleAnimation(0.0F), new SimpleAnimation(0.0F),
            new SimpleAnimation(0.0F), new SimpleAnimation(0.0F),
            new SimpleAnimation(0.0F), new SimpleAnimation(0.0F),
            new SimpleAnimation(0.0F), new SimpleAnimation(0.0F),
            new SimpleAnimation(0.0F), new SimpleAnimation(0.0F)
    };

    public static Color getIconColor() {
        return vapeIconColor;
    }

    public static Color getBackgroundColor(int id, int alpha) {
        int speed = 50;
        Color rawColor = getRawBackgroundColor(id);

        if (!isGooberDate()) {
            if (id == 1) {
                animation[0].setAnimation((float) rawColor.getRed(), speed);
                animation[1].setAnimation((float) rawColor.getGreen(), speed);
                animation[2].setAnimation((float) rawColor.getBlue(), speed);
                return new Color((int) animation[0].getValue(), (int) animation[1].getValue(), (int) animation[2].getValue(), alpha);
            } else if (id == 2) {
                animation[3].setAnimation((float) rawColor.getRed(), speed);
                animation[4].setAnimation((float) rawColor.getGreen(), speed);
                animation[5].setAnimation((float) rawColor.getBlue(), speed);
                return new Color((int) animation[3].getValue(), (int) animation[4].getValue(), (int) animation[5].getValue(), alpha);
            } else if (id == 3) {
                animation[6].setAnimation((float) rawColor.getRed(), speed);
                animation[7].setAnimation((float) rawColor.getGreen(), speed);
                animation[8].setAnimation((float) rawColor.getBlue(), speed);
                return new Color((int) animation[6].getValue(), (int) animation[7].getValue(), (int) animation[8].getValue(), alpha);
            } else if (id == 4) {
                animation[9].setAnimation((float) rawColor.getRed(), speed);
                animation[10].setAnimation((float) rawColor.getGreen(), speed);
                animation[11].setAnimation((float) rawColor.getBlue(), speed);
                return new Color((int) animation[9].getValue(), (int) animation[10].getValue(), (int) animation[11].getValue(), alpha);
            } else {
                return rawColor;
            }
        } else {
            return niceRainbow(1);
        }
    }

    public static Color getBackgroundColor(int id) {
        return getBackgroundColor(id, 255);
    }

    private static Color getRawBackgroundColor(int id) {
        Color color = new Color(255, 0, 0);

        if (!isGooberDate()) {
            switch (id) {
                case 1:
                    color = new Color(26, 33, 42);
                    break;

                case 2:
                    color = new Color(35, 40, 46);
                    break;

                case 3:
                    color = new Color(46, 51, 57);
                    break;

                case 4:
                    color = vapeBackgroundColor;
                    break;
            }

            return color;
        } else {
            return niceRainbow(1);
        }
    }

    public static Color getFontColor(int id, int alpha) {
        Color rawColor = getRawFontColor(id);
        int speed = 12;

        if (!isGooberDate()) {
            if (id == 1) {
                animation[12].setAnimation((float) rawColor.getRed(), speed);
                animation[13].setAnimation((float) rawColor.getGreen(), speed);
                animation[14].setAnimation((float) rawColor.getBlue(), speed);
                return new Color((int) animation[12].getValue(), (int) animation[13].getValue(), (int) animation[14].getValue(), alpha);
            } else if (id == 2) {
                animation[15].setAnimation((float) rawColor.getRed(), speed);
                animation[16].setAnimation((float) rawColor.getGreen(), speed);
                animation[17].setAnimation((float) rawColor.getBlue(), speed);
                return new Color((int) animation[15].getValue(), (int) animation[16].getValue(), (int) animation[17].getValue(), alpha);
            } else {
                return rawColor;
            }
        } else {
            return niceRainbow(1);
        }
    }

    private static Color getRawFontColor(int id) {
        Color color = new Color(0, 0, 255);

        if (!isGooberDate()) {
            switch (id) {
                case 1:
                    color = new Color(255, 255, 255);
                    break;
                case 2:
                    color = new Color(207, 209, 210);
            }

            return color;
        } else {
            return niceRainbow(1);
        }
    }

    public static Color getFontColor(int id) {
        return getFontColor(id, 255);
    }

    public static Color getClientColor(int index, int alpha) {
        if (Smok.inst.colManager.getColorMode().isSpecial()) {
            return niceRainbow(index, alpha);
        } else {
            for (AccentColor c : Smok.inst.colManager.getColorModes()) {
                if (c.equals(Smok.inst.colManager.getColorMode())) {
                    return interpolateColorsBackAndForth(15, index, new Color(c.getColor1().getRed(), c.getColor1().getGreen(), c.getColor1().getBlue(), alpha),
                            new Color(c.getColor2().getRed(), c.getColor2().getGreen(), c.getColor2().getBlue(), alpha), false);
                }
            }

            return interpolateColorsBackAndForth(15, index, new Color(234, 107, 149, alpha), new Color(238, 164, 123, alpha), false);
        }
    }

    public static Color getClientColor(int index) {
        return Smok.inst.colManager.getColorMode().isSpecial() ? niceRainbow(index, 255) : getClientColor(index, 255);
    }

    public static Color interpolateColorsBackAndForth(int speed, int index, Color start, Color end, boolean trueColor) {
        int angle = (int) ((System.currentTimeMillis() / (long) speed + (long) index) % 360L);
        angle = (angle >= 180 ? 360 - angle : angle) * 2;
        return trueColor ? interpolateColorHue(start, end, (float) angle / 360.0F) : getInterpolateColor(start, end, (float) angle / 360.0F);
    }

    private static Color getInterpolateColor(Color color1, Color color2, float amount) {
        amount = Math.min(1.0F, Math.max(0.0F, amount));
        return new Color(MathUtils.interpolateInt(color1.getRed(), color2.getRed(), amount),
                MathUtils.interpolateInt(color1.getGreen(), color2.getGreen(), amount),
                MathUtils.interpolateInt(color1.getBlue(), color2.getBlue(), amount),
                MathUtils.interpolateInt(color1.getAlpha(), color2.getAlpha(), amount));
    }

    private static Color interpolateColorHue(Color color1, Color color2, float amount) {
        amount = Math.min(1.0F, Math.max(0.0F, amount));
        float[] color1HSB = Color.RGBtoHSB(color1.getRed(), color1.getGreen(), color1.getBlue(), null);
        float[] color2HSB = Color.RGBtoHSB(color2.getRed(), color2.getGreen(), color2.getBlue(), null);
        Color resultColor = Color.getHSBColor(MathUtils.interpolateFloat(color1HSB[0], color2HSB[0], amount),
                MathUtils.interpolateFloat(color1HSB[1], color2HSB[1], amount),
                MathUtils.interpolateFloat(color1HSB[2], color2HSB[2], amount));
        return new Color(resultColor.getRed(), resultColor.getGreen(), resultColor.getBlue(), MathUtils.interpolateInt(color1.getAlpha(), color2.getAlpha(), (double) amount));
    }

    public static void setColor(Color color, float alpha) {
        float r = (float) (color.getRGB() >> 16 & 0xFF) / 255.0F;
        float g = (float) (color.getRGB() >> 8 & 0xFF) / 255.0F;
        float b = (float) (color.getRGB() & 0xFF) / 255.0F;
        GlStateManager.color(r, g, b, alpha);
    }

    public static void setColor(Color color) {
        setColor(color, (float) (color.getRGB() >> 24 & 0xFF) / 255.0F);
    }

    public static void clearColor() {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static Color rainbow(int index, double speed, int opacity) {
        int angle = (int) (((double) System.currentTimeMillis() / speed + (double) index) % 360.0);
        Color c = new Color(Color.HSBtoRGB((float) angle / 360.0F, 0.6F, 1.0F));
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), opacity);
    }

    public static Color niceRainbow(int index, int alpha) {
        return rainbow(index, 25.0, alpha);
    }

    public static Color niceRainbow(int index) {
        return rainbow(index, 25.0, 255);
    }

    public static boolean isGooberDate() {
        LocalDate d = LocalDate.now();
        return d.getDayOfMonth() == 24 && d.getMonth() == Month.DECEMBER
                || d.getDayOfMonth() == 11 && d.getMonth() == Month.SEPTEMBER;
    }

    public static void colorFix() {
        ColorUtils.clearColor();
        ColorUtils.setColor(Color.gray);
        ColorUtils.clearColor();
    }

}