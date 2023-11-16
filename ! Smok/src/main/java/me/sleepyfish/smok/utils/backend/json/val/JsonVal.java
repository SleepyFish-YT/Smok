package me.sleepyfish.smok.utils.backend.json.val;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

// Class from SMok Client by SleepyFish
public class JsonVal {

    public static boolean isJsonValid(String json) throws IOException {
        return isJsonValid(new StringReader(json));
    }

    public static boolean isJsonValid(Reader reader) throws IOException {
        return isJsonValid(new JsonReader(reader));
    }

    public static boolean isJsonValid(JsonReader jsonReader) throws IOException {
        try {
            while (true) {
                JsonToken token;
                if ((token = jsonReader.peek()) != JsonToken.END_DOCUMENT && token != null) {
                    switch (token) {
                        case BEGIN_ARRAY:
                            jsonReader.beginArray();
                            continue;
                        case END_ARRAY:
                            jsonReader.endArray();
                            continue;
                        case BEGIN_OBJECT:
                            jsonReader.beginObject();
                            continue;
                        case END_OBJECT:
                            jsonReader.endObject();
                            continue;
                        case NAME:
                            jsonReader.nextName();
                            continue;
                        case STRING:
                        case NUMBER:
                        case BOOLEAN:
                        case NULL:
                            jsonReader.skipValue();
                            continue;
                        case END_DOCUMENT:
                            break;
                        default:
                            throw new AssertionError(token);
                    }
                }

                return true;
            }
        } catch (MalformedJsonException var2) {
            return false;
        }
    }
}