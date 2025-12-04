package nazmplanner.application.calendars.messages;

import java.util.UUID;

public record CalendarEventDeletedMessage(UUID id) implements CalendarsMessage {}