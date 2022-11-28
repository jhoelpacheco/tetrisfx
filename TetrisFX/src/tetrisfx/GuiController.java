package tetrisfx;


import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.DownData;
import logic.ViewData;
import logic.events.EvenType;
import logic.events.EventSource;
import logic.events.InputEventListener;
import logic.events.MoveEvent;


public class GuiController implements Initializable{
    
    private static final int BRICK_SIZE = 20;
    Timeline timeLine;
    private InputEventListener eventListener;
    private Rectangle[][] displayMatrix;
    private Rectangle[][] rectangles;
    
    private BooleanProperty isPause = new SimpleBooleanProperty();
    private BooleanProperty isGameOver = new SimpleBooleanProperty();
    
    @FXML
    private ToggleButton pauseButton;
    @FXML
    private GridPane gamePanel;
    @FXML
    private GridPane nextBrick;
    @FXML
    private GridPane brickPanel;
    @FXML
    private Text scoreValue;
    @FXML
    private Group groupNotification;
    @FXML
    private GameOverPanel gameOverPanel;
    @FXML
    private ListView<String> scoreList;
        
    public void initGameView(int[][] boardMatrix, ViewData viewData){
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for(int i=2 ; i<boardMatrix.length; i++){
            for(int j = 0; j<boardMatrix[i].length; j++){
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i-2);
                
            }
        }
        
        rectangles = new Rectangle[viewData.getBrickData().length][viewData.getBrickData()[0].length];
        
        for(int i=0; i<viewData.getBrickData().length; i++){
            for(int j=0; j<viewData.getBrickData()[i].length ; j++){
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(getFillColor(viewData.getBrickData()[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }

        brickPanel.setLayoutX(gamePanel.getLayoutX() + viewData.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 +gamePanel.getLayoutY() 
                + (viewData.getyPosition() * BRICK_SIZE) + viewData.getyPosition());
        
        generatePreviewPanel(viewData.getNextBrickData());
        
        timeLine = new Timeline(new KeyFrame(Duration.millis(400),
                ae -> moveDown(new MoveEvent(EvenType.DOWN, EventSource.THREAD))));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
        
       
    }
    
    private void generatePreviewPanel(int[][] nextBrickData){
        nextBrick.getChildren().clear();
        for(int i=0; i<nextBrickData.length; i++){
            for(int j=0; j<nextBrickData[i].length ; j++){
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                setRectangleData(nextBrickData[i][j], rectangle);
                if(nextBrickData[i][j] != 0){
                    nextBrick.add(rectangle, j, i);
                }
            }   
        }
    }
    
    public void refreshGameBackground(int[][] board){
        for(int i=2 ; i<board.length; i++){
            for(int j=0; j<board[i].length ;j++){
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    public void setEventLister(InputEventListener eventLister) {
        this.eventListener = eventLister;
    }
    
    public void bindScore(IntegerProperty integerProperty){
        scoreValue.textProperty().bind(integerProperty.asString());
    }
            
    private void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = eventListener.onDownEvent(event);
            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + downData.getClearRow().getScoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            refreshBrick(downData.getViewData());
        }
        gamePanel.requestFocus();
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

    private void refreshBrick(ViewData viewData) {
        brickPanel.setLayoutX(gamePanel.getLayoutX() + viewData.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 +gamePanel.getLayoutY() 
                + (viewData.getyPosition() * BRICK_SIZE) + viewData.getyPosition());
        
        for(int i=0 ;i<viewData.getBrickData().length ;i++){
            for(int j= 0; j<viewData.getBrickData()[i].length; j++){
                setRectangleData(viewData.getBrickData()[i][j], rectangles[i][j]);
            }
        }
        
        generatePreviewPanel(viewData.getNextBrickData());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
                    if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
                        refreshBrick(eventListener.onRotateEvent());
                        event.consume();
                    }
                    if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
                        moveDown(new MoveEvent(EvenType.DOWN, EventSource.USER));
                        event.consume();
                    }
                    if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
                        refreshBrick(eventListener.onLeftEvent());
                        event.consume();
                    }
                    if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                        refreshBrick(eventListener.onRightEvent());
                        event.consume();
                    }
                    if (event.getCode() == KeyCode.SPACE){
                        refreshBrick(eventListener.onDownSpace());
                        event.consume();
                    }
                }
                if (event.getCode() == KeyCode.P) {
                    pauseButton.selectedProperty().setValue(!pauseButton.selectedProperty().getValue());
                }
                if (event.getCode() == KeyCode.N) {
                    newGame(null);
                }
            }
        });
        
        gameOverPanel.setVisible(false);
        
        pauseButton.selectedProperty().bindBidirectional(isPause);
        pauseButton.selectedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    timeLine.pause();
                    pauseButton.setText("Resume");
                } else {
                    timeLine.play();
                    pauseButton.setText("Pause");
                }
            }
        });
                
        Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
        scoreValue.setEffect(reflection);
        
        
        scoreList.getItems().addAll(GameController.readScores());
    }

    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }
    
    public void gameOver(){
        timeLine.stop();
        gameOverPanel.setVisible(true);
        isGameOver.setValue(Boolean.TRUE);
        System.out.println("game over");
    }
    
    public void newGame(ActionEvent actionEvent) {
        timeLine.stop();
        gameOverPanel.setVisible(false);
        eventListener.createNewGame();
        gamePanel.requestFocus();
        timeLine.play();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
    }
    
    
}
