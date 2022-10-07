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
    public static void main(String[] args){
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws IOException{
        
        URL location = getClass().getClassLoader().getResource("resources/GameLayout.fxml");
        ResourceBundle resource = null;
        FXMLLoader fxmlLoader = new FXMLLoader(location, resource);
        Parent root = fxmlLoader.load();
        
        GuiController c = fxmlLoader.getController();
        stage.getIcons().add(new Image("resources/icon.png"));
        stage.setTitle("T E T R I S");
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        GameController gameController = new GameController(c);
    }
}
