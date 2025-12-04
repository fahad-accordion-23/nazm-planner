package nazmplanner.application.tasks.messages;

import java.time.LocalDateTime;

import nazmplanner.util.messaging.Message;

public record TaskAddedMessage(String title, String description, LocalDateTime dueDate) implements TasksMessage {}