/**
 * Rudd Johnson
 * CS 442
 * Final Chess Player
 * 6/1/17
 **/
import java.util.List;
/*
    This class contains the move information regarding move direction for King
    As well as start and ending coordiates
 */
public class King extends Piece {
    public King(char pieceTye, int x, int y) {super(pieceTye,x,y);}
    public King(char pieceType, int x, int y, int nextX, int nextY) {super(pieceType, x, y, nextX, nextY);}

    //find available moves for kind and add them to list of moves
    void find_moves(char [][] boardState, List<Piece> moves, char Side){

        if (y > 0 && notOccupied(boardState[y-1][x],Side))                   //can move N
        {
            King aMove = new King(pieceType,x,y, x,y-1);
            moves.add(aMove);                                               //add move to move list
        }

        if (y > 0 && x < 4 && notOccupied(boardState[y-1][x+1],Side))       //can move NE
        {
            King aMove = new King(pieceType,x,y,x+1,y-1);
            moves.add(aMove);                                              //add move to move list
        }
        if (y >0 && x > 0 && notOccupied(boardState[y-1][x-1], Side))      //can move NW
        {
            King aMove = new King(pieceType,x,y,x-1,y-1);
            moves.add(aMove);                                              //add move to move list
        }

        if(x<4 && notOccupied(boardState[y][x+1],Side))                     //can move east
        {
            King aMove = new King(pieceType,x,y,x+1,y);                     //add to list
            moves.add(aMove);
        }

        if(x>0 &&notOccupied(boardState[y][x-1],Side))                      //can move west
        {
            King aMove = new King(pieceType,x,y,x-1,y);                     //add to list
            moves.add(aMove);
        }

        if(y < 5 && notOccupied(boardState[y+1][x],Side))                   //can move south
        {
            King aMove = new King(pieceType,x,y,x,y+1);                    //add move to list
            moves.add(aMove);
        }

        if(y<5 && x <4 && notOccupied(boardState[y+1][x+1],Side))           //can move SE
        {
            King aMove = new King(pieceType,x,y,x+1,y+1);                   //add move to list
            moves.add(aMove);
        }
        if(y<5 && x >0 && notOccupied(boardState[y+1][x-1],Side))           //can move SW
        {
            King aMove = new King(pieceType,x,y,x-1,y+1);                   //add move to list
            moves.add(aMove);
        }
    }
}
