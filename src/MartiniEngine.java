public class MartiniEngine {

    private String name = "Martini - C3260061";
    private Tree tree = new Tree();
    private int[] currentBoard = new int[42];

    private static int EMPTY = 0;
    private static int OPPONENT = 2;
    private static int MINE = 1;

    public String getName(){return name;}

    public String ready(){return "readyok";}

    public String quit(){return "quitting";}

    public int getTreeDepth(){return tree.getDepth(tree.getRoot());}

    public String bestMove(String[] moveLog){
        StringBuilder bestMove = new StringBuilder();
        bestMove.append("bestmove ");

        if(moveLog.length == 2)
            bestMove.append("3 100");

        return bestMove.toString();
    }

    public void addMove(String input) {

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
                currentBoard[currentSpace] = MINE;
                break;
            }

            else if(currentBoard[currentSpace] != EMPTY){
                if(nextSpace >= 0){
                    currentSpace = nextSpace;
                    nextSpace = nextSpace - 7;
                }
            }
        }
    }

    public boolean checkWin(){
        int upRight = 8;
        int downLeft = -8;
        int upLeft = 6;
        int downRight = -6;

        return true;
    }

    public void debug(){

        currentBoard[0]= MINE;
        currentBoard[1]= MINE;
        currentBoard[2]= MINE;
        currentBoard[3]= MINE;
        /*
        currentBoard[40] = MINE;
        currentBoard[33] = MINE;
        currentBoard[26] = MINE;
        currentBoard[19] = MINE;*/
        /*
        for(int i = 0; i < currentBoard.length; i ++)
            currentBoard[i] = i;*/
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


        System.out.println(sb.toString());
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
                if(spaceBelow + 7 <= currentBoard.length)
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