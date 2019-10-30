import java.util.LinkedList;
import java.util.Queue;

//TODO
//-Implement Alpha beta pruning
//-Adjust calcDepth() func for more effective depth variation

public class MartiniEngine {

    private String name = "Martini-C3260061";
    private Node currentBoardNode = new Node();
    private int[] boardValues = {1,  5, 10, 50,  10, 5, 1,
                                 10, 15, 20, 55,  20, 15, 20,
                                 25, 30, 40, 80, 40, 30, 25,
                                 25, 30, 40, 80, 40, 30, 25,
                                 10, 15, 20, 55,  20, 15, 20,
                                 1,  5, 10, 50,  10, 5, 1};
    private int EMPTY = 0;
    private int OPPONENT = 2;
    private int MINE = 1;
    private int FIRSTPLAYER = 2;

    //Essential Functions
    //------------------------------------------------------------------------------------------------------------------

    //Response to name
    public String getName(){return name;}

    //Response to isready
    public String ready(){return "readyok";}

    //Response to position startpos <moves>
    public int updateBoard(String input, int player) {

        //Converts last character of game log to integer value representing column number
        int col = Character.getNumericValue(input.charAt(input.length() - 1));
        int finalAddress = findAvailableSpace(col, currentBoardNode.getState());

        if(finalAddress >= 0) {
            currentBoardNode.getState()[finalAddress] = player;
        }

        return finalAddress;
    }

    //Response to go ftime x stime y
    public void findBestMove(int timeRemaining){
<<<<<<< Updated upstream
        int index = 0;
        int bestValue;

        //Generates children for Martini, because this function will only ever be called when Martini must make a move
        LinkedList<Node> children = initChildren(currentBoardNode, MINE);

        //If Martini is first, then it must minimise children
        if(isFirst()){
            bestValue = Integer.MIN_VALUE;
            for(int i = 0; i < children.size(); i++){

                //NOT maximisingPlayer as the children are the 2nd players moves
                int currentValue = minimax(children.get(i), calcDepth(timeRemaining), false);
                if(currentValue > bestValue){
                    bestValue = currentValue;
=======
        int bestMoveValue = 0;
        int index = 0;
        LinkedList<Node> children = initChildren(currentBoardNode, MINE);

        if(isMaximising()){
            bestMoveValue = Integer.MIN_VALUE;
            for(int i = 0; i < children.size(); i++){
                int moveScore = alphaBetaMini(children.get(i), calcDepth(timeRemaining), Integer.MIN_VALUE, Integer.MAX_VALUE);
                if(moveScore > bestMoveValue){
                    bestMoveValue = moveScore;
>>>>>>> Stashed changes
                    index = i;
                }

            }
        }
<<<<<<< Updated upstream

        //Otherwise, it must maximise the children as Martini is the minimising player
        else{
            bestValue = Integer.MAX_VALUE;
            for(int i = 0; i < children.size(); i ++){

                //IS maximisingPlayer as the children are Martini's moves
                int currentValue = minimax(children.get(i), calcDepth(timeRemaining), true);
                if(currentValue < bestValue){
                    bestValue = currentValue;
=======
        else{
            bestMoveValue = Integer.MAX_VALUE;
            for(int i = 0; i < children.size(); i++){
                int moveScore = alphaBetaMaxi(children.get(i), calcDepth(timeRemaining), Integer.MIN_VALUE, Integer.MAX_VALUE);
                if(moveScore < bestMoveValue){
                    bestMoveValue = moveScore;
>>>>>>> Stashed changes
                    index = i;
                }
            }
        }

<<<<<<< Updated upstream
        System.out.println("bestmove " + children.get(index).getColNum() +  " " + bestValue);
        updateBoard(Integer.toString(children.get(index).getColNum()), MINE);
=======
        System.out.println("bestmove " + children.get(index).getColNum() +  " " + bestMoveValue);
        updateBoard(Integer.toString(children.get(index).getColNum()), 1);
>>>>>>> Stashed changes
    }

    //Response to perft x
    public int perft(int depth, Node root){
        int nodes = 1;
        if(depth == 0) return 1;
        LinkedList<Node> children = initChildren(root, MINE);
        for(int i = 0; i < children.size(); i++){
            nodes += perft(depth-1, children.get(i));
        }
        return nodes;
    }

    //Response to quit
    public String quit(){return "quitting";}

    //Getters & Setters
    //------------------------------------------------------------------------------------------------------------------
    public int[] getCurrentBoard(){return currentBoardNode.getState();}

    public Node getGameTree(){return currentBoardNode;}
<<<<<<< Updated upstream

    public void setFirst(int FIRSTPLAYER){this.FIRSTPLAYER = FIRSTPLAYER;}

    public boolean isFirst(){return FIRSTPLAYER == MINE;}
=======
    public void setFirstPlayer(int FIRSTPLAYER){this.FIRSTPLAYER = FIRSTPLAYER;}
    public boolean isMaximising(){return FIRSTPLAYER == MINE;}
    public int getFirstPlayer(){return FIRSTPLAYER;}
>>>>>>> Stashed changes

    //Win Checking Functions
    //------------------------------------------------------------------------------------------------------------------
    public WinPair checkWin(int[] boardState){
        WinPair vertWP = checkVertical(boardState);
        WinPair horWP = checkHorizontal(boardState);
        WinPair d1WP = checkDiagOne(boardState);
        WinPair d2WP = checkDiagTwo(boardState);

        if(vertWP.hasWin()) return vertWP;
        else if(horWP.hasWin()) return horWP;
        else if(d1WP.hasWin()) return d1WP;
        else if(d2WP.hasWin()) return d2WP;
        else return new WinPair();
    }
    public WinPair checkHorizontal(int[] boardState){
        WinPair result = new WinPair();
        outerloop:
        for(int i = 0; i < 36; i = i + 7){
            for(int j = 0; j < 4; j++){
                int currentValue = boardState[i+j];
                if(currentValue == EMPTY) continue;

                int second, third, fourth;
                second = boardState[i+j+1];
                third = boardState[i+j+2];
                fourth = boardState[i+j+3];
                if((i+j) % 7 != ((i+j+1) % 7) - 1 || currentValue != second) continue;
                if((i+j) % 7 != ((i+j+2) % 7) - 2 || currentValue != third) continue;
                if((i+j) % 7 != ((i+j+3) % 7) - 3 || currentValue != fourth) continue;

                result.setHasWin(true);
                result.setWinnerNumber(currentValue);
                break outerloop;
            }
        }
        return result;
    }
    public WinPair checkVertical(int[] boardState){
        WinPair result = new WinPair();

        outerloop:
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 21; j = j + 7){
                int currentValue = boardState[i+j];
                if(currentValue == EMPTY) continue;
                int second, third, fourth;
                second = boardState[i+j+7];
                third = boardState[i+j+14];
                fourth = boardState[i+j+21];
                if((i+j) % 7 != ((i+j+7) % 7)  || currentValue != second) continue;
                if((i+j) % 7 != ((i+j+14) % 7) || currentValue != third) continue;
                if((i+j) % 7 != ((i+j+21) % 7) || currentValue != fourth) continue;

                result.setHasWin(true);
                result.setWinnerNumber(currentValue);
            }
        }
        return result;
    }
    public WinPair checkDiagOne(int[] boardState){
        WinPair result = new WinPair();

        for(int i = 3; i < 15; i += 11){
            int currentVal = boardState[i];
            if(currentVal == EMPTY) continue;
            int second, third, fourth;
            second = boardState[i+8];
            third = boardState[i+16];
            fourth = boardState[i+24];
            if((i) % 7 != ((i+8) % 7) - 1 || currentVal != second) continue;
            if((i) % 7 != ((i+16) % 7) - 2 || currentVal != third) continue;
            if((i) % 7 != ((i+24) % 7) - 3 || currentVal != fourth) continue;

            result.setHasWin(true);
            result.setWinnerNumber(currentVal);
        }

        for(int i = 2; i < 8; i += 5){
            int currentVal = boardState[i];
            if(currentVal == EMPTY) continue;
            int second, third, fourth;
            second = boardState[i+8];
            third = boardState[i+16];
            fourth = boardState[i+24];
            if((i) % 7 != ((i+8) % 7) - 1 || currentVal != second) continue;
            if((i) % 7 != ((i+16) % 7) - 2 || currentVal != third) continue;
            if((i) % 7 != ((i+24) % 7) - 3 || currentVal != fourth) continue;

            result.setHasWin(true);
            result.setWinnerNumber(currentVal);
        }

        for(int i = 10; i < 16; i += 5){
            int currentVal = boardState[i];
            if(currentVal == EMPTY) continue;
            int second, third, fourth;
            second = boardState[i+8];
            third = boardState[i+16];
            fourth = boardState[i+24];
            if((i) % 7 != ((i+8) % 7) - 1 || currentVal != second) continue;
            if((i) % 7 != ((i+16) % 7) - 2 || currentVal != third) continue;
            if((i) % 7 != ((i+24) % 7) - 3 || currentVal != fourth) continue;

            result.setHasWin(true);
            result.setWinnerNumber(currentVal);
        }

        for(int i = 0; i < 2; i++){
            int currentVal = boardState[i];
            if(currentVal == EMPTY) continue;
            int second, third, fourth;
            second = boardState[i+8];
            third = boardState[i+16];
            fourth = boardState[i+24];
            if((i) % 7 != ((i+8) % 7) - 1 || currentVal != second) continue;
            if((i) % 7 != ((i+16) % 7) - 2 || currentVal != third) continue;
            if((i) % 7 != ((i+24) % 7) - 3 || currentVal != fourth) continue;

            result.setHasWin(true);
            result.setWinnerNumber(currentVal);
        }

        for(int i = 8; i < 10; i++){
            int currentVal = boardState[i];
            if(currentVal == EMPTY) continue;
            int second, third, fourth;
            second = boardState[i+8];
            third = boardState[i+16];
            fourth = boardState[i+24];
            if((i) % 7 != ((i+8) % 7) - 1 || currentVal != second) continue;
            if((i) % 7 != ((i+16) % 7) - 2 || currentVal != third) continue;
            if((i) % 7 != ((i+24) % 7) - 3 || currentVal != fourth) continue;

            result.setHasWin(true);
            result.setWinnerNumber(currentVal);
        }

        for(int i = 16; i < 18; i++){
            int currentVal = boardState[i];
            if(currentVal == EMPTY) continue;
            int second, third, fourth;
            second = boardState[i+8];
            third = boardState[i+16];
            fourth = boardState[i+24];
            if(currentVal != second) continue;
            if(currentVal != third) continue;
            if(currentVal != fourth) continue;

            result.setHasWin(true);
            result.setWinnerNumber(currentVal);
        }
        return result;
    }
    public WinPair checkDiagTwo(int[] boardState){
        WinPair result = new WinPair();

        for(int i = 3; i < 21; i += 17){
            int currentVal = boardState[i];
            if(currentVal == EMPTY) continue;
            int second, third, fourth;
            second = boardState[i+6];
            third = boardState[i+12];
            fourth = boardState[i+18];
            if((i) % 7 != ((i+6) % 7) + 1 || currentVal != second) continue;
            if((i) % 7 != ((i+12) % 7) + 2 || currentVal != third) continue;
            if((i) % 7 != ((i+18) % 7) + 3 || currentVal != fourth) continue;

            result.setHasWin(true);
            result.setWinnerNumber(currentVal);
        }

        for(int i = 4; i < 14; i += 9){
            int currentVal = boardState[i];
            if(currentVal == EMPTY) continue;
            int second, third, fourth;
            second = boardState[i+6];
            third = boardState[i+12];
            fourth = boardState[i+18];
            if((i) % 7 != ((i+6) % 7) - 1 || currentVal != second) continue;
            if((i) % 7 != ((i+12) % 7) - 2 || currentVal != third) continue;
            if((i) % 7 != ((i+18) % 7) - 3 || currentVal != fourth) continue;

            result.setHasWin(true);
            result.setWinnerNumber(currentVal);
        }

        for(int i = 10; i < 20; i += 9){
            int currentVal = boardState[i];
            if(currentVal == EMPTY) continue;
            int second, third, fourth;
            second = boardState[i+6];
            third = boardState[i+12];
            fourth = boardState[i+18];
            if((i) % 7 != ((i+6) % 7) - 1 || currentVal != second) continue;
            if((i) % 7 != ((i+12) % 7) - 2 || currentVal != third) continue;
            if((i) % 7 != ((i+18) % 7) - 3 || currentVal != fourth) continue;

            result.setHasWin(true);
            result.setWinnerNumber(currentVal);
        }

        for(int i = 5; i < 7; i++){
            int currentVal = boardState[i];
            if(currentVal == EMPTY) continue;
            int second, third, fourth;
            second = boardState[i+6];
            third = boardState[i+12];
            fourth = boardState[i+18];
            if((i) % 7 != ((i+6) % 7) + 1 || currentVal != second) continue;
            if((i) % 7 != ((i+12) % 7) + 2 || currentVal != third) continue;
            if((i) % 7 != ((i+18) % 7) + 3 || currentVal != fourth) continue;

            result.setHasWin(true);
            result.setWinnerNumber(currentVal);
        }

        for(int i = 11; i < 13; i++){
            int currentVal = boardState[i];
            if(currentVal == EMPTY) continue;
            int second, third, fourth;
            second = boardState[i+6];
            third = boardState[i+12];
            fourth = boardState[i+18];
            if((i) % 7 != ((i+6) % 7) + 1 || currentVal != second) continue;
            if((i) % 7 != ((i+12) % 7) + 2 || currentVal != third) continue;
            if((i) % 7 != ((i+18) % 7) + 3 || currentVal != fourth) continue;

            result.setHasWin(true);
            result.setWinnerNumber(currentVal);
        }

        for(int i = 17; i < 19; i++){
            int currentVal = boardState[i];
            if(currentVal == EMPTY) continue;
            int second, third, fourth;
            second = boardState[i+6];
            third = boardState[i+12];
            fourth = boardState[i+18];
            if(currentVal != second) continue;
            if(currentVal != third) continue;
            if(currentVal != fourth) continue;

            result.setHasWin(true);
            result.setWinnerNumber(currentVal);
        }
        return result;
    }

    //Minimax and Evaluation function implementations
    //------------------------------------------------------------------------------------------------------------------
<<<<<<< Updated upstream
=======
    /*
    public int maxi(Node root, int depth, int alpha, int beta){
        int score;
>>>>>>> Stashed changes

    public int minimax(Node root, int depth, boolean maximisingPlayer){
        if(depth == 0) return eval(root, depth+1);

        LinkedList<Node> children;

        //Choosing a move for firstPlayer
        if(maximisingPlayer){

            //Inits children, which will be the 2nd player's possible moves
            children = initChildren(root, 3 - FIRSTPLAYER);
            int currentScore;
            int highestScore = Integer.MIN_VALUE;

            for(int i = 0; i < children.size(); i++){
                currentScore = minimax(children.get(i), depth-1, false);
                if(currentScore > highestScore) highestScore = currentScore;
            }
            return highestScore;
        }

        //Choosing a move for second player
        else{

            //Inits children, which will be the 1st player's possible moves
            children = initChildren(root, FIRSTPLAYER);
            int currentScore;
            int lowestScore = Integer.MAX_VALUE;
            for(int i = 0; i < children.size(); i++){
                currentScore = minimax(children.get(i), depth - 1, true);
                if(currentScore < lowestScore) lowestScore = currentScore;
            }
            return lowestScore;
        }
    }
<<<<<<< Updated upstream
    public int eval(Node root, int depth) {
=======

     */
    public int evaluation(Node root, int depth){
>>>>>>> Stashed changes
        WinPair result = checkWin(root.getState());
        int score = 0;


        score += (numOfTwos(root, FIRSTPLAYER) * 50);
        score += (numOfThrees(root, FIRSTPLAYER) * 200);

        score -= (numOfTwos(root, 3 - FIRSTPLAYER) * 40);
        score -= (numOfThrees(root, 3 - FIRSTPLAYER) * 190);


        if (result.hasWin()) {
            if (result.getWinner() == FIRSTPLAYER) score += 1000000 * depth;
            else score -= 1000000 * depth;
        }

        for (int i = 0; i < root.getState().length; i++) {
            if (root.getState()[i] == FIRSTPLAYER) score += boardValues[i];
            else if (root.getState()[i] == (3 - FIRSTPLAYER)) score -= boardValues[i];
        }

<<<<<<< Updated upstream
        //As the evaluation must be relative to the FIRSTPLAYER, if currentPlayer
        //is the FIRSTPLAYER, the sum is positive. Otherwise, it's negative
        //int multiplier = currentPlayer == FIRSTPLAYER ? 1 : -1;
        /*
        int multiplier;
        if (maximisingPlayer) multiplier = 1;
        else multiplier = -1;
        return score * multiplier;
         */
        return score;
=======
        return sum;
        /*
        //Increase board values if maximisingPlayer can connect 2 or 3 in a row
        sum += (numOfTwos(root, FIRSTPLAYER) * 50);
        sum += (numOfThrees(root, FIRSTPLAYER) * 200);

        //Decrease board values if other player will connect 2 or 3 in a row
        //This acts as maximising player attempting to block the other player
        sum -= (numOfTwos(root, 3-FIRSTPLAYER) * 40);
        sum -= (numOfThrees(root, 3-FIRSTPLAYER) * 190);

        if(result.hasWin()){
            if(result.getWinner() == FIRSTPLAYER){
                sum += 1000000 * depth;
            }
            else{
                sum -= 1000000 * depth;
            }
        }

        for(int i = 0; i < root.getState().length; i++) {
            if (root.getState()[i] == FIRSTPLAYER) sum += boardValues[i];
            else if (root.getState()[i] == 3-FIRSTPLAYER) sum -= boardValues[i];
        }
        return sum;  */
    }

    public int alphaBetaMaxi(Node root, int depth, int alpha, int beta) {

        //Evaluates if depth is reached
        if (depth == 0) return evaluation(root, depth + 1);

        //Initialises children to opposite player's turn
        LinkedList<Node> children = initChildren(root, 3 - root.getPlayer());

        //If root.getPlayer() is the FIRSTPLAYER
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < children.size(); i++) {
                int eval = alphaBetaMini(children.get(i), depth - 1, alpha, beta);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) break;
            }
            return maxEval;
    }
    public int alphaBetaMini(Node root, int depth, int alpha, int beta){
        //Evaluates if depth is reached
        if (depth == 0) return evaluation(root, depth + 1);

        //Initialises children to opposite player's turn
        LinkedList<Node> children = initChildren(root, 3 - root.getPlayer());

        int minEval = Integer.MAX_VALUE;
            for(int i = 0; i < children.size(); i++){
                int eval = alphaBetaMaxi(children.get(i), depth-1, alpha, beta);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if(beta <= alpha) break;
            }
            return minEval;

>>>>>>> Stashed changes
    }



    //Misc functions
    //------------------------------------------------------------------------------------------------------------------
    //Given a column number and a board state, this function returns the lowest free space of that column. If the
    //column is full, it returns -1
    public int findAvailableSpace(int colNum, int[] board){
        boolean found = false;

        //Finds the address of the lowest point of the given column
        int currentSpace = colNum + 35;

        int nextSpace = currentSpace - 7;

        while(!found){
            if(board[currentSpace] == EMPTY) {
                found = true;
            }
            else if(nextSpace >= 0){
                currentSpace = nextSpace;
                nextSpace = nextSpace - 7;
            }
            else {
                currentSpace = -1;
                break;
            }
        }

        return currentSpace;
    }

    //Generates children of a given node. These children contain valid game states.
    public LinkedList<Node> initChildren(Node root, int player){
        LinkedList<Node> children = new LinkedList<>();
        for(int i = 0; i < 7; i ++){
            int lowestAddress = findAvailableSpace(i, root.getState());

            if(lowestAddress < 0) continue;

            Node temp = new Node(root);
            temp.setPlayer(player);
            temp.getState()[lowestAddress] = player;
            temp.setColNum(lowestAddress % 7);
            //evaluation(temp);
            children.add(temp);
        }
        return children;
    }

    public int numOfTwos(Node root, int currentPlayer){
        int sum = 0;
        for(int i = 0; i < PrecomputedIndexes.twoInARow.length; i++){

            //Checks to see if the ith space has the currentPlayer's piece in it
            if(root.getState()[i] == currentPlayer) {

                for (int j = 0; j < PrecomputedIndexes.twoInARow[i].size(); j++) {
                    if(root.getState()[PrecomputedIndexes.twoInARow[i].get(j)] == currentPlayer){
                        sum++;
                    }
                }
            }
        }
        return sum;
    }

<<<<<<< Updated upstream
=======
    //Counts the number of 3 consecutive 'currentPlayer' pieces within the current board
>>>>>>> Stashed changes
    public int numOfThrees(Node root, int currentPlayer){
        int sum = 0;
        for(int i = 0; i < PrecomputedIndexes.threeInARow.length; i++){

            //Checks to see if the ith space has the currentPlayer's piece in it
            if(root.getState()[i] == currentPlayer) {

                for (int j = 0; j < PrecomputedIndexes.threeInARow[i].size() - 1; j = j + 2) {
                    if(root.getState()[PrecomputedIndexes.threeInARow[i].get(j)] == currentPlayer && root.getState()[PrecomputedIndexes.threeInARow[i].get(j+1)] == currentPlayer){
                        sum++;
                    }
                }
            }
        }
        return sum;
    }

    public int calcDepth(int timeRemaining){
        /*
<<<<<<< Updated upstream
        if(timeRemaining > 60000) return 9;
        else if(timeRemaining > 10000 && timeRemaining < 59999) return 5;
        else return 2;
         */
        return 5;
=======
        if(timeRemaining > 15000) return 7;
        else if(timeRemaining > 1000 && timeRemaining < 9999) return 5;
        else if(timeRemaining > 300 && timeRemaining < 999) return 3;
        else return 2;
         */

        return 11;
>>>>>>> Stashed changes
    }

    //Debug functions
    //------------------------------------------------------------------------------------------------------------------
    public void printBoard(){
        StringBuilder sb = new StringBuilder();

        int counter = 0;
        for(int i = 0; i < currentBoardNode.getState().length; i++) {
            sb.append(currentBoardNode.getState()[i]);
            sb.append("\t");
            counter++;
            if(counter == 7) {
                sb.append("\n");
                counter = 0;
            }
        }
        sb.append("------------------------------------------------------------------------");
        sb.append("\n");
        for(int i = 0; i < 7; i++) {
            sb.append(i);
            sb.append("\t");
        }


        System.out.println(sb.toString());
    }
    public void debug(){
<<<<<<< Updated upstream

        currentBoardNode.getState()[0] = MINE;
        currentBoardNode.getState()[1] = MINE;
        currentBoardNode.getState()[2] = MINE;

        //currentBoardNode.getState()[35] = MINE;
        /*
        for(int i = 0; i < currentBoardNode.getState().length; i++)
            currentBoardNode.getState()[i] = (Math.random() <= 0.5) ? 1 : 2;

         */
=======
        FIRSTPLAYER = OPPONENT;
>>>>>>> Stashed changes
    }
    public void getIntro(){
        //Fancy logo
        //ASCII art sourced from https://www.asciiart.eu/food-and-drinks/drinks
        String martini = "()   ()      ()    /\n" +
                "  ()      ()  ()  /\n" +
                "   ______________/___\n" +
                "   \\            /   /\n" +
                "    \\^^^^^^^^^^/^^^/\n" +
                "     \\     ___/   /\n" +
                "      \\   (   )  /\n" +
                "       \\  (___) /\n" +
                "        \\ /    /\n" +
                "         \\    /\n" +
                "          \\  /\n" +
                "           \\/\n" +
                "           ||\n" +
                "           ||\n" +
                "     MARTINI ENGINE\n" +
                "         V 1.0\n" +
                "           ||\n" +
                "           ||\n" +
                "           /\\\n" +
                "          /;;\\\n" +
                "     --------------\n";

        char[] chars = martini.toCharArray();
        long t = 30;
        try {
            for(int i = 0; i < chars.length; i++){
                System.out.print(chars[i]);
                Thread.sleep(t);
            }

        }catch(InterruptedException e){
            e.printStackTrace();
        }

    }
    public void printTreeBreadth(Node root){
        Queue<Node> q = new LinkedList<>();
        q.add(root);

        while(!q.isEmpty()){
            Node current = q.poll();
            if(current != null){
                System.out.println("Name: " + current.name + " Value: " + current.getValue() + " PLAYER: " + current.getPlayer());
                for(int i = 0; i < current.getChildren().size(); i++){
                    q.add(current.getChildren().get(i));
                }
            }
        }

    }
    public void printKids(Node root){
        StringBuilder sb = new StringBuilder();
        sb.append("PARENT: ");
        sb.append(root.name);
        sb.append("\n");
        for(int i = 0; i < root.getChildren().size(); i++){
            sb.append("Child Num ");
            sb.append(root.getChildren().get(i).name);
            sb.append(" VALUE: ");
            sb.append(root.getChildren().get(i).getValue());
            sb.append(" PLAYER: ");
            sb.append(root.getChildren().get(i).getPlayer());
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    //------------------------------------------------------------------------------------------------------------------
    private class WinPair{
        private int winnerNumber;
        private boolean hasWin;

        public WinPair(){
            winnerNumber = -1;
            hasWin = false;
        }

        public void setWinnerNumber(int winnerNumber){this.winnerNumber = winnerNumber;}

        public void setHasWin(boolean hasWin){this.hasWin = hasWin;}

        public int getWinner(){return winnerNumber;}

        public boolean hasWin(){return hasWin;}
    }
<<<<<<< Updated upstream
=======

    //Sneaky lil easter egg ;)
    //Call it. You know you want to...
    public void getIntro(){
        //Fancy logo
        //ASCII art sourced from https://www.asciiart.eu/food-and-drinks/drinks
        String martini = "()   ()      ()    /\n" +
                "  ()      ()  ()  /\n" +
                "   ______________/___\n" +
                "   \\            /   /\n" +
                "    \\^^^^^^^^^^/^^^/\n" +
                "     \\     ___/   /\n" +
                "      \\   (   )  /\n" +
                "       \\  (___) /\n" +
                "        \\ /    /\n" +
                "         \\    /\n" +
                "          \\  /\n" +
                "           \\/\n" +
                "           ||\n" +
                "           ||\n" +
                "     MARTINI ENGINE\n" +
                "         V 1.0\n" +
                "           ||\n" +
                "           ||\n" +
                "           /\\\n" +
                "          /;;\\\n" +
                "     --------------\n";

        char[] chars = martini.toCharArray();
        long t = 30;
        try {
            for(int i = 0; i < chars.length; i++){
                System.out.print(chars[i]);
                Thread.sleep(t);
            }

        }catch(InterruptedException e){
            e.printStackTrace();
        }

    }
    //Maybe another easter egg. You dont know!
    public String[] initJokes(){
        String[] jokes = new String[6];
        jokes[0] = "Why do birds fly to warmer climates in the winter?\nIts much easier than walking!";
        jokes[1] = "What creature is smarter than a talking parrot?\nA spelling bee!";
        jokes[2] = "Why was the picture sent to jail?\nIt was framed!";
        jokes[3] = "My girlfriend is the square root of -100; Absolutely perfect but purely imaginary";
        jokes[4] = "Did you know that 60 out of 50 people have trouble with fractions?";
        jokes[5] = "What do you call a half-twisted, one-sided nudie bar?\nA Strip club!";
        return jokes;
    }

>>>>>>> Stashed changes
}