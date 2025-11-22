package nazmplanner.ui.tasks;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import nazmplanner.domain.tasks.Task;
import nazmplanner.ui.tasks.contracts.TaskCreatorInterface;
import nazmplanner.ui.tasks.contracts.TasksUpdaterInterface;

/**
 * <h2>TasksMediator</h2>
 * 
 * <p>Mediator class for all the UI events to allow for easy coordination between controllers and UI</p>
 * 
 * @author Fahad Hassan
 * @version 22/11/2025
 */
public class TasksMediator
{
    private TaskCreatorInterface addTaskHandler;
    private TasksUpdaterInterface updateTasksHandler;
    
    public void requestAddTask(String title, String description, LocalDateTime dueDate)
    {
        addTaskHandler.addTask(title, description, dueDate);
    }
    
    public void requestUpdateTasks(List<Task> tasks)
    {
        if (Objects.isNull(addTaskHandler))
        {
            throw new IllegalStateException("TasksMediator.addTaskHandler not set!");
        }
        
        updateTasksHandler.updateTasks(tasks);
    }
    
    public void setOnAddTaskHandler(TaskCreatorInterface taskAdder)
    {
        if (Objects.isNull(updateTasksHandler))
        {
            throw new IllegalStateException("TasksMediator.updateTasksHandler not set!");
        }
        
        this.addTaskHandler = taskAdder;
    }
    
    public void setOnUpdateTasksHandler(TasksUpdaterInterface tasksUpdater)
    {
        this.updateTasksHandler = tasksUpdater;
    }
    
}
