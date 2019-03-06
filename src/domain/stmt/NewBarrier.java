package domain.stmt;

import domain.PrgState;
import domain.adt.MyIDictionary;
import domain.adt.MyIHeap;
import domain.adt.MyIList;
import domain.adt.myPair;
import domain.exp.Exp;

import java.util.ArrayList;

public class NewBarrier implements IStmt {

    private String varName;
    private Exp exp;
    private static int id = 0;

    public NewBarrier(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) {
        MyIList<Integer> queue = state.getOut();
        MyIDictionary<String, Integer> symTable = state.getSymTable();
        MyIHeap<Integer,Integer> heap = state.getHeap();

        int number = exp.eval(symTable, heap);

        synchronized (state.getBarrierTable()){

            state.getBarrierTable().add(id, new myPair(new ArrayList<Integer>(), number));
            if(symTable.isDefined(varName))
                symTable.update(varName,id);
            else
                symTable.setValue(varName, id);

        }
        return null;
    }

    @Override
    public String toString() {
        return "NewBarrier(" + varName.toString() + ", " + exp;
    }
}
