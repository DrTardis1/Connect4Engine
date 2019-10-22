import java.util.LinkedList;
import java.util.Queue;

public class MartiniEngine {

    private String name = "Martini-C3260061";
    private Node currentBoardNode = new Node();
    private boolean isFirst = false;

    private int[] directions = {-7, 7, -1, 1, -6, 8, 6, -8};
    //private int[] currentBoard = new int[42];
    private int[] boardValues = {1,  20, 50, 70,  50, 20, 1,
                                 20, 30, 60, 80,  60, 30, 20,
                                 40, 50, 80, 100, 80, 50, 40,
                                 40, 50, 80, 100, 80, 50, 40,
                                 20, 30, 60, 80,  60, 30, 20,
                                 1,  20, 50, 70,  50, 20, 1};
    //Player information
    private int EMPTY = 0;
    private int OPPONENT = 2;
    private int MINE = 1;
    //private int currentPlayer = OPPONENT;

    //Essential Functions
    //---------------------------------------------------------

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
    public void findBestMove(){

        //initGameTree(currentBoardNode, 1);
        int bestVal = Integer.MIN_VALUE;
        int index = 0;
        int score = 0;
        LinkedList<Node> children = initChildren(currentBoardNode, MINE);
        for(int i = 0; i < children.size(); i++) {
            score = -negaMax(children.get(i), 5, OPPONENT);
            if(score > bestVal){
                bestVal = score;
                index = i;
            }
        }
        System.out.println("bestmove " + children.get(index).getColNum() +  " " + bestVal);
        updateBoard(Integer.toString(children.get(index).getColNum()), 1);
        //toggleCurrentPlayer();
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
    //---------------------------------------------------------

    //Getters & Setters
    //---------------------------------------------------------
    /*public void toggleCurrentPlayer(){
        this.currentPlayer = (this.currentPlayer == OPPONENT) ? MINE : OPPONENT;
    }*/

    /*public void isFirst(boolean isFirst){
        this.isFirst = isFirst;
        currentPlayer = this.isFirst ? MINE : OPPONENT;
    }

     */

    public boolean isFull(int colNum){
        return currentBoardNode.getState()[colNum] != EMPTY;
    }

    public int[] getCurrentBoard(){return currentBoardNode.getState();}

    public Node getGameTree(){return currentBoardNode;}

    public int getTreeDepth(){return currentBoardNode.getDepth(currentBoardNode);}
    //---------------------------------------------------------

    //Win Checking Functions
    //---------------------------------------------------------
    public WinPair checkWin(int[] boardState){
        /*
        if(checkHorizontal(boardState).hasWin()) System.out.println("HOR WIN FOUND");
        if(checkVertical(boardState).hasWin()) System.out.println("VERT WIN FOUND");
        if(checkDiagOne(boardState).hasWin()) System.out.println("DIAG1 WIN FOUND");
        if(checkDiagTwo(boardState).hasWin()) System.out.println("DIAG2 WIN FOUND");

         */
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

        for(int i = 4; i < 14; i += 10){
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
        return result;
    }
    //---------------------------------------------------------

    public int negaMax(Node root, int depth, int player){

        if(depth == 0) return evaluation(root, player);

        LinkedList<Node> children = initChildren(root, player);

        if(children.size() == 0) return evaluation(root, player);
        int score;
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < children.size(); i++){
            score = -negaMax(children.get(i), depth - 1, 3 - player);
            if(score > max) max = score;
        }
        return max;
    }

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

    public int evaluation(Node root, int maximisingPlayer){
        WinPair result = checkWin(root.getState());

        int sum = 0;

        /*
        //Makes this node extremely desirable
        if(result.hasWin() && result.getWinner() == maximisingPlayer){
            return -9999;
        }

        //Makes this node extremely undesirable
        else if(result.hasWin() && result.getWinner() != maximisingPlayer){
            return 9999;
        }

         */

        if(result.hasWin()){
            sum = 1000000;
        }

        for(int i = 0; i < root.getState().length; i++) {

            //if (root.getState()[i] == MINE) sum += boardValues[i];
            //else if (root.getState()[i] == OPPONENT) sum -= boardValues[i];
            if (root.getState()[i] == maximisingPlayer) sum += boardValues[i];
            else if (root.getState()[i] != maximisingPlayer && root.getState()[i] != EMPTY) sum -= boardValues[i];
        }

        return sum * ((-2*(maximisingPlayer - 1)) + 1);
    }


    //Debug functions
    //---------------------------------------------------------
    public int recursTraverse(int currentElement, int currentValue, int initialCol, boolean toggle, int direction) {
        int currentCol = (currentElement + direction) % 7;
        if(initialCol >= currentCol && toggle) {
            if (currentElement + direction >= 0 && currentElement + direction <= 41 && currentBoardNode.getState()[currentElement] == currentValue)
                return 1 + recursTraverse(currentElement + direction, currentValue, initialCol, toggle, direction);
            else
                return 0;
        }
        else
            return 0;
    }
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

        currentBoardNode.getState()[35] = MINE;
        currentBoardNode.getState()[36] = MINE;
        currentBoardNode.getState()[37] = MINE;
        //currentBoardNode.getState()[35] = MINE;
        /*
        for(int i = 0; i < currentBoardNode.getState().length; i++)
            currentBoardNode.getState()[i] = (Math.random() <= 0.5) ? 1 : 2;

         */
    }
    public void clearBoard(){
        currentBoardNode.setState(new int[42]);
    }
    public boolean boardIsEmpty(){
        for(int i = 0; i < currentBoardNode.getState().length; i++)
            if(currentBoardNode.getState()[i] != EMPTY) return false;

        return true;
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
    public void printTree(Node root){

        if(root == null) return;

        StringBuilder sb = new StringBuilder();
        sb.append("Name: ");
        sb.append(root.name);
        sb.append(" Value: ");
        sb.append(root.getValue());
        if(root.getParent() != null) {
            sb.append(" Parent: ");
            sb.append(root.getParent().name);
        }
        sb.append("\n");
        System.out.println(sb.toString());

        for(int i = 0; i < root.getChildren().size(); i++)
            printTree(root.getChildren().get(i));


        /*
        for(int i = 0; i < currentBoardNode.getChildren().size(); i++){
            sb.append("Name: ");
            sb.append(i);
            sb.append("\tValue: ");
            sb.append(currentBoardNode.getChildren().get(i).getValue());
            sb.append("\tColNum: ");
            sb.append(currentBoardNode.getChildren().get(i).getColNum());
            sb.append("\n");
        }
        System.out.println(sb.toString());

         */
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


    public class WinPair{
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
}