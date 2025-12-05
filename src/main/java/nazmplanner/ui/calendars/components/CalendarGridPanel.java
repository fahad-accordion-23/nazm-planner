package nazmplanner.ui.calendars.components;

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

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import nazmplanner.application.calendars.CalendarEventDTO;
import nazmplanner.application.calendars.CalendarsMessageBroker;
import nazmplanner.application.calendars.messages.CalendarEventUpdatedMessage;
import nazmplanner.ui.util.GBC;

/**
 * <h2>CalendarGridPanel</h2>
 *
 * <p>Displays a 7x6 calendar grid with events.</p>
 * 
 * @author Fahad Hassan
 * @version 05/12/2025
 */
public class CalendarGridPanel extends JPanel
{
    private final CalendarsMessageBroker calendarsMessageBroker;
    private List<CalendarEventDTO> currentEvents;
    private List<CalendarCellPanel> cellPanels;
    
    private int currentYear;
    private Month currentMonth;

    private static final String[] DAY_NAMES = {
        DayOfWeek.SUNDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        DayOfWeek.MONDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        DayOfWeek.TUESDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        DayOfWeek.WEDNESDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        DayOfWeek.THURSDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        DayOfWeek.FRIDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        DayOfWeek.SATURDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault())
    };

    public CalendarGridPanel(CalendarsMessageBroker calendarsMessageBroker, int year, Month month)
    {
        this.calendarsMessageBroker = calendarsMessageBroker;
        this.currentEvents = new ArrayList<>();
        this.cellPanels = new ArrayList<>();
        this.currentYear = year;
        this.currentMonth = month;
        
        initStyling();
        initLayout();
        
        calendarsMessageBroker.subscribe(CalendarEventUpdatedMessage.class, this::onEventsUpdated);
    }
    
    private void onEventsUpdated(CalendarEventUpdatedMessage message)
    {
        this.currentEvents = message.events();
        
        SwingUtilities.invokeLater(() -> 
        {
            for (CalendarCellPanel cell : cellPanels)
            {
                cell.updateEvents(currentEvents);
            }
        });
    }
    
    private void initStyling()
    {
        setBackground(Color.WHITE); 
    }
    
    public void setYearMonth(int year, Month month)
    {
        this.currentYear = year;
        this.currentMonth = month;
        
        SwingUtilities.invokeLater(() -> 
        {
            cellPanels.clear();
            removeAll();
            initLayout();
            
            // Refresh events in new cells
            for (CalendarCellPanel cell : cellPanels)
            {
                cell.updateEvents(currentEvents);
            }
            
            revalidate();
            repaint();
        });
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
            
            add(header, new GBC(i, 0)
                    .setWeight(1.0, 0.0)
                    .setFill(GridBagConstraints.BOTH));
        }
        
        // 2. Calculate grid layout
        LocalDate firstDay = LocalDate.of(currentYear, currentMonth, 1);
        int daysInMonth = currentMonth.length(firstDay.isLeapYear());
        int startDayOfWeek = firstDay.getDayOfWeek().getValue() % 7; // Convert to Sunday = 0
        
        LocalDate today = LocalDate.now();
        
        int dayCounter = 1;
        for (int row = 1; row <= 6; row++)
        {
            for (int col = 0; col < 7; col++)
            {
                int cellIndex = (row - 1) * 7 + col;
                
                // Empty cells before month starts
                if (cellIndex < startDayOfWeek)
                {
                    JPanel empty = new JPanel();
                    empty.setBackground(new Color(245, 245, 245));
                    empty.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                    add(empty, new GBC(col, row).setWeight(1.0, 1.0).setFill(GridBagConstraints.BOTH));
                    continue;
                }
                
                // Cells after month ends
                if (dayCounter > daysInMonth)
                {
                    JPanel empty = new JPanel();
                    empty.setBackground(new Color(245, 245, 245));
                    empty.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                    add(empty, new GBC(col, row).setWeight(1.0, 1.0).setFill(GridBagConstraints.BOTH));
                    continue;
                }

                // Valid day cells
                LocalDate cellDate = LocalDate.of(currentYear, currentMonth, dayCounter);
                boolean isToday = cellDate.equals(today);
                
                CalendarCellPanel dayCell = new CalendarCellPanel(dayCounter, cellDate, isToday);
                cellPanels.add(dayCell);
                
                add(dayCell, new GBC(col, row)
                        .setWeight(1.0, 1.0)
                        .setFill(GridBagConstraints.BOTH));
                
                dayCounter++;
            }
        }
    }
    
    public String getCurrentMonthYear()
    {
        return currentMonth.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + currentYear;
    }
}