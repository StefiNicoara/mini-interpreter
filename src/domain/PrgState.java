package domain;

import domain.adt.*;
import domain.fileHandling.FileData;
import domain.fileHandling.FileTable;
import domain.fileHandling.IFileTable;
import domain.stmt.IStmt;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Integer> symTable;
    private MyIList<Integer> out;
    private IStmt originalProgram; //optional field, but good to have
    private IFileTable<Integer, FileData> fileTable;
    private MyIHeap<Integer, Integer> heap;
    private BarrierTable<Integer, myPair> barrier;
    private Integer ID;

    public PrgState(MyIStack<IStmt> stack, MyIDictionary<String, Integer> symTable, MyIList<Integer> out, IStmt program, IFileTable<Integer, FileData> fileTable, MyIHeap<Integer, Integer> heap, BarrierTable<Integer, myPair> barrier, Integer ID) {
        this.exeStack = stack;
        this.symTable = symTable;
        this.out = out;
        //this.originalProgram = program;
        //exeStack.push(program);
        this.fileTable = fileTable;
        this.heap = heap;
        this.barrier = barrier;
        this.ID = ID;
    }

    public MyIStack<IStmt> getExeStack() {
        return this.exeStack;
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public MyIDictionary<String, Integer> getSymTable() {
        return this.symTable;
    }

    public void setSymTable(MyIDictionary<String, Integer> symTable) {
        this.symTable = symTable;
    }

    public IFileTable<Integer, FileData> getFileTable() {
        return this.fileTable;
    }

    public MyIList<Integer> getOut() {
        return this.out;
    }

    public BarrierTable<Integer, myPair> getBarrierTable() { return this.barrier; }

    public void setOut(MyIList<Integer> out) {
        this.out = out;
    }

    public IStmt getOriginalProgram() {
        return this.originalProgram;
    }

    public void setOriginalProgram(IStmt originalProgram) {
        this.originalProgram = originalProgram;
    }

    public MyIHeap<Integer, Integer> getHeap() {
        return heap;
    }

    public void setHeap(MyIHeap<Integer, Integer> heap) {
        this.heap = heap;
    }

    public Integer getID() {
        return ID;
    }

    public boolean isNotCompleted(){
        return !exeStack.isEmpty();
    }

    public PrgState oneStep(){

        if(exeStack.isEmpty())
            return null;
        IStmt st = exeStack.pop();

        return st.execute(this);
    }

    public String toString() {
        StringBuffer str = new StringBuffer();

        str.append("\nExeStack: \n" + this.ID + "\n");
        for(IStmt S: exeStack.getStack())
            str.append(""+ S + '\n');

        str.append("\nSymTable:\n");
        for(String key: symTable.getAllKeys())
            str.append(key + "-->" + symTable.getValue(key) +'\n');

        str.append("\nOut:\n");
        for(Integer i: out.getList())
            str.append(""+i+"\n" );


        str.append("\nFileTable:\n");
        for(Integer i: fileTable.getAllKeys())
            str.append("" + i + fileTable.get(i).getFileName() + "\n");

        str.append("\nHeap:\n");
        for(Integer key: heap.getAll())
            str.append("" +key + heap.get(key) + "\n");

        str.append("\n");

        return str.toString();
    }
}