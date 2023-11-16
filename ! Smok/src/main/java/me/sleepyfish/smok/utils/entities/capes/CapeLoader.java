//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.sleepyfish.smok.utils.entities.capes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.utils.backend.json.Json;
import me.sleepyfish.smok.utils.backend.json.JsonParser;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

public class CapeLoader {

    public Cape load(String name) {
        String data = null;

        try {
            InputStream stream = (InputStream) Objects.requireNonNull(CapeLoader.class.getClassLoader().getResourceAsStream("assets/minecraft/gooberclient/cosmetics.json"));
            Throwable var4 = null;

            try {
                data = IOUtils.toString(stream, StandardCharsets.UTF_8);
            } catch (Throwable var19) {
                var4 = var19;
                throw var19;
            } finally {
                if (stream != null) {
                    if (var4 != null) {
                        try {
                            stream.close();
                        } catch (Throwable var18) {
                            var4.addSuppressed(var18);
                        }
                    } else {
                        stream.close();
                    }
                }

            }
        } catch (IOException var21) {
            throw new RuntimeException(var21);
        }

        if (data == null) {
            return null;
        } else {
            Json json = JsonParser.parse(data);
            Json cape = null;
            Gson gson = (new GsonBuilder()).create();

            for (Iterator var6 = json.get("capes").getAsJsonArray().iterator(); var6.hasNext(); cape = null) {
                JsonElement element = (JsonElement) var6.next();
                cape = JsonParser.parse(gson.toJson(element));
                if (cape.getString("name").equalsIgnoreCase(name)) {
                    break;
                }
            }

            if (cape == null) {
                return null;
            } else if (!cape.contains("frameAmount")) {
                JsonCape jsonCape = new JsonCape(cape.contains("display") ? cape.getString("display") : cape.getString("name"), cape.getString("name"), cape.contains("frameAmount") ? cape.getInt("frameAmount") : 0, cape.contains("delay") ? cape.getInt("delay") : 0, new ConcurrentLinkedQueue((Collection) cape.getStringList("frames").stream().map(ResourceLocation::new).collect(Collectors.toList())));
                Smok.inst.capeManager.registerCape(jsonCape);
                return jsonCape;
            } else {
                int frameAmount = cape.getInt("frameAmount");
                Pattern pattern = Pattern.compile("^(?:\\w+/)*frame-\\{FRAME_NUMBER\\}\\.png$");
                List<String> list = (List) cape.getStringList("frames").stream().collect(Collectors.toList());
                String framePath = null;
                Iterator var10 = list.iterator();

                while (var10.hasNext()) {
                    String frames = (String) var10.next();
                    Matcher matcher = pattern.matcher(frames);
                    if (matcher.matches()) {
                        framePath = matcher.group();
                        System.out.println("Matched: " + framePath);
                    }
                }

                ConcurrentLinkedQueue<ResourceLocation> locations = new ConcurrentLinkedQueue();
                ++frameAmount;

                for (int frameNumber = 0; frameNumber < frameAmount; ++frameNumber) {
                    String replaced = framePath.replace("{FRAME_NUMBER}", String.valueOf(frameNumber));
                    locations.add(new ResourceLocation(replaced));
                }

                locations.forEach((resourceLocation) -> {
                    System.out.println(resourceLocation.getResourcePath());
                });
                JsonCape jsonCape = new JsonCape(cape.contains("display") ? cape.getString("display") : cape.getString("name"), cape.getString("name"), cape.getInt("frameAmount"), cape.contains("delay") ? cape.getInt("delay") : 0, locations);
                Smok.inst.capeManager.setCurrentCape(jsonCape);
                return jsonCape;
            }
        }
    }

}