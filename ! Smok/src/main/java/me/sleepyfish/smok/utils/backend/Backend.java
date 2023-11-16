package me.sleepyfish.smok.utils.backend;

import com.google.gson.JsonElement;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.utils.backend.events.BackendFailureEvent;
import me.sleepyfish.smok.utils.backend.events.BackendMessageReceivedEvent;
import me.sleepyfish.smok.utils.backend.events.BackendReadyEvent;
import me.sleepyfish.smok.utils.backend.json.Json;
import me.sleepyfish.smok.utils.backend.json.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// Class from SMok Client by SleepyFish
public class Backend {

    private OkHttpClient client;
    private WebSocket socket;

    public Backend() {
        this.connect();
    }

    public void connect() {
        if (!this.isConnected()) {
            this.client = (new OkHttpClient.Builder()).connectTimeout(1L, TimeUnit.MINUTES).writeTimeout(0L, TimeUnit.MILLISECONDS).readTimeout(0L, TimeUnit.MILLISECONDS).build();
            Request request = (new Request.Builder()).url("https://cosmetic.skidfucker.dev/v1/cosmetic").build();
            this.socket = this.client.newWebSocket(request, new WebSocketListener() {
                public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                    Backend.this.disconnect();
                }

                public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                    BackendFailureEvent event = new BackendFailureEvent();
                    event.callEvent();
                }

                public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                    BackendMessageReceivedEvent event = new BackendMessageReceivedEvent(text);
                    event.callEvent();
                }

                public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                    BackendReadyEvent event = new BackendReadyEvent(Backend.this);
                    event.callEvent();
                }
            });
        }
    }

    public void disconnect() {
        if (this.isConnected()) {
            try {
                if (this.socket != null) {
                    this.socket.close(1000, "");
                }

                this.client.dispatcher().executorService().shutdown();
                boolean success = this.client.dispatcher().executorService().awaitTermination(2L, TimeUnit.SECONDS);
                if (!success) {
                    throw new IllegalAccessException();
                }
            } catch (Exception var2) {
            }

            this.socket = null;
            this.client = null;
        }
    }

    public void sendCosmetics(ConcurrentHashMap<String, Object> additional) {
        assert Smok.inst.mc.thePlayer != null;

        UUID uuid = Smok.inst.mc.thePlayer.getUniqueID();
        ConcurrentHashMap<String, Object> cosmetics = new ConcurrentHashMap();
        cosmetics.put("cape", Smok.inst.capeManager.getCurrentCape() == null ? "INVALID" : Smok.inst.capeManager.getCurrentCape().getName());
        Json json = JsonParser.parse("{}");
        json.set("uuid", uuid.toString());
        json.set("cosmetics", this.bakeData(cosmetics));
        if (additional != null) {
            additional.forEach(json::set);
        }

        this.send(json);
    }

    public void send(Json json) {
        this.send(json);
    }

    public void send(String message) {
        if (this.socket != null) {
            this.socket.send(message);
        }

    }

    private JsonElement bakeData(ConcurrentHashMap<String, Object> elements) {
        Json object = JsonParser.create();

        assert object != null;

        elements.forEach(object::set);
        return object.getObject();
    }

    public boolean isConnected() {
        return this.client != null;
    }

}