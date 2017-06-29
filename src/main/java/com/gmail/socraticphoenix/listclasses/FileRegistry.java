package com.gmail.socraticphoenix.listclasses;

import javax.annotation.processing.ProcessingEnvironment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FileRegistry {
    public static FileRegistry INSTANCE = new FileRegistry();

    private List<String> lines;
    private List<MutatingFile> files;

    public FileRegistry() {
        this.lines = new ArrayList<>();
        this.files = new ArrayList<>();
    }

    public boolean has(String name) {
        return this.lines.contains(name);
    }

    public MutatingFile create(ProcessingEnvironment environment, String pack, String name) throws IOException {
        Optional<MutatingFile> target = this.files.stream().filter(f -> f.getPack().equals(pack) && f.getName().equals(name)).findFirst();
        if(target.isPresent()) {
            return target.get();
        } else {
            MutatingFile file = new MutatingFile(environment, pack, name);
            for(String line : this.lines) {
                file.println(line);
            }
            this.files.add(file);
            return file;
        }
    }

    public void println(String line) throws IOException {
        this.lines.add(line);
        for(MutatingFile file : this.files) {
            file.println(line);
        }
    }

    public boolean empty() {
        return this.files.isEmpty();
    }

    public void closeAll() throws IOException {
        for(MutatingFile file : this.files) {
            file.close();
        }
    }

}
