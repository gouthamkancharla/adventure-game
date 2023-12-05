package AdventureModel.States;

import AdventureModel.AdventureObjects.AdventureObject;
import AdventureModel.Player;

import java.io.Serializable;

/**
 * The MagicalState class represents the state of a player in an adventure game who possesses a magical object in their inventory.
 * This state allows the player to interact with objects and handles transitions between states based on object usage.
 * Extends the abstract class State.
 */
public class MagicalState extends State implements Serializable {

    /**
     * Constructs a new MagicalState object with the specified player.
     *
     * @param player The player for which the magical state is created.
     */
    public MagicalState(Player player) {
        super(player);
    }

    /**
     *
     * @param object
     * @return MagicalState
     */
    public State addToInventory(AdventureObject object) {
        return (new MagicalState(player));
    }

    /**
     * Adds the specified magical adventure object to the player's inventory and stays in the MagicalState.
     *
     * @param object The magical adventure object to be added to the inventory.
     * @return The current magical state.
     */
    public State useObject(AdventureObject object){
        player.inventory.remove(object);
        Boolean magicalPresent = false;
        for(AdventureObject objects: this.player.inventory){
            if(objects.getMagical().equals("magical")){
                magicalPresent = true;
                break;
            }
        }
        if (!magicalPresent){
            return (new NormalState(player));
        }
        else{
            return (new MagicalState(player));
        }
    }
    /**
     * Retrieves a string representation of the current state.
     *
     * @return A string representing the magical state.
     */
    public String getState(){
        return "magicalstate";
    }
}