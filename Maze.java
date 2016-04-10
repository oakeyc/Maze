import java.util.ArrayList;
import javalib.impworld.*;

// Represents a maze.
public class Maze {
    ArrayList<Cell> cells;
    
    Maze() {
        this.cells = new ArrayList<Cell>();
        for (int r = 0; r < 20; r ++) {
            for (int c = 0; c < 40; c++) {
                this.cells.add(new Cell(r, c));
            }
        }
    }
    
    // Draws this maze onto the given base scene.
    WorldScene draw(WorldScene base) {
        for (Cell c: this.cells) {
            base = c.draw(base);
        }
        
        return base;
    }
}
