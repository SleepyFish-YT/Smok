//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.sleepyfish.smok.utils.entities.capes;

import java.util.ArrayList;
import java.util.UUID;
import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.utils.entities.capes.impl.User;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;

public class CapeManager {

    private final ArrayList<Cape> capes = new ArrayList();
    private Cape currentCape;

    public CapeManager() {
        this.addCape(new Cape("Smok", this.getPath("smok"), this.getPreview("smok")));
        this.addCape(new Cape("Wet Cat", this.getPath("wetcat"), this.getPreview("wetcat")));
        this.addCape(new Cape("FPS", this.getPath("minusFps"), this.getPreview("minusFps")));
        this.addCape(new Cape("Lunar", this.getPath("lunar"), this.getPreview("lunar")));
        this.addCape(new Cape("Tenacity", this.getPath("tena"), this.getPreview("tena")));
        this.addCape(new Cape("Import", this.getPath("import_rat"), this.getPreview("import_rat")));
        this.addCape(new Cape("Black Rat", this.getPath("rat_black"), this.getPreview("rat_black")));
        this.addCape(new Cape("White Rat", this.getPath("rat_white"), this.getPreview("rat_white")));
        this.addCape(new Cape("Walter", this.getPath("walter"), this.getPreview("walter")));
        this.addCape(new Cape("Comet", this.getPath("comet"), this.getPreview("comet")));
        this.currentCape = null;
    }

    public void unInject() {
        this.currentCape = null;
        this.getCapes().clear();
        Smok.inst.capeManager = null;
    }

    public Cape getCurrentCape() {
        return this.currentCape;
    }

    public void setCurrentCape(Cape currentCape) {
        this.currentCape = currentCape;
    }

    private void addCape(Cape cape) {
        this.capes.add(cape);
    }

    public ArrayList<Cape> getCapes() {
        return this.capes;
    }

    public Cape getCapeByName(String name) {
        return name != "null" ? this.getCapes().stream().filter((cape) -> {
            return cape.getName().equals(name);
        }).findFirst().orElse(null) : null;
    }

    private ResourceLocation getPath(String name) {
        return new ResourceLocation(ClientUtils.path + "/capes/" + name + "_use.png");
    }

    private ResourceLocation getPreview(String name) {
        return new ResourceLocation(ClientUtils.path + "/capes/" + name + "_pre.png");
    }

    public void setForPlayer(UUID uuid, Cape cape) {
        if (cape != null) {
            NetHandlerPlayClient connection = Smok.inst.mc.getNetHandler();
            if (connection != null) {
                NetworkPlayerInfo info = connection.getPlayerInfo(uuid);
                ResourceLocation location = cape.getFile();
                if (info.getLocationCape() == null || !info.getLocationCape().getResourcePath().equals(location.getResourcePath())) {
                    Smok.inst.capeManager.setCapeForOtherPlayer(uuid, cape);
                    Smok.inst.smokUser.add(new User(uuid, cape.getFile()));
                }
            }
        }
    }

    private void setCapeForOtherPlayer(UUID uuid, Cape cape) {
        Smok.inst.smokUser.add(new User(uuid, cape.getFile()));
    }

    public void removeCapeSelf() {
    }

    public void registerCape(JsonCape jsonCape) {
    }

}