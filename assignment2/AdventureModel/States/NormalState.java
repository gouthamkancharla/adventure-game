package AdventureModel.States;

import AdventureModel.AdventureObjects.AdventureObject;
import AdventureModel.Player;

import java.io.Serializable;

/**
 * The NormalState class represents the normal state of a player in an adventure game.
 * In this state, the player can interact with objects and transition to other states based on the object's properties.
 */
public class NormalState extends State implements Serializable {
    /**
     * Constructs a new NormalState object with the specified player.
     *
     * @param player The player for which the normal state is created.
     */
    public NormalState(Player player) {
        super(player);
    }
    /**
     * Adds the specified adventure object to the player's inventory and transitions to a new state.
     * If the object is magical, transitions to a MagicalState; otherwise, stays in the NormalState.
     *
     * @param object The adventure object to be added to the inventory.
     * @return The new state after adding the object to the inventory.
     */
    public State addToInventory(AdventureObject object) {
        if (object.getMagical().equals("magical")){
            return (new MagicalState(this.player));
        } else {
            return (new NormalState(this.player));
        }
    }
    /**
     * Uses the specified adventure object and transitions to a new NormalState.
     *
     * @param object The adventure object to be used.
     * @return The new state after using the object (always a NormalState in this case).
     */
    public State useObject(AdventureObject object){
        return (new NormalState(player));
    }

    /**
     * Retrieves a string representation of the current state.
     *
     * @return A string representing the normal state.
     */
    public String getState(){
        return "normalstate";
    }
}
