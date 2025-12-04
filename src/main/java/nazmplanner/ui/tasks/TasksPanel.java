package nazmplanner.ui.tasks;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

import nazmplanner.application.tasks.TasksMessageBroker;
import nazmplanner.application.tasks.messages.TaskAddedMessage;
import nazmplanner.application.tasks.messages.TaskDeletedMessage;
import nazmplanner.application.tasks.messages.TaskDisplayedMessage;
import nazmplanner.application.tasks.messages.TaskEditedMessage;
import nazmplanner.application.tasks.messages.TaskMarkedMessage;
import nazmplanner.application.tasks.messages.TaskSelectedMessage;
import nazmplanner.application.tasks.messages.TaskUpdatedMessage;
import nazmplanner.application.tasks.messages.TasksMessage;
import nazmplanner.application.tasks.messages.TasksUpdatedMessage;
import nazmplanner.ui.MainMessageBroker;
import nazmplanner.ui.tasks.components.PrimaryPanel;
import nazmplanner.ui.tasks.components.SidebarPanel;
import nazmplanner.ui.util.GBC;
import nazmplanner.util.messaging.Message;

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
    private TasksMessageBroker tasksMessageBroker;
    private PrimaryPanel primaryPanel;
    private SidebarPanel sidebarPanel;
    
    public TasksPanel(TasksMessageBroker tasksMessageBroker)
    {
        this.tasksMessageBroker = tasksMessageBroker;
        
        initComponents();
        initLayout();
    }

    private void initComponents()
    {
        sidebarPanel = new SidebarPanel(tasksMessageBroker);
        primaryPanel = new PrimaryPanel(tasksMessageBroker);
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
