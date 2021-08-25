package muck.core.character;
import java.util.concurrent.ThreadLocalRandom;

// Random NPC walking
// Example prototype only, PC Movement team may have more specific implementation with Kryo server to handle the
// movement so we can refactor this later. This is to demonstrate that's intended for a random walk NPC behaviour.
// TODO Unit tests for this when integration with frontend & backend is live
public class NPCRandomWalk implements INPCBehaviour {
    NPC npc;
    float timeWait = 30;
    float elapsed = 0;
    String walkDirection = "left";
    boolean walking = false;
    
    public NPCRandomWalk(NPC npc) {
        this.npc = npc;
    }
    
    public void Update() {
        elapsed++;
        
        if (!walking && elapsed >= timeWait) {
            walking = true;
            elapsed = 0;
           
            // Choose direction to now walk
            int randNum = ThreadLocalRandom.current().nextInt(1, 5);
            switch (randNum) {
                case 1:
                    walkDirection="left";
                    break;
                case 2:
                    walkDirection="right";
                    break;
                case 3:
                    walkDirection="up";
                    break;
                case 4:
                    walkDirection="down";
                    break;
            }
        }
        else if (walking && elapsed >= timeWait) {
            // Now stop walking
            elapsed = 0;
            walking = false;
        }
        else if (walking) {
            switch (walkDirection) {
                case "left":
                    npc.moveLeft();
                    break;
                case "right":
                    npc.moveRight();
                    break;
                case "up":
                    npc.moveUp();
                    break;
                case "down":
                    npc.moveDown();
                    break;
            }
        }
    }
}
