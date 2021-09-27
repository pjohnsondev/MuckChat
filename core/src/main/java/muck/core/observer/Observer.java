package muck.core.observer;

public interface Observer<T> {
    void update(T message);
}
