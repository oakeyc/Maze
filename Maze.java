import java.util.ArrayList;
import java.util.Random;
import javalib.impworld.*;

// Represents a maze.
public class Maze {
    int rows;
    int cols;
    ArrayList<Cell> cells;
    
    Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.makeRandomMaze();
    }
    
    // EFFECT: Sets cells to a new list of cells that represents a new random maze.
    void makeRandomMaze() {
        this.cells = new ArrayList<Cell>();
        ArrayList<ArrayList<Cell>> matrix = new ArrayList<ArrayList<Cell>>();
        
        for (int r = 0; r < this.rows; r++) {
            ArrayList<Cell> row = new ArrayList<Cell>();
            for (int c = 0; c < this.cols; c++) {
                row.add(new Cell(r, c));
            }
            matrix.add(row);
        }
        
        ArrayList<Edge> edges = this.generateEdges(matrix, 0);
        
        // Run Kruskal's algorithm on all edges.
        edges = this.kruskal(edges);
        
        // Give edges to their appropriate direction in the appropriate cells.
        for (Edge e: edges) {
            Cell c1 = e.cell1;
            Cell c2 = e.cell2;
            if(c1.r != c2.r) {
                if (c1.r > c2.r) {
                    c2.bottomWall = false;
                    c2.bottom = e;
                    c1.top = e;
                } else {
                    c1.bottomWall = false;
                    c1.bottom = e;
                    c2.top = e;
                }
            } else {
                if (c1.c > c2.c) {
                    c2.rightWall = false;
                    c2.right = e;
                    c1.left = e;
                } else {
                    c1.rightWall = false;
                    c1.right = e;
                    c2.left = e;
                }
            }
        }
    }
    
    // Generates an array-list of edges connecting all cells in the given matrix,
    // like a rectangular grid.
    // Horizontal edges more likely to be chosen in Kruskal's algorithm the more positive
    //   horizontalWeight is.
    // Vertical edges more likely to be chosen in Kruskal's algorithm the more negative
    //   horizontalWeight is.
    ArrayList<Edge> generateEdges(ArrayList<ArrayList<Cell>> matrix, int horizontalWeight) {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        Random rand = new Random();
        
        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.cols; c++) {
                Cell cell = matrix.get(r).get(c);
                this.cells.add(cell);
                if (r < this.rows - 1) {
                    edges.add(new Edge(cell, matrix.get(r + 1).get(c),
                            rand.nextInt(100) + horizontalWeight));
                }
                if (c < this.cols - 1) {
                    edges.add(new Edge(cell, matrix.get(r).get(c + 1),
                            rand.nextInt(100)));
                }
            }
        }
        
        return edges;
    }
    
    // Runs Kruskal's algorithm on the given list of edges.
    ArrayList<Edge> kruskal(ArrayList<Edge> edges) {
        this.edgeSort(edges);
        ArrayList<Edge> result = new ArrayList<Edge>();
        while (edges.size() > 0) {
            Edge edge = edges.remove(0);
            if (!edge.cell1.find(edge.cell2)) {
                edge.cell1.union(edge.cell2);
                result.add(edge);
            }
        }
        
        return result;
    }
    
    // Sorts the given list of edges in order of weight using merge sort.
    // EFFECT: Sorts the given list in place.
    void edgeSort(ArrayList<Edge> edges) {
        this.mergeHelp(edges, 0, edges.size() - 1, edges.size() / 2);
    }
    
    // A helper for edgeSort, sorts the given list from index start to index end,
    // using mid and the middle index.
    // EFFECT: Sorts the given list in place from start to end.
    void mergeHelp(ArrayList<Edge> edges, int start, int end, int mid) {
        // End condition.
        if (end > start) {
            // Split and sort halves.
            mergeHelp(edges, start, mid , (start + mid) / 2);
            mergeHelp(edges, mid + 1, end, (mid + 1 + end) / 2);
            // Merge the halves.
            int ind1 = start;
            int ind2 = mid + 1;
            ArrayList<Edge> result = new ArrayList<Edge>();
            while (ind1 <= mid && ind2 <= end) {
                if (edges.get(ind1).weight <= edges.get(ind2).weight) {
                    result.add(edges.get(ind1));
                    ind1++;
                } else {
                    result.add(edges.get(ind2));
                    ind2++;
                }
            }
            // Handle extra values in one half.
            if (ind1 <= mid) {
                for (int i = ind1; i <= mid; i++) {
                    result.add(edges.get(i));
                }
            } else if (ind2 <= end) {
                for (int i = ind2; i <= end; i++) {
                    result.add(edges.get(i));
                }
            }

            // Fix original list.
            for (int i = 0; i < result.size(); i++) {
                edges.set(start + i, result.get(i));
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
