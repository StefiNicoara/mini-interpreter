package domain.heapStatements;

import domain.adt.MyIHeap;

public class IDHeap {
    private static int number = 1;

    public static int getID() {
        return number++;
    }
}
