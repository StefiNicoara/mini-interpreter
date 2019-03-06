package domain.exp;

import domain.adt.MyIDictionary;
import domain.adt.MyIHeap;

public class ConstExp extends Exp {
    private int number;

    public ConstExp() {
    }

    public ConstExp(int number) {
        this.number = number;
    }

    public int eval(MyIDictionary<String, Integer> symTable, MyIHeap<Integer, Integer> heap) {
        return number;
    }

    public String toString() {
        return Integer.toString(number);
    }

    public int getNumber() {
        return this.number;
    }
}
