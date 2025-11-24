package nazmplanner.ui.tasks;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

import nazmplanner.ui.MainMediator;
import nazmplanner.ui.tasks.components.PrimaryPanel;
import nazmplanner.ui.tasks.components.SidebarPanel;
import nazmplanner.ui.tasks.contracts.TaskAddedEvent;
import nazmplanner.ui.tasks.contracts.TaskDeletedEvent;
import nazmplanner.ui.tasks.contracts.TaskDisplayedEvent;
import nazmplanner.ui.tasks.contracts.TaskEditedEvent;
import nazmplanner.ui.tasks.contracts.TaskMarkedEvent;
import nazmplanner.ui.tasks.contracts.TaskSelectedEvent;
import nazmplanner.ui.tasks.contracts.TaskUpdatedEvent;
import nazmplanner.ui.tasks.contracts.TasksUpdatedEvent;
import nazmplanner.ui.util.GBC;
import nazmplanner.util.Event;

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
        
        mainMediator.subscribe(TasksUpdatedEvent.class, this::onEventForwardToTasksMediator);
        mainMediator.subscribe(TaskUpdatedEvent.class, this::onEventForwardToTasksMediator);
        mainMediator.subscribe(TaskDisplayedEvent.class, this::onEventForwardToTasksMediator);
        
        tasksMediator.subscribe(TaskEditedEvent.class, this::onEventForwardToMainMediator);
        tasksMediator.subscribe(TaskAddedEvent.class, this::onEventForwardToMainMediator);
        tasksMediator.subscribe(TaskDeletedEvent.class, this::onEventForwardToMainMediator);
        tasksMediator.subscribe(TaskMarkedEvent.class, this::onEventForwardToMainMediator);
        tasksMediator.subscribe(TaskSelectedEvent.class, this::onEventForwardToMainMediator);
        
        initComponents();
        initLayout();
    }
    
    public void onEventForwardToTasksMediator(Event event)
    {
        tasksMediator.publish(event);
    }
    
    public void onEventForwardToMainMediator(Event event)
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
