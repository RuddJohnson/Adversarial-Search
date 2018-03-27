/**
 * Rudd Johnson
 * CS 442
 * Final Chess Player
 * 6/1/17
 **/
import java.util.List;
/*
    This class contains the move information regarding move direction for rook
    As well as start and ending coordiates
 */
public class Rook extends Piece{
    public Rook(char pieceTye, int x, int y) {super(pieceTye,x,y);}
    public Rook(char pieceType, int x, int y, int nextX, int nextY) {super(pieceType, x, y, nextX, nextY);}

    /*
        Find every legal move along a path for those pieces that can move multiple spaces at once (queen, rook, bisho)
        Add them to list of moves until they reach an opponent or piece on their own side
     */
    public void find_path(char [][] boardState, String directions,List<Piece> moves, int x, int y, int newX, int newY,char Side)
    {
        if(directions.contentEquals("N"))                               //travelling north
        {
            newX =x;
            for(newY=y-1; newY >=0;--newY)                              //increment y forward until end board
            {
                if(notOccupied(boardState[newY][x],Side) == false)      //make sure not running into any of own pieces
                {return;}

                if(boardState[newY][x] != '.') {
                    Rook aMove = new Rook(pieceType,x,y,newX,newY);        //add move to list
                    moves.add(aMove);
                    return;                                               //if not a piece from own side or empty space, must be opponent
                }
                Rook aMove = new Rook(pieceType,x,y,newX,newY);           //add legal moves to list along the way
                moves.add(aMove);
            }
        }

        else if(directions.contentEquals("E"))                              //Travelling East
        {
            newY = y;
            for(newX=x+1; newX <5; ++newX)                                  //travel until running into own side
            {
                if(notOccupied(boardState[newY][newX],Side) == false)
                {return;}

                if(boardState[newY][newX] != '.'){                         //travel until runing into opponent
                    Rook aMove = new Rook(pieceType,x,y,newX,newY);
                    moves.add(aMove);
                    return;
                }
                Rook aMove = new Rook(pieceType,x,y,newX,newY);            //add legal moves to list along the way
                moves.add(aMove);
            }
        }
        else if(directions.contentEquals("W"))                             //travelling west
        {
            newY =y;
            for(newX=x-1; newX >=0; --newX)
            {
                if(notOccupied(boardState[newY][newX],Side) == false)      //move until running into piece from own side
                {return;}

                if(boardState[newY][newX] != '.'){                          //move until running into opponent
                    Rook aMove = new Rook(pieceType,x,y,newX,newY);
                    moves.add(aMove);
                    return;
                }
                Rook aMove = new Rook(pieceType,x,y,newX,newY);             //add moves to list along the path
                moves.add(aMove);
            }
        }
        else if(directions.contentEquals("S"))                              //moving south
        {
            newX =x;
            for(newY=y+1; newY < 6; ++newY)
            {
                if(notOccupied(boardState[newY][newX],Side) == false)       //move until running into piece from own side
                {return;}

                if(boardState[newY][newX] != '.'){                          //move until running into opponent
                    Rook aMove = new Rook(pieceType,x,y,newX,newY);
                    moves.add(aMove);
                    return;
                }
                Rook aMove = new Rook(pieceType,x,y,newX,newY);             //add moves along the path to list
                moves.add(aMove);
            }
        }
    }

    /*
       Find all moves that a Rook in a given position on the board can
       Move to and add them to the list Pieces which will be used by
       Move generator
    */
    void find_moves(char [][] boardState, List<Piece> moves, char Side){
        if (y > 0 && notOccupied(boardState[y-1][x],Side))              //can move N
        {find_path(boardState,"N",moves,x,y,nextX,nextY,Side);}

        if(x < 4 && notOccupied(boardState[y][x+1], Side))              //can move west
        {find_path(boardState, "E",moves,x,y,nextX,nextY,Side);}

        if(x > 0 && notOccupied(boardState[y][x-1], Side))              //can move east
        {find_path(boardState, "W",moves,x,y,nextX,nextY,Side);}

        if(y < 5 && notOccupied(boardState[y+1][x],Side))               //can move south
        {find_path(boardState,"S",moves,x,y,nextX,nextY,Side);}
    }
}

