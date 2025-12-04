package nazmplanner.application.events;

import nazmplanner.domain.events.CalendarEventSystem;
import nazmplanner.ui.events.EventsMediator;
import nazmplanner.ui.events.messages.*;

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
        eventsMediator.subscribe(CalendarEventAddedMessage.class, this::onEvent);
        eventsMediator.subscribe(CalendarEventDeletedMessage.class, this::onEvent);
    }
    
    public void updateEvents()
    {
        eventsMediator.publish(new CalendarEventsUpdatedMessage(eventSystem.getAllCalendarEvents()));
    }

    // --- Handlers ---

    private void onEvent(CalendarEventAddedMessage event)
    {
        eventSystem.addCalendarEvent(event.title(), event.description(), event.start(), event.end());
        updateEvents();
    }
    
    private void onEvent(CalendarEventDeletedMessage event)
    {
        eventSystem.deleteCalendarEvent(event.id());
        updateEvents();
    }
}