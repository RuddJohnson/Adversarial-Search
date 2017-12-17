/**
 * Rudd Johnson
 * 4/21/17
 */
public class Piece {                //object which board is comprised of, each "square" of board contains type information (P,p,.)
    protected char pieceType;

    public Piece(){}                                                 //initialize piece
    public Piece(char pieceType) {this.pieceType = pieceType;}
                                                                     //get type
    public char getPieceType() {return pieceType;}
                                                                    //set type
    public void setPieceType(char pieceType) {this.pieceType = pieceType;}

}
