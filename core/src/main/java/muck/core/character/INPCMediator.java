package muck.core.character;

public interface INPCMediator {
    void messageToOtherNPC(NPC npcSource, String targetNPCIdentifier, Action action);
}
