package nazmplanner.ui.events.components;

import java.awt.Color;
import javax.swing.JPanel;
import nazmplanner.ui.tasks.TasksMediator;

/**
 * <h2>EventsSidebarPanel</h2>
 *
 * <p>Supposed to display a list of calendar views or event lists.</p>
 */
public class SidebarPanel extends JPanel
{
    public SidebarPanel()
    {
        initStyling();
    }
    
    private void initStyling()
    {
        super.setBackground(Color.DARK_GRAY.darker()); // Slightly different color for distinction
    }
}