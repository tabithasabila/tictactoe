import java.util.Random;

public class AiPlayer implements Player{
    char symbol;

    AiPlayer(char symbol){
        this.symbol = symbol;
    }
    @Override
    public void makeMove(Board board) {

        Random random = new Random();
        int row, col;

        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while(!board.playerMove(row, col, symbol));
        System.out.println("AI placed " + symbol + " at " + "(" + row + "," + col + ")" );
    }

    public char getSymbol(){
        return symbol;
    }
}
