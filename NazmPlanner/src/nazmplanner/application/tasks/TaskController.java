package nazmplanner.application.tasks;

import java.time.LocalDateTime;
import java.util.List;
import nazmplanner.domain.tasks.Task;
import nazmplanner.domain.tasks.TaskSystem;
import nazmplanner.ui.tasks.CreationFormPanel;
import nazmplanner.ui.tasks.TaskCardListPanel;

/**
 * <h2>TaskController</h2>
 * 
 * <p>Coordinates the UI and Domain layers for Tasks</p>
 * 
 * @author Fahad Hassan
 * @version 22/11/2025
 */
public class TaskController
{
    private final TaskSystem taskSystem;
    private final TaskCardListPanel taskCardListPanel;
    private final CreationFormPanel creationFormPanel;
    
    public TaskController(TaskSystem taskSystem, TaskCardListPanel taskCardListPanel, CreationFormPanel creationFormPanel)
    {
        this.taskSystem = taskSystem;
        this.taskCardListPanel = taskCardListPanel;
        this.creationFormPanel = creationFormPanel;
        
        this.creationFormPanel.setTaskCreationListener((title, desc, dueDate) -> 
        {
            createNewTask(title, desc, dueDate);
        });
    }
    
    public void createNewTask(String title, String description, LocalDateTime dueDate)
    {
        taskSystem.addTask(title, description, dueDate);
        taskCardListPanel.setTasks(taskSystem.getAllTasks());
        
    }
}
