import java.util.ArrayList;

// A maze solver.
abstract class ASolver {
    ArrayList<Cell> toVisit;
    ArrayList<Cell> path;
    boolean solved;
    
    // ctor, inits data
    ASolver(Cell current) {
        this.toVisit = new ArrayList<Cell>();
        this.toVisit.add(current);
        this.path = new ArrayList<Cell>();
        this.path.add(current);
        this.solved = false;
    }
    
    // Runs the next step of the solving algorithm.
    // Returns whether the maze has been solved.
    boolean nextStep() {
        Cell current = this.nextCell();
        current.wasVisited = true;
        
        //Remove all from path until it reaches the parent cell.
        this.handlePath(current);
        
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
    
    // Returns the next item in the given list.
    abstract <T> T nextInList(ArrayList<T> cellList);
    
    // Handles getting the path to the current cell.
    abstract void handlePath(Cell current);
    
    // Adds the given cell to the que, and may perform other actions associated
    //   with adding that cell.
    abstract void addCellToQue(Cell toAdd, Cell current);
}

// A maze solver that uses depth first search.
class DepthSolver extends ASolver {
    ArrayList<Cell> parents;
    
    // ctor, inits data
    DepthSolver(Cell current) {
        super(current);
        this.parents = new ArrayList<Cell>();
        this.parents.add(current);
    }
    
    @Override
    // Returns the next item at the end of the given list.
    // EFFECT: Removes the last item from the given list.
    public <T> T nextInList(ArrayList<T> list) {
        return list.remove(list.size() - 1);
    }
    
    @Override
    // EFFECT: Resets the path back to the parent of the current cell,
    //         then adds the current cell.
    public void handlePath(Cell current) {
        Cell parent = this.nextParent();
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
    

    // Returns the next cell in the parents list.
    Cell nextParent() {
        return this.nextInList(this.parents);
    }
}

// A maze solver that uses breadth first search.
class BreadthSolver extends ASolver {
    
    ArrayList<ArrayList<Cell>> paths;
    
    // ctor, inits data
    BreadthSolver(Cell current) {
        super(current);
        this.paths = new ArrayList<ArrayList<Cell>>();
        this.paths.add(this.path);
    }
    
    @Override
    // Returns the next item at the beginning of the given list.
    // EFFECT: Removes the first item from the given list.
    public <T> T nextInList(ArrayList<T> list) {
        return list.remove(0);
    }
    
    @Override
    // EFFECT: Resets the path to a copy of the path of the current cell's parent
    //         (which is stored in paths), then adds the current cell to that path.
    public void handlePath(Cell current) {
        this.path = (ArrayList<Cell>)(this.nextInList(this.paths).clone());
        this.path.add(current);
    }
    
    @Override
    // EFFECT: Adds the given cell to the toVisit list, and the current cell
    //         as its parent to the parents list.
    public void addCellToQue(Cell toAdd, Cell current) {
        this.toVisit.add(toAdd);
        this.paths.add(this.path);
    }
}