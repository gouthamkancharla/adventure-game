package AdventureModel.AdventureObjects;

import AdventureModel.Room;

import java.io.Serializable;

/**
 * The abstract class AdventureObject represents an object in an adventure game.
 * It encapsulates properties such as name, description, location, and magical status.
 */
public abstract class AdventureObject implements Serializable {
    /**
     * The name of the object.
     */
    private String objectName;
    /**
     * The description of the object.
     */
    private String description;
    /**
     * The location of the object.
     */
    private Room location = null;
    /**
     * The magical status of object
     */
    public Boolean magical = null;
    /**
     * Adventure Object Constructor
     * ___________________________
     * This constructor sets the name, description, and location of the object.
     *
     * @param name The name of the Object in the game.
     * @param description One line description of the Object.
     * @param location The location of the Object in the game.
     */
    public AdventureObject(String name, String description, Room location){
        this.objectName = name;
        this.description = description;
        this.location = location;
        this.magical = false;
    }
    /**
     * Getter method for the name attribute.
     *
     * @return name of the object
     */
    public String getName(){
        return this.objectName;
    }
    /**
     * Getter method for the description attribute.
     *
     * @return description of the game
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * This method returns the location of the object if the object is still in
     * the room. If the object has been pickUp up by the player, it returns null.
     *
     * @return returns the location of the object if the objects is still in
     * the room otherwise, returns null.
     */
    public Room getLocation(){
        return this.location;
    }

    /**
     * This method returns if the object is special if the object is special it returns
     * magical otherwise it returns notmagical
     *
     * @return returns a the type of object
     */
    public String getMagical(){
        if (!this.magical){
            return("notmagical");
        } else {
            return("magical");
        }
    }
}
