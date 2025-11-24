package nazmplanner.ui.tasks.contracts;

import java.time.LocalDateTime;
import nazmplanner.util.Event;

public record TaskAddedEvent(String title, String description, LocalDateTime dueDate) implements TaskEvent {}