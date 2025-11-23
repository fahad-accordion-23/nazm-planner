package nazmplanner.ui.tasks.components;

import java.awt.Color;
import java.util.List;
import nazmplanner.domain.tasks.Task;
import nazmplanner.ui.components.CardListPanel;
import nazmplanner.ui.tasks.TasksMediator;
import nazmplanner.ui.tasks.contracts.TasksUpdaterInterface;

/**
 * <h2>TaskCardListPanel</h2>
 * 
 * <p>Container for displaying TaskCardPanel objects in a vbox style</p>
 * 
 *  @author Fahad Hassan
 *  @version 22/11/2025
 */
public class TaskCardListPanel extends CardListPanel implements TasksUpdaterInterface
{
   
    private final TasksMediator tasksMediator;
    
    public TaskCardListPanel(TasksMediator tasksMediator)
    {
        this.tasksMediator = tasksMediator;
        tasksMediator.setOnUpdateTasksHandler(this);
        
        initStyling();
    }
    
    public void updateTasks(List<Task> tasks)
    {
        clear();
        
        for (Task task : tasks)
        {
            addCard(new TaskCardPanel(task));
        }
    }
    
    private void initStyling()
    {
        setBackground(Color.LIGHT_GRAY);
    }
    
}