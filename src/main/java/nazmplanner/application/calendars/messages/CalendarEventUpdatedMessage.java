package nazmplanner.application.calendars.messages;

import java.util.List;

import nazmplanner.application.calendars.CalendarEventDTO;

public record CalendarEventUpdatedMessage(List<CalendarEventDTO> events) implements CalendarsMessage {}
