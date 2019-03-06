package domain.fileHandling;

import java.io.BufferedReader;

public class FileData {
    private String fileName;
    private BufferedReader reader;

    FileData(String n, BufferedReader r) {
        fileName = n;
        reader = r;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public BufferedReader getReader() {
        return this.reader;
    }

    @Override
    public String toString() {
        return fileName;
    }
}
