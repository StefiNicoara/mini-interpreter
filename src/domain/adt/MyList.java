package domain.adt;

import java.util.LinkedList;
import java.util.Queue;

public class MyList<T> implements MyIList<T> {
    private Queue<T> list;

    public MyList() {
        list = new LinkedList<T>();
    }

    @Override
    public void add(T v) {
        list.add(v);
    }

    @Override
    public T pop() {
        return list.poll();
    }

    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder();
        for (T e : list) {
            returnString.append(e.toString()).append("\n");
        }
        return returnString.toString();
    }

    public Queue<T> getList() {
        return list;
    }
}
