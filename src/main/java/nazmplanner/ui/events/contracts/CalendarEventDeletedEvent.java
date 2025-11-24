package nazmplanner.ui.events.contracts;

import java.util.UUID;

public record CalendarEventDeletedEvent(UUID id) implements CalendarEventEvent {}