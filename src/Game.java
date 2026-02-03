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
        Player player1 = new HumanPlayer(selectPlayer(), scanner);
        Player player2 = new AiPlayer((player1.getSymbol() == 'X') ? 'O' : 'X');

       Player currentPlayer = player1;
        Board board = new Board();

        while(true){
            board.printBoard();
            currentPlayer.makeMove(board);

            if(board.isWinner(currentPlayer.getSymbol())){
                board.printBoard();
                System.out.println(currentPlayer.getSymbol() + " wins!");
                break;
            }

            if(board.isBoardFull()){
                board.printBoard();
                System.out.println("Game is a tie");
                break;
            }

            //Switching players
            currentPlayer = (currentPlayer == player1) ? player2 : player1;

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

}
