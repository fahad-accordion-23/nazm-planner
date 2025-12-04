package nazmplanner.ui.events.components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import nazmplanner.ui.core.HeaderPanel;
// nazmplanner.ui.tasks.TasksMediator removed as it was an unused import.
import nazmplanner.ui.util.GBC;

/**
 * <h2>EventsPrimaryPanel</h2>
 * * <p>Supposed to display the main calendar grid and event details.</p>
 */
public class PrimaryPanel extends JPanel
{
    private HeaderPanel headerPanel;
    private CalendarGridPanel calendarGridPanel;
    private JScrollPane calendarGridScrollPane;
    
    public PrimaryPanel()
    {
        initComponents();
        initStyling();
        initLayout();
    }
    
    private void initComponents()
    {
        // Dummy title for the calendar view
        headerPanel = new HeaderPanel("November 2025 - Month View"); 
        
        calendarGridPanel = new CalendarGridPanel();
        calendarGridScrollPane = new JScrollPane(calendarGridPanel);
        // Remove the default border of the scroll pane for a cleaner look
        calendarGridScrollPane.setBorder(null); 
        // Always show vertical scrollbar in case the calendar is smaller than the view
        calendarGridScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }
    
    private void initStyling()
    {
        super.setBackground(Color.WHITE);
    }
    
    private void initLayout()
    {
        super.setLayout(new GridBagLayout());
        
        // Row 0: Header Panel (takes up 100% width, no vertical space)
        super.add(headerPanel, 
                  new GBC(0, 0)
                  .setWeight(1.00, 0.00)
                  .setAnchor(GridBagConstraints.NORTH)
                  .setFill(GridBagConstraints.HORIZONTAL));
        
        // Row 1: Main Calendar Grid (takes up 100% width, 100% remaining vertical space)
        super.add(calendarGridScrollPane, 
                  new GBC(0, 1)
                  .setWeight(1.00, 1.00)
                  .setAnchor(GridBagConstraints.NORTH)
                  .setFill(GridBagConstraints.BOTH));
    }
}