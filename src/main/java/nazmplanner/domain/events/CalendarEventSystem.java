package nazmplanner.domain.events;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class CalendarEventSystem
{
    private CalendarEventRepository calendarEventRepository;
    
    public CalendarEventSystem()
    {
        //calendarEventRepository = new JSONCalendarEventRepository();
    }
    
    public void addCalendarEvent(String title, String description, LocalDateTime start, LocalDateTime end)
    {
        if (Objects.isNull(title) || title.trim().isEmpty())
        {
            throw new IllegalArgumentException("Calendar Event title cannot be null or empty!");
        }
        
        if (Objects.isNull(start))
        {
            throw new IllegalArgumentException("Calendar Event start cannot be null!");
        }

        if (Objects.isNull(end))
        {
            throw new IllegalArgumentException("Calendar Event end cannot be null!");
        }

        if (end.isBefore(start))
        {
            throw new IllegalArgumentException("Calendar Event end time cannot be before start time!");
        }

        CalendarEvent newEvent = new CalendarEvent(title, description, start, end);
        calendarEventRepository.save(newEvent);
    }
    
    public void updateCalendarEvent(UUID id, String title, String description, LocalDateTime start, LocalDateTime end)
    {
        CalendarEvent event = calendarEventRepository.getById(id);
        
        if (Objects.isNull(event))
        {
            throw new IllegalArgumentException("Calendar Event with ID " + id + " not found!");
        }

        if (Objects.isNull(title) || title.trim().isEmpty())
        {
            throw new IllegalArgumentException("Calendar Event title cannot be null or empty!");
        }

        if (end.isBefore(start))
        {
            throw new IllegalArgumentException("Calendar Event end time cannot be before start time!");
        }

        event.setTitle(title);
        event.setDescription(description);
        event.setStart(start);
        event.setEnd(end);

        calendarEventRepository.save(event);
    }
    
    public void deleteCalendarEvent(UUID id)
    {
        calendarEventRepository.delete(id);
    }

    public List<CalendarEvent> getAllCalendarEvents()
    {
        return calendarEventRepository.getAll();
    }
    
    public CalendarEvent getEvent(UUID id)
    {
        return calendarEventRepository.getById(id);
    }
    
    public List<CalendarEvent> getCalendarEventsForDate(LocalDate date)
    {
        return calendarEventRepository.getAll().stream()
                .filter(e -> isOnDate(e, date))
                .sorted(Comparator.comparing(CalendarEvent::getStart))
                .collect(Collectors.toList());
    }

    public List<CalendarEvent> getCalendarEventsForRange(LocalDate start, LocalDate end)
    {
        return calendarEventRepository.getAll().stream()
                .filter(e -> isInWindow(e, start, end))
                .sorted(Comparator.comparing(CalendarEvent::getStart))
                .collect(Collectors.toList());
    }

    /* Helpers */
    
    private boolean isOnDate(CalendarEvent event, LocalDate date)
    {
        LocalDate eventStart = event.getStart().toLocalDate();
        LocalDate eventEnd = event.getEnd().toLocalDate();
        
        return (date.isEqual(eventStart) || date.isEqual(eventEnd)) || 
               (date.isAfter(eventStart) && date.isBefore(eventEnd));
    }
    
    private boolean isInWindow(CalendarEvent event, LocalDate windowStart, LocalDate windowEnd)
    {
        LocalDate eventStart = event.getStart().toLocalDate();
        LocalDate eventEnd = event.getEnd().toLocalDate();
        
        return !eventEnd.isBefore(windowStart) && !eventStart.isAfter(windowEnd);
    }
}
