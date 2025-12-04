package nazmplanner.ui.tasks.components;

import java.awt.Color;
import javax.swing.JPanel;

import nazmplanner.application.tasks.TasksMessageBroker;

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
    
    private TasksMessageBroker tasksMessageBroker;
    
    public SidebarPanel(TasksMessageBroker tasksMessageBroker)
    {
        this.tasksMessageBroker = tasksMessageBroker;
        initStyling();
    }
    
    private void initStyling()
    {
        super.setBackground(Color.GRAY);
    }
    
}
