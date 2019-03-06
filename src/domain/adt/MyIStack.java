package domain.adt;

public interface MyIStack<T> {
    T pop();

    void push(T v);

    boolean isEmpty();

    String toString();

    public Iterable<T> getStack();
}
