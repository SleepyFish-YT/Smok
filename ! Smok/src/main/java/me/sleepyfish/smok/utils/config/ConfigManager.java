package me.sleepyfish.smok.utils.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.rats.impl.visual.Text_Gui;
import me.sleepyfish.smok.utils.entities.capes.Cape;
import me.sleepyfish.smok.utils.misc.ClientUtils;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

// Class from SMok Client by SleepyFish
public class ConfigManager {

    private File cfgDir;
    private final ArrayList<Config> configs;
    private Config moduleCfg;

    public ConfigManager() {
        this.cfgDir = new File(Smok.inst.mc.mcDataDir + File.separator + ".weave" + File.separator + ClientUtils.path + File.separator + "configs");
        this.configs = new ArrayList<>();

        if (!cfgDir.isDirectory()) {
            cfgDir.mkdirs();
        }

        this.discoverConfigs();
        File defaultFile = new File(cfgDir, "default.cfg");
        this.moduleCfg = new Config(defaultFile);

        if (!defaultFile.exists()) {
            this.save();
        } else {
            this.loadConfigByName("default");
        }

        try {
            Desktop.getDesktop().open(cfgDir);
        } catch (Exception ignored) {
        }
    }

    public void unInject() {
        this.save();

        this.cfgDir = null;
        this.moduleCfg = null;
        this.configs.clear();
    }

    @SuppressWarnings("unused")
    public static boolean isOutdated(File file) {
        JsonParser jsonParser = new JsonParser();

        try (FileReader reader = new FileReader(file)) {
            Object obj = jsonParser.parse(reader);
            JsonObject data = (JsonObject) obj;
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public void discoverConfigs() {
        configs.clear();
        if (cfgDir.listFiles() == null || !(Objects.requireNonNull(cfgDir.listFiles()).length > 0))
            return;

        for (File file : Objects.requireNonNull(cfgDir.listFiles())) {
            if (file.getName().endsWith(".cfg")) {
                if (!this.isOutdated(file)) {
                    configs.add(new Config(new File(file.getPath())));
                }
            }
        }
    }

    public Config getModuleCfg() {
        return moduleCfg;
    }

    public void save() {
        Smok.inst.clientConfig.saveConfig();

        JsonObject data = new JsonObject();
        data.addProperty("client", "smok");
        data.addProperty("version", Smok.inst.getClientVersion());
        data.addProperty("author", Smok.inst.mc.getSession().getUsername());
        data.addProperty("created", LocalDate.now().getDayOfMonth() + "/" + LocalDate.now().getMonth() + "/" + LocalDate.now().getYear());

        Cape nC = Smok.inst.capeManager.getCurrentCape();
        if (nC == null) {
            nC = Smok.inst.capeManager.getCapeByName("Black Rat");
        }

        data.addProperty("cape", nC.getName());

        JsonObject ratsObj = new JsonObject();
        for (Rat rat : Smok.inst.ratManager.getRats()) {
            ratsObj.add(rat.getName(), rat.getConfigAsJson());
        }

        data.add("rats", ratsObj);
        this.moduleCfg.save(data);
    }

    public void setModuleCfg(Config moduleCfg) {
        this.moduleCfg = moduleCfg;

        JsonObject cfgData = moduleCfg.getData();
        Cape configCape = Smok.inst.capeManager.getCapeByName(cfgData.get("cape").getAsString());
        Smok.inst.capeManager.setCurrentCape(configCape);

        JsonObject ratData = cfgData.get("rats").getAsJsonObject();
        for (Rat rat : Smok.inst.ratManager.getRats()) {
            if (ratData.has(rat.getName())) {
                rat.applyConfigFromJson(
                        ratData.get(rat.getName()).getAsJsonObject()
                );
            } else {
                rat.resetToDefaults();
            }
        }
    }

    public void loadConfigByName(String replace) {
        this.discoverConfigs();

        for (Config config : configs) {
            if (config.getName().equals(replace))
                this.setModuleCfg(config);
        }
    }

    public ArrayList<Config> getConfigs() {
        this.discoverConfigs();
        return configs;
    }

    public void copyConfig(Config config, String s) {
        File file = new File(cfgDir, s);
        Config newConfig = new Config(file);
        newConfig.save(config.getData());
    }

    public void resetConfig() {
        Smok.inst.ratManager.getRats().forEach(Rat::resetToDefaults);
        this.save();
    }

    public void deleteConfig(Config config) {
        config.file.delete();

        if (config.getName().equals(this.moduleCfg.getName())) {
            this.discoverConfigs();

            if (this.configs.size() < 2) {
                this.resetConfig();

                File defaultFile = new File(cfgDir, "default.cfg");
                this.moduleCfg = new Config(defaultFile);

                this.save();
            } else {
                this.moduleCfg = this.configs.get(0);
            }

            this.save();
        }
    }

}