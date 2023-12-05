package AdventureModel;

import AdventureModel.AdventureObjects.AdventureObject;
import AdventureModel.States.NormalState;
import Trolls.GameTroll;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;
import java.time.LocalTime;

/**
 * Class AdventureGame.  Handles all the necessary tasks to run the Adventure game.
 */
public class AdventureGame implements Serializable {
    private final String directoryName; //An attribute to store the Introductory text of the game.
    private String helpText; //A variable to store the Help text of the game. This text is displayed when the user types "HELP" command.
    private final HashMap<Integer, Room> rooms; //A list of all the rooms in the game.
    private HashMap<String,String> synonyms = new HashMap<>(); //A HashMap to store synonyms of commands.
    private final String[] actionVerbs = {"QUIT","INVENTORY","TAKE","DROP","RESET","INCREASE","DECREASE","ADD"}; //List of action verbs (other than motions) that exist in all games. Motion vary depending on the room and game.
    public Player player; //The Player of the game.
    public ArrayList<String> notes = new ArrayList<>(); //The notes of the player.
    public int[] stats = new int[3]; //The stats of the player (# of rooms visited, objects discovered, notes taken).

    /**
     * Adventure Game Constructor
     * __________________________
     * Initializes attributes
     *
     * @param name the name of the adventure
     */
    public AdventureGame(String name){
        this.synonyms = new HashMap<>();
        this.rooms = new HashMap<>();
        stats[0] = 1; //the player starts in room 1
        this.directoryName = "assignment2/Games/" + name; //all games files are in the Games directory!
        try {
            setUpGame();
        } catch (IOException e) {
            throw new RuntimeException("An Error Occurred: " + e.getMessage());
        }
    }

    /**
     * Save the current state of the game to a file
     * 
     * @param file pointer to file to write to
     */
    public void saveModel(File file) {
        try {
            FileOutputStream outfile = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(outfile);
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * setUpGame
     * __________________________
     *
     * @throws IOException in the case of a file I/O error
     */
    public void setUpGame() throws IOException {

        String directoryName = this.directoryName;
        AdventureLoader loader = new AdventureLoader(this, directoryName);
        loader.loadGame();

        // set up the player's current location
        this.player = new Player(this.rooms.get(1));
        this.player.state = new NormalState(this.player);
    }

    /**
     * tokenize
     * __________________________
     *
     * @param input string from the command line
     * @return a string array of tokens that represents the command.
     */
    public String[] tokenize(String input){

        input = input.toUpperCase();
        String[] commandArray = input.split(" ");

        int i = 0;
        while (i < commandArray.length) {
            if(this.synonyms.containsKey(commandArray[i])){
                commandArray[i] = this.synonyms.get(commandArray[i]);
            }
            i++;
        }
        return commandArray;
    }

    /**
     * movePlayer
     * __________________________
     * Moves the player in the given direction, if possible.
     * Return false if the player wins or dies as a result of the move.
     *
     * @param direction the move command
     * @return false, if move results in death or a win (and game is over).  Else, true.
     */
    public boolean movePlayer(String direction) {
        direction = direction.toUpperCase();
        PassageTable motionTable = this.player.getCurrentRoom().getMotionTable(); //where can we move?
        if (!motionTable.optionExists(direction)) return true; //no move

        ArrayList<Passage> possibilities = new ArrayList<>();
        for (Passage entry : motionTable.getDirection()) {
            if (entry.getDirection().equals(direction)) { //this is the right direction
                possibilities.add(entry); // are there possibilities?
            }
        }

        //try the blocked passages first
        Passage chosen = null;
        if(player.getState().equals("magicalstate")) //if magical state choose the first path that is unblocked
        {
            chosen = (possibilities.get(0));
            Boolean blocked = chosen.getIsBlocked();
            AdventureObject objectMagical = null;
            for(AdventureObject adventureObject: this.player.inventory){
                if (adventureObject.getMagical().equals("magical")){
                    objectMagical = adventureObject;
                    break;
                }
            }
            if (blocked) {
                player.useObject(objectMagical);
            }
        }
        else {
            for (Passage entry : possibilities) {
                System.out.println(entry.getIsBlocked());
                System.out.println(entry.getKeyName());

                if (entry.getIsBlocked()) {
                    if (this.player.getInventory().contains(entry.getKeyName())) {
                        chosen = entry; //we can make it through, given our stuff
                        break;
                    } else if (entry.getKeyName().contains("TIME")) {
                        String textStartTime = entry.getKeyName().substring(5, 10);
                        String textEndTime = entry.getKeyName().substring(11);
                        LocalTime startTime = LocalTime.parse(textStartTime);
                        LocalTime endTime = LocalTime.parse(textEndTime);
                        LocalTime currentTime = LocalTime.now();
                        if (!currentTime.isBefore(startTime) && !currentTime.isAfter(endTime)) {
                            chosen = entry;
                            break;
                        }
                    } else if (entry.getKeyName().contains("TRAP")) {
                        chosen = entry;
                        player.health -= Integer.parseInt(entry.getKeyName().substring(4));
                        if (player.health <= 0) {
                            return false;
                        }
                        break;
                    }
                    }else {
                        chosen = entry;
                        break;
                } //the passage is unlocked
            }
        }
        if (chosen == null) return true; //doh, we just can't move.

        stats[0]++; //increment the number of rooms visited
        int roomNumber = chosen.getDestinationRoom();
        Room room = this.rooms.get(roomNumber);
        this.player.setCurrentRoom(room);
        return !this.player.getCurrentRoom().getMotionTable().getDirection().get(0).getDirection().equals("FORCED");
    }

    /**
     * interpretAction
     * interpret the user's action.
     *
     * @param command String representation of the command.
     */
    public String interpretAction(String command) {

        String[] inputArray = tokenize(command); //look up synonyms

        PassageTable motionTable = this.player.getCurrentRoom().getMotionTable(); //where can we move?
        int priorRoomNumber = this.player.getCurrentRoom().getRoomNumber();

        if (motionTable.optionExists(inputArray[0])) {
            if (!movePlayer(inputArray[0])) {
                if (this.player.getCurrentRoom().getMotionTable().getDirection().get(0).getDestinationRoom() == 0 || this.player.health <= 0)
                    return "GAME OVER";
                else return "FORCED";
            } //something is up here! We are dead or we won.

            for(Passage m: motionTable.getDirection()) {
                if (m.getDirection().equals(inputArray[0]) && m.getIsBlocked() && m.getKeyName().equals("GAMETROLL") && (this.player.getCurrentRoom().getRoomNumber() == priorRoomNumber))
                    return ("GAMETROLL");
            }
            for (Passage m : motionTable.getDirection()) {
                if (m.getDirection().equals(inputArray[0]) && m.getIsBlocked() && m.getKeyName().contains("TROLL") && this.player.getCurrentRoom().getRoomNumber() == priorRoomNumber) return m.getKeyName();
                }
            return null;
        } else if(Arrays.asList(this.actionVerbs).contains(inputArray[0])) {
            if(inputArray[0].equals("QUIT")) { return "GAME OVER"; } //time to stop!
            else if(inputArray[0].equals("INVENTORY") && this.player.getInventory().size() == 0) return "INVENTORY IS EMPTY";
            else if(inputArray[0].equals("INVENTORY") && this.player.getInventory().size() > 0) return "THESE OBJECTS ARE IN YOUR INVENTORY:\n" + this.player.getInventory().toString();
            else if(inputArray[0].equals("TAKE") && inputArray.length < 2) return "THE TAKE COMMAND REQUIRES AN OBJECT";
            else if(inputArray[0].equals("DROP") && inputArray.length < 2) return "THE DROP COMMAND REQUIRES AN OBJECT";
            else if(inputArray[0].equals("TAKE") && inputArray.length == 2) {
                if(this.player.getCurrentRoom().checkIfObjectInRoom(inputArray[1])) {
                    this.player.takeObject(inputArray[1]);
                    stats[1]++; //increment the number of items discovered
                    return "YOU HAVE TAKEN:\n " + inputArray[1];
                } else {
                    return "THIS OBJECT IS NOT HERE:\n " + inputArray[1];
                }
            }
            else if(inputArray[0].equals("DROP") && inputArray.length == 2) {
                if(this.player.checkIfObjectInInventory(inputArray[1])) {
                    this.player.dropObject(inputArray[1]);
                    return "YOU HAVE DROPPED:\n " + inputArray[1];
                } else {
                    return "THIS OBJECT IS NOT IN YOUR INVENTORY:\n " + inputArray[1];
                }
            } else if (command.equalsIgnoreCase("RESET FONT SIZE")) {
                return "FONT SIZE RESET";
            } else if (command.equalsIgnoreCase("INCREASE FONT SIZE")) {
                return "FONT SIZE INCREASED";
            } else if (command.equalsIgnoreCase("DECREASE FONT SIZE")) {
                return "FONT SIZE DECREASED";
            } else if (command.contains("ADD")) {
                String[] words = command.substring(4).split("=");
                synonyms.put(words[0].trim().toUpperCase(), words[1].trim().toUpperCase());
                return "SYNONYM ADDED";
            }
        }
        return "INVALID COMMAND.";
    }

    /**
     * getDirectoryName
     * __________________________
     * Getter method for directory 
     * @return directoryName
     */
    public String getDirectoryName() {
        return this.directoryName;
    }

    /**
     * getInstructions
     * __________________________
     * Getter method for instructions 
     * @return helpText
     */
    public String getInstructions() {
        return helpText;
    }

    /**
     * getPlayer
     * __________________________
     * Getter method for Player 
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * getRooms
     * __________________________
     * Getter method for rooms 
     * @return map of key value pairs (integer to room)
     */
    public HashMap<Integer, Room> getRooms() {
        return this.rooms;
    }

    /**
     * getSynonyms
     * __________________________
     * Getter method for synonyms 
     * @return map of key value pairs (synonym to command)
     */
    public HashMap<String, String> getSynonyms() {
        return this.synonyms;
    }

    /**
     * setHelpText
     * __________________________
     * Setter method for helpText
     * @param help which is text to set
     */
    public void setHelpText(String help) {
        this.helpText = help;
    }
}