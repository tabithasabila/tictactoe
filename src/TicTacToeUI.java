import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        GridPane grid = new GridPane();

        human = 'X';
        ai = 'O';

        statusLabel = new Label("Player " + human + "'s turn");

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                Button btn = new Button(" ");
                btn.setMinSize(100,100);

                final int row = i;
                final int col = j;

                //Action On clicking a grid box
                btn.setOnAction(e -> handleButtonClick(row,col,btn));
                button[i][j] = btn;
                grid.add(btn, j, i);
            }
        }

        VBox root = new VBox(10, statusLabel, grid);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
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
        btn.setText(String.valueOf(human));

        //Check win/tie
        if(board.isWinner(human)){
            statusLabel.setText(human + " wins!");
            disableAllButtons();
            return;
        }

        if(board.isBoardFull()){
            statusLabel.setText("Game is a tie");
        }

        aiMove(board);
        //Place AI move
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                button[i][j].setText(String.valueOf(board.getCell(i, j)));
            }
        }

        //AI winning the game
        if(board.isWinner(ai)){
            statusLabel.setText(ai + " wins!");
            disableAllButtons();
            return;
        }

        //Tie
        if(board.isBoardFull()){
            board.printBoard();
            statusLabel.setText("Game is a tie");
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
    public void aiMove(Board board) {

        Random random = new Random();
        int row, col;

        do{
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while(!board.playerMove(row, col, ai));

        button[row][col].setText(String.valueOf(ai));

    }

}
