package me.sleepyfish.smok.utils.backend.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

// Class from SMok Client by SleepyFish
public class Json {

    private final JsonObject object;
    private final Gson gson;

    public Json(JsonObject object) {
        this.object = object;
        this.gson = new Gson();
    }

    public boolean contains(String path) {
        String[] args = path.split("\\.");
        JsonObject temp = this.getObject();

        for (int i = 0; i < args.length - 1; ++i) {
            String s = args[i].replace("&dot;", ".");
            if (!temp.has(s)) {
                return false;
            }

            temp = temp.getAsJsonObject(s);
        }

        String object = args[args.length - 1].replace("&dot;", ".");
        return temp.has(object);
    }

    public Json remove(String path) {
        String[] args = path.split("\\.");
        JsonObject temp = this.getObject();

        for (int i = 0; i < args.length - 1; ++i) {
            String s = args[i].replace("&dot;", ".");
            if (!temp.has(s)) {
                temp.add(s, new JsonObject());
            }

            JsonElement element = temp.get(s);
            if (!element.isJsonObject()) {
                temp.remove(s);
                temp.add(s, new JsonObject());
            }

            temp = element.getAsJsonObject();
        }

        String object = args[args.length - 1].replace("&dot;", ".");
        temp.remove(object);
        return this;
    }

    public <T> T deserialize(String path, Class<T> classOfT) {
        path = path.replace("\\.", "&dot;");
        String[] args = path.split("\\.");
        JsonObject temp = this.getObject();

        for (int i = 0; i < args.length - 1; ++i) {
            String s = args[i].replace("&dot;", ".");
            if (!temp.has(s)) {
                return null;
            }

            temp = temp.getAsJsonObject(s);
        }

        String object = args[args.length - 1].replace("&dot;", ".");
        if (temp.has(object)) {
            return this.gson.fromJson(temp.get(object), classOfT);
        } else {
            return null;
        }
    }

    public Json serialize(String path, Object data) {
        this.set(path, this.gson.toJson(data));
        return this;
    }

    public JsonElement get(String path) {
        path = path.replace("\\.", "&dot;");
        String[] args = path.split("\\.");
        JsonObject temp = this.getObject();

        for (int i = 0; i < args.length - 1; ++i) {
            String s = args[i].replace("&dot;", ".");
            if (!temp.has(s)) {
                return null;
            }

            temp = temp.getAsJsonObject(s);
        }

        String object = args[args.length - 1].replace("&dot;", ".");
        if (temp.has(object)) {
            return temp.get(object);
        } else {
            return null;
        }
    }

    public JsonArray getJsonArray(String path) {
        JsonElement element = this.get(path);
        return element != null && element.isJsonArray() ? element.getAsJsonArray() : null;
    }

    public Json getJson(String path) {
        JsonElement element = this.get(path);
        if (element != null && element.isJsonObject()) {
            new Json(element.getAsJsonObject());
        }

        return null;
    }

    public String getString(String path) {
        JsonElement element = this.get(path);
        return element != null && element.isJsonPrimitive() ? element.getAsString() : "";
    }

    public int getInt(String path) {
        JsonElement element = this.get(path);
        return element != null && element.isJsonPrimitive() ? element.getAsInt() : -1;
    }

    public long getLong(String path) {
        JsonElement element = this.get(path);
        return element != null && element.isJsonPrimitive() ? element.getAsLong() : -1L;
    }

    public boolean getBoolean(String path) {
        JsonElement element = this.get(path);
        return element != null && element.isJsonPrimitive() ? element.getAsBoolean() : false;
    }

    public double getDouble(String path) {
        JsonElement element = this.get(path);
        return element != null && element.isJsonPrimitive() ? element.getAsDouble() : -1.0;
    }

    public Collection<String> getStringList(String path) {
        JsonElement element = this.get(path);
        if (element != null && element.isJsonArray()) {
            Collection<String> list = new ArrayList();
            Iterator var4 = element.getAsJsonArray().iterator();

            while (var4.hasNext()) {
                JsonElement arrayElement = (JsonElement) var4.next();
                if (arrayElement.isJsonPrimitive()) {
                    list.add(arrayElement.getAsString());
                }
            }

            return list;
        } else {
            return new ArrayList();
        }
    }

    public Collection<Integer> getIntList(String path) {
        JsonElement element = this.get(path);
        if (element != null && element.isJsonArray()) {
            Collection<Integer> list = new ArrayList();
            Iterator var4 = element.getAsJsonArray().iterator();

            while (var4.hasNext()) {
                JsonElement arrayElement = (JsonElement) var4.next();
                if (arrayElement.isJsonPrimitive()) {
                    list.add(arrayElement.getAsInt());
                }
            }

            return list;
        } else {
            return new ArrayList();
        }
    }

    public Collection<Long> getLongList(String path) {
        JsonElement element = this.get(path);
        if (element != null && element.isJsonArray()) {
            Collection<Long> list = new ArrayList();
            Iterator var4 = element.getAsJsonArray().iterator();

            while (var4.hasNext()) {
                JsonElement arrayElement = (JsonElement) var4.next();
                if (arrayElement.isJsonPrimitive()) {
                    list.add(arrayElement.getAsLong());
                }
            }

            return list;
        } else {
            return new ArrayList();
        }
    }

    public Collection<Boolean> getBooleanList(String path) {
        JsonElement element = this.get(path);
        if (element != null && element.isJsonArray()) {
            Collection<Boolean> list = new ArrayList();
            Iterator var4 = element.getAsJsonArray().iterator();

            while (var4.hasNext()) {
                JsonElement arrayElement = (JsonElement) var4.next();
                if (arrayElement.isJsonPrimitive()) {
                    list.add(arrayElement.getAsBoolean());
                }
            }

            return list;
        } else {
            return new ArrayList();
        }
    }

    public Collection<Double> getDoubleList(String path) {
        JsonElement element = this.get(path);
        if (element != null && element.isJsonArray()) {
            Collection<Double> list = new ArrayList();
            Iterator var4 = element.getAsJsonArray().iterator();

            while (var4.hasNext()) {
                JsonElement arrayElement = (JsonElement) var4.next();
                if (arrayElement.isJsonPrimitive()) {
                    list.add(arrayElement.getAsDouble());
                }
            }

            return list;
        } else {
            return new ArrayList();
        }
    }

    public Json set(String path, Object data) {
        synchronized (this) {
            path = path.replace("\\.", "&dot;");
            if (data instanceof String) {
                this.setString(path, (String) data);
            } else if (data instanceof Json) {
                this.setJson(path, (Json) data);
            } else if (data instanceof JsonArray) {
                this.setJsonArray(path, (JsonArray) data);
            } else if (data instanceof Integer) {
                this.setInt(path, (Integer) data);
            } else if (data instanceof Long) {
                this.setLong(path, (Long) data);
            } else if (data instanceof Boolean) {
                this.setBoolean(path, (Boolean) data);
            } else if (data instanceof Double) {
                this.setDouble(path, (Double) data);
            } else if (data instanceof Collection) {
                this.setList(path, (Collection) data);
            } else if (data instanceof Object[]) {
                this.setList(path, Arrays.asList((Object[]) ((Object[]) data)));
            } else {
                this.setString(path, data.toString());
            }

            return this;
        }
    }

    private void setJsonArray(String path, JsonArray data) {
        String[] args = path.split("\\.");
        String object = args[args.length - 1].replace("&dot;", ".");
        JsonObject temp = this.getObject();

        for (int i = 0; i < args.length - 1; ++i) {
            String s = args[i].replace("&dot;", ".");
            if (!temp.has(s)) {
                temp.add(s, new JsonObject());
            }

            JsonElement element = temp.get(s);
            if (!element.isJsonObject()) {
                temp.remove(s);
                temp.add(s, new JsonObject());
            }

            temp = element.getAsJsonObject();
        }

        if (temp.has(object)) {
            temp.remove(object);
        }

        temp.add(object, new JsonArray());
        Iterator var9 = data.iterator();

        while (var9.hasNext()) {
            JsonElement element = (JsonElement) var9.next();
            temp.getAsJsonArray(object).add(element);
        }

    }

    private void setJson(String path, Json data) {
        String[] args = path.split("\\.");
        String object = args[args.length - 1].replace("&dot;", ".");
        JsonObject temp = this.getObject();

        for (int i = 0; i < args.length - 1; ++i) {
            String s = args[i].replace("&dot;", ".");
            if (!temp.has(s)) {
                temp.add(s, new JsonObject());
            }

            JsonElement element = temp.get(s);
            if (!element.isJsonObject()) {
                temp.remove(s);
                temp.add(s, new JsonObject());
            }

            temp = element.getAsJsonObject();
        }

        temp.add(object, data.getObject());
    }

    private void setString(String path, String data) {
        String[] args = path.split("\\.");
        String object = args[args.length - 1].replace("&dot;", ".");
        JsonObject temp = this.getObject();

        for (int i = 0; i < args.length - 1; ++i) {
            String s = args[i].replace("&dot;", ".");
            if (!temp.has(s)) {
                temp.add(s, new JsonObject());
            }

            JsonElement element = temp.get(s);
            if (!element.isJsonObject()) {
                temp.remove(s);
                temp.add(s, new JsonObject());
            }

            temp = element.getAsJsonObject();
        }

        temp.addProperty(object, data.replace("&dot;", "."));
    }

    private void setInt(String path, int data) {
        String[] args = path.split("\\.");
        String object = args[args.length - 1].replace("&dot;", ".");
        JsonObject temp = this.getObject();

        for (int i = 0; i < args.length - 1; ++i) {
            String s = args[i].replace("&dot;", ".");
            if (!temp.has(s)) {
                temp.add(s, new JsonObject());
            }

            JsonElement element = temp.get(s);
            if (!element.isJsonObject()) {
                temp.remove(s);
                temp.add(s, new JsonObject());
            }

            temp = element.getAsJsonObject();
        }

        temp.addProperty(object, data);
    }

    private void setLong(String path, long data) {
        String[] args = path.split("\\.");
        String object = args[args.length - 1].replace("&dot;", ".");
        JsonObject temp = this.getObject();

        for (int i = 0; i < args.length - 1; ++i) {
            String s = args[i].replace("&dot;", ".");
            if (!temp.has(s)) {
                temp.add(s, new JsonObject());
            }

            JsonElement element = temp.get(s);
            if (!element.isJsonObject()) {
                temp.remove(s);
                temp.add(s, new JsonObject());
            }

            temp = element.getAsJsonObject();
        }

        temp.addProperty(object, data);
    }

    private void setBoolean(String path, boolean data) {
        String[] args = path.split("\\.");
        String object = args[args.length - 1].replace("&dot;", ".");
        JsonObject temp = this.getObject();

        for (int i = 0; i < args.length - 1; ++i) {
            String s = args[i].replace("&dot;", ".");
            if (!temp.has(s)) {
                temp.add(s, new JsonObject());
            }

            JsonElement element = temp.get(s);
            if (!element.isJsonObject()) {
                temp.remove(s);
                temp.add(s, new JsonObject());
            }

            temp = element.getAsJsonObject();
        }

        temp.addProperty(object, data);
    }

    private void setDouble(String path, double data) {
        String[] args = path.split("\\.");
        String object = args[args.length - 1].replace("&dot;", ".");
        JsonObject temp = this.getObject();

        for (int i = 0; i < args.length - 1; ++i) {
            String s = args[i].replace("&dot;", ".");
            if (!temp.has(s)) {
                temp.add(s, new JsonObject());
            }

            JsonElement element = temp.get(s);
            if (!element.isJsonObject()) {
                temp.remove(s);
                temp.add(s, new JsonObject());
            }

            temp = element.getAsJsonObject();
        }

        temp.addProperty(object, data);
    }

    private void setList(String path, Collection<?> collection) {
        assert collection != null;

        if (collection.isEmpty()) {
            this.setEmptyList(path);
        }

        for (Object o : collection) {
            if (o instanceof String) {
                this.setStringList(path, (Collection<String>) collection);
            } else if (o instanceof Integer) {
                this.setIntList(path, (Collection<Integer>) collection);
            } else if (o instanceof Long) {
                this.setLongList(path, (Collection<Long>) collection);
            } else if (o instanceof Boolean) {
                this.setBoolList(path, (Collection<Boolean>) collection);
            } else if (o instanceof Double) {
                this.setDoubleList(path, (Collection<Double>) collection);
            } else {
                this.setStringList(path, collection.stream().map(Object::toString).collect(Collectors.toList()));
            }
        }

    }

    private void setEmptyList(String path) {
        String[] args = path.split("\\.");
        String object = args[args.length - 1].replace("&dot;", ".");
        JsonObject temp = this.getObject();

        for (int i = 0; i < args.length - 1; ++i) {
            String s = args[i].replace("&dot;", ".");
            if (!temp.has(s)) {
                temp.add(s, new JsonObject());
            }

            JsonElement element = temp.get(s);
            if (!element.isJsonObject()) {
                temp.remove(s);
                temp.add(s, new JsonObject());
            }

            temp = element.getAsJsonObject();
        }

        if (temp.has(object)) {
            temp.remove(object);
        }

        temp.add(object, new JsonArray());
    }

    private void setStringList(String path, Collection<String> collection) {
        String[] args = path.split("\\.");
        String object = args[args.length - 1].replace("&dot;", ".");
        JsonObject temp = this.getObject();

        String item;
        for (int i = 0; i < args.length - 1; ++i) {
            item = args[i].replace("&dot;", ".");
            if (!temp.has(item)) {
                temp.add(item, new JsonObject());
            }

            JsonElement element = temp.get(item);
            if (!element.isJsonObject()) {
                temp.remove(item);
                temp.add(item, new JsonObject());
            }

            temp = element.getAsJsonObject();
        }

        if (temp.has(object)) {
            temp.remove(object);
        }

        temp.add(object, new JsonArray());
        Iterator var9 = collection.iterator();

        while (var9.hasNext()) {
            item = (String) var9.next();
            temp.getAsJsonArray(object).add(item);
        }

    }

    private void setIntList(String path, Collection<Integer> collection) {
        String[] args = path.split("\\.");
        String object = args[args.length - 1].replace("&dot;", ".");
        JsonObject temp = this.getObject();

        for (int i = 0; i < args.length - 1; ++i) {
            String s = args[i].replace("&dot;", ".");
            if (!temp.has(s)) {
                temp.add(s, new JsonObject());
            }

            JsonElement element = temp.get(s);
            if (!element.isJsonObject()) {
                temp.remove(s);
                temp.add(s, new JsonObject());
            }

            temp = element.getAsJsonObject();
        }

        if (temp.has(object)) {
            temp.remove(object);
        }

        temp.add(object, new JsonArray());
        Iterator var9 = collection.iterator();

        while (var9.hasNext()) {
            int item = (Integer) var9.next();
            temp.getAsJsonArray(object).add(item);
        }

    }

    private void setLongList(String path, Collection<Long> collection) {
        String[] args = path.split("\\.");
        String object = args[args.length - 1].replace("&dot;", ".");
        JsonObject temp = this.getObject();

        for (int i = 0; i < args.length - 1; ++i) {
            String s = args[i].replace("&dot;", ".");
            if (!temp.has(s)) {
                temp.add(s, new JsonObject());
            }

            JsonElement element = temp.get(s);
            if (!element.isJsonObject()) {
                temp.remove(s);
                temp.add(s, new JsonObject());
            }

            temp = element.getAsJsonObject();
        }

        if (temp.has(object)) {
            temp.remove(object);
        }

        temp.add(object, new JsonArray());
        Iterator var9 = collection.iterator();

        while (var9.hasNext()) {
            long item = (Long) var9.next();
            temp.getAsJsonArray(object).add(item);
        }

    }

    private void setBoolList(String path, Collection<Boolean> collection) {
        String[] args = path.split("\\.");
        String object = args[args.length - 1].replace("&dot;", ".");
        JsonObject temp = this.getObject();

        for (int i = 0; i < args.length - 1; ++i) {
            String s = args[i].replace("&dot;", ".");
            if (!temp.has(s)) {
                temp.add(s, new JsonObject());
            }

            JsonElement element = temp.get(s);
            if (!element.isJsonObject()) {
                temp.remove(s);
                temp.add(s, new JsonObject());
            }

            temp = element.getAsJsonObject();
        }

        if (temp.has(object)) {
            temp.remove(object);
        }

        temp.add(object, new JsonArray());
        Iterator var9 = collection.iterator();

        while (var9.hasNext()) {
            boolean item = (Boolean) var9.next();
            temp.getAsJsonArray(object).add(item);
        }

    }

    private void setDoubleList(String path, Collection<Double> collection) {
        String[] args = path.split("\\.");
        String object = args[args.length - 1].replace("&dot;", ".");
        JsonObject temp = this.getObject();

        for (int i = 0; i < args.length - 1; ++i) {
            String s = args[i].replace("&dot;", ".");
            if (!temp.has(s)) {
                temp.add(s, new JsonObject());
            }

            JsonElement element = temp.get(s);
            if (!element.isJsonObject()) {
                temp.remove(s);
                temp.add(s, new JsonObject());
            }

            temp = element.getAsJsonObject();
        }

        if (temp.has(object)) {
            temp.remove(object);
        }

        temp.add(object, new JsonArray());
        Iterator var9 = collection.iterator();

        while (var9.hasNext()) {
            double item = (Double) var9.next();
            temp.getAsJsonArray(object).add(item);
        }

    }

    public void save(File f) {
        synchronized (this) {
            JsonUtil.writeToFile(f, this.getObject());
        }
    }

    public String asString() {
        try {
            Gson gson = (new GsonBuilder()).create();
            return gson.toJson(this.getObject());
        } catch (Exception var2) {
            return "{\"code\":500,\"message\":\"Failed to build a fucking string retard!\"}";
        }
    }

    public JsonObject getObject() {
        return this.object;
    }

    public String toString() {
        return this.asString();
    }

}