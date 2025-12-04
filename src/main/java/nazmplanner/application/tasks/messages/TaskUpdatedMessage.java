package nazmplanner.application.tasks.messages;

import nazmplanner.domain.tasks.Task;
import nazmplanner.util.messaging.Message;

public record TaskUpdatedMessage(Task task) implements TasksMessage {}