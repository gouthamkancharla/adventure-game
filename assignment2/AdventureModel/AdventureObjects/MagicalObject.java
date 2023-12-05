package AdventureModel.AdventureObjects;

import AdventureModel.AdventureObjects.AdventureObject;
import AdventureModel.Room;

import java.io.Serializable;
/**
 * The MagicalObject class represents a magical object in an adventure game.
 * Extends the AdventureObject class and implements the Serializable interface.
 */
public class MagicalObject extends AdventureObject implements Serializable {
    /**
     * Magical Object Constructor
     * ___________________________
     * This constructor sets the name, description, and location of the object.
     *
     * @param name The name of the Object in the game.
     * @param description One line description of the Object.
     * @param location The location of the Object in the game.
     */
    public MagicalObject(String name, String description, Room location){
        super(name,description,location);
        this.magical = true;
    }
}
