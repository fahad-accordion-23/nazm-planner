package nazmplanner.ui.tasks.contracts;

import java.util.UUID;
import nazmplanner.util.Event;

public record TaskSelectedEvent(UUID id) implements TaskEvent {}