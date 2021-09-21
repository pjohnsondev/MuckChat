package muck.core.observer;

public interface Observable<T> {
    void register(Observer observer);
    void unRegister(Observer observer);
    void notifyObservers(T message);
}
