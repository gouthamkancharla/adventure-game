package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class SaveView.
 *
 * Saves Serialized adventure games.
 */
public class QuickSaveView implements SaveType {

    static String saveFileSuccess = "Saved Adventure Game!!";
    static String saveFileExistsError = "Error: File already exists";
    public Label saveFileErrorLabel = new Label("");
    private AdventureGameView adventureGameView;

    /**
     * Constructor
     */
    public QuickSaveView(AdventureGameView adventureGameView) {
        this.adventureGameView = adventureGameView;
    }

    /**
     * Saves the Game
     * Save the game to a serialized (binary) file.
     * Files will be saved to the Games/Saved directory.
     * If the file already exists, set the saveFileErrorLabel to the text in saveFileExistsError
     * If the file doesn't end in .ser, set the saveFileErrorLabel to the text in saveFileNotSerError
     * Otherwise, load the file and set the saveFileErrorLabel to the text in saveFileSuccess
     */
    public void saveGame() {
        try {
            String name = "quicksave.ser";
            File f = new File("Games/Saved/".replace("/", File.separator) + name);
            f.getParentFile().mkdirs();
            if (f.exists()) {
                f.delete();
            }

            if (!f.createNewFile()) {
                saveFileErrorLabel.setText(saveFileExistsError);
            } else {
                adventureGameView.model.saveModel(f);
                saveFileErrorLabel.setText(saveFileSuccess);
            }
        } catch (IOException e) {
            saveFileErrorLabel.setText("An error occurred");
        }
    }


}

