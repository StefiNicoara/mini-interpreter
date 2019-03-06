package domain.stmt;

import domain.PrgState;
import domain.adt.MyIDictionary;
import domain.adt.MyIHeap;
import domain.adt.MyIList;

public class Await implements IStmt {

    private String varName;

    public Await(String varName) {
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) {

        MyIList<Integer> queue = state.getOut();
        MyIDictionary<String, Integer> symTable = state.getSymTable();
        MyIHeap<Integer, Integer> heap = state.getHeap();

        if (!symTable.isDefined(varName))
            throw new RuntimeException("This " + varName + " do not exist in symTable");

        int id = symTable.getValue(varName); //foundIndex=lookup(SymTable,var).

        if (!state.getBarrierTable().contains(id)) // if foundIndex is not an index in the BarrierTable
            throw new RuntimeException("This " + id + " do not exist in BarrierTable");

        synchronized (state.getBarrierTable()) {

            if (state.getBarrierTable().get(id).getFirst().size() == state.getBarrierTable().get(id).getSecond()) ;
            else if (state.getBarrierTable().get(id).getFirst().contains(state.getID()))
                state.getExeStack().push(this);
            else {
                state.getBarrierTable().get(id).getFirst().add(state.getID());
                state.getExeStack().push(this);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Await(" + varName + ")";
    }

}