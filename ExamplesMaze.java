// Assignment 10
// Oka Courtney
// okac
// Obermiller Karl
// obermillerk

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
    Edge e7;
    
    MazeTraveler m1;
    MazeTraveler m2;
    MazeTraveler m3;
    
    // Initializes Edge fields.
    void initEdges() {
        this.initCells();
        this.e1 = new Edge(c1, c2, 20);
        this.e2 = new Edge(c2, c4, 10);
        this.e3 = new Edge(c3, c4, 60);
        this.e4 = new Edge(c3, c1, 10);
        this.e5 = new Edge(c3, c2, 45);
        this.e6 = new Edge(c1, c4, 5);
        this.e7 = null;
    }
    
    // initializes the cells
    void initCells() {
        this.c1 = new Cell(0, 0);
        this.c2 = new Cell(10, 0);
        this.c3 = new Cell(0, 10);
        this.c4 = new Cell(10, 10);
    }
    
    // initializes the MazeTravelers
    void initTraveler()
    {
        initCells();
        this.m1 = new MazeTraveler(this.c1.r, this.c1.c, this.c1);
        this.m2 = new MazeTraveler(this.c2.r, this.c2.c, this.c2);
        this.m3 = new MazeTraveler(this.c3.r, this.c3.c, this.c3);
    }
    
    // Runs the game.
    void testMaze(Tester t) {
        MazeWorld world = new MazeWorld();
        world.bigBang(MazeWorld.WIDTH, MazeWorld.HEIGHT,
                0.001);
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
    
    // Tests the clearVisited method for Mazes.
    void testClearVisited(Tester t) {
        Maze m = new Maze(5, 5, 0);
        m.cellAt(3, 2).wasVisited = true;
        m.cellAt(4, 1).wasVisited = false;
        t.checkExpect(m.cellAt(3, 2).wasVisited, true);
        t.checkExpect(m.cellAt(4, 1).wasVisited, false);
        m.clearVisited();
        t.checkExpect(m.cellAt(3, 2).wasVisited, false);
        t.checkExpect(m.cellAt(4, 1).wasVisited, false);
    }
    
    // Tests the clearPath method for Mazes.
    void testClearPath(Tester t) {
        Maze m = new Maze(5, 5, 0);
        m.cellAt(3, 2).isOnPath = true;
        m.cellAt(4, 1).isOnPath = false;
        t.checkExpect(m.cellAt(3, 2).isOnPath, true);
        t.checkExpect(m.cellAt(4, 1).isOnPath, false);
        m.clearPath();
        t.checkExpect(m.cellAt(3, 2).wasVisited, false);
        t.checkExpect(m.cellAt(4, 1).wasVisited, false);
    }
    
    // Tests the nextBuild method for Mazes.
    void testNextBuild(Tester t) {
        Maze m = new Maze(5, 5, 0);
        Cell c1 = m.cellAt(0, 0);
        Cell c2 = m.cellAt(0, 1);
        Cell c3 = m.cellAt(1, 1);
        Edge e1 = new Edge(c1, c2, 20);
        Edge e2 = new Edge(c2, c3, 11);
        ArrayList<Edge> edges = new ArrayList<Edge>();
        edges.add(e1);
        edges.add(e2);
        m.edges = edges;
        
        t.checkExpect(c1.right, null);
        t.checkExpect(c2.left, null);
        t.checkExpect(c2.bottom, null);
        t.checkExpect(c3.top, null);
        
        t.checkExpect(m.nextBuild(), true);
        
        t.checkExpect(c1.right, e1);
        t.checkExpect(c2.left, e1);
        t.checkExpect(c2.bottom, null);
        t.checkExpect(c3.top, null);
        
        t.checkExpect(m.nextBuild(), false);
        
        t.checkExpect(c2.bottom, e2);
        t.checkExpect(c3.top, e2);
    }
    
    // Tests the setWeightScale method for Mazes.
    void testSetWeightScale(Tester t) {
        Maze m = new Maze(5, 5, 0);
        t.checkExpect(m.weightScale, 50);
        m.setWeightScale(20);
        t.checkExpect(m.weightScale, 20);
        m.setWeightScale(85);
        t.checkExpect(m.weightScale, 85);
    }
    
    // Tests various methods of BreadthSolver.
    void testBreadthSolver(Tester t) {
        this.initEdges();
        this.c4.isEnd = true;
        this.c1.right = this.e1;
        this.c2.left = this.e1;
        this.c1.bottom = this.e4;
        this.c3.top = this.e4;
        this.c3.right = this.e3;
        this.c4.left = this.e3;
        BreadthSolver breadth = new BreadthSolver(this.c1);
        
        // Test nextInList
        ArrayList<String> strings = new ArrayList<String>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        t.checkExpect(breadth.nextInList(strings), "a");
        ArrayList<String> result = new ArrayList<String>(Arrays.asList("b", "c"));
        t.checkExpect(strings, result);
        
        // Test nextStep
        t.checkExpect(breadth.nextStep(), false);
        ArrayList<Cell> toVisit = new ArrayList<Cell>();
        toVisit.add(this.c3);
        toVisit.add(this.c2);
        t.checkExpect(breadth.toVisit, toVisit);
        ArrayList<Cell> path = new ArrayList<Cell>();
        path.add(this.c1);
        path.add(this.c1);
        t.checkExpect(breadth.path, path);
        ArrayList<ArrayList<Cell>> paths =
                new ArrayList<ArrayList<Cell>>();
        paths.add(path);
        paths.add(path);
        t.checkExpect(breadth.paths, paths);
        
        // Test nextCell
        t.checkExpect(breadth.nextCell(), this.c3);
        
        // Test handlePath
        breadth.handlePath(this.c3);
        path.add(this.c3);
        t.checkExpect(breadth.path, path);
        
        // Test addCellToQue
        breadth.addCellToQue(this.c3, this.c1);
        toVisit.remove(0);
        toVisit.add(this.c3);
        t.checkExpect(breadth.toVisit, toVisit);
        
        breadth.nextStep();
        breadth.nextStep();
        t.checkExpect(breadth.nextStep(), true);
    }
    
    // Tests various methods of DepthSolver.
    void testDepthSolver(Tester t) {
        this.initEdges();
        this.c4.isEnd = true;
        this.c1.right = this.e1;
        this.c2.left = this.e1;
        this.c1.bottom = this.e4;
        this.c3.top = this.e4;
        this.c3.right = this.e3;
        this.c4.left = this.e3;
        DepthSolver depth = new DepthSolver(this.c1);
        
        // Test nextInList
        ArrayList<String> strings = new ArrayList<String>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        t.checkExpect(depth.nextInList(strings), "c");
        ArrayList<String> result = new ArrayList<String>(Arrays.asList("a", "b"));
        t.checkExpect(strings, result);
        
        // Test nextStep
        t.checkExpect(depth.nextStep(), false);
        ArrayList<Cell> toVisit = new ArrayList<Cell>(Arrays.asList(this.c3, this.c2));
        t.checkExpect(depth.toVisit, toVisit);
        ArrayList<Cell> path = new ArrayList<Cell>(Arrays.asList(this.c1, this.c1));
        t.checkExpect(depth.path, path);
        ArrayList<Cell> parents = new ArrayList<Cell>(Arrays.asList(this.c1, this.c1));
        t.checkExpect(depth.parents, parents);
        
        // Test nextCell
        t.checkExpect(depth.nextCell(), this.c2);
        
        // Test handlePath
        depth.handlePath(this.c2);
        path.add(this.c2);
        t.checkExpect(depth.path, path);
        
        // Test addCellToQue
        depth.addCellToQue(this.c3, this.c1);
        toVisit.remove(1);
        toVisit.add(this.c3);
        parents.remove(1);
        parents.add(this.c1);
        t.checkExpect(depth.toVisit, toVisit);
        t.checkExpect(depth.parents, parents);
        
        depth.nextStep();
        depth.nextStep();
        t.checkExpect(depth.nextStep(), true);
    }
    
    // tests MazeTraveler move
    void testMove(Tester t)
    {
        this.initCells();
        this.initTraveler();
        
        int r1B = m1.row;
        int r2B = m2.row;
        int r3B = m3.row;
        
        int c1B = m1.col;
        int c2B = m2.col;
        int c3B = m3.col;
        
        m1.move("up");
        m2.move("right");
        m3.move("down");
        
        t.checkExpect(0, m1.row);
        t.checkExpect(r2B, m2.row);
        t.checkExpect(0, m3.row);
        t.checkExpect(c1B, m1.col);
        t.checkExpect(-1, m2.col - 1);
        t.checkExpect(c3B, m3.col);
        
        m1.move("left");
        t.checkExpect(0, m1.col);   
    }
    
    // Tests makeEnd and isEndCell methods for Cells.
    void testEndCell(Tester t) {
        this.initCells();
        t.checkExpect(this.c1.isEndCell(), false);
        this.c1.isEnd = true;
        t.checkExpect(this.c1.isEndCell(), true);

        t.checkExpect(this.c2.isEnd, false);
        this.c2.makeEnd();
        t.checkExpect(this.c2.isEnd, true);
    }
    
    // tests moveOnEdge
    void testMoveOnEdge(Tester t)
    {
        this.initCells();
        this.initEdges();
        this.initTraveler();
        
        Cell currX = this.m1.current;
        Cell currB = this.m2.current;
        boolean moving = this.m2.moveOnEdge(this.e1);
        Cell currA = this.m2.current;
        
        t.checkExpect(this.m1.moveOnEdge(this.e7), false);
        Cell currY = this.m1.current;
        t.checkExpect(moving, true);
        t.checkExpect(currX, currY);
        t.checkExpect(currB != currA, true);
    }  
    
    public static void main(String[] args) {
        MazeWorld m = new MazeWorld();

        m.bigBang(MazeWorld.WIDTH, MazeWorld.HEIGHT,
                0.001);
    }
}

/**
 * To test:
 */