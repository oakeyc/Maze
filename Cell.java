import javalib.impworld.*;
import javalib.worldimages.*;
import java.awt.Color;
import java.util.ArrayList;

// Represents a cell in a maze.
class Cell {
    static final int SIZE = 10;
    
    int r;
    int c;
    boolean rightWall;
    boolean bottomWall;
    boolean wasVisited;
    boolean isOnPath;
    Edge top;
    Edge bottom;
    Edge left;
    Edge right;
    
    Cell(int r, int c) {
        this.r = r;
        this.c = c;
        this.rightWall = true;
        this.bottomWall = true;
        this.wasVisited = false;
        this.isOnPath = false;
        this.top = null;
        this.bottom = null;
        this.left = null;
        this.right = null;
    }
    
    // Draws this cell onto the given base scene.
    WorldScene draw(WorldScene base, boolean drawVisited, boolean drawPath) {
        Color color = new Color(0xE0E0E0);
        if (drawVisited && this.wasVisited) {
            color = new Color(0x80D0FF);
        }
        if (drawPath && this.isOnPath) {
            color = new Color(0x4080FF);
        }
        WorldImage image = new RectangleImage(SIZE, SIZE, "solid", color);
        if (this.rightWall) {
            image = new OverlayOffsetImage(
                    new LineImage(new Posn(0, SIZE), Color.BLACK),
                    -SIZE / 2 + 1,
                    0,
                    image);
        }
        if (this.bottomWall) {
            image = new OverlayOffsetImage(
                    new LineImage(new Posn(SIZE, 0), Color.BLACK),
                    0,
                    -SIZE / 2 + 1,
                    image);
        }
        
        base.placeImageXY(image,
                c * SIZE + SIZE / 2,
                r * SIZE + SIZE / 2);
        
        return base;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Cell) {
            Cell c = (Cell) o;
            return this.r == c.r && this.c == c.c;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.r * 1000 + this.c;
    }
    
    // whether end cell or not
    boolean isEndCell()
    {
        return false;
    }
    
    // given nothing
    // returns the list of neighboring cells
    ArrayList<Cell> getNeighbors()
    {
        ArrayList<Cell> neighbors = new ArrayList<Cell>();
        if (this.top != null)
        {
            neighbors.add(this.top.getOtherCell(this));
        }
        if (this.bottom != null)
        {
            neighbors.add(this.bottom.getOtherCell(this));
        }
        if (this.right != null)
        {
            neighbors.add(this.right.getOtherCell(this));
        }
        if (this.left != null)
        {
            neighbors.add(this.left.getOtherCell(this));
        }
        
        return neighbors;
    }
}