import javalib.impworld.*;
import javalib.worldimages.*;

// A world for the maze game.
class MazeWorld extends World {
    static final int ROWS = 20;
    static final int COLS = 40;
    static final int WIDTH = COLS * Cell.SIZE;
    static final int HEIGHT = ROWS * Cell.SIZE;
    
    boolean drawVisited;
    boolean drawPath;
    
    Maze maze;
    
    MazeWorld() {
        this.maze = new Maze(ROWS, COLS);
    }
    
    @Override
    // Makes the scene for this world.
    public WorldScene makeScene() {
        return maze.draw(this.getEmptyScene(), true, true);
    }

    // Handles key presses.
    @Override
    public void onKeyEvent(String key) {
        // Generate a new random maze.
        if (key.equals("r")) {
            maze.makeMaze(0);
        }
        if (key.equals("v")) {
            maze.makeMaze(-1);
        }
        if (key.equals("h")) {
            maze.makeMaze(1);
        }
    }
}
