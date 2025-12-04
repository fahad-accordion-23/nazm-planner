package nazmplanner.ui.tasks;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

import nazmplanner.ui.MainMediator;
import nazmplanner.ui.tasks.components.PrimaryPanel;
import nazmplanner.ui.tasks.components.SidebarPanel;
import nazmplanner.ui.tasks.message.TaskAddedMessage;
import nazmplanner.ui.tasks.message.TaskDeletedMessage;
import nazmplanner.ui.tasks.message.TaskDisplayedMessage;
import nazmplanner.ui.tasks.message.TaskEditedMessage;
import nazmplanner.ui.tasks.message.TaskMarkedMessage;
import nazmplanner.ui.tasks.message.TaskSelectedMessage;
import nazmplanner.ui.tasks.message.TaskUpdatedMessage;
import nazmplanner.ui.tasks.message.TasksUpdatedMessage;
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
    private MainMediator mainMediator;
    private TasksMediator tasksMediator;
    private PrimaryPanel primaryPanel;
    private SidebarPanel sidebarPanel;
    
    public TasksPanel(MainMediator mainMediator)
    {
        this.tasksMediator = new TasksMediator();
        this.mainMediator = mainMediator;
        
        mainMediator.subscribe(TasksUpdatedMessage.class, this::onEventForwardToTasksMediator);
        mainMediator.subscribe(TaskUpdatedMessage.class, this::onEventForwardToTasksMediator);
        mainMediator.subscribe(TaskDisplayedMessage.class, this::onEventForwardToTasksMediator);
        
        tasksMediator.subscribe(TaskEditedMessage.class, this::onEventForwardToMainMediator);
        tasksMediator.subscribe(TaskAddedMessage.class, this::onEventForwardToMainMediator);
        tasksMediator.subscribe(TaskDeletedMessage.class, this::onEventForwardToMainMediator);
        tasksMediator.subscribe(TaskMarkedMessage.class, this::onEventForwardToMainMediator);
        tasksMediator.subscribe(TaskSelectedMessage.class, this::onEventForwardToMainMediator);
        
        initComponents();
        initLayout();
    }
    
    public void onEventForwardToTasksMediator(Message event)
    {
        tasksMediator.publish(event);
    }
    
    public void onEventForwardToMainMediator(Message event)
    {
        mainMediator.publish(event);
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
