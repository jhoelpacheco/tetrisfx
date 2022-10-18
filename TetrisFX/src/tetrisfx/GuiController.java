package tetrisfx;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;


//SETS UP THE DIMENSIONS OF THE GRID BY DECLARING RECTANGLES [10x22]
public class GuiController {
    
    //cells size [20px]
    private static final int CELLS_SIZE =20;
    
    @FXML
    private GridPane gamePanel;
    
    @FXML
    private GridPane brickPanel;
    
    public void initGameView(int[][] boardMatrix, int[][] brick){
        for(int i=0; i<boardMatrix.length; i++){
            for (int j=0; j<boardMatrix[i].length; j++){
                Rectangle rectangle = new Rectangle(CELLS_SIZE, CELLS_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                gamePanel.add(rectangle, j, i);
            }
        }
        for(int i=0; i<brick.length; i++){
            for(int j=0; j<brick[i].length; j++){
                Rectangle rectangle = new Rectangle(CELLS_SIZE, CELLS_SIZE);
                rectangle.setFill(getFillColor(brick[i][j]));                       
                brickPanel.add(rectangle, j, i);
            }
        }
    }
    //module that is responsible for painting the bricks
    public Paint getFillColor(int i){
        Paint paint;
        switch(i){
            case 0:
                paint = Color.TRANSPARENT;
                break;
            case 1: 
                paint = Color.YELLOW;
                break;
            case 2:
                paint = Color.LIGHTBLUE;
                break;
            case 3:
                paint = Color.BLUE;
                break;
            case 4:
                paint = Color.ORANGE;
                break;
            case 5:
                paint = Color.GREEN;
                break;
            case 6:
                paint = Color.PURPLE;
                break;
            case 7:
                paint = Color.RED;
                break;
            default: 
                paint = Color.WHITE;
                break;
        }
        return paint;
    }
}
