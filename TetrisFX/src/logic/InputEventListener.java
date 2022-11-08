package logic;

import Events.MoveEvent;

public interface InputEventListener 
{
    ViewData onDownEvent(MoveEvent event);

    ViewData onLeftEvent();
}
