import java.awt.Color;
import javalib.impworld.WorldScene;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

// represents a player
class Player 
{
    int row; // row position
    int col; // col position
    Cell current;
    
    // ctor 
    Player(int row, int col, Cell current)
    {
        this.row = row;
        this.col = col;
        this.current = current;
    }
    
    // Draws this player on the give base.
    WorldScene draw(WorldScene base)
    {
        WorldImage image = new RectangleImage(Cell.SIZE - 1, Cell.SIZE - 1, 
                "solid", Color.MAGENTA);

        base.placeImageXY(image, 
                this.col * Cell.SIZE + Cell.SIZE / 2,
                this.row * Cell.SIZE + Cell.SIZE / 2);
        return base;
    }
    
    // Moves the player in the given direction, if possible.
    // EFFECT: Updates r and c to new position and changes current cell, if possible.
    void move(String dir) {
        if (dir.equals("up")) {
            if (this.current.top != null) {
                this.row -= 1;
                this.current = this.current.top.getOtherCell(this.current);
            }
        }
        else if (dir.equals("right")) {
            if (this.current.right != null) {
                this.col += 1;
                this.current = this.current.right.getOtherCell(this.current);
            }
        }
        else if (dir.equals("down")) {
            if (this.current.bottom != null) {
                this.row += 1;
                this.current = this.current.bottom.getOtherCell(this.current);
            }
        }
        else if (dir.equals("left")) {
            if (this.current.left != null) {
                this.col -= 1;
                this.current = this.current.left.getOtherCell(this.current);
            }
        }
        else {
            throw new IllegalArgumentException("Must be a direction.");
        }
    }
}
