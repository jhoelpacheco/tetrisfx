package logic;

import Events.MoveEvent;
import logic.DownData;
import logic.ViewData;
public interface InputEventListener 
{

    ViewData onDownEvent(MoveEvent event);

    ViewData onLeftEvent();

}
