package AdventureModel.AdventureObjects;

import AdventureModel.AdventureObjects.AdventureObject;
import AdventureModel.Room;

import java.io.Serializable; //you will need this to save the game!

/**
 * This class keeps track of the props or the objects in the game.
 * These objects have a name, description, and location in the game.
 * The player with the objects can pick or drop them as they like and
 * these objects can be used to pass certain passages in the game.
 */
public class NormalObject extends AdventureObject implements Serializable {
    /**
     * Normal Object Constructor
     * ___________________________
     * This constructor sets the name, description, and location of the object.
     *
     * @param name The name of the Object in the game.
     * @param description One line description of the Object.
     * @param location The location of the Object in the game.
     */
    public NormalObject(String name, String description, Room location){
        super(name,description,location);
    }
}
