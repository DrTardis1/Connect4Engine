import javax.crypto.spec.OAEPParameterSpec;
import java.util.Random;

public class MartiniEngine {

    private String name = "Martini-C3260061";
    private Node currentBoardNode = new Node();
    private boolean isFirst = false;

    private int[] directions = {-7, 7, -1, 1, -6, 8, 6, -8};
    private int[] currentBoard = new int[42];
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
    private int currentPlayer = OPPONENT;

    //Essential Functions
    //---------------------------------------------------------
    public String getName(){return name;}

    public String ready(){return "readyok";}

    public String quit(){return "quitting";}

    public int perft(int depth, Node root){
        int nodes = 1;
        if(depth == 0) return 1;

        initChildren(root);
        for(int i = 0; i < root.getChildren().size(); i++){
            nodes += perft(depth - 1, root.getChildren().get(i));
        }
        root.deleteChildren();
        return nodes;
    }
    //---------------------------------------------------------

    //Getters & Setters
    //---------------------------------------------------------
    public void toggleCurrentPlayer(){
        this.currentPlayer = (this.currentPlayer == OPPONENT) ? MINE : OPPONENT;
    }

    public void isFirst(boolean isFirst){
        this.isFirst = isFirst;
        currentPlayer = this.isFirst ? MINE : OPPONENT;
    }

    public boolean isFull(int colNum){
        return currentBoard[colNum] != EMPTY;
    }

    public int[] getCurrentBoard(){return currentBoard;}

    public Node getGameTree(){return currentBoardNode;}

    public int getTreeDepth(){return currentBoardNode.getDepth(currentBoardNode);}
    //---------------------------------------------------------

    public void findBestMove(){

        initGameTree(currentBoardNode, 1);
        int bestVal = Integer.MIN_VALUE;
        int index = 0;

        for(int i  = 0; i < currentBoardNode.getChildren().size(); i ++){
            int childValue = maxi(currentBoardNode.getChildren().get(i), 0);
            if(childValue > bestVal) {
                bestVal = childValue;
                index = i;
            }
        }

        Node bestChild = currentBoardNode.getChildren().get(index);
        bestChild.setParent(null);
        currentBoardNode = bestChild;
        currentBoard = currentBoardNode.getState();

        System.out.println("bestmove " + currentBoardNode.getColNum() + " " + bestVal);
        /*
        updateBoard(Integer.toString(bestCol));
        System.out.println("bestmove "  + bestCol + " " + boardValues[bestCol]);

         */
    }

    //Given an input string, this function will take the last character from it (which will be a column number)
    //This function will then add a piece to the corresponding column if possible.
    public int updateBoard(String input) {

        //Converts last character of game log to integer value representing column number
        int col = Character.getNumericValue(input.charAt(input.length() - 1));
        int finalAddress = findAvailableSpace(col, currentBoard);

        if(finalAddress >= 0) {
            currentBoard[finalAddress] = currentPlayer;
            toggleCurrentPlayer();
        }

        return finalAddress;
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

    public int maxi(Node root, int depth){
        //if(depth == 0) return eval(root);
        int max = Integer.MIN_VALUE;
        int score = 0;
        for(int i = 0; i < root.getChildren().size(); i++){
            score = mini(root.getChildren().get(i),depth-1);
            if(score > max) max = score;
        }
        return max;
    }
    public int mini(Node root, int depth){
        //if(depth == 0) return eval(root);
        int min = Integer.MAX_VALUE;
        int score = 0;
        for(int i = 0; i < root.getChildren().size(); i++){
            score = maxi(root.getChildren().get(i), depth-1);
            if(score < min) min = score;
        }
        return min;
    }

    //Generates children of a given node. These children contain valid game states.
    public void initChildren(Node root){
        for(int i = 0; i < 7; i ++){
            int lowestAddress = findAvailableSpace(i, root.getState());

            if(lowestAddress < 0) continue;

            Node temp = new Node(root);
            temp.getState()[lowestAddress] = MINE;
            temp.setColNum(lowestAddress % 7);
            root.addChild(temp);
        }
    }

    public int genGameTree(Node root, int depth){
        if(depth == 0){
            //eval(root);
            return 1;
        }

        initChildren(root);
        for(int i = 0; i < root.getChildren().size(); i++){
            genGameTree(root.getChildren().get(i), depth - 1);
        }
        return depth;
    }

    public void initGameTree(Node root, int depth){
        root.deleteChildren();
        genGameTree(root, depth);
    }

    /*
    public int eval(Node root){
        int sum = 0;
        for(int i = 0; i < root.getState().length; i++){
            if(checkWin(root.getState()[i])){
                sum = 100;
                break;
            }
        }

        root.setValue(sum);
        return sum;
    }
    */

    public boolean checkWin(){
        return checkHorizontal() || checkVertical() || checkDiagOne() || checkDiagTwo();
    }
    public boolean checkHorizontal(){
        boolean winFound = false;
        outerloop:
        for(int i = 0; i < 36; i = i + 7){
            for(int j = 0; j < 4; j++){
                int currentValue = currentBoard[i+j];
                if(currentValue == EMPTY) continue;

                if(currentBoard[i+j+1] == currentValue && currentBoard[i+j+2] == currentValue && currentBoard[i+j+3] == currentValue){
                    winFound = true;
                    break outerloop;
                }
            }
        }
        return winFound;
    }
    public boolean checkVertical(){
        boolean winFound = false;

        outerloop:
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 28; j = j + 7){
                int currentValue = currentBoard[i+j];
                if(currentValue == EMPTY) continue;

                if(currentBoard[i + j + 7] == currentValue && currentBoard[i + j + 14] == currentValue && currentBoard[i + j + 21] == currentValue){
                    winFound = true;
                    break outerloop;
                }
            }
        }
        return winFound;
    }
    public boolean checkDiagOne(){
        boolean winFound = false;
        int[] startingElements = {0,1,2,7,14};

        outerloop:
        for(int i = 0; i < startingElements.length; i++){
            for(int j = 0; j < 27; j = j + 8){
                int startVal = startingElements[i];
                int currentValue = currentBoard[startVal+j];
                if(currentValue == EMPTY) continue;

                if(currentBoard[startVal + j + 8] == currentValue && currentBoard[startVal + j + 16] == currentValue && currentBoard[startVal + j + 24] == currentValue) {
                    winFound = true;
                    break outerloop;
                }
            }
        }
        return winFound;
    }
    public boolean checkDiagTwo(){
        boolean winFound = false;
        int[] startingElements = {3,4,5,6,13,20};

        outerloop:
        for(int i = 0; i < startingElements.length; i++){
            for(int j = 0; j < 27; j = j + 6){
                int startVal = startingElements[i];
                int currentValue = currentBoard[startVal+j];
                if(currentValue == EMPTY) continue;

                if(currentBoard[startVal + j + 6] == currentValue && currentBoard[startVal + j + 12] == currentValue && currentBoard[startVal + j + 18] == currentValue) {
                    winFound = true;
                    break outerloop;
                }
            }
        }
        return winFound;
    }

    //---------------------------------------------------------
    public int recursTraverse(int currentElement, int currentValue, int initialCol, boolean toggle, int direction) {
        int currentCol = (currentElement + direction) % 7;
        if(initialCol >= currentCol && toggle) {
            if (currentElement + direction >= 0 && currentElement + direction <= 41 && currentBoard[currentElement] == currentValue)
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
        for(int i = 0; i < currentBoard.length; i++) {
            sb.append(currentBoard[i]);
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
        for(int i = 0; i < currentBoard.length; i ++)
            currentBoard[i] = (Math.random() <= 0.5) ? 1 : 2;

    }
    public void clearBoard(){
        currentBoard = new int[42];
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
}