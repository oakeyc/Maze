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
    boolean drawDepth;
    boolean drawBreadth;

    Maze maze;
    Player player1;

    MazeWorld() {
        this.maze = new Maze(ROWS, COLS);
        this.player1 = new Player(0, 0, this.maze.cellAt(0, 0));
        this.drawVisited = false;
        this.drawPath = false;
    }

    @Override
    // Makes the scene for this world.
    public WorldScene makeScene() {
        WorldScene scene = this.maze.draw(this.getEmptyScene(), this.drawVisited, this.drawPath);
        scene = this.player1.draw(scene);
        return scene;
    }

    void initMaze(int type)
    {
        this.drawVisited = false;
        this.drawPath = false;
        this.maze.makeMaze(type);
        this.player1 = new Player(0, 0, this.maze.cellAt(0, 0));
    }

    @Override
    public void onTick()
    {
        if (this.drawDepth)
        {
            this.maze.depthSolve(player1.row, player1.col);
        }
    }

    // Handles key presses.
    @Override
    public void onKeyEvent(String key) {
        // Generate a new random maze.
        if (key.equals("r")) {
            initMaze(0);
        }
        else if (key.equals("t")) {
            initMaze(-1);
        }
        else if (key.equals("y")) {
            initMaze(1);
        }
        else if (key.equals("v")) {
            this.drawVisited = !this.drawVisited;
        }
        else if (key.equals("left") || key.equals("right")
                || key.equals("up") || key.equals("down")) {
            this.player1.move(key);
        }
        else if (key.equals("d"))
        {
            this.drawVisited = true;
            this.drawDepth = true;
            this.drawPath = true;
        }
        else if (key.equals("b"))
        {
            this.maze.breadthSolve(player1.row, player1.col);
            this.drawPath = true;
        }
    }
}
