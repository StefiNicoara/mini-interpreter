package domain.booleanExpressions;

import domain.adt.MyIDictionary;
import domain.adt.MyIHeap;
import domain.exp.Exp;
import exception.MyException;

public class booleanExpresion extends Exp {
    private String op;
    private Exp left, right;

    public booleanExpresion(String op, Exp left, Exp right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    public int eval(MyIDictionary<String, Integer> d, MyIHeap<Integer, Integer> heap) throws MyException {

        if (op.equals("=")) {
            if (left.eval(d, heap) == right.eval(d, heap))
                return 1;
            return 0;
        }
        if (op.equals(">")) {
            if (left.eval(d, heap) > right.eval(d, heap))
                return 1;
            return 0;
        }
        if (op.equals(">=")) {
            if (left.eval(d, heap) >= right.eval(d, heap))
                return 1;
            return 0;
        }
        if (op.equals("<")) {
            if (left.eval(d, heap) < right.eval(d, heap))
                return 1;
            return 0;
        }
        if (op.equals("<=")) {
            if (left.eval(d, heap) <= right.eval(d, heap))
                return 1;
            return 0;
        }
        if (op.equals("!=")) {
            if (left.eval(d, heap) != right.eval(d, heap))
                return 1;
            return 0;
        }
        throw new MyException("The provided operator is not valid.");
    }

    @Override
    public String toString(){
        return left.toString() + " " + op + " " + right.toString();
    }
}