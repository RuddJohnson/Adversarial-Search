/**
 * Rudd Johnson
 * CS 442
 * Final Chess Player
 * 6/1/17
 **/
import java.util.List;
/*
    This class contains the move information regarding move direction for Pawn
    As well as start and ending coordiates
 */
public class Pawn extends Piece {          //inherits piece, store information about pawns on the board, where they are at any point and where their next move will be

    public Pawn(char pieceTye, int x, int y) {super(pieceTye,x,y);}
    public Pawn(char pieceType, int x, int y, int nextX, int nextY) {super(pieceType, x, y, nextX, nextY);}

    //find moves for pawn and add them to the list
    void find_moves(char [][] boardState, List<Piece> moves, char Side){
        int forward,left,right;
        forward = left = right =0;
        if (Side    == 'W') {                                                  //if side is White
            forward = y -1;                                                    //find forward, left, right with respect to side orientation
            left = x-1;
            right = x+1;

            if (forward >=0 && boardState[forward][x] == '.')                   //move forward space occupied by '.'
            {
                if(forward == 0){
                    Queen aMove = new Queen('Q',x,y,x,forward);
                    moves.add(aMove);
                }
                else {
                    Pawn aMove = new Pawn('P', x, y, x, forward);
                    moves.add(aMove);
                }
            }
            if (right < 5 && notOccupied(boardState[forward][right],Side) && boardState[forward][right] != '.' )               //attack right (North East) if space occupied by black piece and in bounds
            {
                if(forward == 0){
                    Queen aMove = new Queen('Q',x,y,right,forward);
                    moves.add(aMove);
                }
                else {
                    Pawn aMove = new Pawn('P', x, y, right, forward);
                    moves.add(aMove);
                }
            }
            if (left >=0 && notOccupied(boardState[forward][left],Side) && boardState[forward][left] !='.')                    //attack left (North West) if space occupied by black piece and in bounds
            {
                if(forward == 0){
                    Queen aMove = new Queen('Q',x,y,left,forward);
                    moves.add(aMove);
                }
                else {
                    Pawn aMove = new Pawn('P', x, y, left, forward);
                    moves.add(aMove);
                }
            }
        }
        else {                                                                   //if side is black
            forward = y+1;
            left = x-1;
            right = x+1;
            if (forward < 6 && boardState[forward][x] == '.')                    //move forward if space occupied by '.'
            {
                if(forward == 5){
                    Queen aMove = new Queen('q',x,y,x,forward);
                    moves.add(aMove);
                }
                else {
                    Pawn aMove = new Pawn('p', x, y, x, forward);
                    moves.add(aMove);
                }
            }
            if (right < 5 && notOccupied(boardState[forward][right],Side) && boardState[forward][right]!='.')               //attack right (South East) if space occupied by White piece and in bounds
            {
                if(forward == 5){
                    Queen aMove = new Queen('q',x,y,right,forward);
                    moves.add(aMove);
                }
                else {
                    Pawn aMove = new Pawn('p', x, y, right, forward);
                    moves.add(aMove);
                }
            }
            if (left >=0 && notOccupied(boardState[forward][left],Side) && boardState[forward][left] !='.')                    //attack left (South West) if space occupied by White piece and in bounds
            {
                if(forward == 5){
                    Queen aMove = new Queen('q',x,y,left,forward);
                    moves.add(aMove);
                }
                else {
                    Pawn aMove = new Pawn('p', x, y, left, forward);
                    moves.add(aMove);
                }
            }
        }
    }
}
