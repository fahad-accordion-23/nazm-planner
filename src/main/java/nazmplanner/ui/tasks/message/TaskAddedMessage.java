package nazmplanner.ui.tasks.message;

import java.time.LocalDateTime;

import nazmplanner.util.messaging.Message;

public record TaskAddedMessage(String title, String description, LocalDateTime dueDate) implements TaskMessage {}