package tetrisfx;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Scanner;
import javafx.beans.property.IntegerProperty;
import static javafx.beans.property.IntegerProperty.integerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextInputDialog;
import logic.ClearRow;
import logic.DownData;
import logic.SimpleBoard;
import logic.ViewData;
import logic.events.EventSource;
import logic.events.InputEventListener;
import logic.events.MoveEvent;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class GameController implements InputEventListener{
    
    private SimpleBoard board = new SimpleBoard(22, 10);
    private final GuiController viewController;
    
    
    public GameController(GuiController c){
        
        this.viewController = c;
        this.viewController.setEventLister(this);
        board.createNewBrick();
        this.viewController.initGameView(board.getBoardMatrix(), board.getViewData());
        this.viewController.bindScore(board.getScore().scoreProperty());
    }

    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickdown();
        ClearRow clearRow = null;
        
        if(!canMove){
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            if(clearRow.getLinesRemoved() > 0){
                board.getScore().add(clearRow.getScoreBonus());
            }
            if(board.createNewBrick()){
                viewController.gameOver();
                
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Score");
                dialog.setHeaderText("Introduce tu nombre.");
                dialog.setContentText("Nombre: ");
                
                Optional<String> result= dialog.showAndWait();
                
                //dialog.show();
                String name = result.get();
                Integer score = board.getScore().scoreProperty().getValue();
                
                
                //System.out.println(System.getProperty("user.dir"));
                saveNameAndScore(name,score);
                
                //System.out.println("valor de score: "+ score);
                //System.out.println("valor de nombre: " + nombre);
            }
            
            board.createNewBrick();
        }else{
            if(event.getEventSource() == EventSource.USER){
                board.getScore().add(1);
            }
        }
        
        viewController.refreshGameBackground(board.getBoardMatrix());
        
        //System.out.println("GBM: GAME :"+ board.getBoardMatrix());
        
        return new DownData(clearRow, board.getViewData());
    }

    @Override
    public ViewData onLeftEvent() {
        board.moveBrickLeft();
        
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent() {
        board.moveBrickRight();
        
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent() {
        board.rotateBrickLeft();
        
        return board.getViewData();
    }

    @Override
    public ViewData onDownSpace() {
        board.moveDownSpace();
        
        return board.getViewData();
    }
    
    @Override
    public void createNewGame() {
        board.newGame();
        viewController.refreshGameBackground(board.getBoardMatrix());
    }

    public static void saveNameAndScore(String nombre, Integer puntuacion){
        ArrayList<String> scores = readScores();
        if(scores.size() < 5){
            scores.add(nombre + ",\t\t" + puntuacion);
        }else{
            ArrayList<Integer> scoresInt = new ArrayList<Integer>();
            for(int i = 0; i < scores.size(); i++){
                scoresInt.add(Integer.parseInt(scores.get(i).split(",")[1]));
            }
            if( puntuacion > Collections.min(scoresInt)){
                scores.remove(scoresInt.indexOf(Collections.min(scoresInt)));
                scores.add(nombre + ",\t\t" + puntuacion);
            }
        }
        try {
            File file = new File("src/resources/scores.csv");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for(String s : scores){
                bw.write(s+"\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public static ArrayList<String> readScores() {
        ArrayList<String> scores = new ArrayList<String>();
        File archivo = new File("src/resources/scores.csv");
        try {
            Scanner sc = new Scanner(archivo);
            while (sc.hasNextLine()) {
                scores.add(sc.nextLine());
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }
}
