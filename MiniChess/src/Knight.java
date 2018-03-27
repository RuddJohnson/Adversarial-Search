/**
 * Rudd Johnson
 * CS 442
 * Final Chess Player
 * 6/1/17
 **/
import java.util.List;
/*
    This class contains the move information regarding move direction for Knight
    As well as start and ending coordiates
 */
public class Knight extends Piece{
    public Knight(char pieceTye, int x, int y) {super(pieceTye,x,y);}               //constructors
    public Knight(char pieceType, int x, int y, int nextX, int nextY) {super(pieceType, x, y, nextX, nextY);}

    /*
        Find all moves that a Knight in a given position on the board can
        Move to and add them to the list Pieces which will be used by
        Move generator
     */
    void find_moves(char [][] boardState, List<Piece> moves, char Side ){
        int newX,newY;
        newX = newY=0;

        if (y > 1 && x <4 && notOccupied(boardState[y-2][x+1],Side))    //can move north 2, east 1
        {
            newX = x+1; newY = y-2;
            Knight aMove = new Knight(pieceType,x,y,newX,newY);
            moves.add(aMove);
        }


        if (y > 1 && x > 0 && notOccupied(boardState[y-2][x-1],Side))    //can move north 2 west 1
        {
            newX = x - 1;
            newY = y - 2;
            Knight aMove = new Knight(pieceType, x, y, newX, newY);
            moves.add(aMove);
        }

        if (y >0 && x < 3 && notOccupied(boardState[y-1][x+2], Side))     //can move east 2 and north 1
        {
            newX = x+2; newY = y-1;
            Knight aMove = new Knight(pieceType,x,y,newX,newY);
            moves.add(aMove);
        }

        if(y< 5 && x < 3 && notOccupied(boardState[y+1][x+2],Side))        //can move east 2 and south 1
        {
            newX = x+2; newY = y+1;
            Knight aMove = new Knight(pieceType,x,y,newX,newY);
            moves.add(aMove);
        }

        if (y >0 && x > 1 && notOccupied(boardState[y-1][x-2], Side))      //can move west 2 and north 1
        {
            newX = x-2; newY = y-1;
            Knight aMove = new Knight(pieceType,x,y,newX,newY);
            moves.add(aMove);
        }

        if(y < 5 && x >1 && notOccupied(boardState[y+1][x-2],Side))        //can move west 2 and south 1
        {
            newX = x-2; newY = y+1;
            Knight aMove = new Knight(pieceType,x,y,newX,newY);
            moves.add(aMove);
        }

        if(y < 4 && x <4 && notOccupied(boardState[y+2][x+1],Side))        //can move south 2 and east 1
        {
            newX = x+1; newY = y+2;
            Knight aMove = new Knight(pieceType,x,y,newX,newY);
            moves.add(aMove);
        }

        if(y < 4 && x >0 && notOccupied(boardState[y+2][x-1],Side))        //can move South 2 and west 1
        {
            newX = x-1; newY = y+2;
            Knight aMove = new Knight(pieceType,x,y,newX,newY);
            moves.add(aMove);
        }
    }
}
