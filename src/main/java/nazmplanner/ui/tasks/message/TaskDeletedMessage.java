package nazmplanner.ui.tasks.message;

import java.util.UUID;

import nazmplanner.util.messaging.Message;

public record TaskDeletedMessage(UUID id) implements TaskMessage {}