package nazmplanner.application.calendars;

import java.time.LocalDateTime;
import java.util.UUID;

public record CalendarEventDTO(UUID id, String title, String description, LocalDateTime start, LocalDateTime end)
{

}
