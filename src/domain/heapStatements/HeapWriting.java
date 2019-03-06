package domain.heapStatements;

import domain.PrgState;
import domain.adt.MyIDictionary;
import domain.adt.MyIHeap;
import domain.exp.Exp;
import domain.stmt.IStmt;
import exception.MyException;

public class HeapWriting implements IStmt {

    private String varName;
    private Exp exp;

    public HeapWriting(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) {
        MyIDictionary<String, Integer> symTable = state.getSymTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();

        if (!symTable.isDefined(varName))
            throw new MyException("This key is not in our symTable");

        int heapID = symTable.getValue(varName);

        if (!heap.contains(heapID))
            throw new MyException("This key is not in our heapTable");

        int val = exp.eval(symTable, heap);
        heap.update(heapID, val);

        return null;
    }

    @Override
    public String toString() {
        return "heapWriting(" + varName + "," + exp + ")";
    }
}
