/**
 * Rudd Johnson
 * CS 442
 * Final Chess Player
 * 6/1/17
 **/
import java.util.List;
/*
    This class contains the move information regarding move direction for Bishop
    As well as start and ending coordiates
 */
public class Bishop extends Piece {
    public Bishop(char pieceTye, int x, int y) {super(pieceTye,x,y);}
    public Bishop(char pieceType, int x, int y, int nextX, int nextY) {super(pieceType, x, y, nextX, nextY);}

    /*
        Find every legal move along a path for those pieces that can move multiple spaces at once (queen, rook, bisho)
        Add them to list of moves until they reach an opponent or piece on their own side
     */
    public void find_path(char [][] boardState, String directions, List<Piece> moves, int x, int y, int newX, int newY,char Side)
    {
        if(directions.contentEquals("NE"))                                      //if moving north east
        {
            for(newY = y-1,newX = x+1;newY >=0 && newX <5;--newY,++newX)
            {
                    if(notOccupied(boardState[newY][newX],Side) == false)       //move until reaching piece on own side
                    {return;}

                    if(boardState[newY][newX] != '.'){                           //move until reaching opponent
                        Bishop aMove = new Bishop(pieceType,x,y,newX,newY);
                        moves.add(aMove);
                        return;
                    }
                    Bishop aMove = new Bishop(pieceType,x,y,newX,newY);         //add pieces along the way
                    moves.add(aMove);
            }
        }
        else if(directions.contentEquals("NW"))                                 //if moving north west
        {
            for(newY = y-1,newX = x-1; newY >=0 && newX>=0;--newY,--newX )
            {
                    if(notOccupied(boardState[newY][newX],Side) == false)       //move until reaching piece on own side
                    {return;}

                    if(boardState[newY][newX] != '.'){
                        Bishop aMove = new Bishop(pieceType,x,y,newX,newY);     //move until reaching opponent
                        moves.add(aMove);
                        return;
                    }
                    Bishop aMove = new Bishop(pieceType,x,y,newX,newY);         //add pieces along the way
                    moves.add(aMove);
            }
        }
        else if(directions.contentEquals("SE"))                                 //if moving south east
        {
            for(newY = y+1,newX = x+1; newY <6 && newX < 5;++newY,++newX)
            {
                    if(notOccupied(boardState[newY][newX],Side) == false)      //move until reaching piece on own side
                    {return;}

                    if(boardState[newY][newX] != '.'){
                        Bishop aMove = new Bishop(pieceType,x,y,newX,newY);      //move until reaching opponent
                        moves.add(aMove);
                        return;
                    }
                    Bishop aMove = new Bishop(pieceType,x,y,newX,newY);         //add pieces along the way
                    moves.add(aMove);
            }
        }
        else if(directions.contentEquals("SW"))                                 //if moving south west
        {
            for(newY = y+1, newX = x-1; newY <6 && newX >=0;++newY,--newX)
            {
                    if(notOccupied(boardState[newY][newX],Side) == false)        //move until reaching piece on own side
                    {return;}

                    if(boardState[newY][newX] != '.'){
                        Bishop aMove = new Bishop(pieceType,x,y,newX,newY);    //move until reaching opponent
                        moves.add(aMove);
                        return;
                    }
                    Bishop aMove = new Bishop(pieceType,x,y,newX,newY);         //add pieces along the way
                    moves.add(aMove);
            }
        }
    }
    //find moves for bishop and add them to the list
    void find_moves(char [][] boardState, List<Piece> moves, char Side){
        if (y > 0 && boardState[y-1][x] =='.')                              //can move N
        {
            Bishop aMove = new Bishop(pieceType,x,y, x,y-1);
            moves.add(aMove);
        }
        if (y > 0 && x < 4 && notOccupied(boardState[y-1][x+1],Side))       //can move NE
        {find_path(boardState, "NE",moves,x,y,nextX,nextY,Side);}

        if (y > 0 && x > 0 && notOccupied(boardState[y-1][x-1], Side))      //can move NW
        {find_path(boardState, "NW",moves,x,y,nextX,nextY,Side);}

        if(x < 4 && boardState[y][x+1] =='.' )                               //can move east
        {
            Bishop aMove = new Bishop(pieceType,x,y,x+1,y);
            moves.add(aMove);
        }
        if(x > 0 && boardState[y][x-1] =='.')                                //can move west
        {
            Bishop aMove = new Bishop(pieceType,x,y,x-1,y);
            moves.add(aMove);
        }
        if(y < 5 && boardState[y+1][x] =='.')                                //can move south
        {
            Bishop aMove = new Bishop(pieceType,x,y,x,y+1);
            moves.add(aMove);
        }
        if(y<5 && x <4 && notOccupied(boardState[y+1][x+1],Side))            //can move SE
        {find_path(boardState, "SE",moves,x,y,nextX,nextY,Side);}

        if(y <5 && x >0 && notOccupied(boardState[y+1][x-1],Side))           //can move SW
        {find_path(boardState, "SW",moves,x,y,nextX,nextY,Side);}
    }
}
