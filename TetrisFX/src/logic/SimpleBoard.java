package logic;

import bricks.RandomBrickGenerator;
import java.awt.Point;
import bricks.Brick;

public class SimpleBoard 
{
    private final int width;
    private final int height;
    private int[][] currentGameMatrix;
    private final RandomBrickGenerator brickGenerator;
    private Brick brick;
    private int currentShape = 0;
    private Point currentOffSet;
    private Score score;
    
    public SimpleBoard(int width, int height){
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[width][height];
        brickGenerator = new RandomBrickGenerator();
        score = new Score();
    }
    
    public void setBrick(Brick brick){ 
        this.brick = brick;
        currentOffSet = new Point(3,0);
    }
    
    public int[][] getCurrentShape(){ return this.brick.getBrickMatrix().get(currentShape); }
    
    public boolean createNewBrick(){
        Brick currentBrick = brickGenerator.getBrick();
        setBrick(currentBrick);
        currentOffSet = new Point(3, 0);
        return true;
    }
    
    public int[][] getBoardMatrix(){
        return currentGameMatrix; 
    }
    
    public Score getScore(){ return score; }
    
    public ViewData getViewData(){ 
        return new ViewData(getCurrentShape(),
                            currentOffSet.x,
                            currentOffSet.y);
    }
    
    public boolean moveBrickDown(){
        Point p = new Point(currentOffSet);
        p.translate(0, 1);
        currentOffSet = p;
        boolean conflict = MatrixOperations.intersects(currentGameMatrix, getCurrentShape(), p.x, p.y);
        if (conflict) {
            return false;
        } else {
            currentOffSet = p;
            return true;
        }
    }

    public void mergeBrickToBackground(){
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix,
                getCurrentShape(),
                currentOffSet.x,
                currentOffSet.y);
        /*for(int i=0 ; i<currentGameMatrix.length; i++){
            System.out.println();
            for(int j=0; j<currentGameMatrix[i].length ;j++ ){
                System.out.print(currentGameMatrix[i][j]);
            }
        }*/
    }

    public boolean moveBrickLeft() {
        Point p = new Point(currentOffSet);
        p.translate(-1,0);

        currentOffSet = p;
        boolean conflict = MatrixOperations.intersects(currentGameMatrix, getCurrentShape(), p.x, p.y);
        if (conflict) {
            return false;
        } else {
            currentOffSet = p;
            return true;
        }

    }

    public boolean moveBrickRight() {
        Point p = new Point(currentOffSet);
        p.translate(1,0);

        currentOffSet = p;
        boolean conflict = MatrixOperations.intersects(currentGameMatrix, getCurrentShape(), p.x, p.y);
        if (conflict) {
            return false;
        } else {
            currentOffSet = p;
            return true;
        }
    }
}