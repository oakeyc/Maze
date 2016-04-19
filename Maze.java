// Assignment 10
// Oka Courtney
// okac
// Obermiller Karl
// obermillerk

import java.util.ArrayList;
import java.util.Random;
import javalib.impworld.*;
import java.util.HashMap;

// Represents a maze.
public class Maze {
    int rows;
    int cols;
    int weightScale;
    ArrayList<Cell> cells;
    ArrayList<Edge> edges;

    Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.weightScale = 50;
        this.cells = new ArrayList<Cell>();
    }

    // Constructs a maze without random generation (used solely for testing).
    Maze(int rows, int cols, int notUsed) {
        this.rows = rows;
        this.cols = cols;
        this.weightScale = 50;
        this.cells = new ArrayList<Cell>();
        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.cols; c++) {
                this.cells.add(new Cell(r, c));
            }
        }
    }

    // EFFECT: Sets cells to a new list of cells that represents a new random maze.
    void makeMaze(int type) {
        // Generate grid of cells.
        this.cells = new ArrayList<Cell>();
        ArrayList<ArrayList<Cell>> matrix = new ArrayList<ArrayList<Cell>>();

        for (int r = 0; r < this.rows; r++) {
            ArrayList<Cell> row = new ArrayList<Cell>();
            for (int c = 0; c < this.cols; c++) {
                Cell cell = new Cell(r, c);
                this.cells.add(cell);
                row.add(cell);
            }
            matrix.add(row);
        }

        // Generate edges of random weights.
        ArrayList<Edge> edges;

        if (type == 0) {
            edges = this.generateEdges(matrix, 0);
        }
        else if (type > 0) {
            edges = this.generateEdges(matrix, this.weightScale);
        }
        else {
            edges = this.generateEdges(matrix, -this.weightScale);
        }


        // Run Kruskal's algorithm on all edges, store it for construction animation.
        this.edges = this.kruskal(this.cells, edges);
    }
    
    // Adds a single edge from the list of edges initially generated when generation the maze.
    // Returns whether there are still edges to be added (edges is not empty).
    // EFFECT: Removes an edge from this.edges, and then adds it to the correct cells,
    //         effectively knocking down a wall.
    boolean nextBuild() {
        // Give an edge to its appropriate direction in the appropriate cells.
        Edge e = this.edges.remove(0);
        Cell c1 = e.cell1;
        Cell c2 = e.cell2;
        if (c1.r != c2.r) {
            if (c1.r > c2.r) {
                c2.bottomWall = false;
                c2.bottom = e;
                c1.top = e;
            }
            else {
                c1.bottomWall = false;
                c1.bottom = e;
                c2.top = e;
            }
        }
        else {
            if (c1.c > c2.c) {
                c2.rightWall = false;
                c2.right = e;
                c1.left = e;
            }
            else {
                c1.rightWall = false;
                c1.right = e;
                c2.left = e;
            }
        }
        return !this.edges.isEmpty();
    }

    // Returns the cell at the given row and column.
    Cell cellAt(int r, int c) {
        if (r < 0 || r >= this.rows) {
            throw new IllegalArgumentException("Invalid row.");
        }
        if (c < 0 || c >= this.cols) {
            throw new IllegalArgumentException("Invalid column.");
        }
        return this.cells.get(r * this.cols + c);
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
    ArrayList<Edge> kruskal(ArrayList<Cell> cells, ArrayList<Edge> edges) {
        // Generate the hashmap for union-find.
        HashMap<Integer, Integer> leaders = new HashMap<Integer, Integer>();
        for (Cell cell : cells) {
            leaders.put(cell.hashCode(), cell.hashCode());
        }

        // Run Kruskal's algorithm on edges using union-find.
        Utils.mergeSort(edges, new EdgeWeightComparator());
        ArrayList<Edge> result = new ArrayList<Edge>();
        while (edges.size() > 0) {
            Edge edge = edges.remove(0);
            // Get absolute leader of Cell 1.
            int leader1 = leaders.get(edge.cell1.hashCode());
            while (leader1 != leaders.get(leader1)) {
                leader1 = leaders.get(leader1);
            }
            // Get absolute leader of Cell 2.
            int leader2 = leaders.get(edge.cell2.hashCode());
            while (leader2 != leaders.get(leader2)) {
                leader2 = leaders.get(leader2);
            }
            // Check for same group, and union if not.
            if (leader1 != leader2) {
                leaders.put(leader1, leader2);
                result.add(edge);
            }
        }

        return result;
    }
    
    // EFFECT: Resets all cells in this maze wasVisited flag to false.
    void clearVisited() {
        for (Cell c : this.cells) {
            c.wasVisited = false;
        }
    }
    
    // EFFECT: Resets all cells in this maze isOnPath flag to false.
    void clearPath() {
        for (Cell c : this.cells) {
            c.isOnPath = false;
        }
    }
    
    // Sets how heavily the vertical- or horizontal-tending board generation weights the edges.
    void setWeightScale(int scale) {
        this.weightScale = scale;
    }
    
    // Returns the number of cells that were visited that are not on the correct path.
    int getWrongVisitedCells() {
        int r = 0;
        for (Cell c : this.cells) {
            if (c.wasVisited && !c.isOnPath) {
                r++;
            }
        }
        return r;
    }

    // Draws this maze onto the given base scene.
    WorldScene draw(WorldScene base, boolean drawVisited, boolean drawPath) {
        for (Cell c: this.cells) {
            base = c.draw(base, drawVisited, drawPath);
        }
        return base;
    }
}
