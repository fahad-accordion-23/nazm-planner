package nazmplanner.application.tasks;

import java.util.Objects;
import java.util.UUID;
import nazmplanner.domain.tasks.Task;
import nazmplanner.domain.tasks.TaskStatus;
import nazmplanner.domain.tasks.TaskSystem;
import nazmplanner.ui.tasks.TasksMediator;
import nazmplanner.ui.tasks.contracts.TaskAddedEvent;
import nazmplanner.ui.tasks.contracts.TaskDeletedEvent;
import nazmplanner.ui.tasks.contracts.TaskDisplayedEvent;
import nazmplanner.ui.tasks.contracts.TaskEditedEvent;
import nazmplanner.ui.tasks.contracts.TaskMarkedEvent;
import nazmplanner.ui.tasks.contracts.TaskSelectedEvent;
import nazmplanner.ui.tasks.contracts.TaskUpdatedEvent;
import nazmplanner.ui.tasks.contracts.TasksUpdatedEvent;

/**
 * <h2>TaskController</h2>
 * 
 * <p>Coordinates the UI and Domain layers for Tasks</p>
 * 
 * @author Fahad Hassan
 * @version 24/11/2025
 */
public class TaskController
{
    
    private final TasksMediator tasksMediator;
    private final TaskSystem taskSystem;
    private UUID selectedTaskId;
    
    public TaskController(TaskSystem taskSystem, TasksMediator tasksMediator)
    {
        this.taskSystem = taskSystem;
        this.tasksMediator = tasksMediator;
        
        tasksMediator.subscribe(TaskAddedEvent.class, this::onEvent);
        tasksMediator.subscribe(TaskMarkedEvent.class, this::onEvent);
        tasksMediator.subscribe(TaskDeletedEvent.class, this::onEvent);
        tasksMediator.subscribe(TaskSelectedEvent.class, this::onEvent);
        tasksMediator.subscribe(TaskEditedEvent.class, this::onEvent);
        updateTasks();
    }
    
    public void updateTasks()
    {
        tasksMediator.publish(new TasksUpdatedEvent(taskSystem.getAllTasks()));
    }
    

    public void notifyTaskUpdate(UUID id)
    {
        Task task = taskSystem.findById(id);
        if (task != null)
        {
            tasksMediator.publish(new TaskUpdatedEvent(task));
        }
    }
        

    private void onEvent(TaskAddedEvent event)
    {
        taskSystem.addTask(event.title(), event.description(), event.dueDate());
        updateTasks();
    }

    private void onEvent(TaskMarkedEvent event)
    {
        UUID id = event.id();
        if (event.newStatus() == TaskStatus.COMPLETED)
        {
            taskSystem.markTaskCompleted(id);
        }
        else
        {
            taskSystem.markTaskTodo(id);
        }
        
        updateTasks();
        notifyTaskUpdate(id);
    }
    
    private void onEvent(TaskDeletedEvent event)
    {
        UUID id = event.id();
        taskSystem.deleteTask(id);
        updateTasks();
        
        if (id.equals(selectedTaskId))
        {
            selectedTaskId = null;
            tasksMediator.publish(new TaskDisplayedEvent(null));
        }
    }

    private void onEvent(TaskSelectedEvent event)
    {
        UUID id = event.id();
        this.selectedTaskId = id;
        Task task = taskSystem.findById(id);

        if (Objects.isNull(task))
        {
            throw new IllegalArgumentException("Attempted to select non-existent task: " + id);
        }

        tasksMediator.publish(new TaskDisplayedEvent(task));
    }

    private void onEvent(TaskEditedEvent event)
    {
        UUID id = event.id();
        taskSystem.updateTask(id, event.title(), event.description());
        
        updateTasks();
        notifyTaskUpdate(id);
    }
}
