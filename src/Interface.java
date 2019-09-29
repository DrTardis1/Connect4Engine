/*  Author: Bryce Lynch
    Student Number: 3260061
    Date of Creation: 27/9/19
 */

import java.util.Scanner;

public class Interface {

    public static void main(String args[]) {

        MartiniEngine martini = new MartiniEngine();

        System.out.println(martini.getTreeDepth());
        Scanner coordinator = new Scanner(System.in);
        String[] input = new String[1];
        input[0] = "d";


        while(!input[0].equals("done")){
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
                    if(input.length == 2)
                        martini.addMove(Integer.toString(4));
                    else
                        martini.addMove(input[2]);

                    break;

                case "go":

                    break;

                case "perft":

                    break;

                case "intro":
                    martini.getIntro();
                    break;

                case "print":
                    martini.printBoard();
                    break;

                case "vert":
                    martini.checkVertical(Integer.parseInt(input[1]));
                    break;

                case "quit":
                    System.out.println(martini.quit());
                    break;

            }
        }
    }
}