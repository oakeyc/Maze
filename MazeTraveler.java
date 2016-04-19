// Assignment 10
// Oka Courtney
// okac
// Obermiller Karl
// obermillerk
import java.awt.Color;
import javalib.impworld.WorldScene;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

// represents something that travels on the maze
class MazeTraveler {
    int row; // row position
    int col; // col position
    Cell current;
    boolean solved;
    
    // ctor 
    MazeTraveler(int row, int col, Cell current)
    {
        this.row = row;
        this.col = col;
        this.current = current;
        this.current.wasVisited = true;
        this.solved = false;
    }
    
    // Draws this player on the give base.
    WorldScene draw(WorldScene base)
    {
        WorldImage image = new RectangleImage(
                Cell.SIZE - 1, 
                Cell.SIZE - 1, 
                "solid", Color.MAGENTA);

        base.placeImageXY(image, 
                this.col * Cell.SIZE + Cell.SIZE / 2,
                this.row * Cell.SIZE + Cell.SIZE / 2);
        return base;
    }
    
    // Moves the player in the given direction, if possible.
    // Returns whether this move solved the maze.
    // EFFECT: Updates r and c to new position and changes current cell, if possible.
    //         Also updates the solved flag.
    boolean move(String dir) {
        if (dir.equals("up")) {
            if (this.moveOnEdge(this.current.top)) {
                this.row -= 1;
            }
        }
        else if (dir.equals("right")) {
            if (this.moveOnEdge(this.current.right)) {
                this.col += 1;
            }
        }
        else if (dir.equals("down")) {
            if (this.moveOnEdge(this.current.bottom)) {
                this.row += 1;
            }
        }
        else if (dir.equals("left")) {
            if (this.moveOnEdge(this.current.left)) {
                this.col -= 1;
            }
        }
        else {
            throw new IllegalArgumentException("Must be a direction.");
        }
        
        this.current.wasVisited = true;
        this.solved = this.current.isEndCell();
        return this.solved;
    }
    
    // Returns whether a move was made or not;
    // EFFECT: Changes current cell to the other cell of the edge, if possible.
    boolean moveOnEdge(Edge e) {
        if (e != null) {
            this.current = e.getOtherCell(this.current);
            return true;
        }
        return false;
    }
}
