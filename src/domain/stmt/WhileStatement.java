package domain.stmt;

import domain.PrgState;
import domain.adt.MyIDictionary;
import domain.adt.MyIHeap;
import domain.adt.MyIStack;
import domain.exp.Exp;

public class WhileStatement implements IStmt{

    private Exp exp;
    private IStmt statement;

    public WhileStatement(Exp exp, IStmt statement){
        this.exp = exp;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) {
        MyIStack<IStmt> stack = state.getExeStack();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        MyIDictionary<String, Integer> symTable = state.getSymTable();

        int val = exp.eval(symTable, heap);

        if(val != 0) {
            stack.push(new WhileStatement(exp, statement));
            stack.push(statement);
        }

        return null;
    }

    @Override
    public String toString(){
        return "While("+exp+"){"+statement+"}";
    }
}
