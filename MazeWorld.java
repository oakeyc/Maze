// Assignment 10
// Oka Courtney
// okac
// Obermiller Karl
// obermillerk

import java.awt.Color;
import javalib.impworld.*;
import javalib.worldimages.*;

// A world for the maze game.
class MazeWorld extends World {
    static final int ROWS = 60; 
    static final int COLS = 100; // hard-coded constants
    static final int WIDTH = COLS * Cell.SIZE;
    static final int HEIGHT = ROWS * Cell.SIZE;

    // flags for described behavior
    boolean drawVisited;
    boolean drawPath;
    boolean constructAnimate;
    boolean isSolving;
    boolean isConstructing;
    boolean playerEnabled;

    Maze maze;
    MazeTraveler player1;
    ASolver solver;

    // ctor
    MazeWorld() {
        this.drawVisited = false;
        this.drawPath = false;
        this.isSolving = false;
        this.playerEnabled = true;
        this.constructAnimate = false;
        this.maze = new Maze(ROWS, COLS);
        this.initMaze(0);
    }

    @Override
    // Makes the scene for this world.
    public WorldScene makeScene() {
        WorldScene scene = this.maze.draw(this.getEmptyScene(), this.drawVisited, this.drawPath);
        if (this.playerEnabled) { // draws player on
            scene = this.player1.draw(scene);
        }
        if (this.player1.solved || this.solver.solved) { // the end visual
            WorldImage text = 
                    new AboveImage(
                            new TextImage("The maze has been solved!", COLS / 1.5, Color.RED),
                            new TextImage("Wrong cells visited: " +
                                    this.maze.getWrongVisitedCells(), COLS / 2, Color.RED));
            int boxWidth = 2 * WIDTH / 3;
            int boxHeight = HEIGHT / 6;
            Color c = new Color(1f, 1f, 1f, 0.8f);
            WorldImage box = new RectangleImage(boxWidth, boxHeight, "solid", c);
            box = new OverlayImage(new RectangleImage(boxWidth, boxHeight, "outline", Color.BLACK),
                    box);
            WorldImage solved = new OverlayImage(text, box);
            scene.placeImageXY(solved, WIDTH / 2, HEIGHT / 2);
        }
        return scene;
    }

    // Initializes a maze of the given type.
    void initMaze(int type)
    {
        this.drawVisited = false;
        this.drawPath = false;
        this.isSolving = false;
        this.isConstructing = true;
        this.playerEnabled = false;
        this.maze.makeMaze(type);
        this.solver = new DepthSolver(this.maze.cellAt(0,0));
        if (!this.constructAnimate) {
            while(this.isConstructing) {
                this.onTick();
            }
        }
        this.player1 = new MazeTraveler(0, 0, this.maze.cellAt(0, 0));
    }

    // handles the next state of the world
    @Override
    public void onTick()
    {
        if (this.isConstructing && !this.maze.nextBuild()) {
            // Find the solution, then reset wasVisited of the visited cells.
            this.solver = new DepthSolver(this.maze.cellAt(0, 0));
            while(!this.solver.solved) {
                solver.nextStep();
            }
            this.solver = new DepthSolver(this.maze.cellAt(0, 0));
            this.maze.clearVisited();
            this.isConstructing = false;
            this.playerEnabled = true;
        }
        else if (this.isSolving && !this.solver.solved && this.solver.nextStep())
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
        else if (this.playerEnabled && !this.player1.solved &&
                (key.equals("left") || key.equals("right")
                || key.equals("up") || key.equals("down"))) {
            if (this.player1.move(key)) {
                this.drawPath = true;
                this.drawVisited = true;
            }
        }
        // Solve the maze from the player's current position.
        // Depth first.
        else if (key.equals("d") && !this.isConstructing && !this.isSolving)
        {
            this.isSolving = true;
            this.drawVisited = true;
            this.drawPath = false;
            this.maze.clearVisited();
            this.maze.clearPath();
            this.playerEnabled = false;
            this.solver = new DepthSolver(this.player1.current);
        }
        // Breadth first.
        else if (key.equals("b") && !this.isConstructing && !this.isSolving)
        {
            this.isSolving = true;
            this.drawVisited = true;
            this.drawPath = false;
            this.maze.clearVisited();
            this.maze.clearPath();
            this.playerEnabled = false;
            this.solver = new BreadthSolver(this.player1.current);
        }
        // shows construction on the maze
        else if (key.equals("c") && !this.isConstructing) {
            this.constructAnimate = !this.constructAnimate;
        }
        // Skips solving animation.
        else if (key.equals("i")) {
            while(this.isConstructing || this.isSolving) {
                this.onTick();
            }
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
