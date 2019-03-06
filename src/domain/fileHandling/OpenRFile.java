package domain.fileHandling;

import domain.PrgState;
import domain.adt.MyIDictionary;
import domain.stmt.IStmt;
import exception.FileException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFile implements IStmt {
    private String fileName;
    private String varName;

    public OpenRFile(String varName, String fileName) {
        this.fileName = fileName;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) {

        IFileTable<Integer, FileData> fileTable = state.getFileTable();
        MyIDictionary<String, Integer> symTable = state.getSymTable();
        BufferedReader reader;

        for (FileData d : fileTable.getAllValues()) {
            if (d.getFileName().equals(fileName))
                throw new FileException("The file is already opened in FileTable");
        }

        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (IOException e) {
            throw new FileException("File not found");
        }

        int id = GeneratorInteger.gen_ID();

        fileTable.add(id, new FileData(fileName, reader));
        symTable.setValue(varName, id);

        return null;
    }

    @Override
    public String toString() {
        return "open(" + varName + ", " + fileName + ')';
    }

}
