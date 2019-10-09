
/*public class DEBUG {

    public static void updateBoard(int[][] board, int index){
        for(int i = 6; i > 0; i--){
            if (board[index][i] != 0)
                board[index][i] = 100;
        }
    }


    public int addMove(String input) {

        //Converts last character of game log to integer value representing column number
        char c = input.charAt(input.length() - 1);

        int col = Character.getNumericValue(c);

        //This value is the element number of the lowest space in the given row
        int bottomRow = col + 35;

        //Sets currentSpace to the largest value of the current column
        int currentSpace = bottomRow;

        //Sets nextSpace to be the element directly above the lowest space of the column
        int nextSpace = currentSpace - 7;

        while(true){

            if(currentBoard[currentSpace] == EMPTY) {
                currentBoard[currentSpace] = currentPlayer;
                break;
            }

            else if(currentBoard[currentSpace] != EMPTY){
                if(nextSpace >= 0){
                    currentSpace = nextSpace;
                    nextSpace = nextSpace - 7;
                }
            }
        }

        return currentSpace;
    }
}

public void bestMove(){
        StringBuilder sb = new StringBuilder();
        if(isFirst){
            isFirst = false;
            int bestCol = 0;
            for(int i = 0; i < columnWeights.length; i++){
                if(columnWeights[i] > columnWeights[bestCol]){
                    bestCol = i;
                }
            }
            System.out.println("bestmove "  + bestCol + " " + columnWeights[bestCol]);
        }

        else{
            Random r = new Random();
            System.out.println("bestmove " + r.nextInt(7) + " 100");
        }
    }
*/