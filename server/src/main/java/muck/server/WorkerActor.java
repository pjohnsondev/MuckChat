package muck.server;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.actor.ActorRef;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import muck.protocol.connection.Ping;

/**
 * A worker for handling background tasks.
 *
 * Define how the worker should respond to messages by putting new matchers into the createReceive method.
 */
public class WorkerActor extends AbstractActor {

    /** A logger for logging output */
    private static final Logger logger = LogManager.getLogger(WorkerActor.class);

    public static Props props() {
        return Props.create(WorkerActor.class, WorkerActor::new);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Ping.class,
                        // This matches Ping messages, to show an example of how the worker can do things in response to
                        // receiving a ping.
                        ping -> {
                            ActorRef sender = sender();
                            logger.info("Worker received a ping from {}", sender);
                            sender.tell("String saying I got your ping", self());
                        }
                )
                .matchAny(
                        o -> logger.info("Worker received a message {}", o)
                )
                .build();
    }


}
