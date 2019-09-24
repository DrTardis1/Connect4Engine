/*  Author: Bryce Lynch
    Student Number: 3260061
    Date of Creation: 27/9/19
 */

import java.util.Scanner;

public class Interface {

    public static void main(String args[]) {

        MartiniEngine martini = new MartiniEngine();

        boolean matchInProgress = true;
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

                    break;


                case "go":

                    break;


                case "perft":

                    break;

                case "intro":
                    martini.getIntro();
                    break;


                case "quit":
                    System.out.println(martini.quit());
                    break;

            }

            matchInProgress = false;
        }

    }

}
