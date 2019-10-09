/*  Author: Bryce Lynch
    Student Number: 3260061
    Date of Creation: 27/9/19
 */

import java.util.Scanner;

public class Interface {

    public static void main(String args[]) {

        MartiniEngine martini = new MartiniEngine();
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
                    //Martini is first to move
                    if(input.length == 2) {
                        martini.isFirst(true);
                        martini.setCurrentPlayer(1);
                    }
                    else {

                        martini.addMove(input[2]);
                    }
                    break;

                case "go":
                    martini.getInfo();
                    martini.bestMove();
                    MartiniEngine.toggleCurrentPlayer();
                    break;

                case "perft":

                    break;

                case "intro":
                    martini.getIntro();
                    break;

                case "print":
                    martini.printBoard();
                    break;

                case "quit":
                    System.out.println(martini.quit());
                    finished = true;
                    break;

            }
        }
    }
}