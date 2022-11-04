package tetrisfx;

import java.net.URL;
import java.util.ResourceBundle;

import Events.EventSource;
import Events.EventType;
import Events.MoveEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import logic.DownData;
import logic.ViewData;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.Initializable;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;
import logic.InputEventListener;

//SETS UP THE DIMENSIONS OF THE GRID BY DECLARING RECTANGLES [10x22]
public class GuiController implements Initializable{
    
    //cells size [20px]
    private static final int BRICK_SIZE = 20;

    Timeline timeLine;
    private InputEventListener eventLister;

    private Rectangle[][] displayMatrix;
    private Rectangle[][] rectangles;
    
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

        rectangles = new Rectangle[viewData.getBrickData().length][viewData.getBrickData()[0].length];

        for(int i = 0; i < viewData.getBrickData().length; i++)
        {
            for(int j = 0; j < viewData.getBrickData()[i].length; j++)
            {
                    Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                    rectangle.setFill(getFillColor(viewData.getBrickData()[i][j]));
                    rectangles[i][j] = rectangle;
                    brickPanel.add(rectangle, j, i);
            }
        }
        
        brickPanel.setLayoutX(gamePanel.getLayoutX() + viewData.getXPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + viewData.getYPosition() * BRICK_SIZE);
        
        timeLine = new Timeline(new KeyFrame(Duration.millis(200),
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))));
        timeLine.setCycleCount(Timeline.INDEFINITE);
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
    
    private void moveDown(MoveEvent event) {
        ViewData viewData = eventLister.onDownEvent(event);
        refreshBrick(viewData);
    }
    
    private void refreshBrick(ViewData viewData)
    {
        brickPanel.setLayoutX(gamePanel.getLayoutX() + viewData.getXPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + viewData.getYPosition() * BRICK_SIZE);

       for(int i = 0; i < viewData.getBrickData().length; i++){
           for(int j = 0; j < viewData.getBrickData()[i].length; j++){
               setRectangleData(viewData.getBrickData()[i][j],rectangles[i][j]);
           }
       }

    }

    private void setRectangleData(int i, Rectangle rectangle) {
    }

    public void setEventLister(InputEventListener eventLister) { this.eventLister = eventLister; }
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.D){
                    moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                    event.consume();
                }
                if(event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A){
                    refreshBrick(eventLister.onLeftEvent());
                    event.consume();
                }
            }
        });
        Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
        scoreValue.setEffect(reflection);
    }

    public void refreshGameBackground(int[][] boardMatrix) {
    }
}
