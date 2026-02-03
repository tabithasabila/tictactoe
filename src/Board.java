public class Board {
    private char[][] board;

    Board(){
        board = new char[3][3];
        initialiseBoard();

    }

    //Initialising the board
    public void initialiseBoard(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                board[i][j] = ' ';
            }
        }
    }

    //Printing the board
    public void printBoard(){
        System.out.println("_____________");
        for (int i=0; i<3; i++){
            System.out.print("| ");
            for(int j = 0; j<3; j++){
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("_____________");
        }
    }

    //Player Move
    public boolean playerMove(int row, int col, char player){
        if(row <0 || row >=3 || col<0 || col>=3){
            return false;
        }

        if(board[row][col] != ' '){
            return false;
        }

        //Setting the player's move
        board[row][col] = player;
        return true;
    }

    //Checking for Winner
    public boolean isWinner(char player){
        //checking columns and rows for win
        for(int i = 0; i<3; i++){
            if((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                    (board[0][i] == player && board[1][i] == player && board[2][i] == player)){
                return true;
            }
        }
        //checking diagonals for win
        return (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
                (board[0][2] == player && board[1][1] == player && board[2][0] == player);

    }

    //Checking for a full board
    public boolean isBoardFull(){
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                if(board[i][j] == ' '){
                    return false;
                }
            }
        }
        return true;
    }


    public char getCell(int row, int col) {
        return board[row][col];
    }
}
