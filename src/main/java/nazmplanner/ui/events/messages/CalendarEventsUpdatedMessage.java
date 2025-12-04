package nazmplanner.ui.events.messages;

import java.util.List;
import nazmplanner.domain.events.CalendarEvent;

public record CalendarEventsUpdatedMessage(List<CalendarEvent> events) implements CalendarEventMessage {}
