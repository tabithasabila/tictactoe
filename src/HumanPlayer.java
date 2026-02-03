import java.util.Scanner;

public class HumanPlayer implements Player{
    char symbol;
    private final Scanner scanner;

    HumanPlayer(char symbol, Scanner scanner){
        this.symbol = symbol;
        this.scanner = scanner;

    }

    @Override
    public void makeMove(Board board) {
        while(true){
            //Assigning row and column indices for human player
            System.out.println("Player " + symbol + ", enter your move (row and column): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if(board.playerMove(row, col, symbol)){
                break;
            }
            System.out.println("Invalid move, please try again");
        }
    }

    public char getSymbol(){
        return symbol;

    }
}
