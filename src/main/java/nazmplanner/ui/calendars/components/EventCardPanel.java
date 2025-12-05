package nazmplanner.ui.calendars.components;

import java.awt.BorderLayout;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JLabel;

import nazmplanner.application.calendars.CalendarEventDTO;
import nazmplanner.application.calendars.CalendarsMessageBroker;
import nazmplanner.application.calendars.messages.CalendarEventDeletedMessage;
import nazmplanner.ui.core.CardPanel;

public class EventCardPanel extends CardPanel
{
    private final CalendarEventDTO event;
    private final CalendarsMessageBroker eventsMediator;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("MMM dd HH:mm");

    public EventCardPanel(CalendarEventDTO event, CalendarsMessageBroker eventsMediator)
    {
        this.event = event;
        this.eventsMediator = eventsMediator;
        
        initLayout();
    }
    
    private void initLayout()
    {
        setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("<html><b>" + event.title() + "</b><br/>" + 
                event.start().format(FMT) + " - " + event.end().format(FMT) + "</html>");
        
        JButton deleteBtn = new JButton("X");
        deleteBtn.addActionListener(e -> eventsMediator.publish(new CalendarEventDeletedMessage(event.id())));
        
        add(titleLabel, BorderLayout.CENTER);
        add(deleteBtn, BorderLayout.EAST);
    }
}