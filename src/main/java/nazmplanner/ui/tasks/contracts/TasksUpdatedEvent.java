package nazmplanner.ui.tasks.contracts;

import java.util.List;
import nazmplanner.domain.tasks.Task;
import nazmplanner.util.Event;

/**
 * @author Fahad Hassan
 * @version 24/11/2025
 */
public record TasksUpdatedEvent(List<Task> tasks) implements TaskEvent {}