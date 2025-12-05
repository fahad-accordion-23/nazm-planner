package nazmplanner.application.tasks;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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
    
    private final TasksMessageBroker tasksMessageBroker;
    private final TasksSystem taskSystem;
    private UUID selectedTaskId;
    
    public TasksController(TasksSystem taskSystem, TasksMessageBroker tasksMessageBroker)
    {
        this.taskSystem = taskSystem;
        this.tasksMessageBroker = tasksMessageBroker;
        
        tasksMessageBroker.subscribe(TaskAddedMessage.class, this::onEvent);
        tasksMessageBroker.subscribe(TaskMarkedMessage.class, this::onEvent);
        tasksMessageBroker.subscribe(TaskDeletedMessage.class, this::onEvent);
        tasksMessageBroker.subscribe(TaskSelectedMessage.class, this::onEvent);
        tasksMessageBroker.subscribe(TaskEditedMessage.class, this::onEvent);        
    }
    
    public void updateTasks()
    {
        List<Task> tasks = taskSystem.getAllTasks();
        List<TaskDTO> dtos = toDTOList(tasks);
        tasksMessageBroker.publish(new TasksUpdatedMessage(dtos));
    }
    

    public void notifyTaskUpdate(UUID id)
    {
        Task task = taskSystem.findById(id);
        
        if (task != null) {
            tasksMessageBroker.publish(new TaskUpdatedMessage(toDTO(task)));
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
            tasksMessageBroker.publish(new TaskDisplayedMessage(null));
        }
    }

    private void onEvent(TaskSelectedMessage event)
    {
        UUID id = event.id();
        this.selectedTaskId = id;
        Task task = taskSystem.findById(id);
        
        if (Objects.isNull(task)) {
            throw new IllegalArgumentException("Attempted to select non-existent task: " + id);
        }
        
        tasksMessageBroker.publish(new TaskDisplayedMessage(toDTO(task)));
    }

    private void onEvent(TaskEditedMessage event)
    {
        UUID id = event.id();
        taskSystem.updateTask(id, event.title(), event.description());
        
        updateTasks();
        notifyTaskUpdate(id);
    }
    
    private TaskDTO toDTO(Task task)
    {
        return new TaskDTO(
                task.getID(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getDueDate(),
                task.getCreationDate()
            );
    }
    

    private List<TaskDTO> toDTOList(List<Task> tasks)
    {
        return tasks.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
