import tester.*;
import java.util.ArrayList;

// Examples and tests
class ExamplesMaze {
    
    GMember member1;
    GMember member2;
    GMember member3;
    
    // Initializes GMember fields.
    void initMembers() {
        this.member1 = new Cell(0, 0);
        this.member2 = new Cell(10, 0);
        this.member3 = new Cell(0, 10);
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
        this.initMembers();
        GLeader leader1 = this.member1.leader;
        GLeader leader2 = this.member2.leader;
        t.checkExpect(this.member1.leader, leader1);
        t.checkExpect(this.member2.leader, leader2);
        this.member1.setLeader(this.member2.leader);
        t.checkExpect(this.member1.leader, leader2);
        t.checkExpect(this.member2.leader, leader2);
        this.member2.setLeader(member1.leader);
        t.checkExpect(this.member2.leader, leader2);
    }
    
    // Tests the setLeader method for GMembers.
    void testResetLeader(Tester t) {
        this.initMembers();
        GLeader leader1 = this.member1.leader;
        GLeader leader2 = this.member2.leader;
        t.checkExpect(this.member1.leader, leader1);
        t.checkExpect(this.member2.leader, leader2);
        this.member1.setLeader(this.member2.leader);
        t.checkExpect(this.member1.leader, leader2);
        t.checkExpect(this.member2.leader, leader2);
        this.member1.resetLeader();
        t.checkExpect(this.member1.leader, new GLeader(this.member1));
        t.checkExpect(this.member2.leader, leader2);
        this.member2.resetLeader();
        t.checkExpect(this.member2.leader, new GLeader(this.member2));
    }
    
    // Tests the find method for GMembers.
    void testFind(Tester t) {
        initMembers();
        this.member2.setLeader(this.member3.leader);
        t.checkExpect(this.member1.find(this.member2), false);
        t.checkExpect(this.member2.find(this.member1), false);
        t.checkExpect(this.member2.find(this.member3), true);
        t.checkExpect(this.member3.find(this.member2), true);
    }
    
    // Tests the union method for GMembers.
    void testMemberUnion(Tester t) {
        initMembers();
        t.checkExpect(this.member1.leader, new GLeader(this.member1));
        t.checkExpect(this.member2.leader, new GLeader(this.member2));
        this.member1.union(member2);
        GLeader result = new GLeader(this.member2);
        result.addMember(this.member1);
        t.checkExpect(this.member1.leader, result);
        t.checkExpect(this.member2.leader, result);
        t.checkExpect(this.member3.leader, new GLeader(this.member3));
        result.addMember(this.member3);
        this.member3.union(this.member1);
        t.checkExpect(this.member1.leader, result);
        t.checkExpect(this.member2.leader, result);
        t.checkExpect(this.member3.leader, result);
        this.member2.leader.members.remove(this.member3);
        this.member3.resetLeader();
        this.member1.union(this.member3);
    }
    
    // Tests the union method for GMembers.
    void testGroupSize(Tester t) {
        initMembers();
        GLeader leader1 = this.member1.leader;
        GLeader leader2 = this.member2.leader;
        t.checkExpect(leader1.groupSize(), 1);
        t.checkExpect(leader2.groupSize(), 1);
        leader1.union(leader2);
        t.checkExpect(leader1.groupSize(), 0);
        t.checkExpect(leader2.groupSize(), 2);
        leader2.addMember(this.member3);
        t.checkExpect(leader2.groupSize(), 3);
    }
}

/**
 * To test:
 *  GLeader.groupSize();
 *  GLeader.union();
 *  GLeader.addMember();
 *  
 *  Maze.kruskal();
 *  Maze.mergeHelp();
 */