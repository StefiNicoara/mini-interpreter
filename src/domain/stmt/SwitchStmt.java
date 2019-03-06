package domain.stmt;

import domain.PrgState;
import domain.adt.MyIDictionary;
import domain.adt.MyIHeap;
import domain.adt.MyIStack;
import domain.booleanExpressions.booleanExpresion;
import domain.exp.Exp;

public class SwitchStmt implements IStmt {

    // switch(exp) (case exp1: stmt1) (case exp2: stmt2) (default: stmt3)

    private Exp exp;
    private Exp exp1;
    private IStmt stmt1;
    private Exp exp2;
    private IStmt stmt2;
    private IStmt stmt3;

    public SwitchStmt(Exp exp, Exp exp1, IStmt stmt1, Exp exp2, IStmt stmt2, IStmt stmt3){
        this.exp = exp;
        this.exp1 = exp1;
        this.stmt1 = stmt1;
        this.exp2 = exp2;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    @Override
    public PrgState execute(PrgState state) {
        MyIStack<IStmt> stack = state.getExeStack();
        MyIHeap<Integer, Integer> heap = state.getHeap();
        MyIDictionary<String, Integer> symTable = state.getSymTable();

        int valExp = exp.eval(symTable, heap);
        int valExp1 = exp1.eval(symTable, heap);
        int valExp2 = exp2.eval(symTable, heap);

        IStmt if2 = new IfStmt(new booleanExpresion("=", exp, exp2), stmt2, stmt3); // (if (exp==exp2) then stmt2 else stmt3)
        IStmt if1 = new IfStmt(new booleanExpresion("=", exp, exp1), stmt1, if2); //if(exp==exp1) then stmt1 else (if (exp==exp2) then stmt2 else stmt3)

        state.getExeStack().push(if1);

        return null;
    }

    @Override
    public String toString(){
        return "switch(" + exp + ")" + " (case (" + exp1 + ") : " + stmt1 + " (case (" + exp2 + ") : " + stmt2 + " (default : " + stmt3;
    }

}
