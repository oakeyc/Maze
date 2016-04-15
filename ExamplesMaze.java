import tester.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

// Examples and tests
class ExamplesMaze {
    
    Cell c1;
    Cell c2;
    Cell c3;
    Cell c4;
    
    Edge e1;
    Edge e2;
    Edge e3;
    Edge e4;
    Edge e5;
    Edge e6;
    
    // Initializes Edge fields.
    void initEdges() {
        this.initCells();
        this.e1 = new Edge(c1, c2, 20);
        this.e2 = new Edge(c2, c4, 10);
        this.e3 = new Edge(c3, c4, 60);
        this.e4 = new Edge(c3, c1, 10);
        this.e5 = new Edge(c3, c2, 45);
        this.e6 = new Edge(c1, c4, 5);
    }
    
    void initCells() {
        this.c1 = new Cell(0, 0);
        this.c2 = new Cell(10, 0);
        this.c3 = new Cell(0, 10);
        this.c4 = new Cell(10, 10);
    }
    
    // Runs the game.
    void testMaze(Tester t) {
        MazeWorld world = new MazeWorld();
        world.bigBang(MazeWorld.WIDTH, MazeWorld.HEIGHT,
                0.01);
    }
    
    // Tests the mergeSort method in Utils.
    void testMergeSort(Tester t) {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        ArrayList<Edge> sorted = new ArrayList<Edge>();
        Comparator<Edge> comp = new EdgeWeightComparator();
        this.initEdges();
        Utils.mergeSort(edges, comp);
        t.checkExpect(edges, sorted);
        edges.add(this.e1);
        sorted.add(this.e1);
        Utils.mergeSort(edges, comp);
        t.checkExpect(edges, sorted);
        edges.add(this.e2);
        edges.add(this.e3);
        edges.add(this.e4);
        edges.add(this.e5);
        sorted.clear();
        sorted.add(this.e2);
        sorted.add(this.e4);
        sorted.add(this.e1);
        sorted.add(this.e5);
        sorted.add(this.e3);
        Utils.mergeSort(edges, comp);
        t.checkExpect(edges, sorted);
    }
    
    // Tests the kruskal method for Mazes.
    void testKruskal(Tester t) {
        this.initEdges();
        Maze m = new Maze(5, 5);
        ArrayList<Cell> cells =
                new ArrayList<Cell>(Arrays.asList(this.c1, this.c2, this.c3, this.c4));
        ArrayList<Edge> edges = new ArrayList<Edge>();
        ArrayList<Edge> result = new ArrayList<Edge>();
        edges.add(this.e1);
        edges.add(this.e2);
        edges.add(this.e3);
        edges.add(this.e4);
        edges.add(this.e5);
        result.add(this.e2);
        result.add(this.e4);
        result.add(this.e1);
        t.checkExpect(m.kruskal(cells, edges), result);
        this.initEdges();
        edges.clear();
        edges.add(this.e1);
        edges.add(this.e2);
        edges.add(this.e3);
        edges.add(this.e4);
        edges.add(this.e5);
        edges.add(this.e6);
        result.clear();
        result.add(this.e6);
        result.add(this.e2);
        result.add(this.e4);
        t.checkExpect(m.kruskal(cells, edges), result);
    }
    
    // Tests the mergeHelp method in Utils.
    void testMergeHelp(Tester t) {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        ArrayList<Edge> result = new ArrayList<Edge>();
        this.initEdges();
        Comparator<Edge> comp = new EdgeWeightComparator();
        Utils.mergeHelp(edges, comp, 0, -1);
        t.checkExpect(edges, result);
        edges.add(this.e1);
        result.add(this.e1);
        Utils.mergeHelp(edges, comp, 0, 0);
        t.checkExpect(edges, result);
        edges.add(this.e2);
        edges.add(this.e3);
        edges.add(this.e4);
        edges.add(this.e5);
        result.add(this.e2);
        result.add(this.e4);
        result.add(this.e5);
        result.add(this.e3);
        Utils.mergeHelp(edges, comp, 2, 4);
        t.checkExpect(edges, result);
        result.set(0, this.e2);
        result.set(1, this.e1);
        Utils.mergeHelp(edges, comp, 0, 1);
        t.checkExpect(edges, result);
    }
    
    // Tests the getOtherCell method for Edges.
    void testGetOtherCell(Tester t) {
        this.initEdges();
        t.checkExpect(this.e1.getOtherCell(this.c1), this.c2);
        t.checkExpect(this.e1.getOtherCell(this.c2), this.c1);
        t.checkException(new RuntimeException("The cell is not on this edge."), this.e1,
                "getOtherCell", this.c3);
    }
    
    // Tests the cellAt method for Mazes.
    void testCellAt(Tester t) {
        Maze m = new Maze(5, 5, 0);
        t.checkExpect(m.cellAt(0, 0), new Cell(0, 0));
        t.checkExpect(m.cellAt(3, 3), new Cell(3, 3));
        t.checkException(new IllegalArgumentException("Invalid row."), m, "cellAt", -1, 0);
        t.checkException(new IllegalArgumentException("Invalid row."), m, "cellAt", 5, 0);
        t.checkException(new IllegalArgumentException("Invalid column."), m, "cellAt", 0, -1);
        t.checkException(new IllegalArgumentException("Invalid column."), m, "cellAt", 0, 5);
    }
}

/**
 * To test:
 *  Player.move();
 *  depthSolve()
 *  depthHelper()
 *  breadthSolve()
 *  breadthHelper()
 *  isEnd() ... something like that
 *  onTick
 *  onKeyEvent
 */