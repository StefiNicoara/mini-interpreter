package domain.fileHandling;

import domain.PrgState;
import domain.adt.MyIDictionary;
import domain.adt.MyIHeap;
import domain.exp.Exp;
import domain.stmt.IStmt;
import exception.FileException;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt {
    private Exp exp;

    public CloseRFile(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) {

        MyIDictionary<String, Integer> symTable = state.getSymTable();
        IFileTable<Integer, FileData> fileTable = state.getFileTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        int id = exp.eval(symTable, heap);

        boolean ok = false;
        for (Integer d : fileTable.getAllKeys())
            if (id == d)
                ok = true;

        if (!ok)
            throw new FileException("Can't close the file because don't exist in FileTable");

        FileData fileData = fileTable.get(id);
        BufferedReader bufferedReader = fileData.getReader();

        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new FileException("Can't close the file");
        }

        fileTable.delete(id);
        //dict.delete();

        return null;
    }

    @Override
    public String toString() {
        return "close(" + exp + ")";
    }
}
