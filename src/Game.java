import java.util.Random;
import java.util.Scanner
        ;
public class Game{
//    private static boolean playAgain = true;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        //Replay logic
        boolean playAgain;
        do {
            playGame();
            playAgain = askPlayAgain();
        }while(playAgain);
        System.out.println("Thank you for playing!");
    }

    //Game logic
    public static void playGame(){
        char humanPlayer = selectPlayer();
        char aiPlayer = (humanPlayer == 'X') ? 'O' : 'X';
        char currentPlayer = 'X';
        Board board = new Board();

        while(true){
            board.printBoard();

            if(currentPlayer == humanPlayer){
                //Assigning row and column indices for human player
                System.out.println("Player " + currentPlayer + ", enter your move (row and column): ");
                int row = scanner.nextInt();
                int col = scanner.nextInt();

                if(!board.playerMove(row, col, currentPlayer)){
                    System.out.println("Invalid move, please try again");
                    continue;
                }
            }else {
                //Current player = AI
                aiMove(board, aiPlayer);
            }

            if(board.isWinner(currentPlayer)){
                board.printBoard();
                System.out.println(currentPlayer == humanPlayer? "You win!" : " wins!");
                break;
            }

            if(board.isBoardFull()){
                board.printBoard();
                System.out.println("Game is a tie");
                break;
            }

            //Switching players
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';

        }
    }

    //Starting a new game
    public static boolean askPlayAgain(){
        scanner.nextLine();
        while(true){
            System.out.println("Do you want to continue? Y/N?");
            String input = scanner.nextLine().trim().toUpperCase();

            if(input.equals("Y")){
                return true;
            } else if (input.equals("N")) {
                return false;
            }else{
                System.out.println("Invalid input, please choose Y/N");
            }
        }
    }

    //Choosing X or O
    public static char selectPlayer() {
        while (true) {
            System.out.println("Choose between 'X' and 'O'");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("X")) return 'X';
            if (input.equals("O")) return 'O';

            System.out.println("Invalid choice, try again");
        }
    }


    //AI player logic(random)
    private static void aiMove(Board board, char aiPlayer){
        Random random = new Random();
        int row, col;

        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while(!board.playerMove(row, col, aiPlayer));
        System.out.println("AI placed " + aiPlayer + " at " + "(" + row + "," + col + ")" );

    }
}
