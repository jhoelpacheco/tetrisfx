package tetrisfx;


import logic.ViewData;
import logic.SimpleBoard;
import logic.InputEventListener;

public class GameController implements InputEventListener{
    private final GuiController viewController;
    private SimpleBoard board = new SimpleBoard(22, 10);
    
    public GameController(GuiController c){
        
        this.viewController = c;
        this.viewController.setEventLister(this);
        board.createNewBrick();
        this.viewController.initGameView(board.getBoardMatrix(), board.getViewData());
        this.viewController.bindScore(board.getScore().scoreProperty());
    }
    
    @Override
    public ViewData onDownEvent(MoveEvent event) {
        boolean  canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            if (clearRow.getLinesRemoved() > 0) {
                board.getScore().add(clearRow.getScoreBonus());
            }

            if (board.createNewBrick()) {
                viewController.gameOver();
            }
            else {
                if(event.getEventSource() == EventSource.USER){
                    board.getScore().add(1);
                }
            }
        }
        board.moveBrickDown();
        return board.getViewData();
    }
    
}
