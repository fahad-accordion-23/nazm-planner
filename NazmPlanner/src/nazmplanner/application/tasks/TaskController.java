package nazmplanner.application.tasks;

import java.time.LocalDateTime;
import nazmplanner.domain.tasks.TaskSystem;
import nazmplanner.ui.tasks.TasksMediator;
import nazmplanner.ui.tasks.contracts.TaskCreatorInterface;

/**
 * <h2>TaskController</h2>
 * 
 * <p>Coordinates the UI and Domain layers for Tasks</p>
 * 
 * @author Fahad Hassan
 * @version 22/11/2025
 */
public class TaskController implements TaskCreatorInterface
{
    
    private final TasksMediator tasksMediator;
    private final TaskSystem taskSystem;
    
    public TaskController(TaskSystem taskSystem, TasksMediator tasksMediator)
    {
        this.taskSystem = taskSystem;
        this.tasksMediator = tasksMediator;
        
        tasksMediator.setOnAddTaskHandler(this);
        updateTasks();
    }
    
    public void updateTasks()
    {
        tasksMediator.requestUpdateTasks(taskSystem.getAllTasks());
    }
        
    public void addTask(String title, String description, LocalDateTime dueDate)
    {
        taskSystem.addTask(title, description, dueDate);
        tasksMediator.requestUpdateTasks(taskSystem.getAllTasks());
    }
    
}
