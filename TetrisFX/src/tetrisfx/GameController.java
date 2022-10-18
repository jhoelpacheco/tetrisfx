package tetrisfx;

import logic.SimpleBoard;

public class GameController {
    
    private final SimpleBoard board = new SimpleBoard(22, 10);
    private final GuiController viewController;
    
    public GameController(GuiController c){
        this.viewController = c;
        board.newBrick();
        this.viewController.initGameView(board.getBoardMatrix(), board.getCurrentShape());
    }
}
