package nazmplanner.ui.events.components;

import java.awt.BorderLayout;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JLabel;
import nazmplanner.domain.events.CalendarEvent;
import nazmplanner.ui.components.CardPanel;
import nazmplanner.ui.events.EventsMediator;
import nazmplanner.ui.events.contracts.*;

public class EventCardPanel extends CardPanel
{
    private final CalendarEvent event;
    private final EventsMediator eventsMediator;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("MMM dd HH:mm");

    public EventCardPanel(CalendarEvent event, EventsMediator eventsMediator)
    {
        this.event = event;
        this.eventsMediator = eventsMediator;
        
        initLayout();
    }
    
    private void initLayout()
    {
        setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("<html><b>" + event.getTitle() + "</b><br/>" + 
                event.getStart().format(FMT) + " - " + event.getEnd().format(FMT) + "</html>");
        
        JButton deleteBtn = new JButton("X");
        deleteBtn.addActionListener(e -> eventsMediator.publish(new CalendarEventDeletedEvent(event.getId())));
        
        add(titleLabel, BorderLayout.CENTER);
        add(deleteBtn, BorderLayout.EAST);
    }
}