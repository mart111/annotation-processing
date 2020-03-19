package com.annotation.processing.processors;

import com.annotation.processing.annotations.Generator;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.Generated;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;


@SupportedAnnotationTypes("com.annotation.processing.annotations.Generator")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class GeneratorAnnotationProcessor extends AbstractProcessor {

    public GeneratorAnnotationProcessor() {

    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annWith = roundEnv.getElementsAnnotatedWith(Generator.class);

        try {
            for (Element element : annWith) {
                if (element.getKind() != ElementKind.CLASS) {
                    this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Only classes can be annotated with \" + @Init");
                    throw new Exception("Only classes can be annotated with " + Generator.class.getCanonicalName());
                }

                TypeElement typeElement = ((TypeElement) element);
                Filer filer = this.processingEnv.getFiler();
                Elements elementUtils = this.processingEnv.getElementUtils();
                generateCode(filer, elementUtils, typeElement);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private void generateCode(Filer filer, Elements elementUtils, TypeElement typeElement) throws IOException {
        String newClassName = typeElement.getSimpleName() + "_New";
        PackageElement packageElement = elementUtils.getPackageOf(typeElement);
        String packageName = packageElement.getQualifiedName().toString();
        MethodSpec printMethod = MethodSpec
                .methodBuilder("init")
                .addModifiers(Modifier.PUBLIC)
                .addStatement("$T.out.println($S)", System.class, "Hello from generated class")
                .returns(void.class)
                .build();

        TypeSpec newClass = TypeSpec
                .classBuilder(newClassName)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(AnnotationSpec.builder(Generated.class)
                        .addMember("value", "$S", "com.annotation.processing.processors.Generator")
                        .addMember("date", "$S", LocalDate.now().toString())
                        .build())
                .addMethod(printMethod)
                .build();

        JavaFile.builder(packageName, newClass).build().writeTo(filer);

    }
}
