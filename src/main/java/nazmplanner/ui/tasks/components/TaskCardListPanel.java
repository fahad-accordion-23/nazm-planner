package nazmplanner.ui.tasks.components;

import java.awt.Color;

import nazmplanner.application.tasks.TasksMessageBroker;
import nazmplanner.application.tasks.messages.TasksUpdatedMessage;
import nazmplanner.domain.tasks.Task;
import nazmplanner.ui.core.CardListPanel;

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
   
    private final TasksMessageBroker tasksMediator;
    
    public TaskCardListPanel(TasksMessageBroker tasksMediator)
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