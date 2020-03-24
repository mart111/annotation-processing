package org.example;

import com.annotation.processing.annotations.Component;
import com.annotation.processing.annotations.PostProcessor;
import com.annotation.processing.annotations.Provide;
import org.example.context.ComponentContext;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * Hello world!
 */


@Component
public class App {

    @Provide
    public static Hello hello;

    private static Map<Class<?>, Object> context;

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, InvocationTargetException {

        ComponentContextWrapper.wrap();
        provideDependencies();
        postProcessor();

    }

    private static void provideDependencies() throws IllegalAccessException, InstantiationException {
        Reflections reflections = ReflectionsProvider
                .getReflections();

        Set<Field> fieldsAnnotatedWith =
                reflections.getFieldsAnnotatedWith(Provide.class);

        for (Field field : fieldsAnnotatedWith) {
            if (!field.isAccessible())
                field.setAccessible(true);
            Class<?> declaringClass = field.getDeclaringClass();
            Class<?> fieldType = field.getType();
            Component annotationDeclaringClass = declaringClass.getAnnotation(Component.class);
            Component annotationFieldType = fieldType.getAnnotation(Component.class);
            if (annotationDeclaringClass == null || annotationFieldType == null) {
                throw new RuntimeException("Couldn't inject dependency");
            }
            Object declaringClassInstance = ComponentContext.getInstance().get(declaringClass);
            if (declaringClassInstance != null) {
                field.set(declaringClassInstance, fieldType.newInstance());
            }
        }
    }

    private static void postProcessor() throws InvocationTargetException, IllegalAccessException {
        Reflections reflections = ReflectionsProvider.getReflections();
        Set<Method> methodsAnnotatedWith = reflections.getMethodsAnnotatedWith(PostProcessor.class);
        for (Method method : methodsAnnotatedWith) {
            Class<?> declaringClass = method.getDeclaringClass();
            if (declaringClass.getAnnotation(Component.class) == null) {
                throw new RuntimeException("Must be annotated");
            }

            Object declaredClassObject = ComponentContext.getInstance().get(declaringClass);
            method.invoke(declaredClassObject);
        }
    }
}
