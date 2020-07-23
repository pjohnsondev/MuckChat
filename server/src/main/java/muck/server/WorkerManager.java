package muck.server;

import akka.actor.ActorSystem;
import akka.actor.ActorRef;
import akka.routing.RoundRobinPool;
import akka.actor.Props;

import java.util.function.Consumer;
import java.util.concurrent.CompletableFuture;

import java.time.Duration;

import static akka.pattern.Patterns.ask;

/**
 * The WorkerManager exists to coordinate background tasks using Akka.
 */
public class WorkerManager {

    /** The system of actors! */
    private ActorSystem actorSystem;

    /** The root actor for the system */
    private ActorRef router;

    public WorkerManager() {
        actorSystem = ActorSystem.create("workerManager");
        router = actorSystem.actorOf(new RoundRobinPool(5).props(Props.create(WorkerActor.class)), "router2");
    }

    /**
     * This sends a message to a worker, and when it replies runs onComplete
     */
    public void schedule(Object message, Consumer<Object> onComplete) {
        // Send the message to the router, and get a CompletableFuture (a promise) on the response
        CompletableFuture result = ask(router, message, Duration.ofMillis(1000)).toCompletableFuture();

        // When the response completes/arrives, do whatever was in onComplete
        result.thenAccept(onComplete);

    }

    /** Closes down the actor system */
    public void shutDown() {
        actorSystem.terminate();
    }

}
