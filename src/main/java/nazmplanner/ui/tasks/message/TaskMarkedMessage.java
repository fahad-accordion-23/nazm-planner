package nazmplanner.ui.tasks.message;

import java.util.UUID;
import nazmplanner.domain.tasks.TaskStatus;
import nazmplanner.util.messaging.Message;

public record TaskMarkedMessage(UUID id, TaskStatus newStatus) implements TaskMessage {}