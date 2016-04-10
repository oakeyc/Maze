import java.util.ArrayList;
import javalib.impworld.*;

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
    
    WorldScene draw(WorldScene base) {
        for (Cell c: this.cells) {
            base = c.draw(base);
        }
        
        return base;
    }
}
