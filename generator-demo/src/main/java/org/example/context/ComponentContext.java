package org.example.context;

import java.util.HashMap;
import java.util.Map;

public class ComponentContext {

    private static Map<Class<?>, Object> context = null;

    private ComponentContext() {

    }

    public static Map<Class<?>, Object> getInstance() {
        if (context == null) {
            context = new HashMap<>();
        }

        return context;
    }

}
