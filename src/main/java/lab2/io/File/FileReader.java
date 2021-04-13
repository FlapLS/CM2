package lab2.io.File;

import lab2.io.Reader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileReader implements Reader {
    private final List<String> lines;
    private int currentLine = 0;

    public FileReader(String fileName) throws IOException {
        lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    @Override
    public int readInt() {
        return Integer.parseInt(lines.get(currentLine++));
    }

    @Override
    public int readIntWithInfo(String info) {
        return readInt();
    }

    @Override
    public double readDouble() {
        return Double.parseDouble(lines.get(currentLine++).replace(',', '.'));
    }

    @Override
    public double readDoubleWithInfo(String info) {
        return readDouble();
    }

    @Override
    public String readString() {
        return lines.get(currentLine++);
    }

    @Override
    public String readStringWithInfo (String info) {
        return readString();
    }
}
