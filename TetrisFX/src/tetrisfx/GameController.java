package tetrisfx;

import Events.EventSource;
import Events.MoveEvent;
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
    public ViewData onDownEvent(MoveEvent event){
        boolean canMove = board.moveBrickDown();
        
        if(!canMove){
            board.mergeBrickToBackground();
            board.createNewBrick();
        }else{
            if(event.getEventSource() == EventSource.USER){
                board.getScore().add(1);
            }
        }
        
        viewController.refreshGameBackground(board.getBoardMatrix());
        return board.getViewData();
    }
    
}
