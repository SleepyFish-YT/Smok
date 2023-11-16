package me.sleepyfish.smok.utils.render.color;

import java.awt.Color;

public class NewColorUtils {

    public static Color backgroundColor = ColorUtils.getBackgroundColor(4);
    public static Color backgroundColorGradient = ColorUtils.getBackgroundColor(3);
    public static Color backgroundColorGradientAlpha = ColorUtils.getBackgroundColor(3, 190);

    public static Color fontColor = ColorUtils.getFontColor(1);
    public static Color fontColorGradient = ColorUtils.getFontColor(2);

    public static Color mainIconColor = ColorUtils.getIconColor();
    public static Color clientColorDefIndex = ColorUtils.getClientColor(1);
    public static Color clientColorBigIndex = ColorUtils.getClientColor(90000);

    public static void updateColors() {
        backgroundColor = ColorUtils.getBackgroundColor(4);
        backgroundColorGradient = ColorUtils.getBackgroundColor(3);
        backgroundColorGradientAlpha = ColorUtils.getBackgroundColor(3, 190);

        fontColor = ColorUtils.getFontColor(1);
        fontColorGradient = ColorUtils.getFontColor(2);

        mainIconColor = ColorUtils.getIconColor();
        clientColorDefIndex = ColorUtils.getClientColor(1);
        clientColorBigIndex = ColorUtils.getClientColor(90000);
    }

}