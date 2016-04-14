import java.util.ArrayList;

interface ISolver {
    boolean nextStep();
}

class DepthSolver implements ISolver {
    ArrayList<Cell> toVisit;
    ArrayList<Cell> parents;
    ArrayList<Cell> path;
    boolean solved;
    
    DepthSolver(Cell current) {
        this.toVisit = new ArrayList<Cell>();
        this.toVisit.add(current);
        this.parents = new ArrayList<Cell>();
        this.parents.add(current);
        this.path = new ArrayList<Cell>();
        this.path.add(current);
        this.solved = false;
    }
    
    @Override
    public boolean nextStep() {
        Cell current = this.toVisit.remove(this.toVisit.size() - 1);
        Cell parent = this.parents.remove(this.parents.size() - 1);
        current.wasVisited = true;
        
        //Remove all from path until it reaches the parent cell.
        while (!this.path.get(this.path.size() - 1).equals(parent)) {
            this.path.remove(this.path.size() - 1);
        }
        
        this.path.add(current);
        
        // Check for end.
        if (current.isEndCell()) {
            for (Cell c : this.path) {
                c.isOnPath = true;
            }
            this.solved = true;
        }
        else {
            ArrayList<Cell> neighbors = current.getNeighbors();
            for (Cell c : neighbors) {
                if (!c.wasVisited) {
                    this.toVisit.add(c);
                    this.parents.add(current);
                }
            }
        }
        return this.solved;
    }
}