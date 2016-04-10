import tester.*;
import java.util.ArrayList;

// Examples and tests
class ExamplesMaze {
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
        Cell cell = new Cell(0, 0);
        Edge e1 = new Edge(cell, cell, 20);
        Edge e2 = new Edge(cell, cell, 10);
        Edge e3 = new Edge(cell, cell, 60);
        Edge e4 = new Edge(cell, cell, 10);
        Edge e5 = new Edge(cell, cell, 45);
        t.checkExpect(m.edgeSort(edges), edges);
        edges.add(e1);
        t.checkExpect(m.edgeSort(edges), edges);
        edges.add(e2);
        edges.add(e3);
        edges.add(e4);
        edges.add(e5);
        sorted.add(e2);
        sorted.add(e4);
        sorted.add(e1);
        sorted.add(e5);
        sorted.add(e3);
        t.checkExpect(m.edgeSort(edges), sorted);
    }
    
    // Tests the setLeader method for GMembers.
    void testSetLeader(Tester t) {
        GMember member1 = new Cell(0, 0);
        GMember member2 = new Cell(10, 10);
        GLeader leader1 = member1.leader;
        GLeader leader2 = member2.leader;
        t.checkExpect(member1.leader, leader1);
        t.checkExpect(member2.leader, leader2);
        member1.setLeader(member2.leader);
        t.checkExpect(member1.leader, leader2);
        t.checkExpect(member2.leader, leader2);
        member2.setLeader(member1.leader);
        t.checkExpect(member2.leader, leader2);
    }
    
    // Tests the setLeader method for GMembers.
    void testResetLeader(Tester t) {
        GMember member1 = new Cell(0, 0);
        GMember member2 = new Cell(10, 10);
        GLeader leader1 = member1.leader;
        GLeader leader2 = member2.leader;
        t.checkExpect(member1.leader, leader1);
        t.checkExpect(member2.leader, leader2);
        member1.setLeader(member2.leader);
        t.checkExpect(member1.leader, leader2);
        t.checkExpect(member2.leader, leader2);
        member1.resetLeader();
        t.checkExpect(member1.leader, new GLeader(member1));
        t.checkExpect(member2.leader, leader2);
        member2.resetLeader();
        t.checkExpect(member2.leader, new GLeader(member2));
    }
}

/**
 * To test:
 *  GMember.find();
 *  GMember.union();
 *  
 *  GLeader.groupSize();
 *  GLeader.union();
 *  GLeader.addMember();
 *  
 *  Maze.kruskal();
 *  Maze.mergeHelp();
 */