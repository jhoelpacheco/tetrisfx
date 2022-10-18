package logic;

import logic.bricks.Brick;
import logic.bricks.BrickGenerator;

public class SimpleBoard {
    private final int width;
    private final int heigth;
    private int[][] currentGameMatrix;
    private BrickGenerator brickGen;
    private Brick brick;
    private int currentShape = 0;
    
    public SimpleBoard(int width, int heigth){
        this.width = width;
        this.heigth = heigth;
        currentGameMatrix = new int[width][heigth];
        brickGen = new BrickGenerator();
    }
    
    public boolean newBrick(){
        Brick currentBrick = brickGen.getBrick();
        setBrick(currentBrick);
        return true;
    }

    public void setBrick(Brick brick) {
        this.brick = brick;
    }
    
    public int[][] getCurrentShape(){
        return this.brick.getBrickMatrix().get(currentShape);
    }
    
    public int[][] getBoardMatrix(){
        return currentGameMatrix;
    }
}
