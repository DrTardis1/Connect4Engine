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

        int currentSpace = bottomRow, nextSpace = currentSpace - 7;

        while(true){

            if(currentBoard[currentSpace] == EMPTY) {
                currentBoard[currentSpace] = 2;
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

        for(int i = 0; i < currentBoard.length; i ++)
            currentBoard[i] = i;

        /*
        for(int i = 0; i < 41; i = i+8)
            currentBoard[i] = 2;*/
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