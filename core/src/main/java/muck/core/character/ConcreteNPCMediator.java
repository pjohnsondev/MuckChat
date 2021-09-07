package muck.core.character;

import java.util.HashMap;
import java.util.List;

public class ConcreteNPCMediator implements INPCMediator {
    // We will implement the ConcreteNPCMediator as a singleton, and subscribe it to the global tracker
    private static ConcreteNPCMediator instance = null;

    public static ConcreteNPCMediator getInstance()
    {
        if (instance == null)
            instance = new ConcreteNPCMediator();

        return instance;
    }
    
    // <String identifier, NPC object> for each NPC subscribed to the Mediator
    private final HashMap<String, NPC> npcList = new HashMap<>();
    
    /* 
        Add an NPC to the Mediator to allow communication from other NPCs
     */
    public void addNPCToMediator(NPC npc) {
        npcList.put(npc.getIdentifier(), npc);
    }
    
    /* 
        Removes an NPC from the Mediator
     */
    public void removeNPCFromMediator(String npcIdentifier) {
        npcList.remove(npcIdentifier);
    }
    
    @Override
    public void messageToOtherNPC(NPC npcSource, String targetNPCIdentifier, Action action) {
        NPC otherNpcObject = npcList.get(targetNPCIdentifier);
        otherNpcObject.receive(npcSource.getIdentifier(), action);
    }
}
