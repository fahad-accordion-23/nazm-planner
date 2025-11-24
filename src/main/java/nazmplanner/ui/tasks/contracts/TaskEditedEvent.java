package nazmplanner.ui.tasks.contracts;

import java.util.UUID;
import nazmplanner.util.Event;

public record TaskEditedEvent(UUID id, String title, String description) implements TaskEvent {}
