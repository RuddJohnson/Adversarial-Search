/**
 * Rudd Johnson
 * CS 442
 * Final Chess Player
 * 6/1/17
 **/

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;                                   //imported for user I/O
import java.io.*;

public class Main {
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        String[] boardState = new String[8];                //create array of strings of max input size
        String inputLine = "";                              //string that holds row info for board
        int rowLen, height;                                 //variables for row dimensions
        rowLen = height = 0;
        String row = "";

        if (args.length == 0)            //sanity check for input file
        {
            System.out.println("Must provide input file");
            return;
        }
        try {
            FileReader reader = new FileReader(args[0]);             //open file
            BufferedReader bufferedReader = new BufferedReader(reader);
            while ((row = bufferedReader.readLine()) != null) {
                if (height == 8) {
                    System.out.println("board length must equal 6");    //check board height
                    return;
                }
                boardState[height] = row;                            //attach read in row to board
                ++height;
            }
            height = height;                                         //height of the board
            bufferedReader.close();                                 //close file
        } catch (FileNotFoundException excep) {                     //exception catches
            excep.printStackTrace();
        } catch (IOException excep) {
            excep.printStackTrace();
        }

        //verify that the width of all rows is the same
        int firstLength = boardState[0].length();
        if (firstLength > 5 || firstLength < 3) {
            System.out.println("Row length must equal 5");
            return;
        }

        for (int i = 0; i < height; ++i) {
            if (boardState[i].length() != firstLength) {
                System.out.println("all rows must have the same length");
                return;
            }
        }

        System.out.println("Would you like to offer a game: Y or N");
        String Decision = input.nextLine();
        if(Decision.equalsIgnoreCase("Y")){
            try {                                               //try to connect to minichess server
                Client newGame = new Client("imcs.svcs.cs.pdx.edu", 3589);  //create Client instance
                System.out.println("Connected");                            //output verified connection
                newGame.login("PhiladelphiaCollins", "Bam");                 //login
                System.out.println("Waiting for game");
                char side = newGame.offerGameAndWait();
                System.out.println("Game commencing");
                State minichess = new State(boardState, height, firstLength, side);                     //initialize board
                minichess.display(0);
                int turn = 0;                                                                           //int to determine if win or loss
                String theirMove = "";
                String myMove = "";
                System.out.println("Player is " + side);
                //get opponent move
                if (minichess.getStartColor() == 'W') {                                                 //if white native AI plays first
                    while (minichess.getWhiteTurn() != 40 && minichess.getBlackTurn() != 40) {
                        myMove = minichess.moveAI('W');                                                 //native AI plays
                        try {
                            System.out.println("Sending: " + myMove);
                            newGame.sendMove(myMove);
                        } catch (Exception e) {
                            newGame.close();
                            break;
                        }
                        minichess.display(turn);
                        try {
                            theirMove = newGame.getMove();
                            minichess.moveOpponent(theirMove);                                           //opponent AI plays second
                            minichess.display(turn);
                            minichess.incrementBlackturn();
                            minichess.incrementWhiteturn();
                            ++turn;
                        } catch (Exception e) {
                            newGame.close();
                            break;
                        }
                    }

                } else {                                                                             //if black, opponent plays first
                    while (minichess.getWhiteTurn() != 40 && minichess.getBlackTurn() != 40) {
                        try {
                            System.out.println("Getting move");
                            theirMove = newGame.getMove();
                        } catch (IOException e) {
                            newGame.close();
                            break;
                        }
                        if (theirMove == null) {
                            break;
                        }//check for null return
                        minichess.moveOpponent(theirMove);                                      //opponent AI plays first
                        minichess.display(turn);
                        myMove = minichess.moveAI('B');
                        try {
                            newGame.sendMove(myMove);
                            minichess.display(turn);
                            //native AI plays
                            //if AI has no legal moves
                            minichess.incrementBlackturn();
                            minichess.incrementWhiteturn();                                             //increment pieces after each turn
                            ++turn;
                        } catch (IOException e) {
                            newGame.close();
                            break;
                        }
                    }

                    if (minichess.getWhiteTurn() == 40 && minichess.getBlackTurn() == 40) {    //in the event of a draw, output status
                        System.out.println("Draw");
                    }

                }

            }catch(IOException e){}

        }

        else {
            try {                                               //try to connect to minichess server
                Client newGame = new Client("imcs.svcs.cs.pdx.edu", 3589);  //create Client instance
                System.out.println("Connected");                            //output verified connection
                newGame.login("PhiladelphiaCollins", "Bam");                 //login
                String gameId = "";
                List<IMCSGame> gameList = new ArrayList<>();                 //get list of all current game offerings
                gameList = newGame.getGameList();
                System.out.println("Players offering games are: ");
                for (int i = 0; i < gameList.size(); ++i) {
                    System.out.println(gameList.get(i).ownerName + " " + gameList.get(i).gameId);
                }
                System.out.println("Please select a gameID: ");
                String choice = input.nextLine();
                boolean found = false;                          //find offering from TacklingDummy
                for (int i = 0; i < gameList.size(); ++i) {
                    if (gameList.get(i).gameId != null && gameList.get(i).gameId.equalsIgnoreCase(choice)) {
                        gameId = gameList.get(i).gameId;
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("Game not found");
                }                             //if not found output error
                char side = newGame.accept(gameId);                                                      //players starting side
                State minichess = new State(boardState, height, firstLength, side);                     //initialize board
                minichess.display(0);
                int turn = 0;                                                                           //int to determine if win or loss
                String theirMove = "";
                String myMove = "";
                System.out.println("Player is " + side);
                                                                                                        //get opponent move
                if (minichess.getStartColor() == 'W') {                                                 //if white native AI plays first
                    while (minichess.getWhiteTurn() != 40 && minichess.getBlackTurn() != 40) {
                        myMove = minichess.moveAI('W');                                                 //native AI plays
                        try {
                            System.out.println("Sending: " + myMove);
                            newGame.sendMove(myMove);
                        } catch (Exception e) {
                            newGame.close();
                            break;
                        }
                        minichess.display(turn);
                        try {
                            theirMove = newGame.getMove();
                            minichess.moveOpponent(theirMove);                                           //opponent AI plays second
                            minichess.display(turn);
                            minichess.incrementBlackturn();
                            minichess.incrementWhiteturn();
                            ++turn;
                        } catch (Exception e) {
                            newGame.close();
                            break;
                        }
                    }

                } else {                                                                             //if black, opponent plays first
                    while (minichess.getWhiteTurn() != 40 && minichess.getBlackTurn() != 40) {
                        try {
                            System.out.println("Getting move");
                            theirMove = newGame.getMove();
                        } catch (IOException e) {
                            newGame.close();
                            break;
                        }
                        if (theirMove == null) {
                            break;
                        }//check for null return
                        minichess.moveOpponent(theirMove);                                      //opponent AI plays first
                        minichess.display(turn);
                        myMove = minichess.moveAI('B');
                        try {
                            newGame.sendMove(myMove);
                            minichess.display(turn);
                            //native AI plays
                            //if AI has no legal moves
                            minichess.incrementBlackturn();
                            minichess.incrementWhiteturn();                                             //increment pieces after each turn
                            ++turn;
                        } catch (IOException e) {
                            newGame.close();
                            break;
                        }
                    }

                    if (minichess.getWhiteTurn() == 40 && minichess.getBlackTurn() == 40) {    //in the event of a draw, output status
                        System.out.println("Draw");
                    }

                }

                newGame.close();                                             //close connection
                minichess.checkEnd();
            } catch (IOException e) {
                System.out.println("Game not found");
            }                                                               //handle possible exceptions
        }
    }
}