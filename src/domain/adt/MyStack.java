package domain.adt;

import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    private Stack<T> stack;

    public MyStack() {
        stack = new Stack<>();
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder();
        for (T e : stack) {
            returnString.append(e.toString()).append("\n");
        }
        return returnString.toString();
    }

    @Override
    public Iterable<T> getStack() {
        return stack;
    }
}
