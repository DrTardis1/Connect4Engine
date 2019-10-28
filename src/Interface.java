/*  Author: Bryce Lynch
    Student Number: 3260061
    Date of Creation: 27/9/19
 */

import java.util.Scanner;

public class Interface {

    public static void main(String args[]) {

        MartiniEngine martini = new MartiniEngine();
        PrecomputedIndexes.init();

        Scanner coordinator = new Scanner(System.in);
        String[] input;
        boolean finished = false;

        while(!finished){

            input = coordinator.nextLine().split(" ");

            switch(input[0].toLowerCase()){

                case "name":
                    System.out.println(martini.getName());
                    break;

                case "isready":
                    System.out.println(martini.ready());
                    break;

                case "position":
                    if(input.length == 2) martini.setFirstPlayer(1);

                    if(input.length == 3)
                        martini.updateBoard(input[2], 2);
                    break;

                case "go":
                    if(martini.isFirst())
                        martini.findBestMove(Integer.parseInt(input[2]));
                    else
                        martini.findBestMove(Integer.parseInt(input[4]));
                    break;

                case "perft":
                    Node temp = new Node();
                    temp.setState(martini.getCurrentBoard());
                    System.out.println("perft " + input[1] + " " + martini.perft(Integer.parseInt(input[1]), temp));
                    break;

                case "quit":
                    System.out.println(martini.quit());
                    finished = true;
                    break;

                //------------------------------------------------------------------------------------

                case "e":
                    //System.out.println("CURRENT BOARD VALUE: " + martini.evaluation(martini.getGameTree(), 1));
                    break;

                case "easter":
                    martini.getIntro();
                    break;

                case "pt":
                    martini.printTreeBreadth(martini.getGameTree());
                    break;

                case "pk":
                    martini.printKids(martini.getGameTree());
                    break;

                case "d":
                    martini.debug();
                    break;

                case "two":
                    System.out.println("NUM OF 2s:" + martini.numOfTwos(martini.getGameTree(), 1));
                    break;

                case "three":
                    System.out.println("NUM OF 3s:" + martini.numOfThrees(martini.getGameTree(),  1));
                    break;

                case "print":
                    martini.printBoard();
                    break;
            }
        }
    }
}
