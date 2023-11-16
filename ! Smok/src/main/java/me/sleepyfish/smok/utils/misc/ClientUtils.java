package me.sleepyfish.smok.utils.misc;

import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.rats.impl.visual.GuiModule;
import me.sleepyfish.smok.utils.render.notifications.Notification;
import me.sleepyfish.smok.utils.render.notifications.NotificationManager;
import net.minecraft.util.ChatComponentText;

import java.net.URL;
import java.awt.Desktop;
import java.io.InputStream;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;

// Class from SMok Client by SleepyFish
public class ClientUtils {

    public static final String path = Smok.inst.getClientName().toLowerCase();

    public static String readInputStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line).append('\n');
        } catch (Exception e) {
            ClientUtils.addDebug("readInputStream: " + e.getMessage());
        }

        return stringBuilder.toString();
    }

    public static boolean isSmokTheme() {
        return GuiModule.guiTheme.getMode() == GuiModule.guiThemes.Smok;
    }

    public static void notify(String title, String message, Notification.Icon icon, long seconds) {
        if (GuiModule.toggleNotify.isEnabled()) {
            NotificationManager.show(new Notification(title, message, icon, seconds * 1000L));
        }
    }

    public static String getSeed() {
        return "" + Smok.inst.mc.getIntegratedServer().worldServerForDimension(0).getSeed();
    }

    public static void openLink(String url) {
        try {
            Desktop.getDesktop().browse(new URL(url).toURI());
        } catch (Exception e) {
            ClientUtils.addDebug("openLink: " + e.getMessage());
        }
    }

    public static double getDoubleFromUrl(String url) {
        try {
            URL web = new URL(url);
            URLConnection connection = web.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            return Double.parseDouble(reader.readLine());
        } catch (Exception e) {
            ClientUtils.addDebug("getDoubleFromUrl: " + e.getMessage());
            return 9.9;
        }
    }

    public static void updateClientVersion() {
        Smok.inst.serverVersion = ClientUtils.getDoubleFromUrl("https://raw.githubusercontent.com/SleepyFishYT/SMok-Client/master/Newest-Version.txt");
        ClientUtils.addDebug("updated server double");
    }

    public static void checkLaunch() {
        ClientUtils.updateClientVersion();

        if (ClientUtils.getDoubleFromUrl("https://raw.githubusercontent.com/SleepyFishYT/SMok-Client/master/Newest-Version.txt") > Smok.inst.getClientVersion()) {
            ClientUtils.openLink("https://github.com/SleepyFishYT/SMok-Client/blob/master/Outdatedt-Text.txt");
        }
    }

    public static int getBindForGui(int id) {
        if (id == 1)
            return 211;

        if (id == 2)
            return 54;

        if (id == 3)
            return 14;

        return 0;
    }

    public static boolean inClickGui() {
        return Smok.inst.mc.currentScreen == Smok.inst.guiManager.getClickGui() || Smok.inst.mc.currentScreen == Smok.inst.guiManager.getCapeGui();
    }

    public static void addMessage(String message) {
        Smok.inst.mc.thePlayer.addChatMessage(new ChatComponentText(message));
    }

    public static void addDebug(String message) {
        if (Smok.inst.debugMode && Smok.inst.mc.thePlayer != null) {
            ClientUtils.addMessage("Debug: " + message);
        }
    }

}