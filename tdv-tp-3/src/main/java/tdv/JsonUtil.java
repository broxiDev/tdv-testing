package tdv;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class JsonUtil {

    private JsonUtil() {}

    public static Map<String, Object> parse(String json) {
        Type type = new TypeToken<Map<String, Object>>() {}.getType();
        return new GsonBuilder().create().fromJson(json, type);
    }

    public static String toPrettyString(Object obj) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(obj);
    }
}
