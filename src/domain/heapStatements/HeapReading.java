package domain.heapStatements;

import domain.adt.MyIDictionary;
import domain.adt.MyIHeap;
import domain.exp.Exp;
import exception.MyException;

public class HeapReading extends Exp {

    private String varName;

    public HeapReading(String varName) {
        this.varName = varName;
    }


    @Override
    public int eval(MyIDictionary<String, Integer> symTable, MyIHeap<Integer, Integer> heap) {

        if (!symTable.isDefined(varName)) {
            throw new MyException("This key is not in our symTable");
        }

        int heapID = symTable.getValue(varName);

        if (!heap.contains(heapID)) {
            throw new MyException("This key is not in our heapTable");
        }

        return heap.get(heapID);
    }

    @Override
    public String toString() {
        return "heapReading(" + varName + ")";
    }
}
