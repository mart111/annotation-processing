package org.example;

import com.annotation.processing.annotations.Component;
import org.example.context.ComponentContext;
import org.reflections.Reflections;

import java.util.Set;

public class ComponentContextWrapper {

    private static ComponentContextWrapper wrapper;

    public static void wrap() {
        if (wrapper == null) {
            wrapper = new ComponentContextWrapper();
        }

    }

    private ComponentContextWrapper() {
        createAppContent();
    }

    private void createAppContent() {
        Reflections reflections =
                ReflectionsProvider.getReflections();
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Component.class);
        for (Class<?> aClass : typesAnnotatedWith) {
            try {
                Object instance = aClass.newInstance();
                ComponentContext.getInstance().put(aClass, instance);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }
}
