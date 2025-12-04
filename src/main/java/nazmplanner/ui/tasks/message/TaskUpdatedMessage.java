package nazmplanner.ui.tasks.message;

import nazmplanner.domain.tasks.Task;
import nazmplanner.util.messaging.Message;

public record TaskUpdatedMessage(Task task) implements TaskMessage {}