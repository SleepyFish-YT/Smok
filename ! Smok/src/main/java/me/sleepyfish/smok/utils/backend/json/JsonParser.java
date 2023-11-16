package me.sleepyfish.smok.utils.backend.json;

// Class from SMok Client by SleepyFish
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import me.sleepyfish.smok.utils.backend.json.val.JsonVal;

import java.io.File;
import java.io.FileReader;

public class JsonParser {

    public static Json parse(File f) {
        try {
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }

            f.createNewFile();
            JsonReader reader = new JsonReader(new FileReader(f));
            JsonObject object = (JsonObject) (new Gson()).fromJson(reader, JsonObject.class);
            return object != null && JsonVal.isJsonValid(object.toString()) ? new Json(object) : new Json(com.google.gson.JsonParser.parseString("{}").getAsJsonObject());
        } catch (Exception var3) {
            return null;
        }
    }

    public static Json parse(String json) {
        try {
            if (json != null && JsonVal.isJsonValid(json)) {
                return new Json(com.google.gson.JsonParser.parseString(json).getAsJsonObject());
            }
        } catch (Exception var2) {
        }

        return null;
    }

    public static Json parse(JsonElement element) {
        return parse((new Gson()).toJson(element));
    }

    public static Json create() {
        return new Json(new JsonObject());
    }

}