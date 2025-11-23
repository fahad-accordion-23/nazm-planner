package nazmplanner.ui.tasks.components;

import java.awt.Color;
import javax.swing.JPanel;
import nazmplanner.ui.tasks.TasksMediator;

/**
 * <h2>SidebarPanel</h2>
 * 
 * <p>Not functional right now. Supposed to display task lists.</p>
 * 
 * @author Fahad Hassan
 * @version 22/11/2025 
 */
public class SidebarPanel extends JPanel
{
    
    private TasksMediator tasksMediator;
    
    public SidebarPanel(TasksMediator tasksMediator)
    {
        this.tasksMediator = tasksMediator;
        initStyling();
    }
    
    private void initStyling()
    {
        super.setBackground(Color.GRAY);
    }
    
}
