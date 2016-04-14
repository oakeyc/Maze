import java.util.ArrayList;

// A maze solver.
abstract class ASolver {
    ArrayList<Cell> toVisit;
    ArrayList<Cell> parents;
    ArrayList<Cell> path;
    boolean solved;
    
    ASolver(Cell current) {
        this.toVisit = new ArrayList<Cell>();
        this.toVisit.add(current);
        this.parents = new ArrayList<Cell>();
        this.parents.add(current);
        this.path = new ArrayList<Cell>();
        this.path.add(current);
        this.solved = false;
    }
    
    // Runs the next step of the solving algorithm.
    boolean nextStep() {
        Cell current = this.nextCell();
        Cell parent = this.nextParent();
        current.wasVisited = true;
        
        //Remove all from path until it reaches the parent cell.
        this.handlePath(current, parent);
        
        // Check for end.
        if (current.isEndCell()) {
            for (Cell c : this.path) {
                c.isOnPath = true;
            }
            this.solved = true;
        }
        else {
            // Add neighbors to be visited later.
            ArrayList<Cell> neighbors = current.getNeighbors();
            for (Cell c : neighbors) {
                if (!c.wasVisited) {
                    this.addCellToQue(c, current);
                }
            }
        }
        return this.solved;
    }
    
    // Returns the next cell in the toVisit list.
    Cell nextCell() {
        return this.nextInList(this.toVisit);
    }
    
    // Returns the next cell in the parents list.
    Cell nextParent() {
        return this.nextInList(this.parents);
    }
    
    // Returns the next cell in the given list.
    abstract Cell nextInList(ArrayList<Cell> cellList);
    
    // Handles getting the path to the current cell.
    abstract void handlePath(Cell current, Cell parent);
    
    // Adds the given cell to the que, and may perform other actions associated
    //   with adding that cell.
    abstract void addCellToQue(Cell toAdd, Cell current);
}

// A maze solver that uses depth first search.
class DepthSolver extends ASolver {
    
    DepthSolver(Cell current) {
        super(current);
    }
    
    @Override
    // Returns the next cell at the end of the given list.
    // EFFECT: Removes the last cell from the given list.
    public Cell nextInList(ArrayList<Cell> cellList) {
        return cellList.remove(cellList.size() - 1);
    }
    
    @Override
    // EFFECT: Resets the path back to the parent of the current cell,
    //         then adds the current cell.
    public void handlePath(Cell current, Cell parent) {
        while (!this.path.get(this.path.size() - 1).equals(parent)) {
            this.path.remove(this.path.size() - 1);
        }
        
        this.path.add(current);
    }
    
    @Override
    // EFFECT: Adds toAdd to the toVisit list, and the current cell
    //         as its parent to the parents list.
    public void addCellToQue(Cell toAdd, Cell current) {
        this.toVisit.add(toAdd);
        this.parents.add(current);
    }
}

// A maze solver that uses breadth first search.
class BreadthSolver extends ASolver {
    
    ArrayList<ArrayList<Cell>> paths;
    
    BreadthSolver(Cell current) {
        super(current);
        this.paths = new ArrayList<ArrayList<Cell>>();
        this.paths.add(this.path);
    }
    
    @Override
    // Returns the next cell at the beginning of the given list.
    // EFFECT: Removes the first cell from the given list.
    public Cell nextInList(ArrayList<Cell> cellList) {
        return cellList.remove(0);
    }
    
    @Override
    // EFFECT: Resets the path to a copy of the path of the current cell's parent
    //         (which is stored in paths), then adds the current cell to that path.
    public void handlePath(Cell current, Cell parent) {
        this.path = (ArrayList<Cell>)this.paths.remove(0).clone();
        this.path.add(current);
    }
    
    @Override
    // EFFECT: Adds the given cell to the toVisit list, and the current cell
    //         as its parent to the parents list.
    public void addCellToQue(Cell toAdd, Cell current) {
        this.toVisit.add(toAdd);
        this.parents.add(current);
        this.paths.add(this.path);
    }
}