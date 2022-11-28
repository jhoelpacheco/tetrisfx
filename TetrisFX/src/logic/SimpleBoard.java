package logic;

import java.awt.Point;
import logic.bricks.Brick;
import logic.bricks.RandomBrickGenerator;

public class SimpleBoard {
    private final int width;
    private final int heigth;
    private int[][] currentGameMatrix;
    private final RandomBrickGenerator brickGenerator;
    private Brick brick;
    private int currentShape = 0;
    private Point currentOffset;
    private Score score;
    
    public SimpleBoard(int width, int heigth) {
        this.width = width;
        this.heigth = heigth;
        currentGameMatrix = new int[width][heigth];
        brickGenerator = new RandomBrickGenerator();
        score = new Score();
    }
   
    public boolean createNewBrick(){
        currentShape = 0;
        Brick currentBrick = brickGenerator.getBrick();
        setBrick(currentBrick);
        currentOffset = new Point(3, 0);
        
        return MatrixOperations.intersects(currentGameMatrix,
                getCurrentShape(),
                currentOffset.x,
                currentOffset.y);
    }
    
    public boolean moveBrickdown(){
        Point p = new Point(currentOffset);
        p.translate(0,1);
        currentOffset = p;
        boolean conflict = MatrixOperations.intersects(currentGameMatrix,
                getCurrentShape(),
                p.x,
                p.y);
        
        if(conflict){
            return false;
        }else{
            currentOffset = p;
            return true;
        }
    }
    
    
    public boolean moveDownSpace() {
        
        
        Point p = new Point(currentOffset);
      
        p.translate(0,(howManyDown()-currentOffset.y)-3);
        currentOffset = p;
        boolean conflict = MatrixOperations.intersects(currentGameMatrix,
                getCurrentShape(),
                p.x,
                p.y);
        
        if(conflict){
            return false;
        }else{
            currentOffset = p;
            return true;
        }
    }
    
    public int howManyDown(){
        int howMany = 0;
        int i;
        
        for(i=0; i<getBoardMatrix().length; i++){
            if(getBoardMatrix()[i][currentOffset.x] == 0){
                howMany ++;                
            }else{
                break;
            }
        }
        
        System.out.println("valor de howmany: "+ howMany);
        return howMany;
    }
    
   
        
    public boolean moveBrickLeft() {
        Point p = new Point(currentOffset);
        p.translate(-1,0);
        
        boolean conflict = MatrixOperations.intersects(currentGameMatrix,
                getCurrentShape(),
                p.x,
                p.y);
        
        if(conflict){
            return false;
        }else{
            currentOffset = p;
            return true;
        }
    }
    
    public boolean moveBrickRight() {
        Point p = new Point(currentOffset);
        p.translate(1, 0);

        boolean conflict = MatrixOperations.intersects(currentGameMatrix,
                getCurrentShape(),
                p.x,
                p.y);

        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }
    
    public boolean rotateBrickLeft() {
        NextShapeInfo nextShape = getNextShape();
        boolean conflict = MatrixOperations.intersects(currentGameMatrix,
                nextShape.getShape(),
                currentOffset.x,
                currentOffset.y);
        if (conflict) {
            return false;
        } else {
            setCurrentShape(nextShape.getPosition());
            return true;
        }
    }
    
    public ViewData getViewData(){
        return new ViewData(getCurrentShape(),
                currentOffset.x,
                currentOffset.y,
                brickGenerator.getNextBrick().getBrickMatrix().get(0));
    }
    
    public int[][] getCurrentShape(){
        return this.brick.getBrickMatrix().get(currentShape);
    }

    public void setBrick(Brick brick) {
        this.brick = brick;
        
        currentOffset = new Point(3, 0);
    }

    public Score getScore() {
        return score;
    }
    
    public void newGame() {
        currentGameMatrix = new int[width][heigth];
        score.reset();
        createNewBrick();
    }
    
    public int[][] getBoardMatrix(){
        return currentGameMatrix;
    }

    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix,
                getCurrentShape(),
                currentOffset.x,
                currentOffset.y);
    }

    public NextShapeInfo getNextShape(){
        int nextShape = currentShape;
        nextShape = ++nextShape % brick.getBrickMatrix().size();
        return new NextShapeInfo(brick.getBrickMatrix().get(nextShape), nextShape);
    }

    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = clearRow.getNextMatrix();
        
        return clearRow;
    }

   
}
