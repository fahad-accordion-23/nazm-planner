package nazmplanner.application.calendars;

import nazmplanner.application.calendars.messages.CalendarEventAddedMessage;
import nazmplanner.application.calendars.messages.CalendarEventDeletedMessage;
import nazmplanner.application.calendars.messages.CalendarEventUpdatedMessage;
import nazmplanner.domain.calendars.CalendarsSystem;

/**
 * <h2>CalendarEventController</h2>
 * 
 * <p>Connects EventsMediator to CalendarEventSystem.</p>
 */
public class CalendarsController
{
    private final CalendarsSystem eventSystem;
    private final CalendarsMessageBroker eventsMediator;

    public CalendarsController(CalendarsSystem eventSystem, CalendarsMessageBroker eventsMediator)
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
        eventsMediator.publish(new CalendarEventUpdatedMessage(eventSystem.getAllCalendarEvents()));
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