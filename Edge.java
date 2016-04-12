// Represents an edge in a weighted, non-directed graph.
class Edge {
    int weight;
    Cell cell1;
    Cell cell2;
    
    Edge(Cell cell1, Cell cell2, int weight) {
        this.cell1 = cell1;
        this.cell2 = cell2;
        this.weight = weight;
    }
    
    // Returns the cell other than the given cell,
    //   or throws an exception if that cell is not on this edge.
    Cell getOtherCell(Cell cell) {
        if (this.cell1.equals(cell)) {
            return this.cell2;
        }
        else if (this.cell2.equals(cell)) {
            return this.cell1;
        }
        else {
            throw new RuntimeException("The cell is not on this edge.");
        }
    }
}