package nazmplanner.ui.events.messages;

import java.util.UUID;

public record CalendarEventDeletedMessage(UUID id) implements CalendarEventMessage {}