package muck.core.character;

import java.util.concurrent.ThreadLocalRandom;

/*
    The Random Walk State of an NPC. Its AI behaviour should have that of a random walk, and be open to interaction.
 */
public class NPCStateRandomWalk implements INPCState {
    NPC npc;
    float timeWait;
    float elapsed = 0;
    float timeWalk;
    double speed;
    boolean walking = false;

    /**
     * Initialise with a prefixed random walk times
     * @param npc NPC
     */
    public NPCStateRandomWalk(NPC npc) {
        this.npc = npc;
        this.timeWait = 30;
        this.timeWalk = 30;
        this.speed = 1;

    }

    /**
     * Initialise with optional random walk times
     * @param npc NPC
     * @param speed Speed of NPC walk
     * @param timeWait Time waiting in a spot
     * @param timeWalk Time while walking
     */
    public NPCStateRandomWalk(NPC npc, double speed, float timeWait, float timeWalk) {
        this.npc = npc;
        this.timeWait = timeWait;
        this.timeWalk = timeWalk;
        this.speed = speed;
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
                    npc.setDirection("left");
                    break;
                case 2:
                    npc.setDirection("right");
                    break;
                case 3:
                    npc.setDirection("up");
                    break;
                case 4:
                    npc.setDirection("down");
                    break;
            }
        }
        else if (walking && elapsed >= timeWalk) {
            // Now stop walking
            elapsed = 0;
            walking = false;
        }
        else if (walking) {
            switch (npc.getDirection()) {
                case "left":
                    npc.moveLeft(speed);
                    break;
                case "right":
                    npc.moveRight(speed);
                    break;
                case "up":
                    npc.moveUp(speed);
                    break;
                case "down":
                    npc.moveDown(speed);
                    break;
            }
        }
    }

}
