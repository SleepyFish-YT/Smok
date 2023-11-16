package me.sleepyfish.smok.utils.config;

import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.gui.comp.impl.CategoryComp;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ClientConfig {

    private final File configFile;
    private final String fileName = "config";
    private final String clickGuiPosPrefix = "clickgui-pos~ ";
    private final String loadedConfigPrefix = "loaded-cfg~ ";

    public ClientConfig() {
        File configDir = new File(Minecraft.getMinecraft().mcDataDir, ClientUtils.path);
        if (!configDir.exists())
            configDir.mkdir();

        configFile = new File(configDir, fileName);
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (Exception ignored) {
            }
        }
    }

    public void saveConfig() {
        List<String> config = new ArrayList<>();
        config.add(clickGuiPosPrefix + getClickGuiPos());
        config.add(loadedConfigPrefix + Smok.inst.confManager.getModuleCfg().getName());

        try {
            PrintWriter writer;
            writer = new PrintWriter(this.configFile);
            for (String line : config)
                writer.println(line);

            writer.close();
        } catch (Exception ignored) {
        }
    }

    public void applyConfig() {
        List<String> config = this.parseConfigFile();

        for (String line : config) {
            if (line.startsWith(clickGuiPosPrefix)) {
                loadClickGuiCoords(line.replace(clickGuiPosPrefix, ""));
            } else if (line.startsWith(loadedConfigPrefix)) {
                Smok.inst.confManager.loadConfigByName(line.replace(loadedConfigPrefix, ""));
            }
        }
    }

    private List<String> parseConfigFile() {
        List<String> configFileContents = new ArrayList<>();
        Scanner reader = null;

        try {
            reader = new Scanner(this.configFile);
        } catch (Exception ignored) {
        }

        while (reader.hasNextLine())
            configFileContents.add(reader.nextLine());

        return configFileContents;
    }

    private void loadClickGuiCoords(String decryptedString) {
        for (String what : decryptedString.split("/")) {
            for (CategoryComp cat : Smok.inst.guiManager.getClickGui().getCategoryList()) {
                if (what.startsWith(cat.category.name())) {
                    List<String> cfg = this.StringListToList(what.split("~"));
                    cat.setX(Integer.parseInt(cfg.get(1)));
                    cat.setY(Integer.parseInt(cfg.get(2)));
                    cat.setOpened(Boolean.parseBoolean(cfg.get(3)));
                }
            }
        }
    }

    private List<String> StringListToList(String[] strings) {
        List<String> array = new ArrayList<>();
        Collections.addAll(array, strings);
        return array;
    }

    public String getClickGuiPos() {
        StringBuilder posConfig = new StringBuilder();
        for (CategoryComp cat : Smok.inst.guiManager.getClickGui().getCategoryList()) {
            posConfig.append(cat.category.name());
            posConfig.append("~");
            posConfig.append(cat.getX());
            posConfig.append("~");
            posConfig.append(cat.getY());
            posConfig.append("~");
            posConfig.append(cat.isOpened());
            posConfig.append("/");
        }

        return posConfig.substring(0, posConfig.toString().length() - 2);
    }

}