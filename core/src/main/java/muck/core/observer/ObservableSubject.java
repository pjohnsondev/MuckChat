package muck.core.observer;

import java.util.HashSet;
import java.util.Set;

/**
 * This is the subject of the observable design pattern.
 * The observers of type 'T' will be listening for changes through this class.
 * @param <T>
 */
public class ObservableSubject<T> implements Observable<T> {
    Set<Observer> observers = new HashSet<>();

    /**
     * Method to subscribe to notifications
     * @param observer
     */
    public void register(Observer observer) {
        if (observer == null)
            throw new UnsupportedOperationException("Observer cannot be null");

        observers.add(observer);
    }

    /**
     * Method to un-subscribe to notifications
     * @param observer
     */
    public void unRegister(Observer observer) {
        if (observer == null)
            throw new UnsupportedOperationException("Observer cannot be null");

        observers.remove(observer);
    }

    /**
     * Method to broadcast notifications to subscribers and provide them with messages of type 'T'
     * @param message
     */
    public void notifyObservers(T message) {
        for(Observer observer: observers)
            observer.update(message);
    }
}
