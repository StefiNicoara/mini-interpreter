package domain.heapStatements;

import domain.PrgState;
import domain.adt.MyIDictionary;
import domain.adt.MyIHeap;
import domain.exp.Exp;
import domain.stmt.IStmt;

public class HeapAllocation implements IStmt {

    private String varName;
    private Exp exp;

    public HeapAllocation(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) {

        MyIDictionary<String, Integer> symTable = state.getSymTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();

        int id = IDHeap.getID();
        int val = exp.eval(symTable, heap);

        heap.add(id, val);
        if (symTable.isDefined(varName))
            symTable.update(varName, id);
        else
            symTable.setValue(varName, id);


        return null;
    }

    @Override
    public String toString() {
        return "NewH(" + varName + ", " + exp + ")";
    }
}
