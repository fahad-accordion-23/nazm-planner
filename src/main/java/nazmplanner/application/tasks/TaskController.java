package nazmplanner.application.tasks;

import java.util.Objects;
import java.util.UUID;
import nazmplanner.domain.tasks.Task;
import nazmplanner.domain.tasks.TaskStatus;
import nazmplanner.domain.tasks.TaskSystem;
import nazmplanner.ui.MainMediator;
import nazmplanner.ui.tasks.message.TaskAddedMessage;
import nazmplanner.ui.tasks.message.TaskDeletedMessage;
import nazmplanner.ui.tasks.message.TaskDisplayedMessage;
import nazmplanner.ui.tasks.message.TaskEditedMessage;
import nazmplanner.ui.tasks.message.TaskMarkedMessage;
import nazmplanner.ui.tasks.message.TaskSelectedMessage;
import nazmplanner.ui.tasks.message.TaskUpdatedMessage;
import nazmplanner.ui.tasks.message.TasksUpdatedMessage;

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
    
    private final MainMediator mainMediator;
    private final TaskSystem taskSystem;
    private UUID selectedTaskId;
    
    public TaskController(TaskSystem taskSystem, MainMediator mainMediator)
    {
        this.taskSystem = taskSystem;
        this.mainMediator = mainMediator;
        
        mainMediator.subscribe(TaskAddedMessage.class, this::onEvent);
        mainMediator.subscribe(TaskMarkedMessage.class, this::onEvent);
        mainMediator.subscribe(TaskDeletedMessage.class, this::onEvent);
        mainMediator.subscribe(TaskSelectedMessage.class, this::onEvent);
        mainMediator.subscribe(TaskEditedMessage.class, this::onEvent);
        
        updateTasks();
    }
    
    public void updateTasks()
    {
        mainMediator.publish(new TasksUpdatedMessage(taskSystem.getAllTasks()));
    }
    

    public void notifyTaskUpdate(UUID id)
    {
        Task task = taskSystem.findById(id);
        if (task != null)
        {
            mainMediator.publish(new TaskUpdatedMessage(task));
        }
    }
        

    private void onEvent(TaskAddedMessage event)
    {
        taskSystem.addTask(event.title(), event.description(), event.dueDate());
        updateTasks();
    }

    private void onEvent(TaskMarkedMessage event)
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
    
    private void onEvent(TaskDeletedMessage event)
    {
        UUID id = event.id();
        taskSystem.deleteTask(id);
        updateTasks();
        
        if (id.equals(selectedTaskId))
        {
            selectedTaskId = null;
            mainMediator.publish(new TaskDisplayedMessage(null));
        }
    }

    private void onEvent(TaskSelectedMessage event)
    {
        UUID id = event.id();
        this.selectedTaskId = id;
        Task task = taskSystem.findById(id);

        if (Objects.isNull(task))
        {
            throw new IllegalArgumentException("Attempted to select non-existent task: " + id);
        }

        mainMediator.publish(new TaskDisplayedMessage(task));
    }

    private void onEvent(TaskEditedMessage event)
    {
        UUID id = event.id();
        taskSystem.updateTask(id, event.title(), event.description());
        
        updateTasks();
        notifyTaskUpdate(id);
    }
}
