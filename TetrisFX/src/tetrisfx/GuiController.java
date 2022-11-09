package tetrisfx;

import Events.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import logic.DownData;
import logic.ViewData;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.beans.property.IntegerProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    
    public void initGameView(int[][] boardMatrix, ViewData viewData){
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 0; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i);
            }
        }

        rectangles = new Rectangle[viewData.getBrickData().length][viewData.getBrickData()[0].length];

        for(int i = 0; i < viewData.getBrickData().length; i++){
            for(int j = 0; j < viewData.getBrickData()[i].length; j++){
                    Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                    rectangle.setFill(getFillColor(viewData.getBrickData()[i][j]));
                    rectangles[i][j] = rectangle;
                    brickPanel.add(rectangle, j, i);
            }
        }
        
        brickPanel.setLayoutX(gamePanel.getLayoutX() + viewData.getXPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(
                -42 + gamePanel.getLayoutY() + (viewData.getYPosition() * BRICK_SIZE) + viewData.getYPosition());
        
        timeLine = new Timeline(new KeyFrame(Duration.millis(200),
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }
    
    public Paint getFillColor(int i){
        Paint returnPaint;
        
        returnPaint = switch (i) {
            case 0 -> Color.TRANSPARENT;
            case 1 -> Color.YELLOW;
            case 2 -> Color.CYAN;
            case 3 -> Color.BLUE;
            case 4 -> Color.ORANGE;
            case 5 -> Color.GREEN;
            case 6 -> Color.PINK;
            case 7 -> Color.RED;
            default -> Color.WHITE;
        };
        return returnPaint;
    }
    
    public void bindScore(IntegerProperty integerProperty){
        scoreValue.textProperty().bind(integerProperty.asString());
    }
    
    private void moveDown(MoveEvent event){
        DownData viewData = eventLister.onDownEvent(event);
        refreshBrick(viewData.getViewData());
    }
    
    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }
    
    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }
    
    private void refreshBrick(ViewData viewData){
        brickPanel.setLayoutX(gamePanel.getLayoutX() + viewData.getXPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(
                -42 + gamePanel.getLayoutY() + (viewData.getYPosition() * BRICK_SIZE) + viewData.getYPosition());
        
        for(int i=0 ; i< viewData.getBrickData().length; i++){
            for(int j=0 ;j<viewData.getBrickData()[i].length ;j++ ){
                setRectangleData(viewData.getBrickData()[i][j], rectangles[i][j]);
            }
        }
    }
    
    public void setEventLister(InputEventListener eventLister) { 
        this.eventLister = eventLister; 
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W){
                    refreshBrick(eventLister.onRotateEvent());
                    event.consume();
                }
                if(event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S){
                    moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                    event.consume();
                }
                if(event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A){
                    refreshBrick(eventLister.onLeftEvent());
                    event.consume();
                }
                if(event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D){
                    refreshBrick(eventLister.onRightEvent());
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
}
