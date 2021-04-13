package lab2.io.File;

import lab2.io.Writer;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;

public class FileWriter implements Writer {
    private Path fileName;

    public FileWriter(String fileName) {
        this.fileName = Paths.get(fileName);
    }

    @Override
    @SneakyThrows
    public void printInfo(String info) {
        Files.write(fileName, Collections.singleton(info), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
