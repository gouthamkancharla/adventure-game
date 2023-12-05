package views;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class CustomizeView, implements and instantiates a customization window
 */
public class CustomizeView {
    private AdventureGameView adventureGameView;

    private Label setBGCustomizations;
    private Label setButtonCustomizations;
    private Label setTextCustomizations;

    private Button selectRedBG;
    private Button selectBlueBG;
    private Button selectGreenBG;
    private Button selectRedButtoncolour;
    private Button selectBlueButtoncolour;
    private Button selectGreenButtoncolour;
    private Button selectTextFontArial;
    private Button selectTextFontCS;
    private Button selectTextFontTNR;

    public CustomizeView(AdventureGameView adventureGameView){
        this.adventureGameView = adventureGameView;
//        initialize all the buttons

        selectBlueBG = new Button("Blue Background colour");
        selectBlueBG.setId("selectBlueBG");
        selectBlueBG.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        selectBlueBG.setPrefSize(200, 50);
        selectBlueBG.setFont(new Font(12));
        addBlueBGEvent();
        addCursorHandlerButton(selectBlueBG);

        selectRedBG = new Button("Red Background colour");
        selectRedBG.setId("selectBlueBG");
        selectRedBG.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        selectRedBG.setPrefSize(200, 50);
        selectRedBG.setFont(new Font(12));
        addRedBGEvent();
        addCursorHandlerButton(selectRedBG);

        selectGreenBG = new Button("Green Background colour");
        selectGreenBG.setId("selectGreenBG");
        selectGreenBG.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        selectGreenBG.setPrefSize(200, 50);
        selectGreenBG.setFont(new Font(12));
        addGreenBGevent();
        addCursorHandlerButton(selectGreenBG);

        selectBlueButtoncolour = new Button("Blue button colour");
        selectBlueButtoncolour.setId("selectBlueButtoncolour");
        selectBlueButtoncolour.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        selectBlueButtoncolour.setPrefSize(200, 50);
        selectBlueButtoncolour.setFont(new Font(16));
        addBlueButtonEvent();
        addCursorHandlerButton(selectBlueButtoncolour);

        selectGreenButtoncolour = new Button("Green button colour");
        selectGreenButtoncolour.setId("selectGreenButtoncolour");
        selectGreenButtoncolour.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        selectGreenButtoncolour.setPrefSize(200, 50);
        selectGreenButtoncolour.setFont(new Font(16));
        addGreenButtonEvent();
        addCursorHandlerButton(selectGreenButtoncolour);

        selectRedButtoncolour = new Button("Red button colour");
        selectRedButtoncolour.setId("selectRedButtoncolour");
        selectRedButtoncolour.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        selectRedButtoncolour.setPrefSize(200, 50);
        selectRedButtoncolour.setFont(new Font(16));
        addRedButtonEvent();
        addCursorHandlerButton(selectRedButtoncolour);

        selectTextFontArial = new Button("Arial text font");
        selectTextFontArial.setId("selectTextFontArial");
        selectTextFontArial.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        selectTextFontArial.setPrefSize(200, 50);
        selectTextFontArial.setFont(new Font("Arial", 16));
        addArialFontEvent();
        addCursorHandlerButton(selectTextFontArial);

        selectTextFontCS = new Button ("Comic Sans MS font");
        selectTextFontCS.setId("selectTextFontCS");
        selectTextFontCS.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        selectTextFontCS.setPrefSize(200, 50);
        selectTextFontCS.setFont(new Font("Comic Sans MS", 16));
        addCSFontEvent();
        addCursorHandlerButton(selectTextFontCS);

        selectTextFontTNR = new Button("Times New Roman font");
        selectTextFontTNR.setId("selectTextFontTNR");
        selectTextFontTNR.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        selectTextFontTNR.setPrefSize(200, 50);
        selectTextFontTNR.setFont(new Font("Times New Roman", 16));
        addTNRFontEvent();
        addCursorHandlerButton(selectTextFontTNR);


        setBGCustomizations = new Label("Set Background Colour");
        setBGCustomizations.setTextFill(Color.WHITE);
        setBGCustomizations.setFont(new Font("Arial", 20));
        VBox BGLabel = new VBox(setBGCustomizations);
        HBox BGButtonBox = new HBox(selectBlueBG, selectRedBG, selectGreenBG);
        VBox BGBox = new VBox(BGLabel, BGButtonBox);

        setButtonCustomizations = new Label("Set Button colour");
        setButtonCustomizations.setTextFill(Color.WHITE);
        setButtonCustomizations.setFont(new Font("Arial", 20));
        VBox ButtonLabel = new VBox(setButtonCustomizations);
        HBox ButtonBox = new HBox(selectBlueButtoncolour, selectRedButtoncolour, selectGreenButtoncolour);
        VBox BBox = new VBox(ButtonLabel, ButtonBox);

        setTextCustomizations = new Label("Set text font");
        setTextCustomizations.setTextFill(Color.WHITE);
        setTextCustomizations.setFont(new Font("Arial", 20));
        VBox TextLabel = new VBox(setTextCustomizations);
        HBox TextBox = new HBox(selectTextFontArial, selectTextFontCS, selectTextFontTNR);
        VBox TBox = new VBox(TextLabel, TextBox);

        BGButtonBox.setSpacing(10);
        BGButtonBox.setAlignment(Pos.CENTER);
        ButtonBox.setSpacing(10);
        TextBox.setSpacing(10);
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(adventureGameView.stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setSpacing(150);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setStyle("-fx-background-color: #121212;");
        dialogVbox.getChildren().add(BGBox);
        dialogVbox.getChildren().add(BBox);
        dialogVbox.getChildren().add(TBox);
        Scene dialogScene = new Scene(dialogVbox, 600, 600);
        dialog.setScene(dialogScene);
        dialog.show();

    }
    public void addBlueBGEvent(){
        selectBlueBG.setOnAction(e -> {
            adventureGameView.gridPane.setBackground(new Background(new BackgroundFill(
                    Color.valueOf("#0000FF"),
                    new CornerRadii(0),
                    new Insets(0))));
            VBox textEntry = new VBox();
            textEntry.setStyle("-fx-background-color: #0000FF;");
            textEntry.setPadding(new Insets(20, 20, 20, 20));
            adventureGameView.commandLabel.setStyle("-fx-text-fill: white;");
            adventureGameView.commandLabel.setFont(new Font(adventureGameView.roomdescfont, 16));
            textEntry.getChildren().addAll(adventureGameView.commandLabel, adventureGameView.inputTextField);
            textEntry.setSpacing(10);
            textEntry.setAlignment(Pos.CENTER);
            adventureGameView.gridPane.add( textEntry, 0, 2, 3, 1 );
        });
    }
    public void addRedBGEvent(){
        selectRedBG.setOnAction(e -> {
            adventureGameView.gridPane.setBackground(new Background(new BackgroundFill(
                    Color.valueOf("#FF0000"),
                    new CornerRadii(0),
                    new Insets(0))));
            VBox textEntry = new VBox();
            textEntry.setStyle("-fx-background-color: #FF0000;");
            textEntry.setPadding(new Insets(20, 20, 20, 20));
            adventureGameView.commandLabel.setStyle("-fx-text-fill: white;");
            adventureGameView.commandLabel.setFont(new Font(adventureGameView.roomdescfont, 16));
            textEntry.getChildren().addAll(adventureGameView.commandLabel, adventureGameView.inputTextField);
            textEntry.setSpacing(10);
            textEntry.setAlignment(Pos.CENTER);
            adventureGameView.gridPane.add( textEntry, 0, 2, 3, 1 );
        });
    }
    public void addGreenBGevent(){
        selectGreenBG.setOnAction(e -> {
            adventureGameView.gridPane.setBackground(new Background(new BackgroundFill(
                    Color.valueOf("#172808"),
                    new CornerRadii(0),
                    new Insets(0))));
            VBox textEntry = new VBox();
            textEntry.setStyle("-fx-background-color: #172808;");
            textEntry.setPadding(new Insets(20, 20, 20, 20));
            adventureGameView.commandLabel.setStyle("-fx-text-fill: white;");
            adventureGameView.commandLabel.setFont(new Font(adventureGameView.roomdescfont, 16));
            textEntry.getChildren().addAll(adventureGameView.commandLabel, adventureGameView.inputTextField);
            textEntry.setSpacing(10);
            textEntry.setAlignment(Pos.CENTER);
            adventureGameView.gridPane.add( textEntry, 0, 2, 3, 1 );
        });
    }
    public void addBlueButtonEvent(){
        selectBlueButtoncolour.setOnAction(e -> {
            ArrayList<Button> Buttons = new ArrayList<>();
            Buttons.add(adventureGameView.saveButton);
            Buttons.add(adventureGameView.qSaveButton);
            Buttons.add(adventureGameView.loadButton);
            Buttons.add(adventureGameView.customizerButton);
            Buttons.add(adventureGameView.helpButton);
            Buttons.add(adventureGameView.startoverButton);
            Buttons.add(adventureGameView.exitgameButton);
            for (Button button : Buttons){
                button.setStyle("-fx-background-color: #00008b; -fx-text-fill: white;");
            }
            adventureGameView.helpButtonCol = "-fx-background-color: #00008b; -fx-text-fill: white;";
            adventureGameView.saveButtonCol = "-fx-background-color: #00008b; -fx-text-fill: white;";
            adventureGameView.qSaveButtonCol = "-fx-background-color: #00008b; -fx-text-fill: white;";
            adventureGameView.loadButtonCol = "-fx-background-color: #00008b; -fx-text-fill: white;";
            adventureGameView.customizerButtonCol = "-fx-background-color: #00008b; -fx-text-fill: white;";
            adventureGameView.startoverButtonCol = "-fx-background-color: #00008b; -fx-text-fill: white;";
            adventureGameView.exitgameButtonCol = "-fx-background-color: #00008b; -fx-text-fill: white;";
        });
    }
    public void addGreenButtonEvent(){
        selectGreenButtoncolour.setOnAction(e -> {
            ArrayList<Button> Buttons = new ArrayList<>();
            Buttons.add(adventureGameView.saveButton);
            Buttons.add(adventureGameView.qSaveButton);
            Buttons.add(adventureGameView.loadButton);
            Buttons.add(adventureGameView.customizerButton);
            Buttons.add(adventureGameView.helpButton);
            Buttons.add(adventureGameView.startoverButton);
            Buttons.add(adventureGameView.exitgameButton);
            for (Button button : Buttons){
                button.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
            }
            adventureGameView.helpButtonCol = "-fx-background-color: #17871b; -fx-text-fill: white;";
            adventureGameView.saveButtonCol = "-fx-background-color: #17871b; -fx-text-fill: white;";
            adventureGameView.qSaveButtonCol = "-fx-background-color: #17871b; -fx-text-fill: white;";
            adventureGameView.loadButtonCol = "-fx-background-color: #17871b; -fx-text-fill: white;";
            adventureGameView.customizerButtonCol = "-fx-background-color: #17871b; -fx-text-fill: white;";
            adventureGameView.startoverButtonCol = "-fx-background-color: #17871b; -fx-text-fill: white;";
            adventureGameView.exitgameButtonCol = "-fx-background-color: #17871b; -fx-text-fill: white;";
        });
    }
    public void addRedButtonEvent(){
        selectRedButtoncolour.setOnAction(e -> {
            ArrayList<Button> Buttons = new ArrayList<>();
            Buttons.add(adventureGameView.saveButton);
            Buttons.add(adventureGameView.qSaveButton);
            Buttons.add(adventureGameView.loadButton);
            Buttons.add(adventureGameView.customizerButton);
            Buttons.add(adventureGameView.helpButton);
            Buttons.add(adventureGameView.startoverButton);
            Buttons.add(adventureGameView.exitgameButton);
            for (Button button : Buttons){
                button.setStyle("-fx-background-color: #8b0000; -fx-text-fill: white;");
            }
            adventureGameView.helpButtonCol = "-fx-background-color: #8b0000; -fx-text-fill: white;";
            adventureGameView.saveButtonCol = "-fx-background-color: #8b0000; -fx-text-fill: white;";
            adventureGameView.qSaveButtonCol = "-fx-background-color: #8b0000; -fx-text-fill: white;";
            adventureGameView.loadButtonCol = "-fx-background-color: #8b0000; -fx-text-fill: white;";
            adventureGameView.customizerButtonCol = "-fx-background-color: #8b0000; -fx-text-fill: white;";
            adventureGameView.startoverButtonCol = "-fx-background-color: #8b0000; -fx-text-fill: white;";
            adventureGameView.exitgameButtonCol = "-fx-background-color: #8b0000; -fx-text-fill: white;";
        });
    }
    public void addCSFontEvent(){
        selectTextFontCS.setOnAction(e -> {
            adventureGameView.roomdescfont = "Comic Sans MS";
            adventureGameView.invLabel.setFont(new Font("Comic Sans MS", 16));
            adventureGameView.objLabel.setFont(new Font("Comic Sans MS", 16));
            adventureGameView.commandLabel.setFont(new Font("Comic Sans MS", 16));
            adventureGameView.stopArticulation();
            adventureGameView.updateScene(null, false);
            ArrayList<Button> Buttons = new ArrayList<>();
            Buttons.add(adventureGameView.customizerButton);
            Buttons.add(adventureGameView.helpButton);
            Buttons.add(adventureGameView.saveButton);
            Buttons.add(adventureGameView.loadButton);
            Buttons.add(adventureGameView.qSaveButton);
            Buttons.add(adventureGameView.startoverButton);
            Buttons.add(adventureGameView.exitgameButton);
            for (Button button : Buttons){
                button.setFont(new Font("Comic Sans MS", 16));
            }
        });
    }
    public void addArialFontEvent(){
        selectTextFontArial.setOnAction(e -> {
            adventureGameView.roomdescfont = "Arial";
            adventureGameView.invLabel.setFont(new Font("Arial", 16));
            adventureGameView.objLabel.setFont(new Font("Arial", 16));
            adventureGameView.commandLabel.setFont(new Font("Arial", 16));
            adventureGameView.stopArticulation();
            adventureGameView.updateScene(null, false);
            ArrayList<Button> Buttons = new ArrayList<>();
            Buttons.add(adventureGameView.customizerButton);
            Buttons.add(adventureGameView.helpButton);
            Buttons.add(adventureGameView.saveButton);
            Buttons.add(adventureGameView.loadButton);
            Buttons.add(adventureGameView.qSaveButton);
            Buttons.add(adventureGameView.startoverButton);
            Buttons.add(adventureGameView.exitgameButton);
            for (Button button : Buttons){
                button.setFont(new Font("Arial", 16));
            }
        });
    }
    public void addTNRFontEvent(){
        selectTextFontTNR.setOnAction(e -> {
            adventureGameView.roomdescfont = "Times New Roman";
            adventureGameView.invLabel.setFont(new Font("Times New Roman", 16));
            adventureGameView.objLabel.setFont(new Font("Times New Roman", 16));
            adventureGameView.commandLabel.setFont(new Font("Times New Roman", 16));
            adventureGameView.stopArticulation();
            adventureGameView.updateScene(null, false);
            ArrayList<Button> Buttons = new ArrayList<>();
            Buttons.add(adventureGameView.customizerButton);
            Buttons.add(adventureGameView.helpButton);
            Buttons.add(adventureGameView.saveButton);
            Buttons.add(adventureGameView.loadButton);
            Buttons.add(adventureGameView.qSaveButton);
            Buttons.add(adventureGameView.startoverButton);
            Buttons.add(adventureGameView.exitgameButton);
            for (Button button : Buttons){
                button.setFont(new Font("Times New Roman", 16));
            }
        });
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
                b.setStyle("-fx-background-color: #17851B");
                b.setTextFill(Color.WHITE);
            }
        });
    }
}
