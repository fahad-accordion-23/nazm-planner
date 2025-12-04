package nazmplanner.application.calendars.messages;

import java.util.List;

import nazmplanner.domain.calendars.CalendarEvent;

public record CalendarEventUpdatedMessage(List<CalendarEvent> events) implements CalendarsMessage {}
