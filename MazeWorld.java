import javalib.impworld.*;
import javalib.worldimages.*;

// A world for the maze game.
class MazeWorld extends World {
    static final int ROWS = 60;
    static final int COLS = 100;
    static final int WIDTH = COLS * Cell.SIZE;
    static final int HEIGHT = ROWS * Cell.SIZE;

    boolean drawVisited;
    boolean drawPath;
    boolean isSolving;
    boolean playerEnabled;

    Maze maze;
    Player player1;
    ASolver solver;

    MazeWorld() {
        this.maze = new Maze(ROWS, COLS);
        this.initMaze(0);
        this.player1 = new Player(0, 0, this.maze.cellAt(0, 0));
        this.drawVisited = false;
        this.drawPath = false;
    }

    @Override
    // Makes the scene for this world.
    public WorldScene makeScene() {
        WorldScene scene = this.maze.draw(this.getEmptyScene(), this.drawVisited, this.drawPath);
        if (this.playerEnabled) {
            scene = this.player1.draw(scene);
        }
        return scene;
    }

    void initMaze(int type)
    {
        this.drawVisited = false;
        this.drawPath = false;
        this.isSolving = false;
        this.playerEnabled = true;
        this.maze.makeMaze(type);
        this.player1 = new Player(0, 0, this.maze.cellAt(0, 0));
    }

    @Override
    public void onTick()
    {
        if (this.isSolving && !this.maze.isSolved && this.solver.nextStep())
        {
            this.maze.isSolved = true;
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
        else if (this.playerEnabled &&
                (key.equals("left") || key.equals("right")
                || key.equals("up") || key.equals("down"))) {
            this.player1.move(key);
        }
        else if (key.equals("d") && !this.isSolving)
        {
            this.isSolving = true;
            this.drawVisited = true;
            this.drawPath = true;
            this.maze.clearVisited();
            this.playerEnabled = false;
            this.solver = new DepthSolver(this.maze.cellAt(0, 0));
        }
        else if (key.equals("b") && !this.isSolving)
        {
            this.isSolving = true;
            this.drawVisited = true;
            this.drawPath = true;
            this.maze.clearVisited();
            this.playerEnabled = false;
            this.solver = new BreadthSolver(this.maze.cellAt(0, 0));
        }
    }
}
