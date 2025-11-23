package nazmplanner.ui;

import javax.swing.JFrame;
import nazmplanner.ui.tasks.*;

/**
 * <h2>MainFrame</h2>
 * 
 * <p>Main window/container. It is supposed to have a top navbar in the future.
 * Currently only displays the TasksPanel at the bottom.</p>
 * 
 * @author Fahad Hassan
 * @version 22/11/2025
 */
public class MainFrame extends JFrame
{
    
    private final TasksMediator tasksMediator;
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;
    private TasksPanel tasksPanel;
    
    public MainFrame()
    {
        this.tasksMediator = new TasksMediator();
        
        super.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setTitle("Nazm Planner");
        
        tasksPanel = new TasksPanel(tasksMediator);
        super.add(tasksPanel);
    }
    
    public TasksMediator getTasksMediator()
    {
        return tasksMediator;
    }

}
