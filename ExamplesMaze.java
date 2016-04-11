import tester.*;
import java.util.ArrayList;

// Examples and tests
class ExamplesMaze {
    
    GMember member1;
    GMember member2;
    GMember member3;
    
    Edge e1;
    Edge e2;
    Edge e3;
    Edge e4;
    Edge e5;
    Edge e6;
    
    // Initializes GMember fields.
    void initMembers() {
        this.member1 = new Cell(0, 0);
        this.member2 = new Cell(10, 0);
        this.member3 = new Cell(0, 10);
    }
    
    // Initializes Edge fields.
    void initEdges() {
        Cell c1 = new Cell(0, 0);
        Cell c2 = new Cell(10, 0);
        Cell c3 = new Cell(0, 10);
        Cell c4 = new Cell(10, 10);
        this.e1 = new Edge(c1, c2, 20);
        this.e2 = new Edge(c2, c4, 10);
        this.e3 = new Edge(c3, c4, 60);
        this.e4 = new Edge(c3, c1, 10);
        this.e5 = new Edge(c3, c2, 45);
        this.e6 = new Edge(c1, c4, 5);
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
        t.checkExpect(m.edgeSort(edges), edges);
        edges.add(this.e1);
        t.checkExpect(m.edgeSort(edges), edges);
        edges.add(this.e2);
        edges.add(this.e3);
        edges.add(this.e4);
        edges.add(this.e5);
        sorted.add(this.e2);
        sorted.add(this.e4);
        sorted.add(this.e1);
        sorted.add(this.e5);
        sorted.add(this.e3);
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
    
    // Tests the groupSize method for GLeaders.
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
    
    // Tests the union method for GLeaders.
    void testLeaderUnion(Tester t) {
        initMembers();
        GLeader leader1 = this.member1.leader;
        GLeader leader2 = this.member2.leader;
        GLeader leader3 = this.member3.leader;
        t.checkExpect(leader1, new GLeader(this.member1));
        t.checkExpect(leader2, new GLeader(this.member2));
        t.checkExpect(leader3, new GLeader(this.member3));
        GLeader result1 = new GLeader(this.member2);
        result1.addMember(this.member1);
        GLeader result2 = new GLeader(this.member1);
        result2.members.clear();
        leader1.union(leader2);
        t.checkExpect(leader1, result2);
        t.checkExpect(leader2, result1);
        leader1.union(leader3);
        t.checkExpect(leader3, new GLeader(this.member3));
        t.checkExpect(leader1, result2);
        GLeader result3 = new GLeader(this.member3);
        result3.addMember(member2);
        result3.addMember(member1);
        leader2.union(leader3);
        t.checkExpect(leader2, result2);
        t.checkExpect(leader3, result3);
    }
    
    // Tests the addMember method for GLeaders.
    void testAddMember(Tester t) {
        this.initMembers();
        GLeader leader1 = this.member1.leader;
        ArrayList<GMember> result = new ArrayList<GMember>();
        result.add(this.member1);
        t.checkExpect(leader1.members, result);
        leader1.addMember(this.member3);
        result.add(this.member3);
        t.checkExpect(leader1.members, result);
        leader1.addMember(this.member2);
        result.add(this.member2);
        t.checkExpect(leader1.members, result);
    }
    
    // Tests the kruskal method for Mazes.
    void testKruskal(Tester t) {
        Maze m = new Maze(5, 5);
        ArrayList<Edge> edges = new ArrayList<Edge>();
        ArrayList<Edge> result = new ArrayList<Edge>();
        this.initEdges();
        edges.add(this.e1);
        edges.add(this.e2);
        edges.add(this.e3);
        edges.add(this.e4);
        edges.add(this.e5);
        result.add(this.e2);
        result.add(this.e4);
        result.add(this.e1);
        t.checkExpect(m.kruskal(edges, 4), result);
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
        t.checkExpect(m.kruskal(edges, 4), result);
    }
    
    // Tests the mergeHelp method for Mazes.
    void testMergeHelp(Tester t) {
        Maze m = new Maze(5, 5);
        ArrayList<Edge> edges = new ArrayList<Edge>();
        ArrayList<Edge> result = new ArrayList<Edge>();
        this.initEdges();
        t.checkExpect(m.edgeSort(edges), edges);
        edges.add(this.e1);
        t.checkExpect(m.edgeSort(edges), edges);
        edges.add(this.e2);
        edges.add(this.e3);
        edges.add(this.e4);
        edges.add(this.e5);
        result.add(this.e1);
        result.add(this.e2);
        result.add(this.e4);
        result.add(this.e5);
        result.add(this.e3);
        t.checkExpect(m.mergeHelp(edges, 2, 4, 3), result);
        result.set(0, this.e2);
        result.set(1, this.e1);
        t.checkExpect(m.mergeHelp(edges, 0, 1, 0), result);
    }
}

/**
 * To test:
 *  nothing
 */