package com.gmail.socraticphoenix.listclasses;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MutatingFile {
    private FileObject object;
    private BufferedWriter writer;

    private String pack;
    private String name;

    public MutatingFile(ProcessingEnvironment environment, String pack, String name) throws IOException {
        this.object = environment.getFiler().createResource(StandardLocation.CLASS_OUTPUT, pack, name);
        this.writer = new BufferedWriter(new OutputStreamWriter(this.object.openOutputStream()));
        this.pack = pack;
        this.name = name;
    }

    public String getPack() {
        return pack;
    }

    public String getName() {
        return name;
    }

    public void print(String str) throws IOException {
        this.writer.write(str);
    }

    public void println(String str) throws IOException {
        this.writer.write(str);
        this.writer.newLine();
    }

    public void close() throws IOException {
        this.writer.close();
    }

    public FileObject getObject() {
        return object;
    }

    public void setObject(FileObject object) {
        this.object = object;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public void setWriter(BufferedWriter writer) {
        this.writer = writer;
    }
}
