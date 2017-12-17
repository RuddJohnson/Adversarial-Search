/**
 * Rudd Johnson
 * 4/21/17
 */

import java.util.Scanner;                                   //imported for user I/O
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String[] boardState = new String[8];                //create array of strings of max input size
        String inputLine = "";                              //string that holds row info for board
        int rowLen, height;                                 //variables for row dimensions
        rowLen = height = 0;
        char side;                                          //variable holds starting side
        String row = "";

        side = input.nextLine().charAt(0);                  //read in starting side

        if (side != 'B' && side != 'W') {                   //verify appropriate input
            System.out.println("Input must be B or W");
            return;
        }
        row = input.nextLine();                             //flush buffer

        rowLen = row.length();                              //read in first row
        if (rowLen < 3 || rowLen > 8) {
            System.out.println("row length must be between 3 and 8");       //verify correct dimensions
            return;
        }
        boardState[0] = row;                                                //attach to board

        for (height = 1; height < 9; ++height) {                            //read in additional rows until user enters empty row or goes beyond 8

            row = input.nextLine();

            if (row.length() == 0) {
                break;
            }
            if (height == 8) {
                System.out.println("board length cannot be greater than 9");
                return;
            }
            if (row.length() != rowLen) {
                System.out.println("all rows must have the same length");
                return;
            }
            boardState[height] = row;
        }

        Board hexapawn = new Board(boardState, height, rowLen, side);       //initialize board
        hexapawn.solve();                                                   //determine whether position is winning and output result
    }
}
