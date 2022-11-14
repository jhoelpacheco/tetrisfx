package tetrisfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Tetris extends Application{

    public static void main(String[] args){
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        URL location = getClass().getClassLoader().getResource("resources/GameLayout.fxml");
        ResourceBundle resource = null;
        FXMLLoader fxmlLoader = new FXMLLoader(location, resource);
        Parent root = fxmlLoader.load();
        GuiController c = fxmlLoader.getController();
        
        stage.getIcons().add(new Image("resources/icon.png"));
        stage.setTitle("T E T R I S");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
        new GameController(c);
    }
    
}
