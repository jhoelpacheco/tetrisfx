import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;


public class GameOverPanel extends BorderPane {
    public GameOverPanel() {
        Label gameOverLabel = new Label("Fi del joc");
        gameOverLabel.getStyleClass().add("");


        setCenter(gameOverLabel);
    }
}




