/**
 * Rudd Johnson
 * CS 442
 * Final Chess Player
 * 6/1/17
 **/
/*
    Parent class for all pieces on the board
    Contains start and finish coordinate information
    as well as getters and setters for said data
 */
public class Piece {
    protected char pieceType;
    protected int x;                          //current x-axis
    protected int y;                          //current y-axis
    protected int nextX;                      //next x-axis location for move
    protected int nextY;                      //next y-axis location for move
    public Piece(){}                          //initialize piece

    //constructors
    public Piece(char pieceType, int x, int y) {
        this.pieceType = pieceType;
        this.x = x;
        this.y = y;
    }
    public Piece(char pieceType, int x, int y, int nextX, int nextY) {
        this.pieceType = pieceType;
        this.x = x;
        this.y = y;
        this.nextX = nextX;
        this.nextY = nextY;
    }

    //check that space is not occupied by own side
    public boolean notOccupied(char ply, char Side)
    {
        if(Side == 'W') {if(ply == 'P' ||ply== 'R' || ply=='K' || ply=='N'||ply=='Q'||ply=='B'){return false;}}
        else{if(ply == 'p' ||ply== 'r' || ply=='k' || ply=='n'||ply=='q'||ply=='b'){return false;}}
        return true;
    }

    /*
        Getters and setters for private data members
     */
    public char getPieceType() {return pieceType;}

    public int getX() {return x;}

    public int getY() {return y;}

    public int getNextX() {return nextX;}

    public int getNextY() {return nextY;}

    public void setPieceType(char pieceType) {this.pieceType = pieceType;}

    public void setX(int x) {this.x = x;}

    public void setY(int y) {this.y = y;}

    public void setNextX(int nextX) {this.nextX = nextX;}

    public void setNextY(int nextY) {this.nextY = nextY;}
}
