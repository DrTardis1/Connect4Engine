import java.util.Random;

public class MartiniEngine {

    private String name = "Martini-C3260061";
    private Node currentBoardNode = new Node();
    private boolean isFirst = false;
    private int[] currentBoard = new int[42];
    private int[] boardValues = {1,  20, 50, 70,  50, 20, 1,
                                 20, 30, 60, 80,  60, 30, 20,
                                 40, 50, 80, 100, 80, 50, 40,
                                 40, 50, 80, 100, 80, 50, 40,
                                 20, 30, 60, 80,  60, 30, 20,
                                 1,  20, 50, 70,  50, 20, 1};


    private int EMPTY = 0;
    private int OPPONENT = 2;
    private int MINE = 1;
    private int currentPlayer = OPPONENT;

    public String getName(){return name;}

    public String ready(){return "readyok";}

    public String quit(){return "quitting";}

    public void setCurrentPlayer(int currentPlayer){
        this.currentPlayer = currentPlayer;
    }

    public int getCurrentPlayer(){return currentPlayer;}

    public void toggleCurrentPlayer(){
        this.currentPlayer = (this.currentPlayer == OPPONENT) ? MINE : OPPONENT;
    }

    public void isFirst(boolean isFirst){
        this.isFirst = isFirst;
        currentPlayer = this.isFirst ? MINE : OPPONENT;
    }

    public Node getRoot(){return currentBoardNode;}

    public int[] getCurrentBoard(){return currentBoard;}

    public void updateBoard(String input){

    }

    public int perft(int depth, Node root){
        int nodes = 1;

        if(depth == 0) return 1;

        generateChildren(root);

        for(int i = 0; i < root.getChildren().size(); i++){
            nodes += perft(depth - 1, root.getChildren().get(i));
        }
        root.deleteChildren();
        return nodes;
    }

    public void findBestMove(){
        int bestCol = 0;
        if(isFirst){
            isFirst = false;
            for(int i = 0; i < 7; i++){
                if(boardValues[i] > boardValues[bestCol]){
                    bestCol = i;
                }
            }
        }
        else{
            for(int i = 0; i < 7; i++){
                if(!colIsFull(i)){
                    if(boardValues[i] > boardValues[bestCol])
                        bestCol = i;
                }
            }
        }
        addMove(Integer.toString(bestCol));
        System.out.println("bestmove "  + bestCol + " " + boardValues[bestCol]);
    }

    public boolean colIsFull(int colNum){
        return currentBoard[colNum] != EMPTY;
    }
    public int evaluationFunction(int[] board){
        int sum = 0;
        for(int i = 0; i < board.length; i++){
            if(board[i] == MINE)
                sum += boardValues[i];
            else
                sum -= boardValues[i];
        }
        return sum;
    }

    public int addMove(String input) {

        //Converts last character of game log to integer value representing column number
        char c = input.charAt(input.length() - 1);
        int col = Character.getNumericValue(c);
        int finalAddress = findLowestSpace(col, currentBoard);
        currentBoard[finalAddress] = currentPlayer;
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
            else {
                currentSpace = -1;
                break;
            }
        }


        return currentSpace;
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
        for(int i = 0; i < 7; i ++){
            int lowestAddress = findLowestSpace(i, root.getState());

            if(lowestAddress < 0)
                continue;

            Node temp = new Node(root);
            temp.getState()[lowestAddress] = MINE;
            //temp.setValue(evaluationFunction(temp.getState()));
            root.addChild(temp);
        }
    }

    public void generateMoves(Node root){
        int[] board = root.getState();
        for(int i = 0; i < 7; i++){

        }
    }

    public int numAdjacent(int currentElement, int direction){

        //Checks to see if the next space in either direction is within the limits of the board
        boolean dValid1 = currentElement + direction >= 0 && currentElement + direction <= 41;
        boolean dValid2 = currentElement + direction*-1 >= 0 && currentElement + direction*-1 <= 41;

        int currentValue = currentBoard[currentElement];

        int currentCol = currentElement % 7;

        //Checking vertically
        if(direction == 7 || direction == -7) {

            //Cannot check above
            if ((currentElement - direction) < 0) {
                System.out.println("WOULD BE GOING OUT OF BOUNDS! NOT CHECKING ABOVE!");
                if(currentBoard[currentElement + (direction*-1)] == currentValue){
                    currentBoard[currentElement + direction*-1] = MINE;
                    return 1 + numAdjacent(currentElement-direction*-1, direction);
                }
            }

            //Cannot check below
            else if((currentElement + direction) > 41){
                System.out.println("WOULD BE GOING OUT OF BOUNDS! NOT CHECKING ABOVE!");
                if(currentBoard[currentElement + (direction*-1)] == currentValue){
                    currentBoard[currentElement + direction*-1] = MINE;
                    return 1 + numAdjacent(currentElement + direction*-1, direction);
                }
            }

            else{
                if(currentBoard[currentElement + (direction)] == currentValue) {
                    currentBoard[currentElement + direction] = MINE;
                    return 1 + numAdjacent(currentElement + direction, direction);
                }
                if(currentBoard[currentElement + (direction*-1)] == currentValue) {
                    currentBoard[currentElement + direction*-1] = MINE;
                    return 1 + numAdjacent(currentElement + direction * -1, direction);
                }

            }
        }

        //If checking any other direction
        else{

        }

        return 10;
    }

    public void beginTraversal(int currentElement, int direction){
        int initialCol = currentElement % 7;
        int sum = 0;

        if(currentElement + direction >= 0 && currentElement + direction <= 41){
            sum = recursTraverse(currentElement, currentBoard[currentElement], initialCol, initialCol >= (currentElement + direction) % 7, direction);
        }

        System.out.println("There are " + sum + " " + currentBoard[currentElement] + "'s in a row");
    }

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

        //currentBoard[1] = MINE;
        for(int i = 8; i < 42; i++) {
            currentBoard[i] = MINE;
        }
        /*
        Random r = new Random();
        int[] numSpaces = new int[7];
        int colNum = r.nextInt(7);
        while(numSpaces[colNum] >=7)
            colNum = r.nextInt(7);

        checkWin(addMove(String.valueOf(colNum)));
        numSpaces[colNum]++;

        System.out.println("bestmove " + colNum + " 100");
        */
    }
    public void clearBoard(){
        currentBoard = new int[42];
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