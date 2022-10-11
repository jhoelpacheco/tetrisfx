package tetrisfx;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

//SETS UP THE DIMENSIONS OF THE GRID BY DECLARING RECTANGLES [10x22]
public class GuiController {
    
    //cells size [20px]
    private static final int CELLS_SIZE =20;
    
    @FXML
    private GridPane gamePanel;
    
    public void initGameView(){
        for(int i=0; i<22; i++){
            for (int j=0; j<10; j++){
                Rectangle rectangle = new Rectangle(CELLS_SIZE, CELLS_SIZE);
                
                //background color and cell margin
                rectangle.setFill(Color.rgb(100, 100, 100));
                rectangle.setStyle("-fx-stroke: black; -fx-stroke-width: 0.5;");
                
                gamePanel.add(rectangle, j, i);
            }
        }
    }
}
