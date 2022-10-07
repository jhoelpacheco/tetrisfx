package tetrisfx;

public class GameController {
    private final GuiController viewController;
    
    public GameController(GuiController c){
        this.viewController = c;
        this.viewController.initGameView();
    }
}
