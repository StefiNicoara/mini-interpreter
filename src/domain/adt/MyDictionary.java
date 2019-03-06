package domain.adt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exception.MyException;

public class MyDictionary<T1, T2> implements MyIDictionary<T1, T2> {
    private HashMap<T1, T2> dictionary;

    public MyDictionary() {
        dictionary = new HashMap<T1, T2>();
    }

    @Override
    public void add(T1 v1, T2 v2) {
        dictionary.put(v1, v2);
    }

    @Override
    public void update(T1 v1, T2 v2) {
        dictionary.put(v1, v2);
    }

    @Override
    public T2 lookup(T1 id) throws MyException {
        if (dictionary.get(id) != null) {
            return dictionary.get(id);
        }
        throw new MyException("Couldn't find the given id.");
    }

    @Override
    public boolean isDefined(T1 id) {
        return dictionary.get(id) != null;
    }

    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder();
        for (HashMap.Entry<T1, T2> e : dictionary.entrySet()) {
            returnString.append("Key: ").append(e.getKey().toString()).append(", Value: ").append(e.getValue().toString()).append("\n");
        }
        return returnString.toString();
    }

    @Override
    public T2 getValue(T1 key) {
        return dictionary.get(key);
    }

    @Override
    public void setValue(T1 key, T2 value) {
        dictionary.put(key, value);
    }

    @Override
    public MyIDictionary<T1, T2> copy() {
        MyIDictionary<T1, T2> symTable = new MyDictionary<>();
        for (Map.Entry<T1, T2> e : dictionary.entrySet()) {
            symTable.setValue(e.getKey(), e.getValue());
        }
        return symTable;
    }

    public HashMap<T1, T2> getDictionary() {
        return dictionary;
    }

    public Iterable<T1> getAllKeys() {
        return dictionary.keySet();
    }

    public List<T2> getAllValues() {
        List<T2> l = new ArrayList<>();

        for (T1 key : this.getAllKeys())
            l.add(this.getValue(key));

        return l;
    }

}
