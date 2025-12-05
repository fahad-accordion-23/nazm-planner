package nazmplanner.ui.calendars;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import nazmplanner.application.calendars.CalendarsMessageBroker;
import nazmplanner.ui.calendars.components.*;
import nazmplanner.ui.util.GBC;

/**
 * <h2>EventsPanel</h2>
 * 
 * <p>The primary view for all Calendar related things. Has a sidebar on 
 * the left and a primary view on the right.</p>
 * 
 * @author Fahad Hassan
 * @version 05/12/2025
 */
public class CalendarsPanel extends JPanel
{
    private final CalendarsMessageBroker calendarsMessageBroker;
    private PrimaryPanel primaryPanel;
    private SidebarPanel sidebarPanel;
    
    public CalendarsPanel(CalendarsMessageBroker calendarsMessageBroker)
    {
        this.calendarsMessageBroker = calendarsMessageBroker;
        
        initComponents();
        initLayout();
    }
    
    private void initComponents()
    {
        primaryPanel = new PrimaryPanel(calendarsMessageBroker);
        
        sidebarPanel = new SidebarPanel(calendarsMessageBroker, new SidebarPanel.MonthNavigationListener()
        {
            @Override
            public void onPreviousMonth()
            {
                primaryPanel.navigateToPreviousMonth();
            }
            
            @Override
            public void onNextMonth()
            {
                primaryPanel.navigateToNextMonth();
            }
        });
    }
    
    private void initLayout()
    {
        super.setLayout(new GridBagLayout());
        
        super.add(sidebarPanel, 
                  new GBC(0, 0, 1, 1)
                  .setAnchor(GridBagConstraints.WEST)
                  .setWeight(0.20, 1.00)
                  .setFill(GridBagConstraints.BOTH));
        
        super.add(primaryPanel, 
                  new GBC(1, 0, 1, 1)
                  .setAnchor(GridBagConstraints.EAST)
                  .setWeight(0.80, 1.00)
                  .setFill(GridBagConstraints.BOTH));
    }
}