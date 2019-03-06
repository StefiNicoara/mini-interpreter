package domain.adt;

import exception.MyException;

import java.util.HashMap;
import java.util.List;

public interface MyIDictionary<T1, T2> {
    void add(T1 v1, T2 v2);

    void update(T1 v1, T2 v2);

    T2 lookup(T1 id) throws MyException;

    boolean isDefined(T1 id);

    String toString();

    public HashMap<T1, T2> getDictionary();

    public Iterable<T1> getAllKeys();

    public T2 getValue(T1 key);

    public void setValue(T1 key, T2 value);

    MyIDictionary<T1, T2> copy();

    public List<T2> getAllValues();
}
