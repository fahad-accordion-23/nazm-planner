package nazmplanner.ui.calendars.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nazmplanner.application.calendars.CalendarEventDTO;

/**
 * <h2>CalendarCellPanel</h2>
 * 
 * <p>Represents a single day cell in the calendar grid.</p>
 * 
 * @author Fahad Hassan
 * @version 05/12/2025
 */
public class CalendarCellPanel extends JPanel
{
    private final int dayNumber;
    private final LocalDate cellDate;
    private final boolean isToday;
    private JLabel numberLabel;
    private JPanel eventsContainer;
    
    private static final int MAX_VISIBLE_EVENTS = 3;
    
    public CalendarCellPanel(int dayNumber, LocalDate cellDate, boolean isToday)
    {
        this.dayNumber = dayNumber;
        this.cellDate = cellDate;
        this.isToday = isToday;
        
        initComponents();
        initLayout();
        initStyling();
    }
    
    private void initComponents()
    {
        numberLabel = new JLabel(String.valueOf(dayNumber));
        numberLabel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 0));
        numberLabel.setFont(numberLabel.getFont().deriveFont(Font.PLAIN, 12));
        
        if (isToday)
        {
            numberLabel.setFont(numberLabel.getFont().deriveFont(Font.BOLD));
            numberLabel.setForeground(Color.BLUE);
        }
        
        eventsContainer = new JPanel();
        eventsContainer.setLayout(new BoxLayout(eventsContainer, BoxLayout.Y_AXIS));
        eventsContainer.setOpaque(false);
        eventsContainer.setBorder(BorderFactory.createEmptyBorder(2, 2, 0, 2));
    }
    
    private void initLayout()
    {
        setLayout(new BorderLayout());
        add(numberLabel, BorderLayout.NORTH);
        add(eventsContainer, BorderLayout.CENTER);
    }
    
    private void initStyling()
    {
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        setBackground(isToday ? new Color(240, 248, 255) : Color.WHITE);
    }
    
    public void updateEvents(List<CalendarEventDTO> events)
    {
        eventsContainer.removeAll();
        
        List<CalendarEventDTO> daysEvents = events.stream()
                .filter(e -> isEventOnDate(e, cellDate))
                .collect(Collectors.toList());
        
        int eventCount = 0;
        for (CalendarEventDTO event : daysEvents)
        {
            if (eventCount >= MAX_VISIBLE_EVENTS)
            {
                int remaining = daysEvents.size() - MAX_VISIBLE_EVENTS;
                JLabel moreLabel = new JLabel("+" + remaining + " more...");
                moreLabel.setFont(new Font("SansSerif", Font.ITALIC, 9));
                moreLabel.setForeground(Color.GRAY);
                moreLabel.setBorder(BorderFactory.createEmptyBorder(1, 3, 1, 3));
                moreLabel.setAlignmentX(LEFT_ALIGNMENT);
                eventsContainer.add(moreLabel);
                break;
            }
            
            JLabel eventPill = createEventPill(event);
            eventsContainer.add(eventPill);
            eventsContainer.add(Box.createVerticalStrut(2));
            eventCount++;
        }
        
        revalidate();
        repaint();
    }
    
    private JLabel createEventPill(CalendarEventDTO event)
    {
        JLabel eventPill = new JLabel(event.title());
        eventPill.setOpaque(true);
        eventPill.setBackground(new Color(173, 216, 230));
        eventPill.setFont(new Font("SansSerif", Font.PLAIN, 10));
        eventPill.setBorder(BorderFactory.createEmptyBorder(1, 3, 1, 3));
        eventPill.setAlignmentX(LEFT_ALIGNMENT);
        return eventPill;
    }
    
    private boolean isEventOnDate(CalendarEventDTO event, LocalDate date)
    {
        LocalDate eventStart = event.start().toLocalDate();
        LocalDate eventEnd = event.end().toLocalDate();
        
        return (date.isEqual(eventStart) || date.isEqual(eventEnd)) || 
               (date.isAfter(eventStart) && date.isBefore(eventEnd));
    }
    
    public LocalDate getCellDate()
    {
        return cellDate;
    }
}