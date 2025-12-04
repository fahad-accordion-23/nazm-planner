package nazmplanner.ui.tasks.message;

import java.util.UUID;

import nazmplanner.util.messaging.Message;

public record TaskEditedMessage(UUID id, String title, String description) implements TaskMessage {}
