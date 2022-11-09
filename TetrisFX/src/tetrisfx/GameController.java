package tetrisfx;

import Events.EventSource;
import Events.MoveEvent;
import logic.*;

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
    public DownData onDownEvent(MoveEvent event){
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        if(!canMove){
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            System.out.println(clearRow.getLinesRemoved());
            board.createNewBrick();
        }else{
            if(event.getEventSource() == EventSource.USER){
                board.getScore().add(1);
            }
        }
        
        viewController.refreshGameBackground(board.getBoardMatrix());
        return new DownData(clearRow, board.getViewData());
    }

    public ViewData onLeftEvent() {
        board.moveBrickLeft();

        return board.getViewData();

    }

    @Override
    public ViewData onRightEvent() {
        board.moveBrickRight();

        return board.getViewData();

    }

    @Override
    public ViewData onRotateEvent() {
        board.rotateBrickLeft();

        return board.getViewData();
    }

}
