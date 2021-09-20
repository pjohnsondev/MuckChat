package muck.core.observer;

import java.util.HashSet;
import java.util.Set;

public class ObservableSubject<T> implements Observable<T> {
    Set<Observer> observables = new HashSet<>();

    public void register(Observer observer) {}

    public void unRegister(Observer observer) {}

    public void notifyObservers(T observer) {}
}
