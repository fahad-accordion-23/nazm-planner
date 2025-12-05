package nazmplanner.ui.calendars.components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import nazmplanner.application.calendars.CalendarsMessageBroker;
import nazmplanner.ui.core.HeaderPanel;
// nazmplanner.ui.tasks.TasksMediator removed as it was an unused import.
import nazmplanner.ui.util.GBC;

/**
 * <h2>EventsPrimaryPanel</h2>
 * * <p>Supposed to display the main calendar grid and event details.</p>
 */
public class PrimaryPanel extends JPanel
{
    private final CalendarsMessageBroker calendarsMessageBroker;
    
    private HeaderPanel headerPanel;
    private CalendarGridPanel calendarGridPanel;
    private JScrollPane calendarGridScrollPane;
    
    public PrimaryPanel(CalendarsMessageBroker calendarsMessageBroker)
    {
        this.calendarsMessageBroker = calendarsMessageBroker;
        
        initComponents();
        initStyling();
        initLayout();
    }
    
    private void initComponents()
    {
        headerPanel = new HeaderPanel("November 2025 - Month View"); 
        
        calendarGridPanel = new CalendarGridPanel(calendarsMessageBroker);
        
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
}