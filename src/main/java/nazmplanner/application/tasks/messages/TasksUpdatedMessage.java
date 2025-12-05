package nazmplanner.application.tasks.messages;

import java.util.List;

import nazmplanner.application.tasks.TaskDTO;

/**
 * @author Fahad Hassan
 * @version 24/11/2025
 */
public record TasksUpdatedMessage(List<TaskDTO> tasks) implements TasksMessage {}