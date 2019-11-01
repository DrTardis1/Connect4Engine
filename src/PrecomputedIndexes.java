import java.util.LinkedList;


//Class that contains addresses of possible two or three in a row combinations within a given board state.
//Used to calculate how many 2 or 3 in a row combinations there are in a board.
public class PrecomputedIndexes {

    public static LinkedList<Integer>[] twoInARow;
    public static LinkedList<Integer>[] threeInARow;
    public static LinkedList<Integer>[] fourInARow;


    public static void init(){initTwoInARow(); initThreeInARow(); initFourInARow();}

    //Initialises twoInARow, which is a linked list containing arrays of indexes that can contain 2 consecutive pieces
    public static void initTwoInARow(){
        twoInARow = new LinkedList[41];

        for(int i = 0; i < twoInARow.length; i++){

            twoInARow[i] = new LinkedList<>();

            //In bottom row
            if(i >= 35){
                twoInARow[i].add(i+1);
            }

            //In left column
            else if(i % 7 == 0){
                twoInARow[i].add(i+1);
                twoInARow[i].add(i+8);
                twoInARow[i].add(i+7);
            }

            //In right column
            else if(i % 7 == 6){
                twoInARow[i].add(i+7);
                twoInARow[i].add(i+6);
            }
            else{
                twoInARow[i].add(i+1);
                twoInARow[i].add(i+8);
                twoInARow[i].add(i+7);
                twoInARow[i].add(i+6);
            }
        }
    }
    //Initialises threeInARow, which is a linked list containing arrays of indexes that can contain 3 consecutive pieces
    public static void initThreeInARow(){
        threeInARow = new LinkedList[40];

        for(int i = 0; i < threeInARow.length; i++){

            threeInARow[i] = new LinkedList<>();

            //In bottom row
            if(i >= 28){
                threeInARow[i].add(i+1);
                threeInARow[i].add(i+2);
            }

            //In left column
            else if(i % 7 == 0 || i % 7 == 1){
                threeInARow[i].add(i+1);
                threeInARow[i].add(i+2);

                threeInARow[i].add(i+8);
                threeInARow[i].add(i+16);

                threeInARow[i].add(i+7);
                threeInARow[i].add(i+14);
            }

            //In right column
            else if(i % 7 == 6 || i % 7 == 5){
                threeInARow[i].add(i+7);
                threeInARow[i].add(i+14);

                threeInARow[i].add(i+6);
                threeInARow[i].add(i+12);
            }

            else{
                threeInARow[i].add(i+1);
                threeInARow[i].add(i+2);
                threeInARow[i].add(i+8);
                threeInARow[i].add(i+16);
                threeInARow[i].add(i+7);
                threeInARow[i].add(i+14);
                threeInARow[i].add(i+6);
                threeInARow[i].add(i+12);
            }

        }
    }

    public static void initFourInARow(){
        fourInARow = new LinkedList[39];

        for(int i = 0; i < fourInARow.length; i++){

            fourInARow[i] = new LinkedList<>();

            if(i >= 35 && i <=38) {
                fourInARow[i].add(i + 1);
                fourInARow[i].add(i + 2);
                fourInARow[i].add(i + 3);
            }
        }
    }

    public static void printTwo(){
        for(int i = 0; i < twoInARow.length; i++){
            System.out.print("INDEX: " + i + " - ");
            for(int j = 0; j < twoInARow[i].size(); j++){
                System.out.print(twoInARow[i].get(j) + ", ");
            }
            System.out.println();
        }
    }
    public static void printThree(){
        for(int i = 0; i < threeInARow.length; i++){
            System.out.print("INDEX: " + i + " - ");
            for(int j = 0; j < threeInARow[i].size(); j++){
                System.out.print(threeInARow[i].get(j) + ", ");
            }
            System.out.println();
        }
    }
    public static void printFour(){
        for(int i = 0; i < fourInARow.length; i++){
            System.out.print("INDEX: " + i + " - ");
            for(int j = 0; j < fourInARow[i].size(); j++){
                System.out.print(fourInARow[i].get(j) + ", ");
            }
            System.out.println();
        }
    }

}
