package muck.core.observer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

public class ObservableSubjectTest {
    private TestObserver testObserver;

    @BeforeEach
    public void init() {
        testObserver = mock(TestObserver.class, CALLS_REAL_METHODS);
    }

    public class TestClass {

    }

    public class TestObserver implements Observer<TestClass> {
        @Override
        public void update(TestClass message) {

        }
    }

    @Test
    public void observerIsTrackedWhenRegistered() {
        ObservableSubject<TestClass> observableSubject = new ObservableSubject<>();

        observableSubject.register(testObserver);
        assertEquals(1,observableSubject.observers.size());

        observableSubject.unRegister(testObserver);
        assertEquals(0,observableSubject.observers.size());
    }

    @Test
    public void observerIsNotified() {
        ObservableSubject<TestClass> observableSubject = new ObservableSubject<>();
        observableSubject.register(testObserver);

        TestClass testClass = new TestClass();
        observableSubject.notifyObservers(testClass);

        verify(testObserver, times(1)).update(testClass);
    }

    @Test
    public void observerIsNotNotifiedIfUnregistered() {
        ObservableSubject<TestClass> observableSubject = new ObservableSubject<>();
        observableSubject.register(testObserver);
        observableSubject.unRegister(testObserver);

        TestClass testClass = new TestClass();
        observableSubject.notifyObservers(testClass);

        verify(testObserver, times(0)).update(testClass);
    }
}
