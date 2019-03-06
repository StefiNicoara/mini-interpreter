package domain.stmt;

import domain.PrgState;
import domain.adt.*;
import domain.fileHandling.FileData;
import domain.fileHandling.IFileTable;


public class ForkStmt implements IStmt {

    private IStmt statement;

    public ForkStmt(IStmt statement) {
        this.statement = statement;
    }


    @Override
    public PrgState execute(PrgState state) {

        MyIStack<IStmt> stack2 = new MyStack<>();
        MyIDictionary<String,Integer> dict2 = state.getSymTable().copy();
        MyIList<Integer> out2 = state.getOut();
        MyIHeap<Integer,Integer> heap2 = state.getHeap();
        IFileTable<Integer, FileData> fileTable2 = state.getFileTable();
        BarrierTable<Integer, myPair> barrier = state.getBarrierTable();

        stack2.push(statement);
        Integer ID = GenIDFork.getID();
        return new PrgState(stack2, dict2, out2,null, fileTable2, heap2, barrier, ID);

    }

    @Override
    public String toString(){
        return "fork("+statement+")";
    }
}