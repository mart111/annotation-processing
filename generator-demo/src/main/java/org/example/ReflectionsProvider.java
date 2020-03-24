package org.example;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

public class ReflectionsProvider {

    private static Reflections reflections;

    public static Reflections getReflections() {
        if (reflections == null) {
            reflections = new Reflections(
                    new ConfigurationBuilder()
                            .setUrls(ClasspathHelper.forJavaClassPath())
                            .addScanners(new MethodAnnotationsScanner())
                            .addScanners(new TypeAnnotationsScanner())
                            .addScanners(new FieldAnnotationsScanner()));
        }

        return reflections;
    }
}
