package nazmplanner.application.tasks.messages;

import nazmplanner.application.tasks.TaskDTO;

public record TaskDisplayedMessage(TaskDTO task) implements TasksMessage {}