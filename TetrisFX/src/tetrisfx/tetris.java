package tetrisfx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class tetris extends Application{
    
    //MAIN LAUNCH
    public static void main(String[] args){
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws IOException{
        
        //IMPORTS WINDOW DIMENSIONS AND SCORE/LINE DATA FROM GAMELAYOUT.FXML, LOADS INTO PARENT ROOT
        URL location = getClass().getClassLoader().getResource("resources/GameLayout.fxml");
        ResourceBundle resource = null;
        FXMLLoader fxmlLoader = new FXMLLoader(location, resource);
        Parent root = fxmlLoader.load();
        
        //IMPORTS USER INTERFACE
        GuiController c = fxmlLoader.getController();
        
        //ADDS APP ICON AND TITLE FOR TOP LEFT WINDOW
        stage.getIcons().add(new Image("resources/icon.png"));
        stage.setTitle("FX Tetris");
        
        //DISABLES WINDOW EXPANDING
        stage.setResizable(false);
        
        //SETS SCENE WITH SPECIFIED WINDOW SIZES ON ROOT
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        //IMPORTS CONTROLS (IN PROGRESS)
        GameController gameController = new GameController(c);
        GuiController guiController = new GuiController();
    }
}
