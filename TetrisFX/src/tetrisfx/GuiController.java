package tetrisfx;

//import java.net.URL;
//import java.util.ResourceBundle;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import logic.ViewData;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.beans.property.IntegerProperty;
//import javafx.scene.effect.Reflection;
import logic.InputEventListener;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;
import logic.InputEventListener;

//SETS UP THE DIMENSIONS OF THE GRID BY DECLARING RECTANGLES [10x22]
public class GuiController {
    
    //cells size [20px]
    private static final int BRICK_SIZE = 20;

    Timeline timeLine;
    private InputEventListener eventLister;
    
    @FXML
    private GridPane gamePanel;
    
    @FXML
    private GridPane brickPanel;
    
    @FXML
    private Text scoreValue;
    
    public void initGameView(int[][] boardMatrix, ViewData viewData)
    {
        for(int i = 0; i< boardMatrix.length; i++)
        {
            for (int j = 0; j < boardMatrix[i].length; j++)
            {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                
                //background color and cell margin
                rectangle.setFill(Color.TRANSPARENT);
                //rectangle.setStyle("-fx-stroke: #008800; -fx-stroke-width: 0.5;");
                
                gamePanel.add(rectangle, j, i);
            }
        }

        for(int i = 0; i < viewData.getBrickData().length; i++)
        {
            for(int j = 0; j < viewData.getBrickData()[i].length; j++)
            {
                    Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                    rectangle.setFill(getFillColor(viewData.getBrickData()[i][j]));
                    brickPanel.add(rectangle, j, i);
            }
        }
        
        brickPanel.setLayoutX(gamePanel.getLayoutX() + viewData.getXPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + viewData.getYPosition() * BRICK_SIZE);
        
        timeLine = new Timeline(new KeyFrame(Duration.millis(200), ae -> moveDown()));
        timeLine.setCycleCount(21);
        timeLine.play();
    }
    
    public Paint getFillColor(int i)
    {
        Paint returnPaint;
        
        switch(i)
        {
            case 0: returnPaint = Color.TRANSPARENT;
                    break;
            case 1: returnPaint = Color.YELLOW;
                    break;
            case 2: returnPaint = Color.CYAN;
                    break;
            case 3: returnPaint = Color.BLUE;
                    break;
            case 4: returnPaint = Color.ORANGE;
                    break;
            case 5: returnPaint = Color.GREEN;
                    break;
            case 6: returnPaint = Color.PINK;
                    break;
            case 7: returnPaint = Color.RED;
                    break;
            default:returnPaint = Color.WHITE;
                    break;
        }
        return returnPaint;
    }
    
    public void bindScore(IntegerProperty integerProperty)
    {
        scoreValue.textProperty().bind(integerProperty.asString());
    }
    
    private void moveDown()
    {
        ViewData viewData = eventLister.onDownEvent();
        refreshBrick(viewData);
    }
    
    private void refreshBrick(ViewData viewData)
    {
        brickPanel.setLayoutX(gamePanel.getLayoutX() + viewData.getXPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + viewData.getYPosition() * BRICK_SIZE);
    }
    
    public void setEventLister(InputEventListener eventLister) { this.eventLister = eventLister; }
    
    //@Override
    /*
    public void initialize(URL location, ResourceBundle resources)
    {
        Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
        scoreValue.setEffect(reflection);
    }*/
}
