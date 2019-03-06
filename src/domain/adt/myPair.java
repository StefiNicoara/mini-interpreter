package domain.adt;

import java.util.ArrayList;

public class myPair {
    private ArrayList<Integer> first;
    private Integer second;

    public myPair(ArrayList<Integer> first, Integer second) {
        this.first = first;
        this.second = second;
    }

    public ArrayList<Integer> getFirst() {
        return first;
    }

    public void setFirst(ArrayList<Integer> first) {
        this.first = first;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public Integer getSecond() {

        return second;
    }
}
