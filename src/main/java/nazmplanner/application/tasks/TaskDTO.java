package nazmplanner.application.tasks;

import java.time.LocalDateTime;
import java.util.UUID;

import nazmplanner.domain.tasks.TaskStatus;

public record TaskDTO(UUID id, String title, String description, TaskStatus status, LocalDateTime dueDate, LocalDateTime creationDate
    ) {}
