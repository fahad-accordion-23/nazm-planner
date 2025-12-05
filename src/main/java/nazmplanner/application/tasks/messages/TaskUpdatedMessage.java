package nazmplanner.application.tasks.messages;

import nazmplanner.application.tasks.TaskDTO;

public record TaskUpdatedMessage(TaskDTO task) implements TasksMessage {}