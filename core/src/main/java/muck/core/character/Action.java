package muck.core.character;

/**
    This enum describes the action that is being performed from one NPC to another. The receiving NPC can react
    based upon the action taken of the sending NPC (it doesn't have to, but it can). 
    This adheres to the Mediator pattern.
 */
public enum Action {
    None, // No current action
    Attack, // I'm attacking you
    Flee, // I'm running away from you
    Follow, // I'm following you
    Communicate, // I wish to talk with you
    Dying // I'm dying
}
