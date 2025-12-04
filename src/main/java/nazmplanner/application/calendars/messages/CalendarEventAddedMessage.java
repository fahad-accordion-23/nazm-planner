package nazmplanner.application.calendars.messages;

import java.time.LocalDateTime;

public record CalendarEventAddedMessage(String title, String description, LocalDateTime start, LocalDateTime end) implements CalendarsMessage 
{}

