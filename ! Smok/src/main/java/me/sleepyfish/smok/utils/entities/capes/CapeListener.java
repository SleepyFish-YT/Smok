//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.sleepyfish.smok.utils.entities.capes;

import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.utils.backend.events.BackendMessageReceivedEvent;
import me.sleepyfish.smok.utils.backend.events.BackendReadyEvent;
import me.sleepyfish.smok.utils.backend.json.Json;
import me.sleepyfish.smok.utils.backend.json.JsonParser;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.weavemc.loader.api.event.SubscribeEvent;

public class CapeListener {

    @SubscribeEvent
    public void handleBackendConnect(BackendReadyEvent event) {
        ClientUtils.addDebug("Backend ready !");
        ArrayList<UUID> uuids = this.getOnlinePlayersUUIDS();

        assert Smok.inst.mc.thePlayer != null;

        UUID self = Smok.inst.mc.thePlayer.getUniqueID();
        ConcurrentHashMap<String, Object> data = new ConcurrentHashMap<>();
        data.put("data", uuids.stream().filter((uuid) -> {
            return !uuids.contains(self);
        }).map(UUID::toString).collect(Collectors.toList()));
        event.getBackend().sendCosmetics(data);
    }

    @SubscribeEvent
    public void handleBackendMessage(BackendMessageReceivedEvent event) {
        ClientUtils.addDebug("Backend message : " + event.getMessage());
        Json json = JsonParser.parse(event.getMessage());

        assert json != null;

        if (json.contains("data") || json.contains("uuid")) {
            if (json.contains("uuid")) {
                this.updateCosmetics(UUID.fromString(json.getString("uuid")), json);
            } else {
                JsonElement data = json.get("data");
                if (data.isJsonArray()) {
                    Iterator var4 = data.getAsJsonArray().iterator();

                    while (var4.hasNext()) {
                        JsonElement element = (JsonElement) var4.next();
                        Json target = JsonParser.parse(element);
                        this.updateCosmetics(UUID.fromString(target.getString("uuid")), target);
                    }

                }
            }
        }
    }

    private ArrayList<UUID> getOnlinePlayersUUIDS() {
        ArrayList<UUID> ids = new ArrayList<>();

        for (EntityPlayer player : Smok.inst.mc.theWorld.playerEntities) {
            if (!player.isDead) {
                ids.add(player.getUniqueID());
            } else {
                ids.remove(player.getUniqueID());
            }
        }

        return ids;
    }

    private void updateCosmetics(UUID uuid, Json json) {
        if (json.contains("cosmetics")) {
            json = JsonParser.parse(json.get("cosmetics"));
            if (json.contains("cape")) {
                System.out.println("CAPE OTHER GYU:" + json.getString("cape"));
                Cape cape = Cape.fromName(json.getString("cape"));

                if (cape != null) {
                    Smok.inst.capeManager.setForPlayer(uuid, cape);
                } else {
                    Smok.inst.capeManager.removeCapeSelf();
                }
            }

        }
    }
}