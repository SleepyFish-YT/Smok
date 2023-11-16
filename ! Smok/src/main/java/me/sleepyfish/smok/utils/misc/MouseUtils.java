package me.sleepyfish.smok.utils.misc;

import me.sleepyfish.smok.utils.render.RoundedUtils;
import org.lwjgl.input.Mouse;
import java.awt.Color;

// Class from SMok Client by SleepyFish
public class MouseUtils {

    public static final int MOUSE_RIGHT = 1;
    public static final int MOUSE_RIGHT_EVENT = 4;
    public static final int MOUSE_LEFT = 0;
    public static final int MOUSE_LEFT_EVENT = 16;

    public static int mouseX;
    public static int mouseY;

    public static boolean isInside(int mouseX, int mouseY, double x, double y, double width, double height) {
        return (double) mouseX > x && (double) mouseX < x + width && (double) mouseY > y && (double) mouseY < y + height;
    }

    public static boolean isInsideRect(float x, float y, float width, float height, float radius, Color color) {
        RoundedUtils.drawRound(x, y, width, height, radius, color);
        return isInside(mouseX, mouseY, x, y, width, height);
    }

    public static boolean isButtonDown(int key) {
        return Mouse.isButtonDown(key);
    }

    public static void setMousePos(int x, int y) {
        Mouse.setCursorPosition(x, y);
    }

    public static int getX() {
        return Mouse.getX();
    }

    public static int getY() {
        return Mouse.getY();
    }

    public static float getRealScroll() {
        return Mouse.getDWheel();
    }

    public static float getScroll() {
        return getRealScroll() * 0.10F;
    }

}