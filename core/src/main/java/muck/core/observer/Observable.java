package muck.core.observer;

/**
 * Observable is in interface for making a class of type T Observable
 * @param <T>
 */
public interface Observable<T> {
    void register(Observer observer);
    void unRegister(Observer observer);
    void notifyObservers(T message);
}
