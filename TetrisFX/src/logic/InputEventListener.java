package logic;

import Events.MoveEvent;

import javax.swing.text.View;

public interface InputEventListener 
{
    DownData onDownEvent(MoveEvent event);

    ViewData onLeftEvent();

    ViewData onRightEvent();

    ViewData onRotateEvent();
}
