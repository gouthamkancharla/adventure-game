package views;

import javafx.scene.control.Label;

/**
 * Interface SaveType
 *
 * Interface for methods of saving the game.
 */
public interface SaveType {
    static String saveFileSuccess = "Saved Adventure Game!!";
    static String saveFileExistsError = "Error: File already exists";
    public Label saveFileErrorLabel = new Label("Saved Adventure Game!!");
    public AdventureGameView adventureGameView = null;
    public void saveGame();
}
