package nazmplanner.ui.events.contracts;

import java.util.List;
import nazmplanner.domain.events.CalendarEvent;

public record CalendarEventsUpdatedEvent(List<CalendarEvent> events) implements CalendarEventEvent {}
