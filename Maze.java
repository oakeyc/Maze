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
    ArrayList<Cell> cells;
    boolean isSolved; // whether the maze is solved

    Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.makeMaze(0);
    }

    // Constructs a maze without random generation (used solely for testing).
    Maze(int rows, int cols, int notUsed) {
        this.rows = rows;
        this.cols = cols;
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
            edges = this.generateEdges(matrix, 100);
        }
        else {
            edges = this.generateEdges(matrix, -100);
        }


        // Run Kruskal's algorithm on all edges.
        edges = this.kruskal(this.cells, edges);

        // Give remaining edges to their appropriate direction in the appropriate cells.
        for (Edge e: edges) {
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
        }
    }

    // Returns the cell at the given row and column.
    Cell cellAt(int r, int c) {
        if (r < 0 || r >= this.rows) {
            throw new IllegalArgumentException("Invalid row.");
        }
        if (c < 0 || c >= this.cols) {
            throw new IllegalArgumentException("Invalid column.");
        }
        return this.cells.get(r + c * this.cols);
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
        this.edgeSort(edges);
        ArrayList<Edge> result = new ArrayList<Edge>();
        while (edges.size() > 0) {
            Edge edge = edges.remove(0);
            int leader1 = leaders.get(edge.cell1.hashCode());
            while (leader1 != leaders.get(leader1)) {
                leader1 = leaders.get(leader1);
            }
            int leader2 = leaders.get(edge.cell2.hashCode());
            while (leader2 != leaders.get(leader2)) {
                leader2 = leaders.get(leader2);
            }
            if (leader1 != leader2) {
                leaders.put(leader1, leader2);
                result.add(edge);
            }
        }

        return result;
    }

    // Sorts the given list of edges in order of weight using merge sort.
    // EFFECT: Sorts the given list in place.
    void edgeSort(ArrayList<Edge> edges) {
        this.mergeHelp(edges, 0, edges.size() - 1);
    }

    // A helper for edgeSort, sorts the given list from index start to index end,
    // using mid and the middle index.
    // EFFECT: Sorts the given list in place from start to end.
    void mergeHelp(ArrayList<Edge> edges, int start, int end) {
        // End condition.
        if (end > start) {
            int mid = (start + end) / 2;
            // Split and sort halves.
            mergeHelp(edges, start, mid);
            mergeHelp(edges, mid + 1, end);
            // Merge the halves.
            int ind1 = start;
            int ind2 = mid + 1;
            ArrayList<Edge> result = new ArrayList<Edge>();
            while (ind1 <= mid && ind2 <= end) {
                if (edges.get(ind1).weight <= edges.get(ind2).weight) {
                    result.add(edges.get(ind1));
                    ind1++;
                }
                else {
                    result.add(edges.get(ind2));
                    ind2++;
                }
            }
            // Handle extra values in one half.
            if (ind1 <= mid) {
                for (int i = ind1; i <= mid; i++) {
                    result.add(edges.get(i));
                }
            }
            else if (ind2 <= end) {
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

    // solves the maze with a breadth first algorithm
    // given a position, we find the solution
    // draws it as it solves
    void breadthSolve(int row, int col)
    {   
        // Generate the hashmap for union-find.
        HashMap<Integer, Integer> leaders = new HashMap<Integer, Integer>();
        for (Cell cell : cells) {
            leaders.put(cell.hashCode(), cell.hashCode());
        }

        Cell current = this.cellAt(row,  col);
        ArrayList<Cell> neighbs = current.getNeighbors();

        ArrayList<Cell> workList = new ArrayList<Cell>();
        ArrayList<Cell> solvePath = new ArrayList<Cell>();

        while (!current.isEndCell())
        {
            boolean wasAdded = false;

            for (Cell curr: neighbs)
            {
                int leader1 = leaders.get(curr.hashCode());
                while (leader1 != leaders.get(leader1)) {
                    leader1 = leaders.get(leader1);
                }
                int leader2 = leaders.get(curr.hashCode());
                while (leader2 != leaders.get(leader2)) {
                    leader2 = leaders.get(leader2);
                }
                if (leader1 != leader2) {
                    leaders.put(leader1, leader2);
                    // visit it
                    workList.add(curr);
                    wasAdded = true;
                }
            }

            current.wasVisited = true;
            if (!wasAdded)
                solvePath.remove(solvePath.size() - 1);
            else
                solvePath.add(current);

            current = workList.remove(0);
        }

        for (Cell c : solvePath)
        {
            c.isOnPath = true;
        }


        this.isSolved = true;
    }

    void breadthHelp(HashMap<Integer, Integer> visted)
    {

    }

    // solves the maze with a depth first algorithm
    // given a position, we find the solution
    // draws it as it solves
    //    void depthSolve(int row, int col)
    //    {
    //     // Generate the hashmap for union-find.
    //        HashMap<Integer, Integer> leaders = new HashMap<Integer, Integer>();
    //        for (Cell cell : cells) {
    //            leaders.put(cell.hashCode(), cell.hashCode());
    //        }
    //
    //        Cell current = this.cellAt(row,  col);
    //        ArrayList<Cell> neighbs = current.getNeighbors();
    //
    //        ArrayList<Cell> workList = new ArrayList<Cell>();
    //        workList.add(current);
    //        ArrayList<Cell> solvePath = new ArrayList<Cell>();
    //        solvePath.add(current);
    //        
    //        while (!this.isSolved)
    //        {
    //            current = workList.get(workList.size() - 1);
    //
    //            System.out.println(current.hashCode());
    //            if (current.isEndCell()) {
    //                this.isSolved = true;
    //                break;
    //            }
    //
    //            current.wasVisited = true;
    //            neighbs = current.getNeighbors();
    //            boolean wasAdded = false;
    //            
    //            for (Cell curr: neighbs)
    //            {
    //                int leader1 = leaders.get(curr.hashCode());
    //                while (leader1 != leaders.get(leader1)) {
    //                    leader1 = leaders.get(leader1);
    //                }
    //                int leader2 = leaders.get(current.hashCode());
    //                while (leader2 != leaders.get(leader2)) {
    //                    leader2 = leaders.get(leader2);
    //                }
    //                if (leader1 != leader2) {
    //                    leaders.put(leader1, leader2);
    //                    // visit it
    //                    workList.add(curr);
    //                    wasAdded = true;
    //                }
    //            }
    //            
    //            if (!wasAdded)// && workList.size() != 0 && solvePath.size() != 0)
    //            {
    //                workList.remove(workList.size() - 1);
    //                solvePath.remove(solvePath.size() - 1);
    //            }
    //            else
    //                solvePath.add(current);
    //        }cd
    //        
    //        for (Cell c : solvePath)
    //        {
    //            c.isOnPath = true;
    //        }
    //    }

    void depthSolve(int row, int col)
    {
        Cell current = this.cellAt(row,  col);

        ArrayList<ArrayList<Cell>> toVisit = new ArrayList<ArrayList<Cell>>();
        ArrayList<Cell> nei = current.getNeighbors();
        toVisit.add(nei);

        depthHelp(current, nei.size(), 1, toVisit);
    }

    // found the solution
    boolean depthHelp(Cell current, int numNeib, int numList, ArrayList<ArrayList<Cell>> toVisit)
    {
        System.out.println("-------------------------------------------------");

        if (this.isSolved)
        {
            return true;
        }
        current.wasVisited = true;

        // if we are at the end of the maze, we've found the solution
        if (current.isEndCell())
        {
            current.isOnPath = true;
            this.isSolved = true;
            return true;
        }

        // no more neighbors, it must return from where it came
        if (numNeib == 0)
        {
            current.isOnPath = false;
            System.out.println("Not on the path");
            return false;
        }

        current.isOnPath = true;

        System.out.println("toVisit size: " + toVisit.size());
        System.out.println("Number of Neighbors: " + numNeib);

        // the next cell is the lastest one
        Cell next = toVisit.get(toVisit.size() - numList).remove(numNeib - 1);
        ArrayList<Cell> neigh = next.getNeighbors();
        ArrayList<Cell> nextNeigh = new ArrayList<Cell>();

        int count = 0;
        // adding non visited cells into the work list
        for (Cell c : neigh)
        {
            if (!c.wasVisited)
            {
                nextNeigh.add(c);
                count++;
            }
        }
        toVisit.add(nextNeigh);
        //        numNeib--;

        // recurse to the next level
        if (depthHelp(next, count, 1, toVisit))
        {
            return true;
        }
        else // go back a list
        {
//            numList++;
            toVisit.remove(toVisit.size() - 1);


            System.out.println("\nRecusion back to " + (toVisit.size() - 1));
            System.out.println("Size of last list: " + toVisit.get(toVisit.size() - numList).size() + "\n");

            while (toVisit.get(toVisit.size() - 1).size() > 0)
            {

                // recurse through the next one set of neighbors
                int tV_size = toVisit.size() - 1;
                System.out.println("\n\ntV_Size: " + tV_size + " neighbs : " + (toVisit.get(tV_size).size() - 1));

                next = toVisit.get(tV_size).remove(toVisit.get(tV_size).size() - 1);

                return depthHelp(next, toVisit.get(tV_size).size(), 1, toVisit);
            }
            
//            return depthHelp(toVisit.get(0).get(0), 1, numList, toVisit);
        }
        return false; // idk
    }

    // Draws this maze onto the given base scene.
    WorldScene draw(WorldScene base, boolean drawVisited, boolean drawPath) {
        for (Cell c: this.cells) {
            base = c.draw(base, drawVisited, drawPath);
        }

        return base;
    }
}
