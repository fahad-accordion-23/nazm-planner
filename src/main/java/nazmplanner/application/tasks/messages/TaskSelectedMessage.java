package nazmplanner.application.tasks.messages;

import java.util.UUID;

import nazmplanner.util.messaging.Message;

public record TaskSelectedMessage(UUID id) implements TasksMessage {}