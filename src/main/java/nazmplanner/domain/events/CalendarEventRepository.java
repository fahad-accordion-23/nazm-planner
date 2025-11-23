package nazmplanner.domain.events;

import java.util.List;
import java.util.UUID;

/**
 * <h2<CalendarEventRepository</h2>
 * 
 * @author Fahad Hassan
 * @version 23/11/25
 */
public interface CalendarEventRepository
{
    void save(CalendarEvent calendarEvent);
    List<CalendarEvent> getAll();
    CalendarEvent getById(UUID id);
    void delete(UUID id);
}
