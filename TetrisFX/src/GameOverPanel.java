
import javafx.scene.*;
import javafx.scene.layout.BorderPane;

import java.awt.*;


public class GameOverPanel extends BorderPane {
    public GameOver() {
        Label gameOverLabel = new Label("Fi del joc");
        gameOverLabel.getStyleClass().add("");


        setCenter(gameOverLabel);
    }


