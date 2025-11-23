package nazmplanner.application.tasks;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import nazmplanner.domain.tasks.Task;
import nazmplanner.domain.tasks.TaskSystem;
import nazmplanner.ui.tasks.TasksMediator;
import nazmplanner.ui.tasks.contracts.TaskCreatorInterface;
import nazmplanner.ui.tasks.contracts.TaskDeleterInterface;
import nazmplanner.ui.tasks.contracts.TaskEditorInterface;
import nazmplanner.ui.tasks.contracts.TaskMarkerInterface;
import nazmplanner.ui.tasks.contracts.TaskSelectorInterface;

/**
 * <h2>TaskController</h2>
 * 
 * <p>Coordinates the UI and Domain layers for Tasks</p>
 * 
 * @author Fahad Hassan
 * @version 22/11/2025
 */
public class TaskController implements TaskCreatorInterface, TaskMarkerInterface, 
                                       TaskDeleterInterface, TaskSelectorInterface,
                                       TaskEditorInterface
{
    
    private final TasksMediator tasksMediator;
    private final TaskSystem taskSystem;
    private UUID selectedTaskId;
    
    public TaskController(TaskSystem taskSystem, TasksMediator tasksMediator)
    {
        this.taskSystem = taskSystem;
        this.tasksMediator = tasksMediator;
        
        tasksMediator.setOnAddTaskHandler(this);
        tasksMediator.setOnMarkTaskHandler(this);
        tasksMediator.setOnDeleteTaskHandler(this);
        tasksMediator.setOnSelectTaskHandler(this);
        tasksMediator.setOnEditTaskHandler(this);
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
        
        if (id.equals(selectedTaskId))
        {
            refreshDetailView(id);
        }
    }

    @Override
    public void markTaskTodo(UUID id)
    {
        taskSystem.markTaskTodo(id);
        tasksMediator.requestUpdateTasks(taskSystem.getAllTasks());
        
        if (id.equals(selectedTaskId))
        {
            refreshDetailView(id);
        }
    }
    
    @Override
    public void deleteTask(UUID id)
    {
        taskSystem.deleteTask(id);
        tasksMediator.requestUpdateTasks(taskSystem.getAllTasks());
        
        if (id.equals(selectedTaskId))
        {
            selectedTaskId = null;
            tasksMediator.displaySelectedTask(null);
        }
    }

    @Override
    public void selectTask(UUID id)
    {
        this.selectedTaskId = id;
        Task task = taskSystem.findById(id);

        if (Objects.isNull(task))
        {
            throw new IllegalArgumentException("Attempted to select non-existent task: " + id);
        }

        tasksMediator.displaySelectedTask(task);
    }
    
    private void refreshDetailView(UUID id)
    {
        Task freshTask = taskSystem.findById(id);
        if (freshTask != null)
        {
            tasksMediator.displaySelectedTask(freshTask);
        }
    }

    @Override
    public void editTask(UUID id, String newTitle, String newDescription)
    {
        taskSystem.updateTask(id, newTitle, newDescription);
        
        tasksMediator.requestUpdateTasks(taskSystem.getAllTasks());
        
        if (id.equals(selectedTaskId))
        {
            Task freshTask = taskSystem.findById(id);
            tasksMediator.displaySelectedTask(freshTask);
        }
    }
    
}
