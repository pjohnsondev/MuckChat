package muck.core.character;

import java.util.concurrent.ThreadLocalRandom;

/*
    The Random Walk State of an NPC. Its AI behaviour should have that of a random walk, and be open to interaction.
 */
public class NPCStateRandomWalk implements INPCState {
    NPC npc;
    float timeWait = 30;
    float elapsed = 0;
    String walkDirection = "left";
    boolean walking = false;

    public NPCStateRandomWalk(NPC npc) {
        this.npc = npc;
    }

    @Override
    public void handle() {
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
