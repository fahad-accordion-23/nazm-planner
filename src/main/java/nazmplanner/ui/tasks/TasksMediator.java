package nazmplanner.ui.tasks;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import nazmplanner.domain.tasks.Task;
import nazmplanner.ui.tasks.contracts.TaskCreatorInterface;
import nazmplanner.ui.tasks.contracts.TaskDeleterInterface;
import nazmplanner.ui.tasks.contracts.TaskEditorInterface;
import nazmplanner.ui.tasks.contracts.TaskMarkerInterface;
import nazmplanner.ui.tasks.contracts.TaskSelectorInterface;
import nazmplanner.ui.tasks.contracts.TaskViewerInterface;
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
    private TaskMarkerInterface markTaskHandler;
    private TaskDeleterInterface deleteTaskHandler;
    private TaskSelectorInterface selectTaskHandler;
    private TaskViewerInterface taskViewer;
    private TaskEditorInterface editTaskHandler;
    
    /* Notifiers */
    
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
    
    public void requestMarkTaskCompleted(UUID id)
    {
        if (Objects.isNull(markTaskHandler))
        {
            throw new IllegalStateException("TasksMediator.markTaskHandler not set!");
        }
        
        markTaskHandler.markTaskCompleted(id);
    }
        
    public void requestMarkTaskTodo(UUID id)
    {
        if (Objects.isNull(markTaskHandler))
        {
            throw new IllegalStateException("TasksMediator.markTaskHandler not set!");
        }
        
        markTaskHandler.markTaskTodo(id);
    }
    
    public void requestDeleteTask(UUID id)
    {
        if (Objects.isNull(deleteTaskHandler))
        {
            throw new IllegalStateException("TasksMediator.deleteTaskHandler not set!");
        }
        
        deleteTaskHandler.deleteTask(id);
    }
        
    public void requestSelectTask(UUID id)
    {
        if (Objects.isNull(selectTaskHandler))
        {
            throw new IllegalStateException("TasksMediator.selectTaskHandler not set!");
        }
        
        selectTaskHandler.selectTask(id);
    }
        
    public void displaySelectedTask(Task task)
    {
        if (Objects.isNull(taskViewer))
        {
            throw new IllegalStateException("TasksMediator.taskViewer not set!");
        }
        
        taskViewer.displayTask(task);
    }
    
    public void requestEditTask(UUID id, String title, String description)
    {
        if (Objects.isNull(taskViewer))
        {
            throw new IllegalStateException("TasksMediator.taskViewer not set!");
        }
        
        editTaskHandler.editTask(id, title, description);
    }
    
    /* Setters */
    
    public void setOnAddTaskHandler(TaskCreatorInterface taskAdder)
    {        
        this.addTaskHandler = taskAdder;
    }
    
    public void setOnUpdateTasksHandler(TasksUpdaterInterface tasksUpdater)
    {        
        this.updateTasksHandler = tasksUpdater;
    }
    
    public void setOnMarkTaskHandler(TaskMarkerInterface taskMarker)
    {
        this.markTaskHandler = taskMarker;
    }
    
    public void setOnDeleteTaskHandler(TaskDeleterInterface taskDeleter)
    {
        this.deleteTaskHandler = taskDeleter;
    }
    
    public void setOnSelectTaskHandler(TaskSelectorInterface taskSelector)
    {
        this.selectTaskHandler = taskSelector;
    }
    
    public void setOnTaskViewer(TaskViewerInterface viewer)
    {
        this.taskViewer = viewer;
    }
    
    public void setOnEditTaskHandler(TaskEditorInterface editTaskHandler)
    {
        this.editTaskHandler = editTaskHandler;
    }
    
}
