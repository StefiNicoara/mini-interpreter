package domain.stmt;

import domain.PrgState;
import domain.adt.MyIStack;

public class CompStmt implements IStmt {
    private IStmt first, second;

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public PrgState execute(PrgState state) {
        MyIStack<IStmt> stack = state.getExeStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + ";" + second.toString() + ")";
    }

    public IStmt getFirst() {
        return this.first;
    }

    public IStmt getSecond() {
        return this.second;
    }
}
