/**
 * Rudd Johnson
 * CS 442
 * Final Chess Player
 * 6/1/17
 **/
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.*;

public class State {                        //class manages the depth first search of game tree for given input state
    Scanner input = new Scanner(System.in);
    private char startColor;                //hold the starting side
    private char [][] board;                //store input board
    private int length;                     //store board dimensions
    private int width;
    private int whiteTurn;                  //number of turns white has taken
    private int blackTurn;                  //number of turns black has taken
    private int whiteScore;
    private int blackScore;
    private String columns;
    private String rows;
    public State(String[]boardState, int len, int wid, char start) {       //constructor initializes private data members
        this.startColor = start;
        this.length = len;
        this.width = wid;
        this.board = new char[length][width];
        this.whiteTurn = 0;
        this.blackTurn = 0;
        this.whiteScore =0;
        this.blackScore =0;
        this.rows = "123456";
        this.columns = "abcde";

        build(this.board, boardState);                                      //function constructs object board and coppies user input strings into it
    }

    public int getWhiteTurn() {return whiteTurn;}
    public int getBlackTurn() {return blackTurn;}
    public void incrementBlackturn(){this.blackTurn+= 1;}
    public void incrementWhiteturn(){this.whiteTurn+= 1;}
    public char getStartColor() {return startColor;}

    public void build(char[][] board, String[]boardState) {

        for (int i = 0; i < length; ++i) {                                 //iterate through object board, initialize each piece and copy string character to appropriate location
            for (int j = 0; j < width; ++j) {
                this.board[i][j] = boardState[i].charAt(j);
            }
        }
    }

    private void copyBoard(char [][] sourceBoard, char [][] destBoard)    //copy one object board to another, which is the state of is then changed for each recursive move
    {
        for (int i = 0; i < length; ++i)
            for (int j = 0; j < width; ++j) {
                destBoard[i][j] = sourceBoard[i][j];
            }
    }

    /*
        move generation, determines which moves are legal for all AI's pieces on the board and stores
        those moves in a list of moves
    */
    private int legalMoves(char [][] boardState, List <Piece> moves, char Side) {      //find legal moves for side that is passed in in a given state, append the move to moves list
        int forward,left,right;
        forward = left = right =0;
        if (Side == 'W') {                                                              //if side is White
            for (int i = 0; i < length; ++i){                                           //iterate through board
                for (int j = 0; j < width; ++j) {
                    if (boardState[i][j] == '.'){continue;}                             //no need to scan types if no piece present
                    else if (boardState[i][j] == 'P'){Pawn move = new Pawn('P',j,i); move.find_moves(boardState,moves,Side);}                                       //if piece is pawn
                    else if (boardState[i][j] == 'R'){Rook move = new Rook('R',j,i); move.find_moves(boardState,moves,Side);}                                        //if piece is rook
                    else if (boardState[i][j] == 'N'){Knight move = new Knight('N',j,i); move.find_moves(boardState,moves,Side);}                                       //if piece is knight
                    else if (boardState[i][j] == 'B'){Bishop move = new Bishop('B',j,i); move.find_moves(boardState,moves,Side);}                                       //if piece is bishop
                    else if (boardState[i][j] == 'Q'){Queen move = new Queen('Q',j,i); move.find_moves(boardState,moves,Side);}                                       //if piece is queen
                    else if (boardState[i][j] == 'K'){King move = new King('K',j,i); move.find_moves(boardState,moves,Side);}                                        //if piece is king
                }
            }
        }
        else {                                                                                            //if side is black
            for (int i = 0; i < length; ++i) {                                                            //iterate through board
                for (int j = 0; j < width; ++j) {
                    if (boardState[i][j] == '.'){continue;}                             //no need to scan types if no piece present
                    else if (boardState[i][j] == 'p'){Pawn move = new Pawn('p',j,i); move.find_moves(boardState,moves,Side);}                                        //if piece is pawn
                    else if (boardState[i][j] == 'r'){Rook move = new Rook('r',j,i); move.find_moves(boardState,moves,Side);}                                        //if piece is rook
                    else if (boardState[i][j] == 'n'){Knight move = new Knight('n',j,i); move.find_moves(boardState,moves,Side);}                                        //if piece is knight
                    else if (boardState[i][j] == 'b'){Bishop move = new Bishop('b',j,i); move.find_moves(boardState,moves,Side);}                                        //if piece is bishop
                    else if (boardState[i][j] == 'q'){Queen move = new Queen('q',j,i); move.find_moves(boardState,moves,Side);}                                        //if piece is queen
                    else if (boardState[i][j] == 'k'){King move = new King('k',j,i); move.find_moves(boardState,moves,Side);}                                      //if piece is king
                }
            }
        }
        return moves.size();                                                         //return how many moves there are in list, if none, then color on move looses
    }

    /*
        Scan the board to determine the opponent's king is gone
        return false if their king is found, true otherwise
     */
    private boolean opponentGone(char [][] boardState, char side)                    //iterate through board and check for win based on opponent elimination
    {
        if(side == 'W') {                                                            //if side white, check for black
            for (int i = 0; i < length; ++i) {
                for (int j = 0; j < width; ++j) {
                    if(boardState[i][j] == 'k')                                     //if black's king remains return false
                    {return false;}
                }
            }
        }
        else{                                                                        //if side black, check for white
            for (int i = 0; i < length; ++i) {
                for (int j = 0; j < width; ++j) {
                    if(boardState[i][j] == 'K')                                      //if white's king remain return false
                    {return false;}
                }
            }
        }
        return true;                                                                 //return true if no opponent found
    }

    /*
        is passed the current board state and the piece to be move
        updates the board according to the values in the piece and
        checks if this update wins the game for side on move
     */
    private boolean movePiece(char [][] boardState, Piece toMove, char Side)                  //make a move on board copy for next recursive iteration
    {
        boardState[toMove.getY()][toMove.getX()] = '.';                                         //set to location being moved from piece type to empty
        boardState[toMove.getNextY()][toMove.getNextX()] = toMove.getPieceType();               //label new location with piece type

        if(opponentGone(boardState,Side)){return true;}                                         //if opponent king gone
        return false;                                                                           //return false if no winning condition has been met
    }

    /*
        functions returns the highest value for two arguments
        that are passes in, is uesed for Negamax
     */
    private int max_of(int max_val, int val)    //return the highest value
    {
        if(max_val > val){return max_val;}
        return val;
    }

    /*
        display the current board's contents
     */
    public void display(int turn) {                                 //display function outputs the contents of board state passed to it
       System.out.println("Turn: "+turn);

        for (int i = 0; i < length; ++i) {
            for (int x = 0; x < width; ++x) {
                System.out.print(this.board[i][x]);
            }
            System.out.print("\n");
        }
        System.out.println('\n');
    }

    /*
        Used for state evaluation of board. Most basic
        Heuristic in which point values are assigned to each
        piece so the difference between sides can be taken and
        move that causes most harm to opponent can be chosen
     */
    private void findScore(char [][] temp)
    {
        this.whiteScore = this.blackScore =0;
        for (int i = 0; i < length; ++i) {              //iterate through board and update the value of
            for (int j = 0; j < width; ++j) {           //of black/white score based on their respective pieces on the board
                if (temp[i][j] == 'p') {
                    this.blackScore +=100;
                }else if (temp[i][j] == 'n') {
                    this.blackScore += 300;
                } else if (temp[i][j] == 'b') {
                    this.blackScore += 300;
                } else if (temp[i][j] == 'r') {
                    this.blackScore += 500;
                } else if (temp[i][j] == 'q') {
                    this.blackScore += 900;
                }else if(temp[i][j] == 'k'){
                    this.blackScore+=1000;
                } else if (temp[i][j] == 'P') {
                    this.whiteScore += 100;
                } else if (temp[i][j] == 'N') {
                    this.whiteScore += 300;
                } else if (temp[i][j] == 'B') {
                    this.whiteScore += 300;
                } else if (temp[i][j] == 'R') {
                    this.whiteScore += 500;
                } else if (temp[i][j] == 'Q') {
                    this.whiteScore += 900;
                }else if (temp[i][j] == 'K'){
                    this.whiteScore += 1000;
                }
            }
        }
    }

    /*
        Used instead of findScore - which is used for a basic state evaluator. With score(), same functionality,
        except rather than adusting the datamembers whiteScore and blackScore, temps representative of them
        at a depth of 6 in the move tree are evaluated and returned. This returned value is then compared
        with whiteScore and blackScore to determine the best move given the available moves.
    */
    private int score(char [][] boardState, char side)
    {
        int tempWhiteScore, tempBlackScore;
        tempWhiteScore = tempBlackScore=0;
        for (int i = 0; i < length; ++i) {              //iterate through board and update the value of
            for (int j = 0; j < width; ++j) {           //of black/white score based on their respective pieces on the board
                if (boardState[i][j] == 'p') {
                    tempBlackScore +=100;
                }else if (boardState[i][j] == 'n') {
                    tempBlackScore += 300;
                } else if (boardState[i][j] == 'b') {
                    tempBlackScore += 300;
                } else if (boardState[i][j] == 'r') {
                    tempBlackScore += 500;
                } else if (boardState[i][j] == 'q') {
                    tempBlackScore += 900;
                }else if (boardState[i][j] == 'k'){
                    tempBlackScore +=1000;
                } else if (boardState[i][j] == 'P') {
                    tempWhiteScore += 100;
                } else if (boardState[i][j] == 'N') {
                    tempWhiteScore += 300;
                } else if (boardState[i][j] == 'B') {
                    tempWhiteScore += 300;
                } else if (boardState[i][j] == 'R') {
                    tempWhiteScore += 500;
                } else if (boardState[i][j] == 'Q') {
                    tempWhiteScore += 900;
                }else if (boardState[i][j] == 'K'){
                    tempWhiteScore+=1000;
                }
            }
        }

        if (side == 'B') {return tempBlackScore - tempWhiteScore;}     //find score with respect with opponent based on side choose piece that gives oppoonent the lowest score
        else {return  tempWhiteScore - tempBlackScore;}
    }

    /*
       Parse input string, containg starting and ending position in board coordinates and identify piece at start coordinates
       Next, make and return piece with this starting and ending position
     */
    public Piece inputMove(String opponentMove)
    {
        int [] nextMove = new int[4];       //create an array of integers to hold the coordiates that game input row/collumn system will map to
        int tempStart=0;
        Piece opMove = null;                //create piece that will point to new piece being returned

        nextMove[0] = columns.indexOf(opponentMove.charAt(0));      //translate input to board coordinates
        tempStart = rows.indexOf(opponentMove.charAt(1));
        tempStart+=1;
        if(tempStart == 1){nextMove[1] = 5;}
        else if(tempStart ==2){nextMove[1] = 4;}
        else if(tempStart ==3){nextMove[1] = 3;}
        else if(tempStart ==4){nextMove[1] = 2;}
        else if(tempStart ==5){nextMove[1] = 1;}
        else if(tempStart ==6){nextMove[1] = 0;}

        nextMove[2] = columns.indexOf(opponentMove.charAt(3));
        tempStart = rows.indexOf(opponentMove.charAt(4));
        tempStart+=1;
        if(tempStart == 1){nextMove[3] = 5;}
        else if(tempStart ==2){nextMove[3] =4;}
        else if(tempStart ==3){nextMove[3] =3;}
        else if(tempStart ==4){nextMove[3] =2;}
        else if(tempStart ==5){nextMove[3] =1;}
        else if(tempStart ==6){nextMove[3] =0;}

        if (this.board[nextMove[1]][nextMove[0]] == 'p') {Pawn toMove = new Pawn('p', nextMove[0],nextMove[1], nextMove[2], nextMove[3]); opMove = toMove;}
        else if (this.board[nextMove[1]][nextMove[0]]== 'n') { Knight toMove = new Knight('n',nextMove[0],nextMove[1], nextMove[2], nextMove[3]);opMove = toMove; }
        else if (this.board[nextMove[1]][nextMove[0]] == 'b') {Bishop toMove = new Bishop('b',nextMove[0],nextMove[1], nextMove[2], nextMove[3]); opMove = toMove;}
        else if (this.board[nextMove[1]][nextMove[0]] == 'r') {Rook toMove = new Rook('r',nextMove[0],nextMove[1], nextMove[2], nextMove[3]); opMove = toMove;}
        else if (this.board[nextMove[1]][nextMove[0]]== 'q') {Queen toMove = new Queen('q',nextMove[0],nextMove[1], nextMove[2], nextMove[3]); opMove = toMove;}
        else if (this.board[nextMove[1]][nextMove[0]] == 'k'){King toMove = new King('k',nextMove[0],nextMove[1], nextMove[2], nextMove[3]); opMove = toMove;}
        else if (this.board[nextMove[1]][nextMove[0]] == 'P') {Pawn toMove = new Pawn('P',nextMove[0],nextMove[1], nextMove[2], nextMove[3]); opMove = toMove;}
        else if (this.board[nextMove[1]][nextMove[0]]== 'N') { Knight toMove = new Knight('N',nextMove[0],nextMove[1], nextMove[2], nextMove[3]);opMove = toMove; }
        else if (this.board[nextMove[1]][nextMove[0]] == 'B') {Bishop toMove = new Bishop('B',nextMove[0],nextMove[1], nextMove[2], nextMove[3]); opMove = toMove;}
        else if (this.board[nextMove[1]][nextMove[0]] == 'R') {Rook toMove = new Rook('R',nextMove[0],nextMove[1], nextMove[2], nextMove[3]); opMove = toMove;}
        else if (this.board[nextMove[1]][nextMove[0]] == 'Q') {Queen toMove = new Queen('Q',nextMove[0],nextMove[1], nextMove[2], nextMove[3]); opMove = toMove;}
        else if (this.board[nextMove[1]][nextMove[0]] == 'K'){King toMove = new King('K',nextMove[0],nextMove[1], nextMove[2], nextMove[3]); opMove = toMove;}

        return opMove;        //return updated piece
    }

    /*
        Format input/output string into a-e1-6 format for
        chess server
     */
    public String formatMove(int [] theMove) {
        char str1, str3;
        String str2, str4;
        str2 = str4 = "";
        str1 = str3 = 0;

        str1 = columns.charAt(theMove[0]);

        if (theMove[1] == 0) {
            str2 = "6";
        } else if (theMove[1] == 1) {
            str2 = "5";
        } else if (theMove[1] == 2) {
            str2 = "4";
        } else if (theMove[1] == 3) {
            str2 = "3";
        } else if (theMove[1] == 4) {
            str2 = "2";
        } else if (theMove[1] == 5) {
            str2 = "1";
        }

        str3 = columns.charAt(theMove[2]);

        if (theMove[3] == 0) {
            str4 = "6";
        } else if (theMove[3] == 1) {
            str4 = "5";
        } else if (theMove[3] == 2) {
            str4 = "4";
        } else if (theMove[3] == 3) {
            str4 = "3";
        } else if (theMove[3] == 4) {
            str4 = "2";
        } else if (theMove[3] == 5) {
            str4 = "1";
        }

        String pt1 = Character.toString(str1);
        String pt3 = Character.toString(str3);


        String outputMove = pt1 + "" + str2;
        outputMove = outputMove + "-";
        outputMove = outputMove + pt3;
        outputMove = outputMove + str4;

        return outputMove;
    }

    /*
        Negamax function does a depth first search (to depth 6) evaluating the value of the position
        at that depth (or if king is eliminated before) and returning it. The allows the move generator to
        return the best possible move up to three ply's ahead from current state
     */
	private int negaMax(char [][] board,char side, int depth, int alpha, int beta)
	{
	    char storage =0;
	    if(depth <=0 || opponentGone(board,side))
		{ return score(board,side); }

        List<Piece> moves = new ArrayList<Piece>();             //create list of possible moves
        if (legalMoves(board, moves, side) == 0) {return -10000;}   //find legal moves for side, return negative -10000, which is loss
        Collections.shuffle(moves);                                 //shuffle the potential moves
        int max_val = -10000000;                                    //set max to negative infinity
        for (int i = 0; i < moves.size(); ++i)                  //iterate through every legal move for side
        {
            storage = board[moves.get(i).getNextY()][moves.get(i).getNextX()];                            //store piece in place being take
            this.board[moves.get(i).getNextY()][moves.get(i).getNextX()] = moves.get(i).getPieceType();     //move piece to that place on board
            this.board[moves.get(i).getY()][moves.get(i).getX()] = '.';

            if(side =='W'){side = 'B';}else {side = 'W';}//switch sides
            int val =  -negaMax(board, side, depth-1,-beta,-alpha);                                           //recurse and return opposite value for opposite side
            max_val = max_of(max_val, val);                                                                 //find maximum between returned values and max_val
            if(side =='W'){side = 'B';}else {side = 'W';}//switch sides on return

            this.board[moves.get(i).getNextY()][moves.get(i).getNextX()] = storage;                        //reset
            this.board[moves.get(i).getY()][moves.get(i).getX()] = moves.get(i).getPieceType();
            alpha = max_of(alpha,val);                                                                     //set alpha to highest val

            if ( alpha >= beta){break;}                                                                    //if worst better than best, break
        }
        moves =null;                                                                                        //garbage collection because of heavy memory usage
        System.gc();
        return max_val;                                                                                    //return max_val
	}
    /*
        Performs heuristic where of list of pieces, optimal move is determined.
        List iterated and value of the board at each state determined. The move that takes
        most points away from opponent is chosen though call to negamax with iterative deepening
     */
    private Piece stateEvaluator(char [][] boardState, List <Piece> moves, char side) {
        Piece toChoose;
        char storage=0;
        double start,duration,tick;
        double convert = 1000000000.0;
        int tempMax,alphaBeta,id,d;
        id = tempMax =100000000;        //initialize to current score of board state
        toChoose = moves.get(0);    // in case no better choice is available, prevents returning NULL
        Collections.shuffle(moves);

        double numMove = moves.size();
        double maxTime = 1.5/numMove;

        for (int i = 0; i < moves.size(); ++i)
        {
            start = duration = tick =0.0;
            storage = board[moves.get(i).getNextY()][moves.get(i).getNextX()];                            //store piece in place being take
            this.board[moves.get(i).getNextY()][moves.get(i).getNextX()] = moves.get(i).getPieceType();     //move piece to that place on board
            this.board[moves.get(i).getY()][moves.get(i).getX()] = '.';                                     //set position being moved from to '.'
            if(side =='W'){side = 'B';}else {side = 'W';}
            start = System.nanoTime();                                                                      //start time for iterative deepening
            alphaBeta = negaMax(board,side,2, -10000000,10000000);                                          //first call to negaMax so something exists to return

            d =3;                                                                                           //subsequent depths to traverse t

            while (d <=40)                                                                                  //traverse until max moves have been done
            {
                if(((System.nanoTime() - start)/convert)  >= maxTime){break;};                               //time out
                alphaBeta = negaMax(board,side,d, -10000000,10000000);                                       //call alpahbeta to as deep a depth as possible
                ++d;                                                                                         //increment depth
            }

            if(side =='W'){side = 'B';}else {side = 'W';}                                                   //switch side back
            if(tempMax > alphaBeta)                                                                         //if current move creates higher score for opponent than next move
            {
                tempMax = alphaBeta;                                                                        //choose next move
                toChoose = moves.get(i);
            }

            this.board[moves.get(i).getNextY()][moves.get(i).getNextX()] = storage;                 //reset board
            this.board[moves.get(i).getY()][moves.get(i).getX()] = moves.get(i).getPieceType();
        }

        return toChoose;                                                                           //return choice
    }

    /*
        declare list of pieces, pass that list to legal moves which will evaluate
         all possible moves for a piece given a position and add them to the board
         returns the piece that is chosen from the list by the state evaluator
     */
    private Piece moveGenerate(char [][] boardState, char side)
    {
        List<Piece> moves = new ArrayList<Piece>();             //declare list of all possible moves
        if (legalMoves(board, moves, side) == 0) { return null;}   //find legal moves for side, return loss if there are none
        Piece chosenOne = stateEvaluator(boardState,moves,side);
        moves = null;
        System.gc();
        return chosenOne;
    }

    /*
        takes string input from oponent on server and parses
        input to coordinates via inputMove. Board is then updated
        with piece returned by inputMove
     */
    public int moveOpponent(String aMove){
        Piece strToMove = inputMove(aMove);     //get piece at starting coordinates and store move in it
        if(strToMove == null)                   //sanity check of move
        {return 2;}
        //System.out.println(strToMove.getX()+" "+strToMove.getY()+" "+strToMove.getNextX()+" "+strToMove.getNextY());
        this.board[strToMove.getY()][strToMove.getX()] = '.';                               //set to location being moved from piece type to empty
        this.board[strToMove.getNextY()][strToMove.getNextX()] = strToMove.getPieceType(); //label new location with piece type
        if(opponentGone(this.board,this.startColor)){return 1;}                             //return win if opponent king gone
        return 0;
    }

    /*
           Native AI move function. move generator returns the optimal move to nextMove
           board is then updated based on this returned move
     */
    public String moveAI(char side){
        Piece nextMove;
        String theMove;
        int [] positions = new int[4];
        nextMove = moveGenerate(board,side);                //get piece at starting coordinates and store move in it
        if(nextMove == null){return null;}                     //if not legal moves

        positions[0]= nextMove.getX();                      //to be passed to formatMove so that it can be converted to string - string format for server
        positions[1]= nextMove.getY();
        positions[2]= nextMove.getNextX();
        positions[3]= nextMove.getNextY();
        theMove = formatMove(positions);
        this.board[nextMove.getY()][nextMove.getX()] = '.'; //set to location being moved from piece type to empty
        this.board[nextMove.getNextY()][nextMove.getNextX()] = nextMove.getPieceType(); //label new location with piece type

        return theMove;
    }
    /*
        Scan the board from main to check if
        if a side won, and to ouput which side that may be.
        Triggers end of communication with chess server
     */
    public boolean checkEnd(){
        boolean k,K;
        k=K=false;

        for(int i=0; i<6; ++i){
            for(int j=0; j<5; ++j){
                if(board[i][j]=='K'){K = true;}
                else if(board[i][j] == 'k'){k =true;}
            }
        }
        if(k && K){return true;}
        if(k && !K){System.out.println("B won"); return false;}
        System.out.println("W won");
        return false;
    }
}
