package nazmplanner.ui.tasks.message;

import java.util.List;
import nazmplanner.domain.tasks.Task;
import nazmplanner.util.messaging.Message;

/**
 * @author Fahad Hassan
 * @version 24/11/2025
 */
public record TasksUpdatedMessage(List<Task> tasks) implements TaskMessage {}