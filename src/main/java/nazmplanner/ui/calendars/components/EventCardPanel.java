package nazmplanner.ui.calendars.components;

import java.awt.BorderLayout;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JLabel;

import nazmplanner.application.calendars.CalendarsMessageBroker;
import nazmplanner.application.calendars.messages.CalendarEventDeletedMessage;
import nazmplanner.domain.calendars.CalendarEvent;
import nazmplanner.ui.core.CardPanel;

public class EventCardPanel extends CardPanel
{
    private final CalendarEvent event;
    private final CalendarsMessageBroker eventsMediator;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("MMM dd HH:mm");

    public EventCardPanel(CalendarEvent event, CalendarsMessageBroker eventsMediator)
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
        deleteBtn.addActionListener(e -> eventsMediator.publish(new CalendarEventDeletedMessage(event.getId())));
        
        add(titleLabel, BorderLayout.CENTER);
        add(deleteBtn, BorderLayout.EAST);
    }
}