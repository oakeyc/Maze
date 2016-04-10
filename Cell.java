import javalib.impworld.*;
import javalib.worldimages.*;
import java.awt.Color;

class Cell extends GMember {
    static final int SIZE = 10;
    
    int r;
    int c;
    boolean onRight;
    boolean onBottom;
    boolean wasVisited;
    boolean isOnPath;
    
    Cell() {
        super();
    }
    
    WorldScene draw(WorldScene base) {
        WorldImage image = new RectangleImage(SIZE, SIZE, "solid", Color.BLUE);
        image = new OverlayOffsetImage(
                new LineImage(new Posn(SIZE, 0), Color.BLACK),
                SIZE / 2,
                0,
                image);
        
        base.placeImageXY(image, r * SIZE, c*SIZE);
        
        return base;
    }
}