/**
 * Rudd Johnson
 * CS 442
 * Final Chess Player
 * 6/1/17
 **/
import java.util.List;
/*
    This class contains the move information regarding move direction for Queen
    As well as start and ending coordiates
 */
public class Queen extends Piece{
    public Queen(char pieceTye, int x, int y) {super(pieceTye,x,y);}
    public Queen(char pieceType, int x, int y, int nextX, int nextY) {super(pieceType, x, y, nextX, nextY);}

    /*
        Find every legal move along a path for those pieces that can move multiple spaces at once (queen, rook, bisho)
        Add them to list of moves until they reach an opponent or piece on their own side
     */
    public void find_path(char [][] boardState, String directions, List<Piece> moves, int x, int y, int newX, int newY,char Side)
    {

        if(directions.contentEquals("N"))                           //if moving north
        {
            newX =x;
            for(newY=y-1; newY>=0;--newY)                              //increment y forward until end board
            {
                if(notOccupied(boardState[newY][x],Side) == false)  //make sure not running into any of own pieces
                {return;}                                    //decrement value so not overlapping own piece

                if(boardState[newY][x] != '.') {
                    Queen aMove = new Queen(pieceType,x,y,newX,newY);                   //add move to list
                    moves.add(aMove);
                    return;             //if not a piece from own side or empty space, must be opponent
                }
                Queen aMove = new Queen(pieceType,x,y,newX,newY);
                moves.add(aMove);
            }
        }
        else if(directions.contentEquals("NE"))                                 //if moving north east
        {
            for(newY = y-1,newX = x+1; newY>=0 & newX <5;--newY, ++newX)
            {
                    if(notOccupied(boardState[newY][newX],Side) == false)       //move until reaching piece on own side
                    {return;}

                    if(boardState[newY][newX] != '.'){                          //move until reaching opponent
                        Queen aMove = new Queen(pieceType,x,y,newX,newY);
                        moves.add(aMove);
                        return;
                    }
                    Queen aMove = new Queen(pieceType,x,y,newX,newY);           //add pieces along the way
                    moves.add(aMove);
            }
        }
        else if(directions.contentEquals("NW"))                                 //if moving north west
        {
            for(newY = y-1,newX = x-1; newY>=0 && newX>=0;--newY,--newX)
            {
                    if(notOccupied(boardState[newY][newX],Side) == false)        //move until reaching piece on own side
                    {return;}

                    if(boardState[newY][newX] != '.'){                          //move until reaching opponent
                        Queen aMove = new Queen(pieceType,x,y,newX,newY);
                        moves.add(aMove);
                        return;
                    }
                    Queen aMove = new Queen(pieceType,x,y,newX,newY);            //add pieces along the way
                    moves.add(aMove);
            }
        }
        else if(directions.contentEquals("E"))                                  //if moving east
        {
            newY = y;
            for(newX=x+1; newX <5; ++newX)
            {
                if(notOccupied(boardState[newY][newX],Side) == false)            //move until reaching piece on own side
                {return;}

                if(boardState[newY][newX] != '.'){                                //move until reaching opponent
                    Queen aMove = new Queen(pieceType,x,y,newX,newY);
                    moves.add(aMove);
                    return;
                }
                Queen aMove = new Queen(pieceType,x,y,newX,newY);               //add pieces along the way
                moves.add(aMove);
            }
        }
        else if(directions.contentEquals("W"))                                   //if moving west
        {
            newY =y;
            for(newX=x-1; newX >=0; --newX)
            {
                if(notOccupied(boardState[newY][newX],Side) == false)            //move until reaching piece on own side
                {return;}

                if(boardState[newY][newX] != '.'){                               //move until reaching opponent
                    Queen aMove = new Queen(pieceType,x,y,newX,newY);
                    moves.add(aMove);
                    return;
                }
                Queen aMove = new Queen(pieceType,x,y,newX,newY);                 //add pieces along the way
                moves.add(aMove);
            }
        }
        else if(directions.contentEquals("S"))                                  //if moving south
        {
            newX =x;
            for(newY=y+1; newY < 6; ++newY)
            {
                if(notOccupied(boardState[newY][newX],Side) == false)            //move until reaching piece on own side
                {return;}

                if(boardState[newY][newX] != '.'){                              //move until reaching opponent
                    Queen aMove = new Queen(pieceType,x,y,newX,newY);
                    moves.add(aMove);
                    return;
                }
                Queen aMove = new Queen(pieceType,x,y,newX,newY);                //add pieces along the way
                moves.add(aMove);
            }
        }
        else if(directions.contentEquals("SE"))                                 //if moving south east
        {
            for(newY = y+1, newX = x+1; newY <6 && newX < 5;++newY,++newX)
            {
                    if(notOccupied(boardState[newY][newX],Side) == false)        //move until reaching piece on own side
                    {return;}

                    if(boardState[newY][newX] != '.'){                          //move until reaching opponent
                        Queen aMove = new Queen(pieceType,x,y,newX,newY);
                        moves.add(aMove);
                        return;
                    }
                    Queen aMove = new Queen(pieceType,x,y,newX,newY);           //add pieces along the way
                    moves.add(aMove);
            }
        }
        else if(directions.contentEquals("SW"))                                 //if moving south west
        {
            for(newY = y+1, newX = x-1; newY <6 &&  newX >=0;++newY,--newX)
            {
                    if(notOccupied(boardState[newY][newX],Side) == false)        //move until reaching piece on own side
                    {return;}

                    if(boardState[newY][newX] != '.'){                             //move until reaching opponent
                        Queen aMove = new Queen(pieceType,x,y,newX,newY);
                        moves.add(aMove);
                        return;
                    }
                    Queen aMove = new Queen(pieceType,x,y,newX,newY);             //add pieces along the way
                    moves.add(aMove);
            }
        }
    }
    //find moves for queen and add them to the list
    void find_moves(char [][] boardState, List<Piece> moves, char Side){
        if (y > 0 && notOccupied(boardState[y-1][x],Side))    //can move N
        {find_path(boardState,"N",moves,x,y,nextX,nextY,Side);}

        if (y > 0 && x < 4 && notOccupied(boardState[y-1][x+1],Side))       //can move NE
        {find_path(boardState, "NE",moves,x,y,nextX,nextY,Side);}

        if (y > 0 && x > 0 && notOccupied(boardState[y-1][x-1], Side))      //can move NW
        {find_path(boardState, "NW",moves,x,y,nextX,nextY,Side);}

        if(x < 4 && notOccupied(boardState[y][x+1], Side))                   //can move west
        {find_path(boardState, "E",moves,x,y,nextX,nextY,Side);}

        if(x > 0 && notOccupied(boardState[y][x-1], Side))                   //can move east
        {find_path(boardState, "W",moves,x,y,nextX,nextY,Side);}

        if(y < 5 && notOccupied(boardState[y+1][x],Side))                    //can move south
        {find_path(boardState,"S",moves,x,y,nextX,nextY,Side);}

        if(y <5 && x <4 && notOccupied(boardState[y+1][x+1],Side))           //can move SE
        {find_path(boardState, "SE",moves,x,y,nextX,nextY,Side);}

        if(y<5 && x >0 && notOccupied(boardState[y+1][x-1],Side))            //can move SW
        {find_path(boardState, "SW",moves,x,y,nextX,nextY,Side);}
    }
}

