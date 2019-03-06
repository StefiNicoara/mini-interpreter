package domain.fileHandling;

import domain.PrgState;
import domain.adt.MyIDictionary;
import domain.adt.MyIHeap;
import domain.exp.Exp;
import domain.stmt.IStmt;
import exception.FileException;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt {
    private Exp exp;
    private String varName;

    public ReadFile(Exp exp, String varName) {
        this.exp = exp;
        this.varName = varName;
    }


    @Override
    public PrgState execute(PrgState state) {

        MyIDictionary<String, Integer> symTable = state.getSymTable();
        IFileTable<Integer, FileData> fileTable = state.getFileTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        int id = exp.eval(symTable, heap);


        if (!fileTable.contains(id))
            throw new FileException("This ID is not in FileTable");

        BufferedReader reader = fileTable.get(id).getReader();

        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new FileException("Can't read line");
        }

        int value = 0;
        if (line != null)
            value = Integer.parseInt(line);

        symTable.setValue(varName, value);

        return null;
    }

    @Override
    public String toString() {
        return "read(" + varName + ", " + exp + ")";
    }
}
