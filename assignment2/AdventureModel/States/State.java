package AdventureModel.States;

import AdventureModel.AdventureObjects.AdventureObject;
import AdventureModel.Player;

import java.io.Serializable;

/**
 * The abstract class State represents the state of a player in an adventure game.
 * Different states define the behavior when interacting with objects in the game.
 */
public abstract class State implements Serializable {
    /**
     * The player which has the state
     */
    public Player player;
    /**
     * Constructs a new State object with the specified player.
     *
     * @param player The player for which the state is created.
     */
    public State(Player player){
        this.player = player;
    }
    /**
     * Adds the specified adventure object to the player's inventory based on the current state.
     * The behavior is defined by the concrete implementations of this method in subclasses.
     *
     * @param object The adventure object to be added to the inventory.
     * @return The new state after adding the object to the inventory.
     */
    public abstract State addToInventory(AdventureObject object);

    /**
     * Uses the specified adventure object based on the current state.
     * The behavior is defined by the concrete implementations of this method in subclasses.
     *
     * @param object The adventure object to be used.
     * @return The new state after using the object.
     */
    public abstract State useObject(AdventureObject object);

    /**
     * Retrieves a string representation of the current state.
     * The specific details are provided by the concrete implementations of this method in subclasses.
     *
     * @return A string representing the current state.
     */
    public abstract String getState();
}
