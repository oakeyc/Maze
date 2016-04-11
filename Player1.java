import java.awt.Color;
import javalib.impworld.WorldScene;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

// represents a player
class Player 
{
    int row; // row position
    int col; // col position
    
    // ctor 
    Player(int row, int col)
    {
        this.row = row;
        this.col = col;
    }
    
    WorldScene draw(WorldScene base)
    {
        WorldImage image = new RectangleImage(Cell.SIZE, Cell.SIZE, 
                "solid", Color.MAGENTA);

        base.placeImageXY(image, 
                this.row * Cell.SIZE, this.col * Cell.SIZE);
        return base;
    }
}
