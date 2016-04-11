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
}