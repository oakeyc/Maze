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
        this.drawVisited = false;
        this.drawPath = false;
        this.isSolving = false;
        this.playerEnabled = true;
        this.maze = new Maze(ROWS, COLS);
        this.player1 = new Player(0, 0, this.maze.cellAt(0, 0));
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

    // Initializes a maze of the given type.
    void initMaze(int type)
    {
        this.drawVisited = false;
        this.drawPath = false;
        this.isSolving = false;
        this.playerEnabled = true;
        this.maze.makeMaze(type);
        this.player1 = new Player(0, 0, this.maze.cellAt(0, 0));
        this.solver = new DepthSolver(this.maze.cellAt(0, 0));
        while(!this.solver.solved) {
            solver.nextStep();
        }
    }

    @Override
    public void onTick()
    {
        if (this.isSolving && !this.solver.solved && this.solver.nextStep())
        {
            this.isSolving = false;
            this.drawPath = true;
        }
    }

    // Handles key presses.
    @Override
    public void onKeyEvent(String key) {
        // Generate a new random maze.
        if (key.equals("r")) {
            initMaze(0);
        }
        // Generate a random vertical maze.
        else if (key.equals("t")) {
            initMaze(-1);
        }
        // Generate a random horizontal maze.
        else if (key.equals("y")) {
            initMaze(1);
        }
        else if (key.equals("v") && !this.isSolving) {
            this.drawVisited = !this.drawVisited;
        }
        // Player movement
        else if (this.playerEnabled &&
                (key.equals("left") || key.equals("right")
                || key.equals("up") || key.equals("down"))) {
            this.player1.move(key);
        }
        // Solve the maze
        // Depth first.
        else if (key.equals("d") && !this.isSolving)
        {
            this.isSolving = true;
            this.drawVisited = true;
            this.drawPath = false;
            this.maze.clearVisited();
            this.playerEnabled = false;
            this.solver = new DepthSolver(this.maze.cellAt(0, 0));
        }
        // Breadth first.
        else if (key.equals("b") && !this.isSolving)
        {
            this.isSolving = true;
            this.drawVisited = true;
            this.drawPath = false;
            this.maze.clearVisited();
            this.playerEnabled = false;
            this.solver = new BreadthSolver(this.maze.cellAt(0, 0));
        }
        // Skips solving animation.
        else if (key.equals("i") && this.isSolving) {
            while (!this.solver.solved) {
                this.onTick();
            }
        }
        // Go back to playing the maze if it's been solved.
        else if (key.equals("p") && !this.playerEnabled && !this.isSolving) {
            this.maze.clearVisited();
            this.drawVisited = false;
            this.drawPath = false;
            this.playerEnabled = true;
        }
        // Make vertical and horizontal mazes more or less likely to have respective passages.
        else if (key.equals("1") || key.equals("2") || key.equals("3") ||
                 key.equals("4") || key.equals("5") || key.equals("6") ||
                 key.equals("7") || key.equals("8") || key.equals("9")) {
            this.maze.setWeightScale(10 * Integer.parseInt(key));
        }
        else if (key.equals("0")) {
            this.maze.setWeightScale(100);
        }
    }
}
