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

    public String getName(){return name;}

    public String ready(){return "readyok";}

    public String quit(){return "quitting";}

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
        if(depth == 0) return eval(root);
        int max = Integer.MIN_VALUE;
        int score = 0;
        for(int i = 0; i < root.getChildren().size(); i++){
            score = mini(root.getChildren().get(i),depth-1);
            if(score > max) max = score;
        }
        return max;
    }
    public int mini(Node root, int depth){
        if(depth == 0) return eval(root);
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
            eval(root);
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

    public boolean checkWin(int inputElement){

        if(currentBoardNode.getState()[inputElement] == 0) return false;

        else if(checkVertical(inputElement))
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