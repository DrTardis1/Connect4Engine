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

                case "d":
                    martini.debug();
                    if(martini.checkWin(martini.getGameTree().getState()).hasWin())
                        System.out.println("Win found");
                    else
                        System.out.println("NO WIN FOUND");
                    martini.printBoard();
                    break;

                case "name":
                    System.out.println(martini.getName());
                    break;

                case "isready":
                    System.out.println(martini.ready());
                    break;

                case "position":
                    //Martini is first to move
                    if(input.length == 2) {

                    }
                    else {
                        martini.updateBoard(input[2], 2);
                    }
                    martini.printBoard();
                    break;

                case "go":
                    martini.findBestMove();
                    //martini.toggleCurrentPlayer();
                    martini.printBoard();
                    break;

                case "perft":
                    Node temp = new Node();
                    temp.setState(martini.getCurrentBoard());
                    System.out.println("perft " + input[1] + " " + martini.perft(Integer.parseInt(input[1]), temp));
                    break;

                case "print":
                    martini.printBoard();
                    break;


                case "quit":
                    System.out.println(martini.quit());
                    finished = true;
                    break;

                case "bestmove":
                    martini.updateBoard(input[1], 1);
                    martini.printBoard();
                    if(martini.checkWin(martini.getCurrentBoard()).hasWin())
                        System.out.println(martini.checkWin(martini.getCurrentBoard()).getWinner() + " WON THE GAME");
                    //martini.toggleCurrentPlayer();
                    break;

                case "pt":
                    martini.printTreeBreadth(martini.getGameTree());
                    break;

                case "pk":
                    martini.printKids(martini.getGameTree());
                    break;
            }
        }
    }
}