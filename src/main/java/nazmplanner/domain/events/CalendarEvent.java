package nazmplanner.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <h2>CalendarEvent</h2>
 * 
 * @author Fahad Hassan
 * @version 23/11/2025
 */
public class CalendarEvent 
{
    private final UUID id;
    private String title;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    
    public CalendarEvent(String title, String description, LocalDateTime start, LocalDateTime end)
    {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public LocalDateTime getStart()
    {
        return start;
    }
    
    public void setStart(LocalDateTime start)
    {
        this.start = start;
    }
    
    public LocalDateTime getEnd()
    {
        return end;
    }
    
    public void setEnd(LocalDateTime end)
    {
        this.end = end;
    }
    
    public UUID getId()
    {
        return id;
    }
    
}
