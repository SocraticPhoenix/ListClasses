package com.gmail.socraticphoenix.listclasses;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("*")
public class ListProcessor extends AbstractProcessor {
    private List<String> processed = new ArrayList<>();

    private ListClass defInst = new ListClass() {

        @Override
        public Class<? extends Annotation> annotationType() {
            return this.getClass();
        }

        @Override
        public boolean ignore() {
            return false;
        }

        @Override
        public boolean supplyList() {
            return false;
        }

        @Override
        public String listName() {
            return "class_index.txt";
        }

    };

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            for(Element element : roundEnv.getRootElements()) {
                while (element != null && !(element instanceof TypeElement)) {
                    element = element.getEnclosingElement();
                }

                if(element != null) {
                    TypeElement type = (TypeElement) element;
                    if(!this.processed.contains(type.getQualifiedName().toString())) {
                        this.processed.add(type.getQualifiedName().toString());
                        this.process(type.getAnnotation(ListClass.class), type);
                    }
                }
            }

            if(roundEnv.processingOver()) {
                if(FileRegistry.INSTANCE.empty()) {
                    FileRegistry.INSTANCE.create(this.processingEnv, "", "class_index.txt");
                }
                FileRegistry.INSTANCE.closeAll();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed processing due to IOException", e);
        }
        return false;
    }

    private void process(ListClass listClass, TypeElement element) throws IOException {
        String name = element.getQualifiedName().toString();
        String packName = this.processingEnv.getElementUtils().getPackageOf(element).getQualifiedName().toString();

        log("ListProcessing is processing: " + name);
        if(listClass == null) {
            log("No explicit annotation detected, falling back to default values");
            listClass = this.defInst;
        }


        if(!listClass.ignore()) {
            log("Logging class: " + name);
            FileRegistry.INSTANCE.println(name);
        } else {
            log("Ignore class: " + name);
        }

        if(listClass.supplyList()) {
            log(name + " requested a copy of the class index with name \"" + listClass.listName() + "\"");
            FileRegistry.INSTANCE.create(this.processingEnv, packName, listClass.listName());
        }

        log("ListProcessing finished processing: " + name);
    }

    private void log(String str) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, str);
    }

}
