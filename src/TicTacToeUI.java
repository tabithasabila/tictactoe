import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Random;

public class TicTacToeUI extends Application{
    private Label statusLabel;
    private char human;
    private Board board;
    private char ai;
    Button[][] button = new Button[3][3];

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        board = new Board();

        //Nodes
        Image gameIcon = new Image("game-icon.png");
        ComboBox<String> symbolSelector = new ComboBox<>();
        symbolSelector.getItems().addAll("X", "O");
        symbolSelector.setValue("X");

        human = 'X';
        ai = 'O';

        //Grid pane
        GridPane grid = new GridPane();
        grid.getStyleClass().add("grid-pane");
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        //Dropdown behavior
        symbolSelector.setOnAction(e ->{
            human = symbolSelector.getValue().charAt(0);
            ai = (human == 'X') ? 'O' : 'X';
            resetGame();
        });

        //Label
        statusLabel = new Label("Player " + human + "'s turn");
        statusLabel.getStyleClass().add("status-label");

        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e -> resetGame());

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                Button btn = new Button(" ");
                btn.setMinSize(150,150);

                final int row = i;
                final int col = j;

                //Action On clicking a grid box
                btn.setOnAction(e -> handleButtonClick(row,col,btn));
                button[i][j] = btn;
                grid.add(btn, j, i);
            }
        }

        //Layout
        VBox root = new VBox(20,symbolSelector, statusLabel, grid, resetButton);

        //Creating the scene
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        //Setting the stage
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(gameIcon);
        primaryStage.setFullScreen(true);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.show();


    }

    private void handleButtonClick(int row, int col, Button btn) {
        //Flow of the game, initiated by huma
        if (!board.playerMove(row, col, human)) {
            statusLabel.setText("Invalid move, please try again");
            return;
        }

        //If valid print symbol
        updateButtonUi(btn, human);

        //Check win/tie
        if(board.isWinner(human)){
            statusLabel.setText(human + " wins!");
            disableAllButtons();
            return;
        }

        if(board.isBoardFull()){
            statusLabel.setText("Game is a tie");
            return;
        }

        aiMove();

        //AI winning the game
        if(board.isWinner(ai)){
            statusLabel.setText(ai + " wins!");
            disableAllButtons();
            return;
        }

        //Tie
        if(board.isBoardFull()){
            statusLabel.setText("Game is a tie");
            return;
        }

        statusLabel.setText("Player " + human + "'s turn");
    }

    //Disabling the buttons
    private void disableAllButtons() {
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                button[i][j].setDisable(true);
            }
        }
    }

    //Controlling the Ai moves
    public void aiMove() {

        Random random = new Random();
        int row, col;

        do{
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while(!board.playerMove(row, col, ai));

        updateButtonUi(button[row][col], ai);

    }

    //Resetting the game
    public void resetGame(){
        board = new Board();

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                button[i][j].setText(" ");
                button[i][j].setDisable(false);
            }
        }

        //Resetting the label
        statusLabel.setText("Player " + human + "'s turn");
    }

    private void updateButtonUi(Button btn, char symbol){
        btn.setText(String.valueOf(symbol));

        btn.getStyleClass().removeAll("o-cell", "x-cell");
        if(symbol == 'X'){
            btn.getStyleClass().add("x-cell");
        }else{
            btn.getStyleClass().add("o-cell");
        }
    }

}
