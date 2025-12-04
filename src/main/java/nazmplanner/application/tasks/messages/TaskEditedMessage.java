package nazmplanner.application.tasks.messages;

import java.util.UUID;

import nazmplanner.util.messaging.Message;

public record TaskEditedMessage(UUID id, String title, String description) implements TasksMessage {}
