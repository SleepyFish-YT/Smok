package me.sleepyfish.smok.utils.font;

import java.awt.Font;
import java.util.Map;
import java.util.HashMap;
import java.io.InputStream;

import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.ScaledResolution;

// Class from SMok Client by SleepyFish
public class FontUtils {

    public static MinecraftFontRenderer r12, r16, r20, r22, r24, rBold18, rBold20, rBold22, rBold26, rBold14, i18, i20, i202, i24, i28;

    private static int prevScale;

    public static void inject() {
        Map<String, Font> locationMap = new HashMap<>();
        ScaledResolution sr = new ScaledResolution(Smok.inst.mc);
        int scale = sr.getScaleFactor();
        if (scale != prevScale) {
            prevScale = scale;
            Font r12_ = getFont(locationMap, "regular.ttf", 12.0F);
            r12 = new MinecraftFontRenderer(r12_);
            Font r16_ = getFont(locationMap, "regular.ttf", 16.0F);
            r16 = new MinecraftFontRenderer(r16_);
            Font r20_ = getFont(locationMap, "regular.ttf", 20.0F);
            r20 = new MinecraftFontRenderer(r20_);
            Font r22_ = getFont(locationMap, "regular.ttf", 22.0F);
            r22 = new MinecraftFontRenderer(r22_);
            Font r24_ = getFont(locationMap, "regular.ttf", 24.0F);
            r24 = new MinecraftFontRenderer(r24_);
            Font rBold18_ = getFont(locationMap, "regular_bold.ttf", 18.0F);
            rBold18 = new MinecraftFontRenderer(rBold18_);
            Font rBold20_ = getFont(locationMap, "regular_bold.ttf", 20.0F);
            rBold20 = new MinecraftFontRenderer(rBold20_);
            Font rBold22_ = getFont(locationMap, "regular_bold.ttf", 22.0F);
            rBold22 = new MinecraftFontRenderer(rBold22_);
            Font rBold26_ = getFont(locationMap, "regular_bold.ttf", 26.0F);
            rBold26 = new MinecraftFontRenderer(rBold26_);
            Font rBold14_ = getFont(locationMap, "regular_bold.ttf", 14.0F);
            rBold14 = new MinecraftFontRenderer(rBold14_);
            Font i18_ = getFont(locationMap, "icon.ttf", 18.0F);
            i18 = new MinecraftFontRenderer(i18_);
            Font i20_ = getFont(locationMap, "icon.ttf", 20.0F);
            i20 = new MinecraftFontRenderer(i20_);
            Font i202_ = getFont(locationMap, "icon2.ttf", 20.0F);
            i202 = new MinecraftFontRenderer(i202_);
            Font i24_ = getFont(locationMap, "icon.ttf", 24.0F);
            i24 = new MinecraftFontRenderer(i24_);
            Font i28_ = getFont(locationMap, "icon.ttf", 28.0F);
            i28 = new MinecraftFontRenderer(i28_);
        }

    }

    public static void unInject() {
        i28 = null;
        i24 = null;
        i202 = null;
        i20 = null;
        i18 = null;
        rBold14 = null;
        rBold26 = null;
        rBold22 = null;
        rBold20 = null;
        rBold18 = null;
        r24 = null;
        r22 = null;
        r20 = null;
        r16 = null;
        r12 = null;
        prevScale = 0;
    }

    public static Font getFont(Map<String, Font> locationMap, String location, float size) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        size *= (float) sr.getScaleFactor() / 2.0F;

        Font font;
        try {
            if (locationMap.containsKey(location)) {
                font = locationMap.get(location).deriveFont(0, size);
            } else {
                InputStream is = Minecraft.getMinecraft()
                        .getResourceManager().getResource(new ResourceLocation(ClientUtils.path + "/fonts/" + location))
                        .getInputStream();
                locationMap.put(location, font = Font.createFont(0, is));
                font = font.deriveFont(0, size);
            }
        } catch (Exception ignored) {
            font = new Font("default", 0, 10);
        }

        return font;
    }

}