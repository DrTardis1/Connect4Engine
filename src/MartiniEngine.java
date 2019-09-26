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
        int col = 7 - Character.getNumericValue(c);

        int currentSpace = col, nextSpace = currentSpace + 7;

        while(true){

            if(currentBoard[currentSpace] == EMPTY) {
                currentBoard[currentSpace] = 2;
                break;
            }

            else if(currentBoard[currentSpace] != EMPTY){
                if(nextSpace <= 41){
                    currentSpace = nextSpace;
                    nextSpace = nextSpace + 7;
                }
            }
        }
    }

    public void printBoard(){
        StringBuilder sb = new StringBuilder();
        for(int i = 41; i >= 0; i--){
            sb.append(currentBoard[i]);
            if(i % 7 == 0 && i != 0)
                sb.append("\n");
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