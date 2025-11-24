package nazmplanner.ui.tasks.contracts;

import java.util.UUID;
import nazmplanner.util.Event;

public record TaskDeletedEvent(UUID id) implements TaskEvent {}