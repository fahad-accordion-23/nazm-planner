package nazmplanner.ui.events.messages;

import java.time.LocalDateTime;

public record CalendarEventAddedMessage(String title, String description, LocalDateTime start, LocalDateTime end) implements CalendarEventMessage 
{}

