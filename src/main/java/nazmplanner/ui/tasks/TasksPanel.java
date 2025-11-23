package nazmplanner.ui.tasks;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

import nazmplanner.ui.tasks.components.PrimaryPanel;
import nazmplanner.ui.tasks.components.SidebarPanel;
import nazmplanner.ui.util.GBC;

/**
 * <h2>TasksPanel</h2>

 * <p>The primary view for all the Task related things. Has a sidebar on 
 * the left and a primary view on the right. </p>
 *  
 * @brief Has a sidebar and a primary view for showing tasks.
 * @author Fahad Hassan
 * @version 22/11/2025
 */
public class TasksPanel extends JPanel
{
    private TasksMediator tasksMediator;
    private PrimaryPanel primaryPanel;
    private SidebarPanel sidebarPanel;
    
    public TasksPanel(TasksMediator tasksMediator)
    {
        this.tasksMediator = tasksMediator;
        
        initComponents();
        initLayout();
    }
    
    
    private void initComponents()
    {
        sidebarPanel = new SidebarPanel(tasksMediator);
        primaryPanel = new PrimaryPanel(tasksMediator);
    }
    
    private void initLayout()
    {
        super.setLayout(new GridBagLayout());
        
        super.add(sidebarPanel, 
                  new GBC(0, 0, 1, 1)
                  .setAnchor(GridBagConstraints.SOUTH)
                  .setWeight(0.20, 1.00)
                  .setFill(GridBagConstraints.BOTH));
        
        super.add(primaryPanel, 
                  new GBC(1, 0, 1, 1)
                  .setAnchor(GridBagConstraints.NORTH)
                  .setWeight(0.80, 1.00)
                  .setFill(GridBagConstraints.BOTH));
    }
    
}
