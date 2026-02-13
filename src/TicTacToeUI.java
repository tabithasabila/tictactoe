import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class TicTacToeUI extends Application{
    private Board board;

    //Players
    private char human;
    private char ai;

    //ScoreCards
    VBox xCard;
    VBox oCard;

    //Labels
    private Label statusLabel;
    private Label xCounter;
    private Label oCounter;

    private int xScore = 0;
    private int oScore = 0;

    //Button
    Button[][] button = new Button[3][3];

    public static void main(String[] args){
        launch(args);
    }

    //Start Method
    @Override
    public void start(Stage primaryStage){
        board = new Board();

        //Label for Player X
        Label xName = new Label("Player X");
        xName.getStyleClass().add("player_name");

        xCounter = new Label("0");
        xCounter.getStyleClass().add("x_score");

        xCard = new VBox(5, xName, xCounter);
        xCard.getStyleClass().add("score_card");
        xCard.setAlignment(Pos.CENTER);


        //Label for VS
        Label versus = new Label("VS");
        versus.getStyleClass().add("versus");


        //Label for Player O
        Label oName = new Label("Player O");
        oName.getStyleClass().add("player_name");

        oCounter = new Label("0");
        oCounter.getStyleClass().add("o_score");

        oCard = new VBox(5, oName, oCounter);
        oCard.getStyleClass().add("score_card");
        oCard.setAlignment(Pos.CENTER);

        //HBox
        HBox scoreBoard = new HBox(30,xCard,versus,oCard);
        scoreBoard.setAlignment(Pos.CENTER);


        //Nodes
        Image gameIcon = new Image("game-icon.png");
        ComboBox<String> symbolSelector = new ComboBox<>();
        symbolSelector.getItems().addAll("X", "O");
        symbolSelector.setValue("X");

        human = 'X';
        ai = 'O';

        statusLabel = new Label("Player " + human + "'s turn");
        statusLabel.getStyleClass().add("status-label");

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
                addHoverAnimation(btn);
            }
        }

        //Layout
        VBox root = new VBox(20,symbolSelector, scoreBoard, statusLabel, grid, resetButton);

        Rectangle overlay = new Rectangle();
        overlay.setFill(Color.rgb(0,0,0,0.5));

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

        //Flow of the game, initiated by human
        if (!board.playerMove(row, col, human)) {
            //statusLabel.setText("Invalid move, please try again");
            return;
        }

        //If Valid print symbol
        updateButtonUi(btn, human);


        //Check win/tie
        if(board.isWinner(human)){
            statusLabel.setText(human + " wins!");
            disableAllButtons();
            updateScore(human);
            //showWinnerAlert(human);
            return;
        }

        if(board.isBoardFull()){
            statusLabel.setText("Game is a tie");
            return;
        }

        updateTurnUi(ai);

        PauseTransition pauseAi = new PauseTransition(Duration.millis(700));
        pauseAi.setOnFinished(e->{
            aiMove();
            updateTurnUi(human);
        });
        pauseAi.play();

        //AI winning the game
        if(board.isWinner(ai)){
            statusLabel.setText(ai + " wins!");
            disableAllButtons();
            updateScore(ai);
            //showWinnerAlert(ai);
            return;
        }

        //Tie
        if(board.isBoardFull()){
            statusLabel.setText("Game is a tie");
            return;
        }

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

    private void showWinnerAlert(char winner) {
        Label title = new Label("Player " + winner + " 's!");
        title.getStyleClass().add("win_title");
        title.setAlignment(Pos.CENTER);

        Button btn = new Button("Play Again");
        btn.setMinWidth(200);
        btn.getStyleClass().add("play-again");
        btn.setAlignment(Pos.CENTER);

        Button home = new Button("Return to home");
        home.setMinWidth(200);
        home.getStyleClass().add("home");
        home.setAlignment(Pos.CENTER);

        VBox card = new VBox();
        card.getStyleClass().add("win_card");
        card.setMinSize(300, 250);
        card.setAlignment(Pos.CENTER);

        card.getChildren().addAll(title,btn,home);
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
        updateTurnUi(human);
    }


    //Making Updates to the UI
    private void updateButtonUi(Button btn, char symbol){
        btn.setText(String.valueOf(symbol));

        btn.getStyleClass().removeAll("o-cell", "x-cell");
        if(symbol == 'X'){
            btn.getStyleClass().add("x-cell");
        }else{
            btn.getStyleClass().add("o-cell");
        }
    }

    private void updateScore(char winner){

        if(winner == 'X'){
            xScore++;
            xCounter.setText(String.valueOf(xScore));
            addPopAnimation(xCounter);
        }
        if(winner == 'O'){
            oScore++;
            oCounter.setText(String.valueOf(oScore));
            addPopAnimation(oCounter);
        }
    }

    private void updateTurnUi(char player){
        //Label for taking turns
        statusLabel.setText("Player " + player + "'s turn");

        xCard.getStyleClass().remove("active_player");
        oCard.getStyleClass().remove("active_player");

        if(player == 'X'){
            xCard.getStyleClass().add("active_player");
        }else{
            oCard.getStyleClass().add("active_player");
        }
    }


    //Animations
    private void addHoverAnimation(Node target){
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(150), target);
        scaleUp.setToX(1.1);
        scaleUp.setToY(1.1);

        ScaleTransition scaleDown= new ScaleTransition(Duration.millis(150), target);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);

        target.setOnMouseEntered(e-> scaleUp.playFromStart());

        target.setOnMouseExited(e-> scaleDown.playFromStart());
    }

    private void addPopAnimation(Node target){
        ScaleTransition pop = new ScaleTransition(Duration.millis(200), target);
        pop.setFromX(1);
        pop.setFromY(1);
        pop.setToX(1.1);
        pop.setToY(1.1);
        pop.setAutoReverse(true);
        pop.setCycleCount(2);
        pop.play();
    }

}
