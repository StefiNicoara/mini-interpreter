package repository;

import domain.PrgState;
import domain.adt.*;
import domain.fileHandling.FileData;
import domain.fileHandling.IFileTable;
import domain.stmt.IStmt;
import exception.FileException;
import java.beans.Statement;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> list = new ArrayList<>();

    @Override
    public void addPrg(PrgState newPrg) {
        list.add(newPrg);
    }

    @Override
    public List<PrgState> getPrgList() {
        return list;
    }

    @Override
    public void setPrgList(List<PrgState> list) {
        this.list = list;
    }

    @Override
    public void logPrgStateExec(PrgState state) throws FileException {

        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter("text2.txt", true)))) {

            MyIStack<IStmt> stack = state.getExeStack();
            MyIList<Integer> out = state.getOut();
            MyIDictionary<String, Integer> symTable = state.getSymTable();
            IFileTable<Integer, FileData> fileTable = state.getFileTable();
            MyIHeap<Integer, Integer> heap = state.getHeap();

            logFile.println("ID: " + state.getID());

            logFile.println("ExeStack:");
            for (IStmt S : stack.getStack())
                logFile.println("" + S);


            logFile.println("\nSymTable:\n");
            for (String key : symTable.getAllKeys())
                logFile.println(key + "-->" + symTable.getValue(key) + '\n');

            logFile.println("\nOut:\n");
            for (Integer i : out.getList())
                logFile.println("" + i + "\n");

            logFile.println("\nFileTable:\n");
            for(Integer i: fileTable.getAllKeys())
                logFile.println("" + i + fileTable.get(i).getFileName() + "\n");

            logFile.println("\nHeap:\n");
            for(Integer key: heap.getAll())
                logFile.println(key + "-->" + heap.get(key) +'\n');

            logFile.println("\n");

        } catch (FileNotFoundException e) {
            throw new FileException("File not found in PrintWriter");
        } catch (IOException e) {
            throw new FileException("IO exception at PrintWriter");
        }
    }
}
