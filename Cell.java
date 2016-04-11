import javalib.impworld.*;
import javalib.worldimages.*;
import java.awt.Color;
import java.util.ArrayList;

// Represents a cell in a maze.
class Cell extends GMember {
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
        super();
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
}