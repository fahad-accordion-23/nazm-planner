package nazmplanner.application.events;

import nazmplanner.domain.events.CalendarEventSystem;
import nazmplanner.ui.events.EventsMediator;
import nazmplanner.ui.events.contracts.*;

/**
 * <h2>CalendarEventController</h2>
 * 
 * <p>Connects EventsMediator to CalendarEventSystem.</p>
 */
public class CalendarEventController
{
    private final CalendarEventSystem eventSystem;
    private final EventsMediator eventsMediator;

    public CalendarEventController(CalendarEventSystem eventSystem, EventsMediator eventsMediator)
    {
        this.eventSystem = eventSystem;
        this.eventsMediator = eventsMediator;
        
        registerSubscribers();
        updateEvents();
    }

    private void registerSubscribers()
    {
        eventsMediator.subscribe(CalendarEventAddedEvent.class, this::onEvent);
        eventsMediator.subscribe(CalendarEventDeletedEvent.class, this::onEvent);
    }
    
    public void updateEvents()
    {
        eventsMediator.publish(new CalendarEventsUpdatedEvent(eventSystem.getAllCalendarEvents()));
    }

    // --- Handlers ---

    private void onEvent(CalendarEventAddedEvent event)
    {
        eventSystem.addCalendarEvent(event.title(), event.description(), event.start(), event.end());
        updateEvents();
    }
    
    private void onEvent(CalendarEventDeletedEvent event)
    {
        eventSystem.deleteCalendarEvent(event.id());
        updateEvents();
    }
}