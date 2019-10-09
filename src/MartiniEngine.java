import java.util.Random;

public class MartiniEngine {

    private String name = "Martini-C3260061";
    private Tree tree = new Tree();
    private boolean isFirst = false;
    private int[] currentBoard = new int[42];
    private int[] columnWeights = {0, 2, 5, 10, 5, 2, 0};

    private static int currentPlayer = 0;
    private static int EMPTY = 0;
    private static int OPPONENT = 2;
    private static int MINE = 1;

    public String getName(){return name;}

    public String ready(){return "readyok";}

    public String quit(){return "quitting";}

    public void setCurrentPlayer(int currentPlayer){
        MartiniEngine.currentPlayer = currentPlayer;
    }

    public int getCurrentPlayer(){return currentPlayer;}

    public static void toggleCurrentPlayer(){
        currentPlayer = (currentPlayer == OPPONENT) ? MINE : OPPONENT;
    }

    public void isFirst(boolean isFirst){this.isFirst = isFirst;}

    public int getTreeDepth(){return tree.getDepth(tree.getRoot());}

    public void updateBoard(String input){}

    public void bestMove(){
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
            if(tree.getRoot() == null){
                tree.setRoot(new Node(currentBoard));
            }

            else{

            }
        }
    }

    public int evaluationFunction(Node root){
        return 7;
    }

    public int addMove(String input) {

        //Converts last character of game log to integer value representing column number
        char c = input.charAt(input.length() - 1);
        int col = Character.getNumericValue(c);
        int finalAddress = findLowestSpace(col, currentBoard);

        /*if(finalAddress > 41) {
            System.out.println("ERROR HAS OCCURED. SHUTTING DOWN");
            System.exit(0);
        }*/

        currentBoard[finalAddress] = currentPlayer;

        tree.getRoot().setState(currentBoard);
        tree.getRoot().deleteChildren();
        generateChildren(tree.getRoot());

        return finalAddress;
    }

    public int findLowestSpace(int colNum, int[] board){
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
            else
                currentSpace = Integer.MAX_VALUE;
        }


        return currentSpace;
    }

    public void debug(){
        Random r = new Random();
        int[] numSpaces = new int[7];
        int colNum = r.nextInt(7);
        while(numSpaces[colNum] >=7)
            colNum = r.nextInt(7);

        checkWin(addMove(String.valueOf(colNum)));
        numSpaces[colNum]++;

        System.out.println("bestmove " + colNum + " 100");
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

    public void clearBoard(){
        currentBoard = new int[42];
    }

    public boolean checkWin(int inputElement){
        if(checkVertical(inputElement))
            return true;
        else if (checkHorizontal(inputElement))
            return true;
        else if(checkDiagonals(inputElement))
            return true;
        else
            return false;
    }

    public boolean checkVertical(int inputElement) {
        int currentValue = currentBoard[inputElement];
        int spaceAbove = inputElement;
        int spacesToCheck = 3;

        //Finds the space above currentElement that is still the same token as currentElement
        while (spaceAbove - 7 >= 0 && currentBoard[spaceAbove - 7] == currentValue) {
            spaceAbove = spaceAbove - 7;
            spacesToCheck--;
        }

        //If there are no more spaces to check (i.e. currentElement was the bottom of a connect 4 vertical line)
        if (spacesToCheck == 0) {
            return true;
        }

        else{
            int spaceBelow = inputElement;
            for (int i = 0; i < spacesToCheck; i++) {

                //If there is a valid space below the current element
                if(spaceBelow + 7 <= 41)
                    spaceBelow = spaceBelow + 7;

                //If there is no valid space below the current element
                else if(spaceBelow + 7 > 41) {
                    return false;
                }

                //If there is a valid space below the current element, but it does not
                //contain the same value as the current element
                if (currentBoard[spaceBelow] != currentValue) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkHorizontal(int inputElement){

        int currentValue = currentBoard[inputElement];

        //Checks to see if the currentElement is closer to the left or right
        int closestEdge = (inputElement % 7 <= 3) ? 0 : 6;
        int boundaryElement = inputElement;

        if(closestEdge == 0){
            while(boundaryElement % 7 != closestEdge){
                boundaryElement--;
            }

            for(int i = boundaryElement; i < boundaryElement + 4; i++){
                if(currentBoard[i] != currentValue){
                    return false;
                }

            }
        }
        else{
            while(boundaryElement % 7 != closestEdge){
                boundaryElement++;
            }

            for(int i = boundaryElement; i > boundaryElement - 4; i--){
                if(currentBoard[i] != currentValue){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkDiagonals(int inputElement){

        //Determines which column the inputElement is in
        int initialModClass = inputElement % 7;
        int currentValue = currentBoard[inputElement];

        int leftMostDiag = inputElement;
        int currentDiagModClass = leftMostDiag % 7;
        int spacesLeft = 3;

        //Checks diagonally up right
        while(spacesLeft > 0){

            //Ensures the element diagonally up and left of the inputElement is still a valid element on the board
            //Ensures the mod class of that element is only 1 less than the currentModClass
            if(leftMostDiag - 8 >= 0 && (leftMostDiag - 8) % 7 == currentDiagModClass-1 && currentBoard[leftMostDiag-8] == currentValue) {
                leftMostDiag = leftMostDiag - 8;
                currentDiagModClass = leftMostDiag % 7;
                spacesLeft--;
            }
            else{
                break;
            }
        }

        if(spacesLeft == 0){
            return true;
        }

        //Checks diagonally up left
        else{
            int rightMostDiag = inputElement;
            currentDiagModClass = rightMostDiag % 7;
            spacesLeft = 3;
            while(spacesLeft > 0){

                if(rightMostDiag - 6 >= 0 && (rightMostDiag - 6) % 7 == currentDiagModClass-1 && currentBoard[rightMostDiag-6] == currentValue){
                    rightMostDiag = rightMostDiag - 6;
                    currentDiagModClass = rightMostDiag % 7;
                    spacesLeft--;
                }
                else{
                    break;
                }
            }
        }

        if(spacesLeft == 0){
            return true;
        }

        //Checks Diagonally down right
        else{
            int rightMostDiag = inputElement;
            currentDiagModClass = rightMostDiag % 7;
            spacesLeft = 3;
            while(spacesLeft > 0){

                if(rightMostDiag + 8 <= 41 && (rightMostDiag + 8) % 7 == currentDiagModClass+1 && currentBoard[rightMostDiag+8] == currentValue){
                    rightMostDiag = rightMostDiag + 8;
                    currentDiagModClass = rightMostDiag % 7;
                    spacesLeft--;
                }
                else{
                    break;
                }
            }
        }

        if(spacesLeft == 0){
            return true;
        }

        //Checks diagonally down left
        else{
            leftMostDiag = inputElement;
            currentDiagModClass = leftMostDiag % 7;
            spacesLeft = 3;
            while(spacesLeft > 0){

                if(leftMostDiag + 6 <= 41 && (leftMostDiag + 6) % 7 == currentDiagModClass-1 && currentBoard[leftMostDiag+6] == currentValue){
                    leftMostDiag = leftMostDiag + 6;
                    currentDiagModClass = leftMostDiag % 7;
                    spacesLeft--;
                }
                else{
                    break;
                }
            }

            if(spacesLeft == 0){
                return true;
            }
            return false;
        }
    }

    public void generateChildren(Node root){
        for(int i = 6; i > 0; i --){
            Node temp = new Node(root);
            int lowestAddress = findLowestSpace(i, temp.getState());

            if(lowestAddress > 41)
                continue;

            temp.getState()[lowestAddress] = currentPlayer;
            root.addChild(temp);
        }
    }

    public void generateMoves(Node root){
        int[] board = root.getState();
        for(int i = 0; i < 7; i++){

        }
    }

    public void getInfo(){
        System.out.println("info depth " + tree.getDepth(tree.getRoot()));
    }
    //Fancy logo
    //ASCII art sourced from https://www.asciiart.eu/food-and-drinks/drinks
    public void getIntro(){
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