package muck.core.observer;

/**
 * Interface that is implemented by listeners(subscribers) for the class of generic type T
 * @param <T>
 */
public interface Observer<T> {
    void update(T message);
}
