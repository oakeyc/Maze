import tester.*;

// Examples and tests
class ExamplesMaze {
    // Runs the game.
    void testMaze(Tester t) {
        MazeWorld world = new MazeWorld();
        world.bigBang(MazeWorld.WIDTH, MazeWorld.HEIGHT);
    }
}

/**
 * To test:
 *  GMember.resetLeader();
 *  GMember.setLeader();
 *  GMember.find();
 *  GMember.union();
 *  
 *  GLeader.groupSize();
 *  GLeader.union();
 *  GLeader.addMember();
 */