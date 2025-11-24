package nazmplanner.ui.tasks.contracts;

import nazmplanner.domain.tasks.Task;
import nazmplanner.util.Event;

public record TaskUpdatedEvent(Task task) implements TaskEvent {}