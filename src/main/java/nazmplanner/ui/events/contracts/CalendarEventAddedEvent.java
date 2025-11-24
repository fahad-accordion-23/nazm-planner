package nazmplanner.ui.events.contracts;

import java.time.LocalDateTime;

public record CalendarEventAddedEvent(String title, String description, LocalDateTime start, LocalDateTime end) implements CalendarEventEvent 
{}

