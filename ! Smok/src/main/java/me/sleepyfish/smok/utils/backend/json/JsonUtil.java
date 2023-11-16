package me.sleepyfish.smok.utils.backend.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.MalformedJsonException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.file.Path;
import me.sleepyfish.smok.utils.backend.json.val.JsonVal;

// Class from SMok Client by SleepyFish
public class JsonUtil {

    public static void writeToFile(File file, JsonElement element) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception var15) {
            }
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            Throwable var3 = null;

            try {
                String jsonArrayString = (new Gson()).toJson(element);
                writer.write(jsonArrayString);
                writer.flush();
            } catch (Throwable var14) {
                var3 = var14;
                throw var14;
            } finally {
                if (writer != null) {
                    if (var3 != null) {
                        try {
                            writer.close();
                        } catch (Throwable var13) {
                            var3.addSuppressed(var13);
                        }
                    } else {
                        writer.close();
                    }
                }

            }
        } catch (Exception var17) {
        }

    }

    public static void writeToFile(String filePath, JsonElement element) {
        File file = new File(filePath);
        writeToFile(file, element);
    }

    public static void writeToFile(Path filePath, JsonElement element) {
        File file = filePath.toFile();
        writeToFile(file, element);
    }

    public static JsonElement readFromFile(File file, Type typeOfSrc) {
        try {
            JsonReader reader = new JsonReader(new FileReader(file));
            JsonElement element = (JsonElement) (new Gson()).fromJson(reader, typeOfSrc);
            if (element != null && JsonVal.isJsonValid(element.toString())) {
                return element;
            } else {
                throw new MalformedJsonException("Failed to read Json!");
            }
        } catch (Exception var4) {
            return null;
        }
    }

}