import java.awt.Color;
import javalib.impworld.WorldScene;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

// represents a player
class Player extends MazeTraveler
{
    // ctor
    Player(int row, int col, Cell current) {
        super(row, col, current);
    }
}
