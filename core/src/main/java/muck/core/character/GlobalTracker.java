package muck.core.character;

/*
    Keep track of global objects related to characters
 */
public enum GlobalTracker {
    INSTANCE;

    //Singleton instance of ConcreteNPCMediator
    public static ConcreteNPCMediator concreteNPCMediator= new ConcreteNPCMediator();
}
