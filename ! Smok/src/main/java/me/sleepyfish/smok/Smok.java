package me.sleepyfish.smok;

import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.gui.GuiManager;
import me.sleepyfish.smok.utils.config.ClientConfig;
import me.sleepyfish.smok.utils.font.FontUtils;
import me.sleepyfish.smok.utils.anticheat.Check;
import me.sleepyfish.smok.utils.backend.Backend;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.rats.event.EventManager;
import me.sleepyfish.smok.utils.entities.SoundUtils;
import me.sleepyfish.smok.utils.entities.RotsManager;
import me.sleepyfish.smok.utils.config.ConfigManager;
import me.sleepyfish.smok.utils.entities.capes.impl.User;
import me.sleepyfish.smok.utils.render.color.ColorManager;
import me.sleepyfish.smok.utils.entities.role.RoleManager;
import me.sleepyfish.smok.utils.entities.capes.CapeManager;
import me.sleepyfish.smok.utils.entities.capes.CapeListener;

import net.minecraft.client.Minecraft;
import net.weavemc.loader.api.event.EventBus;

import org.lwjgl.opengl.Display;

import java.util.List;
import java.util.ArrayList;

// Class from SMok Client by SleepyFish
public class Smok {

    /*
     * - Thanks to DevOfDeath for saving my ass
     * - DevOfDeath's message: i can beat my wife because i have 8 million power in rise of kingdom
     */

    /**
     * Instance : Finally coding in 240 Hz
     **/
    public static final Smok inst = new Smok();

    private double clientVersion;

    private String clientName;
    private String clientDiscordLink;

    public List<User> smokUser;

    public boolean injected = true;
    public boolean debugMode = false;
    public boolean fastMode = true;
    public double serverVersion;

    public Minecraft mc;
    public Rat.Manager ratManager;
    public GuiManager guiManager;
    public ColorManager colManager;
    public EventManager eveManager;
    public RotsManager rotManager;
    public Check.Manager anticheat;
    public Backend backend;
    public CapeManager capeManager;
    public RoleManager roleManager;

    public ClientConfig clientConfig;
    public ConfigManager confManager;

    public void inject() {
        try {
            this.clientName = "Smok";
            this.clientVersion = 2.2;
            this.clientDiscordLink = "https://discord.gg/7JXXvkufJK";
            ClientUtils.checkLaunch();
            this.mc = Minecraft.getMinecraft();

            FontUtils.inject();
            SoundUtils.inject();

            this.ratManager = new Rat.Manager();
            this.guiManager = new GuiManager();
            this.colManager = new ColorManager();
            this.eveManager = new EventManager();
            this.rotManager = new RotsManager();
            this.anticheat = new Check.Manager();
            this.capeManager = new CapeManager();

            this.smokUser = new ArrayList<>();
            this.roleManager = new RoleManager();

            this.confManager = new ConfigManager();
            this.clientConfig = new ClientConfig();
            this.clientConfig.applyConfig();

            EventBus.subscribe(new CapeListener());
        } catch (Exception e) {
            Display.setTitle(e.getClass() + ":" + e.getMessage());
        }
    }

    public void unInject() {
        try {
            this.mc.displayGuiScreen(null);

            this.confManager.unInject();
            this.roleManager.unInject();
            this.capeManager.unInject();
            this.anticheat.unInject();
            this.rotManager.unInject();
            this.eveManager.unInject();
            this.colManager.unInject();
            this.guiManager.unInject();
            this.ratManager.unInject();
            this.smokUser = null;

            SoundUtils.inject();
            FontUtils.inject();

            this.mc = null;
            this.clientDiscordLink = "";
            this.clientVersion = 0;
            this.clientName = "";
            this.fastMode = false;
            this.debugMode = false;
            this.injected = false;
        } catch (Exception e) {
            Display.setTitle("Smok Client: Failed unInjecting. Error: " + e.getMessage());
        }
    }

    public String getClientName() {
        return this.clientName;
    }

    public String getDiscord() {
        return this.clientDiscordLink;
    }

    public double getClientVersion() {
        return this.clientVersion;
    }

    public double getServerVersion() {
        return this.serverVersion;
    }

}