package nazmplanner.application.tasks;

import java.util.Objects;
import java.util.UUID;

import nazmplanner.application.tasks.messages.TaskAddedMessage;
import nazmplanner.application.tasks.messages.TaskDeletedMessage;
import nazmplanner.application.tasks.messages.TaskDisplayedMessage;
import nazmplanner.application.tasks.messages.TaskEditedMessage;
import nazmplanner.application.tasks.messages.TaskMarkedMessage;
import nazmplanner.application.tasks.messages.TaskSelectedMessage;
import nazmplanner.application.tasks.messages.TaskUpdatedMessage;
import nazmplanner.application.tasks.messages.TasksUpdatedMessage;
import nazmplanner.domain.tasks.Task;
import nazmplanner.domain.tasks.TaskStatus;
import nazmplanner.domain.tasks.TasksSystem;
import nazmplanner.ui.MainMessageBroker;

/**
 * <h2>TaskController</h2>
 * 
 * <p>Coordinates the UI and Domain layers for Tasks</p>
 * 
 * @author Fahad Hassan
 * @version 24/11/2025
 */
public class TasksController
{
    
    private final MainMessageBroker mainMediator;
    private final TasksSystem taskSystem;
    private UUID selectedTaskId;
    
    public TasksController(TasksSystem taskSystem, MainMessageBroker mainMediator)
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
