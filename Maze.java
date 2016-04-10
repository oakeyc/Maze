import java.util.ArrayList;
import javalib.impworld.*;

// Represents a maze.
public class Maze {
    ArrayList<Cell> cells;
    
    Maze(int rows, int cols) {
        this.cells = new ArrayList<Cell>();
        for (int r = 0; r < rows; r ++) {
            for (int c = 0; c < cols; c++) {
                this.cells.add(new Cell(r, c));
            }
        }
    }
    
    // Draws this maze onto the given base scene.
    WorldScene draw(WorldScene base, boolean drawVisited, boolean drawPath) {
        for (Cell c: this.cells) {
            base = c.draw(base, drawVisited, drawPath);
        }
        
        return base;
    }
}
