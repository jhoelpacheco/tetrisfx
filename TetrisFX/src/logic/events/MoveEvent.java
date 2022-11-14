package logic.events;


public class MoveEvent {
    private final EvenType evenType;
    private final EventSource eventSource;

    public MoveEvent(EvenType evenType, EventSource eventSource) {
        this.evenType = evenType;
        this.eventSource = eventSource;
    }        

    public EvenType getEvenType() {
        return evenType;
    }

    public EventSource getEventSource() {
        return eventSource;
    }
}
