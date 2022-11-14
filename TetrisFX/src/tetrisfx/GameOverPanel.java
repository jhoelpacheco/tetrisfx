package tetrisfx;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class GameOverPanel extends BorderPane {
    public GameOverPanel() {
        Label gameOverLabel = new Label("Fi del joc");
        gameOverLabel.getStyleClass().add("");

        gameOverLabel.getStyleClass().add("gameOverStyle");
        
        setCenter(gameOverLabel);
    }
}

