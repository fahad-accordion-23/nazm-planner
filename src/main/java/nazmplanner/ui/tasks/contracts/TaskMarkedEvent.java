package nazmplanner.ui.tasks.contracts;

import java.util.UUID;
import nazmplanner.domain.tasks.TaskStatus;
import nazmplanner.util.Event;

public record TaskMarkedEvent(UUID id, TaskStatus newStatus) implements TaskEvent {}