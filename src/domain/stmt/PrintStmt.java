package domain.stmt;

import domain.PrgState;
import domain.adt.MyIDictionary;
import domain.adt.MyIHeap;
import domain.adt.MyIList;
import domain.exp.Exp;
import exception.MyException;

public class PrintStmt implements IStmt {
    private Exp expression;

    public PrintStmt(Exp expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIList<Integer> queue = state.getOut();
        MyIDictionary<String, Integer> symTable = state.getSymTable();
        MyIHeap<Integer,Integer> heap = state.getHeap();
        queue.add(expression.eval(symTable, heap));
        return null;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }

    public Exp getExpression() {
        return this.expression;
    }
}
