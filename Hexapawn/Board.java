/**
 * Rudd Johnson
 * 4/21/17
 */
import java.util.ArrayList;
import java.util.List;
public class Board {                        //class manages the depth first search of game tree for given input state
    private char startColor;                //hold the starting side
    private Piece[][] board;                //store input board
    private int length;                     //store board dimensions
    private int width;

    public Board(String[] boardState, int len, int wid, char start) {       //constructor initializes private data members
        this.startColor = start;
        this.length = len;
        this.width = wid;
        this.board = new Piece[length][width];
        build(this.board, boardState);                                      //function constructs object board and coppies user input strings into it
    }

    public void build(Piece[][] board, String[] boardState) {

        for (int i = 0; i < length; ++i)                                    //iterate through object board, initialize each piece and copy string character to appropriate location
            for (int j = 0; j < width; ++j) {
                this.board[i][j] = new Piece(boardState[i].charAt(j));
            }
    }

    private void copyBoard(Piece [][] sourceBoard, Piece [][] destBoard)    //copy one object board to another, which is the state of is then changed for each recursive move
    {
        for (int i = 0; i < length; ++i)
            for (int j = 0; j < width; ++j) {
                destBoard[i][j] = new Piece(sourceBoard[i][j].getPieceType());
            }
    }


    private int legalMoves(Piece [][] boardState, List <Pawns> moves, char Side) {      //find legal moves for side that is passed in in a given state, append the move to moves list
        int forward,left,right;
        forward = left = right =0;
        if (Side == 'W') {                                                              //if side is White
            for (int i = 0; i < length; ++i){                                           //iterate through board
                for (int j = 0; j < width; ++j) {
                    if (boardState[i][j].getPieceType() == 'P')                         //if piece is white
                    {
                        forward = i -1;                                                 //find forward, left, right with respect to side orientation
                        left = j-1;
                        right = j+1;

                        if (boardState[forward][j].getPieceType() == '.')                       //move forward space occupied by '.'
                        {

                            Pawns aMove = new Pawns('P',boardState[forward][j],j,i, j,forward);
                            moves.add(aMove);                                                   //add move to move list
                        }

                        if (right < width-1 && boardState[forward][right].getPieceType() == 'p')        //attack right (North East) if space occupied by black piece and in bounds
                        {
                            Pawns aMove = new Pawns('P',boardState[forward][right],j,i,right,forward);
                            moves.add(aMove);                                                           //add move to move list
                        }

                        if (left > 0 && boardState[forward][left].getPieceType() == 'p')                //attack left (North West) if space occupied by black piece and in bounds
                        {
                            Pawns aMove = new Pawns('P',boardState[forward][left],j,i,left,forward);
                            moves.add(aMove);                                                           //add move to move list
                        }

                    }
                }
            }
        }

        else {                                                                                            //if side is black
            for (int i = 0; i < length; ++i) {                                                            //iterate through board
                for (int j = 0; j < width; ++j) {
                    if (boardState[i][j].getPieceType() == 'p') //if piece is black
                    {
                        forward = i+1;
                        left = j-1;
                        right = j+1;

                        if (boardState[forward][j].getPieceType() == '.')    //move forward if space occupied by '.'
                        {
                            Pawns aMove = new Pawns('p',boardState[forward][j],j,i,j,forward);
                            moves.add(aMove);                                   //add move to move list
                        }

                        if (right < width -1 && boardState[forward][right].getPieceType() == 'P') //attack right (South East) if space occupied by White piece and in bounds
                        {
                            Pawns aMove = new Pawns('p',boardState[forward][right],j,i,right,forward);
                            moves.add(aMove);                                                     //add move to move list
                        }

                        if (left > 0 && boardState[forward][left].getPieceType() == 'P') //attack left (South West) if space occupied by White piece and in bounds
                        {
                            Pawns aMove = new Pawns('p',boardState[forward][left],j,i,left,forward);
                            moves.add(aMove);                                                       //add move to move list
                        }
                    }
                }
            }

        }

        return moves.size();    //return how many moves there are in list, if none, then color on move looses
    }

    private boolean opponentGone(Piece [][] boardState, char side)          //iterate through board and check for win based on opponent elimination
    {

        if(side == 'W') {                                                   //if side white, check for black
            for (int i = 0; i < length; ++i) {
                for (int j = 0; j < width; ++j) {
                    if(boardState[i][j].getPieceType() == 'p')  //if any black pawns remain
                    {
                        return false;
                    }
                }
            }
        }

        else{                                                              //if side black, check for white
            for (int i = 0; i < length; ++i) {
                for (int j = 0; j < width; ++j) {
                    if(boardState[i][j].getPieceType() == 'P')  //if any white pawns remain
                    {
                        return false;
                    }
                }
            }
        }

        return true;                               //return true if no opponent found
    }

    private boolean reachedEnd(Pawns toMove)      //check to see if pawn from piece in play has reached the other side of the board
    {
        if(toMove.getPieceType() == 'P')         //if white reaches end return true
        {
            if(toMove.getNextY() == 0) {
                return true;}
        }

        else{                               //if black reaches end return true
            if(toMove.getNextY() == length -1){ return true;}
        }

        return false;                       //if neither reach the end return false
    }

    private boolean movePiece(Piece [][] boardState, Pawns toMove)                  //make a move on board copy for next recursive iteration
    {
        boardState[toMove.getY()][toMove.getX()].setPieceType('.'); //set to location being moved from piece type to empty
        boardState[toMove.getNextY()][toMove.getNextX()].setPieceType(toMove.getPieceType()); //label new location with piece type

        if(opponentGone(boardState,toMove.getPieceType())) {
            return true; //if opponent is gone
        }

        if(reachedEnd(toMove)) {
            return true;   //of move is at the end of the board
        }
        return false;       //return false if no winning condition has been met
    }

    private int max_of(int max_val, int val)    //return the highest value
    {
        if(max_val > val){return max_val;}
        return val;
    }

    public void display(Piece [][] board) {     //display function outputs the contents of board state passed to it
        for (int i = 0; i < length; ++i) {
            for (int x = 0; x < width; ++x) {
                System.out.print(board[i][x].getPieceType());
            }
            System.out.print("\n");
        }
    }

    private int win_search(Piece [][] board, char side) {       //recursive function that that performs DFS of game tree to determine if win is possible for given side
        List<Pawns> moves = new ArrayList<Pawns>();             //declare list

        if (legalMoves(board, moves, side) == 0) {return -1;}   //find legal moves for side, return loss if there are none
        int max_val = -1;
        for (int i = 0; i < moves.size(); ++i)                  //iterate through every legal move for side
        {
            Piece[][] nextState = new Piece[length][width];    //copy board to pass to move function, will be used for future state
            copyBoard(board, nextState);
            if (movePiece(nextState, moves.get(i))) {           //if move results in a win through reach opposite side of eliminating opponent, return 1
                return 1;
            }                                                    //move piece to new position and check for win

            else                                                           //if not a win
            {
                if (side == 'W') {
                    side = 'B';
                }                                                          //switch sides and recurse
                else {
                    side = 'W';
                }

                int val =  -win_search(nextState, side);                   //recurse and return opposite value for opposite side

                if (side == 'W') {
                    side = 'B';
                }                                                          //switch sides on return for further iterations that may occur
                else {
                    side = 'W';
                }

                max_val = max_of(max_val, val);                            //find maximum between returned values and max_val
            }
        }
        return max_val;                                                    //return maximum
    }

    public void solve(){                                                   //called by main, runs and returns result from win_search
        int value = win_search(board,startColor);
        System.out.println("The value of the position is: "+ value);
    }
}
