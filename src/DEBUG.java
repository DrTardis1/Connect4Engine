public class DEBUG {

    public static void updateBoard(int[][] board, int index){
        for(int i = 6; i > 0; i--){
            if (board[index][i] != 0)
                board[index][i] = 100;
        }
    }
}
