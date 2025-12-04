package nazmplanner.ui.tasks.components;

import java.awt.Color;
import nazmplanner.domain.tasks.Task;
import nazmplanner.ui.core.CardListPanel;
import nazmplanner.ui.tasks.TasksMediator;
import nazmplanner.ui.tasks.message.TasksUpdatedMessage;

/**
 * <h2>TaskCardListPanel</h2>
 * 
 * <p>Container for displaying TaskCardPanel objects in a vbox style</p>
 * 
 *  @author Fahad Hassan
 *  @version 24/11/2025
 */
public class TaskCardListPanel extends CardListPanel
{
   
    private final TasksMediator tasksMediator;
    
    public TaskCardListPanel(TasksMediator tasksMediator)
    {
        this.tasksMediator = tasksMediator;
        tasksMediator.subscribe(TasksUpdatedMessage.class, this::onTaskUpdated);
        
        initStyling();
    }
    
    public void onTaskUpdated(TasksUpdatedMessage event)
    {
        clear();
        
        for (Task task : event.tasks())
        {
            addCard(new TaskCardPanel(task, tasksMediator));
        }
    }
    
    private void initStyling()
    {
        setBackground(Color.LIGHT_GRAY);
    }
    
}