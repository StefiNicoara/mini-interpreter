package domain.exp;

import domain.adt.MyIDictionary;
import domain.adt.MyIHeap;
import exception.MyException;

public abstract class Exp {
    public abstract int eval(MyIDictionary<String, Integer> symTable, MyIHeap<Integer, Integer> heap) throws MyException;

    public abstract String toString();
}