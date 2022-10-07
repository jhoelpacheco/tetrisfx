package tetrisfx;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class GuiController {
    
    private static final int BRICK_SIZE =20;
    
    @FXML
    private GridPane gamePanel;
    
    public void initGameView(){
        for(int i=2; i<25; i++){
            for (int j=0; j<10; j++){
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle .setFill(Color.TRANSPARENT);
                gamePanel.add(rectangle, j, i -2);
            }
        }
    }
}
