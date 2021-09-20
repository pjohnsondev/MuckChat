package muck.core.observer;

import java.util.HashSet;
import java.util.Set;

public class ObservableSubject<T> implements Observable<T> {
    Set<Observer> observers = new HashSet<>();

    public void register(Observer observer) {
        if (observer == null)
            throw new UnsupportedOperationException("Observer cannot be null");

        observers.add(observer);
    }

    public void unRegister(Observer observer) {
        if (observer == null)
            throw new UnsupportedOperationException("Observer cannot be null");

        observers.remove(observer);
    }

    public void notifyObservers(T message) {
        for(Observer observer: observers)
            observer.update(message);
    }
}
