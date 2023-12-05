package Trolls;

import AdventureModel.Player;
import AdventureModel.Room;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
/**
 * The GameTroll class represents a simple implementation of the Tic-Tac-Toe game with a troll opponent.
 * The game is played on a 3x3 grid, and the players take turns marking cells with 'X' and 'O'. The game
 * ends when a player wins, the troll wins, or the board is full resulting in a draw.
 */
public class GameTroll extends Application{
    private Room destination;
    private Player myPlayer;
    public boolean won = false;
    private static final int SIZE = 3;
    public boolean finished = false;
    private Button[][] buttons = new Button[SIZE][SIZE];
    private char[][] board = new char[SIZE][SIZE];
    private char currentPlayer = 'X';
    private ArrayList<Pair<Integer, Integer>> player = new ArrayList<>();
    private ArrayList<Pair<Integer,Integer>> troll = new ArrayList<>();
    public GameTroll(Player player , Room room){
        this.myPlayer = player;
        this.destination = room;
    }

    public static void main(String[] args) {
        launch(args);
    }
    public boolean getWon(){
        return won;
    }

    public void startGame(){
       Stage stage = new Stage();
       start(stage);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                buttons[row][col] = new Button();
                buttons[row][col].setMinSize(100, 100);
                int finalRow = row;
                int finalCol = col;
                buttons[row][col].setOnAction(e -> handleButtonClick(finalRow, finalCol,primaryStage));
                grid.add(buttons[row][col], col, row);
            }
        }

        Scene scene = new Scene(grid, 300, 300);
        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    private void handleButtonClick(int row, int col, Stage stage) {
        if (board[row][col] == 0) {
            if (currentPlayer == 'O'){
                troll.add(new Pair<>(row,col));
            }else{
                player.add(new Pair<>(row,col));
            }
            board[row][col] = currentPlayer;
            buttons[row][col].setText(String.valueOf(currentPlayer));
            if (checkWin()) {
                if (currentPlayer == 'X') {
                    won = true;
                    finished = true;
                    showAlert("You won!");
                    this.myPlayer.setCurrentRoom(destination);
                    stage.close();
                }
                else{
                    showAlert("I. the Trolls.GameTroll won you may not pass!");
                    finished = true;
                    won = false;
                    this.myPlayer.setCurrentRoom(myPlayer.getCurrentRoom());
                    stage.close();
                }
                //won = currentPlayer == 'X';
            } else if (isBoardFull()) {
                showAlert("Nice try it was a draw but you still may not pass");
                this.myPlayer.setCurrentRoom(myPlayer.getCurrentRoom());
                won = false;
                finished = true;
                stage.close();
            } else {
                if(currentPlayer == 'X'){
                    currentPlayer = 'O';
                }
                else{
                    currentPlayer = 'X';
                }
                if (currentPlayer == 'O') {
                    makeRandomMove(stage);
                }
            }
        }
    }

    private void makeRandomMove(Stage stage) {
        // Implement logic for PC to choose a random empty cell
        // For simplicity, let's assume the board is not full for this example
        int row, col;
        do {
            row = (int) (Math.random() * SIZE);
            col = (int) (Math.random() * SIZE);
        } while (board[row][col] != 0);

        handleButtonClick(row, col,stage);
    }

    public boolean checkWin() {
        if (player.size() < 3) {
            // Not enough moves for a win
            return false;
        }
        for(int j = 0; j< player.size(); j++){
            int countHorizontal = 0;
            for(int i = j+1; i < player.size(); i++) {
                if (player.get(j).getKey().equals(player.get(i).getKey())){
                    countHorizontal += 1;
                }
                if (countHorizontal == 2){
                    return true;
                }
            }
        }
        for(int j = 0; j < player.size()-1; j++) {
            int countVertical = 0;
            for(int i = j+1; i<player.size(); i++){
                if(player.get(j).getValue().equals(player.get(i).getValue())){
                    countVertical += 1;
                }
            }
            if (countVertical == 2){
                return true;
            }
        }
        ArrayList<Pair<Integer,Integer>> compare = new ArrayList<>();
        compare.add(new Pair<>(0,0));
        compare.add(new Pair<>(1,1));
        compare.add(new Pair<>(2,2));

        ArrayList<Pair<Integer,Integer>> compare2 = new ArrayList<>();
        compare2.add(new Pair<>(0,2));
        compare2.add(new Pair<>(1,1));
        compare2.add(new Pair<>(2,0));
        int matches = 0;
        for (int i = 0; i<player.size(); i++){
            for(int j = 0; j<compare.size(); j++){
                if(player.get(i).equals(compare.get(j))){
                    matches += 1;
                }
            }
            if (matches == 3){
                return true;
            }
        }
        int matches2 = 0;
        for(int i = 0; i<player.size(); i++){
            for (int j = 0; j< compare2.size();j++){
                if (player.get(i).equals(compare2.get(j))){
                    matches2 += 1;
                }
            }
            if (matches2 == 3){
                return true;
            }
        }

        if (troll.size() < 3) {
            // Not enough moves for a win
            return false;
        }
        for(int j = 0; j< troll.size(); j++){
            int countHorizontal = 0;
            for(int i = j+1; i < troll.size(); i++) {
                if (troll.get(j).getKey().equals(troll.get(i).getKey())){
                    countHorizontal += 1;
                }
                if (countHorizontal == 2){
                    return true;
                }
            }
        }
        for(int j = 0; j < troll.size()-1; j++) {
            int countVertical = 0;
            for(int i = j+1; i<troll.size(); i++){
                if(troll.get(j).getValue().equals(troll.get(i).getValue())) {
                    countVertical += 1;
                }
            }
            if (countVertical == 2){
                return true;
            }
        }
        matches = 0;
        for (int i = 0; i<troll.size(); i++){
            for(int j = 0; j<compare.size(); j++){
                if(troll.get(i).equals(compare.get(j))){
                    matches += 1;
                }
            }
            if (matches == 3){
                return true;
            }
        }
        matches2 = 0;
        for(int i = 0; i<troll.size(); i++){
            for (int j = 0; j< compare2.size();j++){
                if (troll.get(i).equals(compare2.get(j))){
                    matches2 += 1;
                }
            }
            if (matches2 == 3){
                return true;
            }
        }
        return false;
    }
    private boolean areEqual(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
        return p1.getKey().equals(p2.getKey()) && p1.getValue().equals(p2.getValue());
    }

    private boolean isBoardFull() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void resetGame() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                buttons[row][col].setText("");
                board[row][col] = 0;
            }
        }
        currentPlayer = 'X';
    }
}