import tester.*;

import java.util.ArrayList;
import java.util.Arrays;

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
        world.bigBang(MazeWorld.WIDTH, MazeWorld.HEIGHT);
    }
    
    // Tests the edgeSort method for Mazes.
    void testEdgeSort(Tester t) {
        Maze m = new Maze(5, 5);
        ArrayList<Edge> edges = new ArrayList<Edge>();
        ArrayList<Edge> sorted = new ArrayList<Edge>();
        this.initEdges();
        m.edgeSort(edges);
        t.checkExpect(edges, sorted);
        edges.add(this.e1);
        sorted.add(this.e1);
        m.edgeSort(edges);
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
        m.edgeSort(edges);
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
    
    // Tests the mergeHelp method for Mazes.
    void testMergeHelp(Tester t) {
        Maze m = new Maze(5, 5);
        ArrayList<Edge> edges = new ArrayList<Edge>();
        ArrayList<Edge> result = new ArrayList<Edge>();
        this.initEdges();
        m.mergeHelp(edges, 0, -1);
        t.checkExpect(edges, result);
        edges.add(this.e1);
        result.add(this.e1);
        m.mergeHelp(edges, 0, 0);
        t.checkExpect(edges, result);
        edges.add(this.e2);
        edges.add(this.e3);
        edges.add(this.e4);
        edges.add(this.e5);
        result.add(this.e2);
        result.add(this.e4);
        result.add(this.e5);
        result.add(this.e3);
        m.mergeHelp(edges, 2, 4);
        t.checkExpect(edges, result);
        result.set(0, this.e2);
        result.set(1, this.e1);
        m.mergeHelp(edges, 0, 1);
        t.checkExpect(edges, result);
    }
}

/**
 * To test:
 *  nothing
 */