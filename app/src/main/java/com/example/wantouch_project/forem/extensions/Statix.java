package com.example.wantouch_project.forem.extensions;

import com.example.wantouch_project.forem.functionalInterfaces.ForemUnarySetter;

import java.util.HashMap;
import java.util.function.Function;

public interface Statix {
    HashMap<String, Object> staticVariables = new HashMap<>();

    static void set(String key, Object value) {
        staticVariables.put(key, value);
    }

    static <T> void setter(String key, ForemUnarySetter<T> fs) {
        fs.set(get(key));
    }

    static <T> void editSet(String key, Function<T, T> func) {
        set(key, func.apply(get(key)));
    }

    static <T> T get(String key) {
        return (T) staticVariables.get(key);
    }
}
