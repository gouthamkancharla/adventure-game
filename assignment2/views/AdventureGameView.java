package views;

import AdventureModel.AdventureGame;
import AdventureModel.AdventureObjects.AdventureObject;
import AdventureModel.Passage;
import AdventureModel.AdventureObjects.AdventureObject;
import AdventureModel.PassageTable;
import AdventureModel.Room;
import Trolls.GameTroll;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.input.KeyEvent; //you will need these!
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.event.EventHandler; //you will need this too!
import javafx.scene.AccessibleRole;

import javafx.scene.Node;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import Trolls.NoteTroll;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import javax.imageio.stream.ImageInputStream;

/**
 * Class AdventureGameView.
 *
 * This is the Class that will visualize your model.
 * You are asked to demo your visualization via a Zoom
 * recording. Place a link to your recording below.
 *
 * ZOOM LINK: https://drive.google.com/file/d/16WPIVQE-nIK_0biBzQq-4lQBOcvoX3yJ/view?usp=sharing
 * PASSWORD: <PASSWORD HERE>
 */
public class AdventureGameView {

    AdventureGame model; //model of the game
    Stage stage; //stage on which all is rendered
    Button saveButton, loadButton, helpButton, startoverButton, exitgameButton, qSaveButton, customizerButton; //buttons

    String saveButtonCol, loadButtonCol, helpButtonCol, qSaveButtonCol, customizerButtonCol, startoverButtonCol, exitgameButtonCol;
    Boolean helpToggle = false; //is help on display?
    Font currFont;

    GridPane gridPane = new GridPane(); //to hold images and buttons
    Label roomDescLabel = new Label(); //to hold room description and/or instructions
    VBox objectsInRoom = new VBox(); //to hold room items
    VBox objectsInInventory = new VBox(); //to hold inventory items
    ImageView roomImageView; //to hold room image
    TextField inputTextField; //for user input
    String roomdescfont;
    //Initializing labels to be able to access them in CustomizeView
    Label objLabel;
    Label invLabel;
    Label commandLabel;

    private MediaPlayer mediaPlayer; //to play audio
    private boolean mediaPlaying; //to know if the audio is playing

    private final FontSizeMemento memento = new FontSizeMemento(16);
    private NoteTroll troll;
    private int counter = 0;

    /**
     * Adventure Game View Constructor
     * __________________________
     * Initializes attributes
     */
    public AdventureGameView(AdventureGame model, Stage stage) {
        this.model = model;
        this.stage = stage;
        intiUI();
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
    }

    /**
     * Initialize the UI
     */
    public void intiUI() {

        // setting up the stage
        this.stage.setTitle("kanchar9's Adventure Game"); //Replace <YOUR UTORID> with your UtorID

        //Inventory + Room items
        objectsInInventory.setSpacing(10);
        objectsInInventory.setAlignment(Pos.TOP_CENTER);
        objectsInRoom.setSpacing(10);
        objectsInRoom.setAlignment(Pos.TOP_CENTER);

        // GridPane, anyone?
        gridPane.setPadding(new Insets(20));
        gridPane.setBackground(new Background(new BackgroundFill(
                Color.valueOf("#000000"),
                new CornerRadii(0),
                new Insets(0)
        )));

        //Three columns, three rows for the GridPane
        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(650);
        ColumnConstraints column3 = new ColumnConstraints(150);
        column3.setHgrow( Priority.SOMETIMES ); //let some columns grow to take any extra space
        column1.setHgrow( Priority.SOMETIMES );

        // Row constraints
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints( 550 );
        RowConstraints row3 = new RowConstraints();
        row1.setVgrow( Priority.SOMETIMES );
        row3.setVgrow( Priority.SOMETIMES );

        gridPane.getColumnConstraints().addAll( column1 , column2 , column1 );
        gridPane.getRowConstraints().addAll( row1 , row2 , row1 );

        // Buttons
        saveButton = new Button("Save");
        saveButton.setId("Save");
        customizeButton(saveButton, 100, 50);
        makeButtonAccessible(saveButton, "Save Button", "This button saves the game.", "This button saves the game. Click it in order to save your current progress, so you can play more later.");
        addSaveEvent();
        addCursorHandlerButton(saveButton);
        saveButtonCol = "-fx-background-color: #17871b;";

        qSaveButton = new Button("Quicksave");
        qSaveButton.setId("Quicksave");
        customizeButton(qSaveButton, 100, 50);
        makeButtonAccessible(qSaveButton, "Quicksave Button", "This button quicksaves the game.", "This button quicksaves the game. Click it in order to save your current progress, so you can play more later.");
        addQSaveEvent();
        addCursorHandlerButton(qSaveButton);
        qSaveButtonCol = "-fx-background-color: #17871b;";

        loadButton = new Button("Load");
        loadButton.setId("Load");
        customizeButton(loadButton, 100, 50);
        makeButtonAccessible(loadButton, "Load Button", "This button loads a game from a file.", "This button loads the game from a file. Click it in order to load a game that you saved at a prior date.");
        addLoadEvent();
        addCursorHandlerButton(loadButton);
        loadButtonCol = "-fx-background-color: #17871b;";

        helpButton = new Button("Instructions");
        helpButton.setId("Instructions");
        customizeButton(helpButton, 150, 50);
        makeButtonAccessible(helpButton, "Help Button", "This button gives game instructions.", "This button gives instructions on the game controls. Click it to learn how to play.");
        addInstructionEvent();
        addCursorHandlerButton(helpButton);
        helpButtonCol = "-fx-background-color: #17871b;";

        startoverButton = new Button("StartOver");
        startoverButton.setId("StartOver");
        customizeButton(startoverButton, 100, 50);
        makeButtonAccessible(startoverButton, "StartOver button", "This button allows you to start the game over", "When you beat the game or die, you can click this button to either reset the game if you have no save files, or load to the most recent save if you have save files");
        addStartOverEvent();
        addCursorHandlerButton(startoverButton);
        startoverButtonCol = "-fx-background-color: #17871b;";

        exitgameButton = new Button("ExitGame");
        exitgameButton.setId("ExitGame");
        customizeButton(exitgameButton, 100, 50);
        makeButtonAccessible(exitgameButton, "ExitGame button", "This button will quit the game", "When you beat the game or die, you can click this button to exit the game and close the window");
        addExitGameEvent();
        addCursorHandlerButton(exitgameButton);
        exitgameButtonCol = "-fx-background-color: #17871b;";

        customizerButton = new Button("Customize");
        customizerButton.setId("Customize");
        customizeButton(customizerButton, 100, 50);
        makeButtonAccessible(customizerButton, "Customize Button", "This button will open the customize menu", "When you click this button, a customizing menu will open, allowing you to change the font, colour of the buttons and background");
        addCustomizeEvent();
        addCursorHandlerButton(customizerButton);
        customizerButtonCol = "-fx-background-color: #17851B";

        HBox topButtons = new HBox();
        topButtons.getChildren().addAll(saveButton, qSaveButton, helpButton, loadButton, customizerButton);
        topButtons.setSpacing(10);
        topButtons.setAlignment(Pos.CENTER);

        inputTextField = new TextField();
        inputTextField.setFont(new Font(roomdescfont, 16));
        inputTextField.setFocusTraversable(true);

        inputTextField.setAccessibleRole(AccessibleRole.TEXT_AREA);
        inputTextField.setAccessibleRoleDescription("Text Entry Box");
        inputTextField.setAccessibleText("Enter commands in this box.");
        inputTextField.setAccessibleHelp("This is the area in which you can enter commands you would like to play.  Enter a command and hit return to continue.");
        addTextHandlingEvent(); //attach an event to this input field

        //labels for inventory and room items
        objLabel =  new Label("Objects in Room");
        objLabel.setAlignment(Pos.CENTER);
        objLabel.setStyle("-fx-text-fill: white;");
        objLabel.setFont(new Font(roomdescfont, 16));

        invLabel =  new Label("Your Inventory");
        invLabel.setAlignment(Pos.CENTER);
        invLabel.setStyle("-fx-text-fill: white;");
        invLabel.setFont(new Font(roomdescfont, 16));

        //add all the widgets to the GridPane
        gridPane.add( objLabel, 0, 0, 1, 1 );  // Add label
        gridPane.add( topButtons, 1, 0, 1, 1 );  // Add buttons
        gridPane.add( invLabel, 2, 0, 1, 1 );  // Add label

        commandLabel = new Label("What would you like to do?");
        commandLabel.setStyle("-fx-text-fill: white;");
        commandLabel.setFont(new Font(roomdescfont, 16));

        updateScene("", false); //method displays an image and whatever text is supplied
        updateItems(); //update items shows inventory and objects in rooms

        // adding the text area and submit button to a VBox
        VBox textEntry = new VBox();
        textEntry.setStyle("-fx-background-color: #000000;");
        textEntry.setPadding(new Insets(20, 20, 20, 20));
        textEntry.getChildren().addAll(commandLabel, inputTextField);
        textEntry.setSpacing(10);
        textEntry.setAlignment(Pos.CENTER);
        gridPane.add( textEntry, 0, 2, 3, 1 );

        roomdescfont = "Arial";

        // Render everything
        var scene = new Scene( gridPane ,  1000, 800);
        scene.setFill(Color.BLACK);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();

    }

    /**
     * addCursorHandlerButton
     * ------------------------
     * Adds EventHandlers for when the mouse cursor is moved
     * onto or off a button.
     * @param b the button to add EventHandlers for
     */
    private void addCursorHandlerButton(Button b) {
        b.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                b.setStyle("-fx-background-color: LightBlue");
                b.setTextFill(Color.BLACK);
            }
        });
        b.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (b == saveButton){
                    b.setStyle(saveButtonCol);
                    b.setTextFill(Color.WHITE);
                } else if (b == qSaveButton) {
                    b.setStyle(qSaveButtonCol);
                    b.setTextFill(Color.WHITE);
                } else if (b == loadButton) {
                    b.setStyle(loadButtonCol);
                    b.setTextFill(Color.WHITE);
                } else if (b == helpButton) {
                    b.setStyle(helpButtonCol);
                    b.setTextFill(Color.WHITE);
                } else if (b == customizerButton) {
                    b.setStyle(customizerButtonCol);
                    b.setTextFill(Color.WHITE);
                } else if (b == startoverButton) {
                    b.setStyle(startoverButtonCol);
                    b.setTextFill(Color.WHITE);
                } else if (b == exitgameButton) {
                    b.setStyle(exitgameButtonCol);
                    b.setTextFill(Color.WHITE);
                } else {
                    b.setStyle("-fx-background-color: #17851B");
                    b.setTextFill(Color.WHITE);
                }
            }
        });
    }


    /**
     * makeButtonAccessible
     * __________________________
     * For information about ARIA standards, see
     * https://www.w3.org/WAI/standards-guidelines/aria/
     *
     * @param inputButton the button to add screenreader hooks to
     * @param name ARIA name
     * @param shortString ARIA accessible text
     * @param longString ARIA accessible help text
     */
    public static void makeButtonAccessible(Button inputButton, String name, String shortString, String longString) {
        inputButton.setAccessibleRole(AccessibleRole.BUTTON);
        inputButton.setAccessibleRoleDescription(name);
        inputButton.setAccessibleText(shortString);
        inputButton.setAccessibleHelp(longString);
        inputButton.setFocusTraversable(true);
    }

    /**
     * customizeButton
     * __________________________
     *
     * @param inputButton the button to make stylish :)
     * @param w width
     * @param h height
     */
    private void customizeButton(Button inputButton, int w, int h) {
        inputButton.setPrefSize(w, h);
        inputButton.setFont(new Font(roomdescfont, 16));
        inputButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
    }

    /**
     * addTextHandlingEvent
     * __________________________
     * Add an event handler to the myTextField attribute 
     *
     * Your event handler should respond when users 
     * hits the ENTER or TAB KEY. If the user hits 
     * the ENTER Key, strip white space from the
     * input to myTextField and pass the stripped 
     * string to submitEvent for processing.
     *
     * If the user hits the TAB key, move the focus 
     * of the scene onto any other node in the scene
     * graph by invoking requestFocus method.
     */
    private void addTextHandlingEvent() {
        //add your code here!
        inputTextField.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    if (troll == null) {
                        submitEvent(inputTextField.getText().strip());
                    } else {
                        if (troll.playGame(inputTextField.getText().strip()).contains("MAY PASS")) {
                            model.getPlayer().setCurrentRoom(model.getRooms().get(troll.blockedPassage.getDestinationRoom()));
                            updateScene(model.getPlayer().getCurrentRoom().getRoomDescription(), false);
                            articulateRoomDescription();
                        } else {
                            model.getPlayer().health -= troll.trollDamage;
                            if (model.getPlayer().health <= 0) {
                                updateScene("GAME OVER", false);
                                updateItems();
                                inputTextField.setDisable(true);
                                PauseTransition pause = new PauseTransition(Duration.seconds(10));
                                pause.setOnFinished(event -> {
                                    Platform.exit();
                                });
                            }
                            updateScene(troll.playGame(inputTextField.getText().strip()), false);
                        }
                        troll = null;
                    }
                    inputTextField.setText("");
                } else if (keyEvent.getCode() == KeyCode.TAB) {
                    gridPane.requestFocus();
                }
            }
        });
    }


    /**
     * submitEvent
     * __________________________
     *
     * @param text the command that needs to be processed
     */
    private void submitEvent(String text) {

        text = text.strip(); //get rid of white space
        stopArticulation(); //if speaking, stop


        if (text.equalsIgnoreCase("LOOK") || text.equalsIgnoreCase("L")) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription();
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (!objectString.isEmpty()) roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            articulateRoomDescription(); //all we want, if we are looking, is to repeat description.
            return;
        } else if (text.equalsIgnoreCase("HELP") || text.equalsIgnoreCase("H")) {
            showInstructions();
            return;
        } else if (text.equalsIgnoreCase("COMMANDS") || text.equalsIgnoreCase("C")) {
            showCommands(); //this is new!  We did not have this command in A1
            return;
        } else if (text.equalsIgnoreCase("RESET FONT SIZE")) {
            memento.resetFontSize();
        } else if (text.equalsIgnoreCase("INCREASE FONT SIZE")) {
            memento.increaseFontSize();
        } else if (text.equalsIgnoreCase("DECREASE FONT SIZE")) {
            memento.decreaseFontSize();
        } else if (text.equalsIgnoreCase("INVENTORY")) {
            articulateInventory();
        } else if (text.equalsIgnoreCase("CHANGE THEME")) {
            if (counter++ % 2 == 0) {
                setWhiteTheme();
            } else {
                setDarkTheme();
            }
            return;
        } else if (text.equalsIgnoreCase("CHECK HEALTH")) {
            roomDescLabel.setText("Your health is " + model.getPlayer().health + "/100 hearts.");
            return;
        } else if (text.contains("NOTE ")) {
            model.notes.add(text.substring(5));
            model.stats[2]++;
            updateScene("NOTE ADDED", false);
            return;
        } else if (text.equalsIgnoreCase("SHOW NOTES")) {
            roomDescLabel.setText(String.join(", ", model.notes));
            return;
        } else if (text.equalsIgnoreCase("DISPLAY STATS")) {
            roomDescLabel.setText("# of rooms visited: " + model.stats[0] + "\n# of objects discovered: " + model.stats[1] + "\n# of notes taken: " + model.stats[2]);
            return;
        }

        //try to move!
        String output = this.model.interpretAction(text); //process the command!

        if (output == null || (!output.equals("GAME OVER") && !output.equals("FORCED") && !output.equals("HELP")) && !output.contains("TROLL")) {
            updateScene(output, false);
            updateItems();
        } else if (output.equals("GAME OVER")) {
            updateScene("GAME OVER", false);
            updateItems();
            PauseTransition pause = new PauseTransition(Duration.seconds(10));
            pause.setOnFinished(event -> {
                Platform.exit();
            });
            pause.play();
        } else if (output.equals("FORCED")) {
            //write code here to handle "FORCED" events!
            //Your code will need to display the image in the
            //current room and pause, then transition to
            //the forced room.
            moveForced();
        } else if (output.equals("HELPERTROLL")) {
            Room curroom = model.player.getCurrentRoom();
            stopArticulation();
            updateScene("", true);
            stopArticulation();
            Integer destroomnum = 0;
            for (Passage passage : curroom.getMotionTable().passageTable){
                if (Objects.equals(passage.getKeyName(), "HELPERTROLL")){
                    destroomnum = passage.getDestinationRoom();
                }
            }
            Room destroom = model.getRooms().get(destroomnum);
            ArrayList<Room> rooms_w_objects = new ArrayList<>();
            for (Room room : model.getRooms().values()){
                if (!room.objectsInRoom.isEmpty() && !Objects.equals(curroom, room)){
                    rooms_w_objects.add(room);
                }
            }
            Collections.shuffle(rooms_w_objects);
            model.player.setCurrentRoom(rooms_w_objects.get(0));
            Collections.shuffle(model.player.getCurrentRoom().objectsInRoom);
            AdventureObject randomobj = model.player.getCurrentRoom().objectsInRoom.get(0);
            model.player.getCurrentRoom().objectsInRoom.remove(0);
            model.player.addToInventory(randomobj);
            model.player.setCurrentRoom(curroom);
            roomDescLabel.setText("A HELPFUL troll has arrived, he seems more friendly, wearing glasses. He tells you, 'Greetings traveler, i have decided to give you a gift, this " + randomobj.getName() + "! Please enjoy this offering in your travels!'");
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(5));
            inputTextField.setDisable(true);
            pauseTransition.setOnFinished(e -> {
                inputTextField.setDisable(false);
                model.player.setCurrentRoom(destroom);
                updateScene("", false);
                updateItems();
            });
            pauseTransition.play();
//
            return;

        } else if (output.equals("GAMETROLL")) {
            Room chosen = null;
            for (Passage m : this.model.player.getCurrentRoom().getMotionTable().getDirection()) {
                if (m.getIsBlocked()) {
                    if (m.getKeyName().equals("GAMETROLL") && (text.toUpperCase().equals((m.getDirection())))) {
                        chosen = this.model.getRooms().get(m.getDestinationRoom());
                    }
                }
            }
            GameTroll gameTroll = new GameTroll(this.model.player, chosen);
            Stage stage = new Stage();
            gameTroll.start(stage);
            stage.setOnHidden(event -> {updateScene("", false);
                    updateItems();
                });
            }

        else if (output.contains("TROLL")) {
            PassageTable motionTable = model.getPlayer().getCurrentRoom().getMotionTable();
            for (Passage entry : motionTable.getDirection()) {
                    if (entry.getKeyName() != null && entry.getKeyName().contains("TROLL")) {
                        troll = new NoteTroll(entry, Integer.parseInt(entry.getKeyName().substring(5)));
                        updateScene(troll.startGame(), true);
                        break;
                    }
                }
            }
        if (model.getPlayer().getState().equals("magicalstate")) {
            inputTextField.setBackground(Background.fill(Color.RED));
        }
        else{
            inputTextField.setBackground(Background.fill(Color.WHITE));
        }
    }

    /**
     * moveForced
     * __________________________
     *
     * transition through forced rooms when the current room has a forced passage.
     */
    private void moveForced() {
        inputTextField.setEditable(false);
        updateScene(null, false);
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> {
            String output = this.model.interpretAction("FORCED");
            updateScene(null, false);
            updateItems();
            if ("FORCED".equals(output)) {
                moveForced();
            } else if ("GAME OVER".equals(output)) {
                PauseTransition new_pause = new PauseTransition(Duration.seconds(10));
                new_pause.setOnFinished(new_event -> {
                    Platform.exit();
                });
                new_pause.play();
            } else {
                inputTextField.setEditable(true);
            }
        });
        pause.play();
    }


    /**
     * showCommands
     * __________________________
     *
     * update the text in the GUI (within roomDescLabel)
     * to show all the moves that are possible from the 
     * current room.
     */
    private void showCommands() {
        roomDescLabel.setText(model.getPlayer().getCurrentRoom().getCommands());
    }


    /**
     * updateScene
     * __________________________
     *
     * Show the current room, and print some text below it.
     * If the input parameter is not null, it will be displayed
     * below the image.
     * Otherwise, the current room description will be dispplayed
     * below the image.
     * 
     * @param textToDisplay the text to display below the image.
     */
    public void updateScene(String textToDisplay, boolean trollGame) {

        if (!trollGame) {
            getRoomImage(); //get the image of the current room
        } else {
            roomImageView = new ImageView(new Image("Trolls/troll.png"));
            roomImageView.setPreserveRatio(true);
            roomImageView.setFitWidth(400);
            roomImageView.setFitHeight(400);
        }
        formatText(textToDisplay); //format the text to display
        roomDescLabel.setPrefWidth(500);
        roomDescLabel.setPrefHeight(500);
        roomDescLabel.setTextOverrun(OverrunStyle.CLIP);
        roomDescLabel.setWrapText(true);
        VBox roomPane = new VBox(roomImageView,roomDescLabel);
        roomPane.setPadding(new Insets(10));
        roomPane.setAlignment(Pos.TOP_CENTER);
        roomPane.setStyle("-fx-background-color: #000000;");
        if(this.model.player.getState().equals("magicalstate")){
            inputTextField.setBackground(Background.fill(Color.RED));
        }
        else {
            inputTextField.setBackground(Background.fill(Color.WHITE));
        }
        //        Account for if room is FORCED 0 for startoverbutton and exitgameButton
        for (Passage passage : model.player.getCurrentRoom().getMotionTable().passageTable){
            if (Objects.equals(passage.getDirection(), "FORCED") && passage.getDestinationRoom() == 0){
                startoverButton.setVisible(true);
                startoverButton.setDisable(false);
                exitgameButton.setVisible(true);
                exitgameButton.setDisable(false);
                helpButton.setVisible(false);
                saveButton.setVisible(false);
                loadButton.setVisible(false);
                helpButton.setDisable(true);
                saveButton.setDisable(true);
                loadButton.setDisable(true);
                qSaveButton.setDisable(true);
                qSaveButton.setVisible(false);
                customizerButton.setDisable(true);
                customizerButton.setVisible(false);
                HBox test = new HBox();
                test.getChildren().addAll(startoverButton, exitgameButton);
                test.setSpacing(10);
                test.setAlignment(Pos.CENTER);
                gridPane.add(test, 1, 0, 1, 1);
            }
        }

        gridPane.add(roomPane, 1, 1);
        stage.sizeToScene();

        //finally, articulate the description
        if (textToDisplay == null || textToDisplay.isBlank()) articulateRoomDescription();
    }

    /**
     * formatText
     * __________________________
     *
     * Format text for display.
     * 
     * @param textToDisplay the text to be formatted for display.
     */
    private void formatText(String textToDisplay) {
        if (textToDisplay == null || textToDisplay.isBlank()) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription() + "\n";
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (objectString != null && !objectString.isEmpty()) roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            else roomDescLabel.setText(roomDesc);
        } else roomDescLabel.setText(textToDisplay);
        roomDescLabel.setStyle("-fx-text-fill: white;");
        roomDescLabel.setFont(new Font(roomdescfont, memento.currentSize));
        roomDescLabel.setAlignment(Pos.CENTER);
    }

    /**
     * getRoomImage
     * __________________________
     *
     * Get the image for the current room and place 
     * it in the roomImageView 
     */
    private void getRoomImage() {

        int roomNumber = this.model.getPlayer().getCurrentRoom().getRoomNumber();
        String roomImagePath = this.model.getDirectoryName() + "/room-images/" + roomNumber + ".png";

        InputStream stream;
        try {
            stream = new FileInputStream(roomImagePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image roomImageFile = new Image(stream);
        roomImageView = new ImageView(roomImageFile);
        roomImageView.setPreserveRatio(true);
        roomImageView.setFitWidth(400);
        roomImageView.setFitHeight(400);

        //set accessible text
        roomImageView.setAccessibleRole(AccessibleRole.IMAGE_VIEW);
        roomImageView.setAccessibleText(this.model.getPlayer().getCurrentRoom().getRoomDescription());
        roomImageView.setFocusTraversable(true);
    }

    /**
     * updateItems
     * __________________________
     *
     * This method is partially completed, but you are asked to finish it off.
     *
     * The method should populate the objectsInRoom and objectsInInventory Vboxes.
     * Each Vbox should contain a collection of nodes (Buttons, ImageViews, you can decide)
     * Each node represents a different object.
     * 
     * Images of each object are in the assets 
     * folders of the given adventure game.
     */
    public void updateItems() {
        //write some code here to add images of objects in a given room to the objectsInRoom Vbox
        //Code partially generated in response to comments. GitHub CoPilot, 12 Oct. 2023,
        ArrayList<ImageView> imageViews = new ArrayList<>();
        for (AdventureObject o : model.getPlayer().getCurrentRoom().objectsInRoom) {
            Boolean highlight = false;
            if (o.getMagical().equals("magical")) {
                highlight = true;
            }
            String imagePath = model.getDirectoryName() + "/objectImages/".replace("/", File.separator) + o.getName() + ".jpg";
            try {
                ImageView imageView = new ImageView(new Image(new FileInputStream(imagePath)));
                imageView.setAccessibleText(o.getDescription());
                imageView.setFitWidth(100);
                if (highlight) {
                    imageView.setStyle("-fx-background-color: gold; -fx-border-width: 5;");
                }
                imageView.setOnMouseClicked(e -> {
                    PassageTable motionTable = model.getPlayer().getCurrentRoom().getMotionTable();
                    if (!motionTable.optionExists("FORCED")) {
                        updateScene(model.interpretAction("TAKE " + o.getName()), false);
                        updateItems();
                    }
                });
                imageViews.add(imageView);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        objectsInRoom = new VBox(imageViews.toArray(new Node[0]));
        objectsInRoom.setSpacing(10);
        objectsInRoom.setAlignment(Pos.TOP_CENTER);
        //write some code here to add images of objects in a player's inventory room to the objectsInInventory Vbox
        imageViews.clear();
        for (AdventureObject o : model.getPlayer().inventory) {
            ImageView imageView = getImageView(o);
            imageViews.add(imageView);
        }
        objectsInInventory = new VBox(imageViews.toArray(new Node[0]));
        objectsInInventory.setSpacing(10);
        objectsInInventory.setAlignment(Pos.TOP_CENTER);
        //please use setAccessibleText to add "alt" descriptions to your images!
        //the path to the image of any is as follows:
        //this.model.getDirectoryName() + "/objectImages/" + objectName + ".jpg";

        ScrollPane scO = new ScrollPane(objectsInRoom);
        scO.setPadding(new Insets(10));
        scO.setStyle("-fx-background: #000000; -fx-background-color:transparent;");
        scO.setFitToWidth(true);
        gridPane.add(scO,0,1);

        ScrollPane scI = new ScrollPane(objectsInInventory);
        scI.setFitToWidth(true);
        scI.setStyle("-fx-background: #000000; -fx-background-color:transparent;");
        gridPane.add(scI,2,1);
    }

    /**
     * getImageView
     * __________________________
     *
     * @param o the object to get an ImageView for
     * @return an ImageView of the object
     */
    private ImageView getImageView(AdventureObject o) {
        String imagePath = model.getDirectoryName() + "/objectImages/".replace("/", File.separator) + o.getName() + ".jpg";
        try {
            ImageView imageView = new ImageView(new Image(new FileInputStream(imagePath)));
            imageView.setAccessibleText(o.getDescription());
            imageView.setFitWidth(100);
            imageView.setOnMouseClicked(e -> {
                PassageTable motionTable = model.getPlayer().getCurrentRoom().getMotionTable();
                if (!motionTable.optionExists("FORCED")) {
                    updateScene(model.interpretAction("DROP " + o.getName()), false);
                    updateItems();
                }
            });
            return imageView;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Show the game instructions.
     *
     * If helpToggle is FALSE:
     * -- display the help text in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- use whatever GUI elements to get the job done!
     * -- set the helpToggle to TRUE
     * -- REMOVE whatever nodes are within the cell beforehand!
     *
     * If helpToggle is TRUE:
     * -- redraw the room image in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- set the helpToggle to FALSE
     * -- Again, REMOVE whatever nodes are within the cell beforehand!
     */
    public void showInstructions() {
        if (helpToggle) {
            //Code partially generated in response to comments. GitHub CoPilot, 8 Oct. 2023, https://github.com/features/copilot
            //remove nodes from cell 1,1
            ArrayList<Node> nodesToRemove = new ArrayList<>();
            for (Node node : gridPane.getChildren()) {
                if (GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 1) {
                    nodesToRemove.add(node);
                }
            }
            for (Node node : nodesToRemove) {
                gridPane.getChildren().remove(node);
            }
            updateScene(null, false);
            helpToggle = false;
        } else {
            ArrayList<Node> nodesToRemove = new ArrayList<>();
            for (Node node : gridPane.getChildren()) {
                if (GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 1) {
                    nodesToRemove.add(node);
                }
            }
            for (Node node : nodesToRemove) {
                gridPane.getChildren().remove(node);
            }
            gridPane.add(new Label(model.getInstructions()), 1, 1);
            helpToggle = true;
        }
    }

    /**
     * This method handles the event related to the
     * help button.
     */
    public void addInstructionEvent() {
        helpButton.setOnAction(e -> {
            stopArticulation(); //if speaking, stop
            showInstructions();
        });
    }

    /**
     * This method handles the event related to the
     * save button.
     */
    public void addSaveEvent() {
        saveButton.setOnAction(e -> {
            gridPane.requestFocus();
            SaveType saveView = new SaveView(this);
        });
    }

    /**
     * This method handles the event related to the
     * quick save button.
     */
    private void addQSaveEvent() {
        qSaveButton.setOnAction(e -> {
            gridPane.requestFocus();
            SaveType qSaveView = new QuickSaveView(this);
            qSaveView.saveGame();
            roomDescLabel.setText(qSaveView.saveFileErrorLabel.getText());
        });
    }

    /**
     * This method handles the event related to the
     * load button.
     */
    public void addLoadEvent() {
        loadButton.setOnAction(e -> {
            gridPane.requestFocus();
            LoadView loadView = new LoadView(this);
        });
    }

    /**
     * This method handles the event related to the
     * StartOver button
     */
    public void addStartOverEvent(){
        startoverButton.setOnAction(e -> {
            model = new AdventureGame("TinyGame");
            updateScene("", false);
            updateItems();
            stopArticulation();
            startoverButton.setDisable(true);
            exitgameButton.setDisable(true);
            startoverButton.setVisible(false);
            exitgameButton.setVisible(false);
            inputTextField.setEditable(true);
            saveButton = new Button("Save");
            saveButton.setId("Save");
            customizeButton(saveButton, 100, 50);
            makeButtonAccessible(saveButton, "Save Button", "This button saves the game.", "This button saves the game. Click it in order to save your current progress, so you can play more later.");
            addSaveEvent();
            addCursorHandlerButton(saveButton);
            saveButton.setStyle(saveButtonCol);
            saveButton.setTextFill(Color.WHITE);

            qSaveButton = new Button("Quicksave");
            qSaveButton.setId("Quicksave");
            customizeButton(qSaveButton, 100, 50);
            makeButtonAccessible(qSaveButton, "Quicksave Button", "This button quicksaves the game.", "This button quicksaves the game. Click it in order to save your current progress, so you can play more later.");
            addQSaveEvent();
            addCursorHandlerButton(qSaveButton);
            qSaveButton.setStyle(qSaveButtonCol);
            qSaveButton.setTextFill(Color.WHITE);

            loadButton = new Button("Load");
            loadButton.setId("Load");
            customizeButton(loadButton, 100, 50);
            makeButtonAccessible(loadButton, "Load Button", "This button loads a game from a file.", "This button loads the game from a file. Click it in order to load a game that you saved at a prior date.");
            addLoadEvent();
            addCursorHandlerButton(loadButton);
            loadButton.setStyle(loadButtonCol);
            loadButton.setTextFill(Color.WHITE);

            helpButton = new Button("Instructions");
            helpButton.setId("Instructions");
            customizeButton(helpButton, 150, 50);
            makeButtonAccessible(helpButton, "Help Button", "This button gives game instructions.", "This button gives instructions on the game controls. Click it to learn how to play.");
            addInstructionEvent();
            addCursorHandlerButton(helpButton);
            helpButton.setStyle(helpButtonCol);
            helpButton.setTextFill(Color.WHITE);

            customizerButton = new Button("Customize");
            customizerButton.setId("Customize");
            customizeButton(customizerButton, 100, 50);
            makeButtonAccessible(customizerButton, "Customize Button", "This button will open the customize menu", "When you click this button, a customizing menu will open, allowing you to change the font, colour of the buttons and background");
            addCustomizeEvent();
            addCursorHandlerButton(customizerButton);
            customizerButton.setStyle(customizerButtonCol);
            customizerButton.setTextFill(Color.WHITE);
            HBox topButtons = new HBox();
            topButtons.getChildren().addAll(saveButton, qSaveButton, helpButton, loadButton, customizerButton);
            topButtons.setSpacing(10);
            topButtons.setAlignment(Pos.CENTER);
            gridPane.add(topButtons, 1, 0, 1, 1);

        });
    }

    /**
     * This method handles the event related to
     * the ExitGame button
     */
    public void addExitGameEvent(){
        exitgameButton.setOnAction(e -> {
            Platform.exit();
        });
    }
    /**
     * This method will handle the event related to
     * the Customize button
     */
    public void addCustomizeEvent(){
        customizerButton.setOnAction(e -> {
            CustomizeView customizeView = new CustomizeView(this);
        });
    }


    /**
     * This method articulates Room Descriptions
     */
    public void articulateRoomDescription() {
        String musicFile;
        String adventureName = this.model.getDirectoryName();
        String roomName = this.model.getPlayer().getCurrentRoom().getRoomName();

        if (!this.model.getPlayer().getCurrentRoom().getVisited()) musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + "-long.mp3" ;
        else musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + "-short.mp3" ;
        musicFile = musicFile.replace(" ","-");

        Media sound = new Media(new File(musicFile).toURI().toString());

        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        mediaPlaying = true;

    }

    /**
     * articulateInventory
     *
     * This method articulates the player's inventory
     */
    public void articulateInventory() {
        VoiceManager vm = VoiceManager.getInstance();
        Voice voice = vm.getVoice("kevin");
        voice.allocate();
        voice.speak(String.valueOf(model.getPlayer().getInventory()));
    }

    /**
     * This method stops articulations 
     * (useful when transitioning to a new room or loading a new game)
     */
    public void stopArticulation() {
        if (mediaPlaying) {
            mediaPlayer.stop(); //shush!
            mediaPlaying = false;
        }
    }

    /**
     * setWhiteTheme
     *
     * This method sets the theme to white
     */
    private void setWhiteTheme() {
        gridPane.setBackground(new Background(new BackgroundFill(
                Color.valueOf("#FFFFFF"),
                new CornerRadii(0),
                new Insets(0)
        )));

        for (Node node : gridPane.getChildren()) {
            if (!(node instanceof VBox)) {
                node.setStyle("-fx-background: #FFFFFF; -fx-background-color:transparent;");
            }
            if (node instanceof VBox) {
                ( (VBox) node).setBackground(new Background(new BackgroundFill(
                        Color.valueOf("#FFFFFF"),
                        new CornerRadii(10),
                        new Insets(10)
                )));
                for (Node child : ((VBox) node).getChildren()) {
                    if (child instanceof Label) {
                        ((Label) child).setTextFill(Color.BLACK);
                    }
                }
            }
        }
    }

    /**
     * setDarkTheme
     *
     * This method sets the theme to dark
     */
    private void setDarkTheme() {
        gridPane.setBackground(new Background(new BackgroundFill(
                Color.valueOf("#000000"),
                new CornerRadii(0),
                new Insets(0)
        )));

        for (Node node : gridPane.getChildren()) {
            if (!(node instanceof VBox)) {
                node.setStyle("-fx-background: #000000; -fx-background-color:transparent;");
            }
            if (node instanceof VBox) {
                ( (VBox) node).setBackground(new Background(new BackgroundFill(
                        Color.valueOf("#000000"),
                        new CornerRadii(0),
                        new Insets(0)
                )));
                for (Node child : ((VBox) node).getChildren()) {
                    if (child instanceof Label) {
                        ((Label) child).setTextFill(Color.WHITE);
                    }
                }
            }
        }
    }

    /**
     * Class FontSizeMemento.
     *
     * This is the Class that will save and restore previous font sizes.
     */
    private class FontSizeMemento {
        double defaultSize;
        double currentSize;

        /**
         * Font Size Memento Constructor
         * __________________________
         * Initializes attributes
         */
        public FontSizeMemento(double defaultSize) {
            this.defaultSize = defaultSize;
            currentSize = defaultSize;
        }

        /**
         * resetFontSize
         *
         * Resets the font size to the default size.
         */
        public void resetFontSize() {
            roomDescLabel.setFont(new Font(roomdescfont, defaultSize));
            currentSize = defaultSize;
        }

        /**
         * increaseFontSize
         *
         * Increases the font size by 1.
         */
        public void increaseFontSize() {
            roomDescLabel.setFont(new Font(roomdescfont, ++currentSize));
        }

        /**
         * decreaseFontSize
         *
         * Decreases the font size by 1.
         */
        public void decreaseFontSize() {
            if (roomDescLabel.getFont().getSize() > defaultSize) {
                roomDescLabel.setFont(new Font(roomdescfont, --currentSize));
            }
        }
    }
}
