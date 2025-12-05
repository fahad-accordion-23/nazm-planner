package nazmplanner.ui.calendars.components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.time.LocalDate;
import java.time.Month;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import nazmplanner.application.calendars.CalendarsMessageBroker;
import nazmplanner.ui.core.HeaderPanel;
import nazmplanner.ui.util.GBC;

/**
 * <h2>EventsPrimaryPanel</h2>
 * 
 * <p>Displays the main calendar grid with dynamic month/year header.</p>
 * 
 * @author Fahad Hassan
 * @version 05/12/2025
 */
public class PrimaryPanel extends JPanel
{
    private final CalendarsMessageBroker calendarsMessageBroker;
    
    private HeaderPanel headerPanel;
    private CalendarGridPanel calendarGridPanel;
    private JScrollPane calendarGridScrollPane;
    
    private int currentYear;
    private Month currentMonth;
    
    public PrimaryPanel(CalendarsMessageBroker calendarsMessageBroker)
    {
        this.calendarsMessageBroker = calendarsMessageBroker;
        
        // Initialize to current date
        LocalDate now = LocalDate.now();
        this.currentYear = now.getYear();
        this.currentMonth = now.getMonth();
        
        initComponents();
        initStyling();
        initLayout();
    }
    
    private void initComponents()
    {
        calendarGridPanel = new CalendarGridPanel(calendarsMessageBroker, currentYear, currentMonth);
        headerPanel = new HeaderPanel(calendarGridPanel.getCurrentMonthYear());
        calendarGridScrollPane = new JScrollPane(calendarGridPanel);
    }
    
    private void initStyling()
    {
        super.setBackground(Color.WHITE);
        
        calendarGridScrollPane.setBorder(null); 
        calendarGridScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }
    
    private void initLayout()
    {
        super.setLayout(new GridBagLayout());
        
        super.add(headerPanel, new GBC(0, 0)
                  .setWeight(1.00, 0.00)
                  .setAnchor(GridBagConstraints.NORTH)
                  .setFill(GridBagConstraints.HORIZONTAL));
        
        super.add(calendarGridScrollPane, new GBC(0, 1)
                  .setWeight(1.00, 1.00)
                  .setAnchor(GridBagConstraints.NORTH)
                  .setFill(GridBagConstraints.BOTH));
    }
    
    public void navigateToPreviousMonth()
    {
        if (currentMonth == Month.JANUARY)
        {
            currentMonth = Month.DECEMBER;
            currentYear--;
        }
        else
        {
            currentMonth = currentMonth.minus(1);
        }
        
        updateCalendar();
    }
    
    public void navigateToNextMonth()
    {
        if (currentMonth == Month.DECEMBER)
        {
            currentMonth = Month.JANUARY;
            currentYear++;
        }
        else
        {
            currentMonth = currentMonth.plus(1);
        }
        
        updateCalendar();
    }
    
    private void updateCalendar()
    {
        calendarGridPanel.setYearMonth(currentYear, currentMonth);
        headerPanel.setText(calendarGridPanel.getCurrentMonthYear());
    }
}