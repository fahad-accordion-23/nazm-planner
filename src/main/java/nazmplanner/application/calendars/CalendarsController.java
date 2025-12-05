package nazmplanner.application.calendars;

import nazmplanner.application.calendars.messages.*;
import nazmplanner.domain.calendars.CalendarsSystem;
import nazmplanner.util.messaging.Message;
import nazmplanner.util.messaging.MessageReceiver;

/**
 * <h2>CalendarEventController</h2>
 * 
 * <p>Connects EventsMediator to CalendarEventSystem.</p>
 */
public class CalendarsController
{
    private final CalendarsSystem calendarsSystem;
    private final CalendarsMessageBroker calendarsMessageBroker;

    public CalendarsController(CalendarsSystem calendarsSystem, CalendarsMessageBroker calendarsMessageBroker)
    {
        this.calendarsSystem = calendarsSystem;
        this.calendarsMessageBroker = calendarsMessageBroker;
        
        registerSubscribers();
        updateEvents();
    }

    private void registerSubscribers()
    {
        calendarsMessageBroker.subscribe(CalendarEventAddedMessage.class, this::onEvent);
        calendarsMessageBroker.subscribe(CalendarEventDeletedMessage.class, this::onEvent);
    }
    
    public void updateEvents()
    {
        calendarsMessageBroker.publish(new CalendarEventUpdatedMessage(calendarsSystem.getAllCalendarEvents()));
    }

    public void onEvent(CalendarEventAddedMessage message)
    {
        calendarsSystem.addCalendarEvent(message.title(), message.description(), message.start(), message.end());
        updateEvents();
    }
    
    public void onEvent(CalendarEventDeletedMessage message)
    {
        calendarsSystem.deleteCalendarEvent(message.id());
        updateEvents();
    }
}