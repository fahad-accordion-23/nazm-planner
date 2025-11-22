package nazmplanner.application.tasks;

import java.time.LocalDateTime;
import java.util.UUID;

import nazmplanner.domain.tasks.TaskSystem;
import nazmplanner.ui.tasks.TasksMediator;
import nazmplanner.ui.tasks.contracts.TaskCreatorInterface;
import nazmplanner.ui.tasks.contracts.TaskDeleterInterface;
import nazmplanner.ui.tasks.contracts.TaskMarkerInterface;

/**
 * <h2>TaskController</h2>
 * 
 * <p>Coordinates the UI and Domain layers for Tasks</p>
 * 
 * @author Fahad Hassan
 * @version 22/11/2025
 */
public class TaskController implements TaskCreatorInterface, TaskMarkerInterface, TaskDeleterInterface
{
    
    private final TasksMediator tasksMediator;
    private final TaskSystem taskSystem;
    
    public TaskController(TaskSystem taskSystem, TasksMediator tasksMediator)
    {
        this.taskSystem = taskSystem;
        this.tasksMediator = tasksMediator;
        
        tasksMediator.setOnAddTaskHandler(this);
        tasksMediator.setOnMarkTaskHandler(this);
        tasksMediator.setOnDeleteTaskHandler(this);
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

    @Override
    public void markTaskCompleted(UUID id)
    {
        taskSystem.markTaskCompleted(id);
        tasksMediator.requestUpdateTasks(taskSystem.getAllTasks());
    }

    @Override
    public void markTaskTodo(UUID id)
    {
        taskSystem.markTaskTodo(id);
        tasksMediator.requestUpdateTasks(taskSystem.getAllTasks());
    }
    
    @Override
    public void deleteTask(UUID id)
    {
        taskSystem.deleteTask(id);
        tasksMediator.requestUpdateTasks(taskSystem.getAllTasks());
    }
    
}
