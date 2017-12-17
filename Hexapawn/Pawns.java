/**
 * Rudd Johnson
 * 4/21/17
 */
public class Pawns extends Piece {          //inherits piece, store information about pawns on the board, where they are at any point and where their next move will be
    private Piece location;                 //pointer to location on board
    private int x;                          //current x-axis
    private int y;                          //current y-axis
    private int nextX;                      //next x-axis location for move
    private int nextY;                      //next y-axis location for move

    Pawns(char type, Piece loc, int X, int Y, int newX, int newY){      //initialize variables
        super(type);                                                    //call parent constructor
        this.x = X;
        this.y =Y;
        this.location = loc;
        this.nextX = newX;
        this.nextY = newY;
    }

    public Piece getLocation() {return location;} //getters and setters for variables

    public void setLocation(Piece location) {this.location = location;}

    public int getX() {return x;}

    public int getY() {return y;}

    public int getNextX(){ return nextX;}

    public int getNextY(){return nextY;}
}
