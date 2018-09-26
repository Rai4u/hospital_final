package ua.nure.shevchenko.hospital;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private static Map<String, Object> source = new HashMap<>();

    public static void put(String name, Object object) {
        source.put(name, object);
    }

    public static Object get(String name){
        return source.get(name);
    }

    @SuppressWarnings("unchecked")
    public static <T>T get(Class<T> clazz){
        return (T)source.values().stream()
                .filter(clazz::isInstance)
                .findFirst().orElse(null);
    }
}
