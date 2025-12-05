package nazmplanner.ui.calendars.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import nazmplanner.application.calendars.CalendarsMessageBroker;
import nazmplanner.application.calendars.messages.CalendarEventUpdatedMessage;
import nazmplanner.domain.calendars.CalendarEvent;
import nazmplanner.ui.util.GBC;

/**
 * <h2>CalendarGridPanel</h2>
 *
 * <p>Displays a 7x6 calendar grid with events.</p>
 * @author Fahad Hassan
 * @version 04/12/2025
 */
public class CalendarGridPanel extends JPanel
{
    private final CalendarsMessageBroker calendarsMessageBroker;
    private List<CalendarEvent> currentEvents;
    
    // Updated to December 2025
    private static final int CURRENT_YEAR = 2025;
    private static final Month CURRENT_MONTH = Month.DECEMBER;

    private static final String[] DAY_NAMES = {
        DayOfWeek.SUNDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        DayOfWeek.MONDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        DayOfWeek.TUESDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        DayOfWeek.WEDNESDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        DayOfWeek.THURSDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        DayOfWeek.FRIDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        DayOfWeek.SATURDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault())
    };

    public CalendarGridPanel(CalendarsMessageBroker calendarsMessageBroker)
    {
        this.calendarsMessageBroker = calendarsMessageBroker;
        this.currentEvents = new ArrayList<>();
        
        initStyling();
        initLayout(); // Draw empty grid initially
        
        // Subscribe to updates
        calendarsMessageBroker.subscribe(CalendarEventUpdatedMessage.class, this::onEventsUpdated);
    }
    
    /**
     * Re-draws the entire grid when events change.
     */
    private void onEventsUpdated(CalendarEventUpdatedMessage message)
    {
        this.currentEvents = message.events();
        
        SwingUtilities.invokeLater(() -> 
        {
            removeAll();
            initLayout();
            revalidate();
            repaint();
        });
    }
    
    private void initStyling()
    {
        setBackground(Color.WHITE); 
    }
    
    private void initLayout()
    {
        setLayout(new GridBagLayout());
        
        // 1. Draw Headers (Sun, Mon, Tue...)
        for (int i = 0; i < 7; i++)
        {
            JLabel header = new JLabel(DAY_NAMES[i], SwingConstants.CENTER);
            header.setFont(header.getFont().deriveFont(Font.BOLD));
            header.setBackground(Color.LIGHT_GRAY.brighter());
            header.setOpaque(true);
            header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.GRAY));
            
            super.add(header, new GBC(i, 0)
                    .setWeight(1.0, 0.0)
                    .setFill(GridBagConstraints.BOTH));
        }
        
        // 2. Draw Days
        int dayCounter = 1;
        for (int row = 1; row <= 6; row++)
        {
            for (int col = 0; col < 7; col++)
            {
                // Updated logic: stop if we pass 31 days (December limit)
                if (dayCounter > 31) { 
                     // Fill remaining cells with gray or empty
                    JPanel empty = new JPanel();
                    empty.setBackground(new Color(245, 245, 245));
                    empty.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                    super.add(empty, new GBC(col, row).setWeight(1.0, 1.0).setFill(GridBagConstraints.BOTH));
                    continue;
                }

                JPanel dayCell = createDayCell(dayCounter);
                
                super.add(dayCell, new GBC(col, row)
                        .setWeight(1.0, 1.0)
                        .setFill(GridBagConstraints.BOTH));
                
                dayCounter++;
            }
        }
    }
    
    private JPanel createDayCell(int dayNumber)
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panel.setBackground(Color.WHITE);

        // 1. Day Number Label (Top Left)
        JLabel numberLabel = new JLabel(String.valueOf(dayNumber));
        numberLabel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 0));
        numberLabel.setFont(numberLabel.getFont().deriveFont(Font.PLAIN, 12));
        
        // Highlight "Today" (Assuming today is Dec 25th for demo purposes)
        if (dayNumber == 25)
        {
            panel.setBackground(new Color(240, 248, 255)); // Alice Blue
            numberLabel.setFont(numberLabel.getFont().deriveFont(Font.BOLD));
            numberLabel.setForeground(Color.BLUE);
        }
        
        panel.add(numberLabel, BorderLayout.NORTH);
        
        // 2. Events Container (Center - Stacked Vertically)
        JPanel eventsContainer = new JPanel();
        eventsContainer.setLayout(new BoxLayout(eventsContainer, BoxLayout.Y_AXIS));
        eventsContainer.setOpaque(false);
        eventsContainer.setBorder(BorderFactory.createEmptyBorder(2, 2, 0, 2));
        
        // 3. Find events for this specific day (Assuming CURRENT_MONTH is now DECEMBER)
        LocalDate cellDate = LocalDate.of(CURRENT_YEAR, CURRENT_MONTH, dayNumber);
        
        List<CalendarEvent> daysEvents = currentEvents.stream()
                .filter(e -> isEventOnDate(e, cellDate))
                .collect(Collectors.toList());

        // 4. Create UI pills for events
        for (CalendarEvent event : daysEvents)
        {
            JLabel eventPill = new JLabel(event.getTitle());
            eventPill.setOpaque(true);
            eventPill.setBackground(new Color(173, 216, 230)); // Light Blue
            eventPill.setFont(new Font("SansSerif", Font.PLAIN, 10));
            eventPill.setBorder(BorderFactory.createEmptyBorder(1, 3, 1, 3));
            
            // Fix width to fit cell
            eventPill.setAlignmentX(LEFT_ALIGNMENT);
            
            eventsContainer.add(eventPill);
            eventsContainer.add(Box.createVerticalStrut(2));
        }

        panel.add(eventsContainer, BorderLayout.CENTER);
        
        return panel;
    }
    
    private boolean isEventOnDate(CalendarEvent event, LocalDate date)
    {
        return event.getStart().toLocalDate().equals(date);
    }
}