package nazmplanner.application.tasks;
import java.util.UUID;

import java.time.LocalDateTime;
import nazmplanner.domain.tasks.TaskSystem;
import nazmplanner.ui.tasks.TasksMediator;
import nazmplanner.ui.tasks.contracts.TaskCreatorInterface;

import nazmplanner.domain.tasks.Task;
import nazmplanner.domain.tasks.TaskSystem;

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
    
    public void searchTaskById(String idString) {
        try {
            UUID id = UUID.fromString(idString.trim());
            Task task = taskSystem.findById(id);
            
            if (task != null) {
                // For now, we just show a popup with the result
                javax.swing.JOptionPane.showMessageDialog(null, 
                    "Found Task:\nTitle: " + task.getTitle() + "\nStatus: " + task.getStatus(), 
                    "Task Found", 
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "No task found with that ID.", "Not Found", javax.swing.JOptionPane.WARNING_MESSAGE);
            }
        } catch (IllegalArgumentException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Invalid ID format.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteTask(UUID id) {
        taskSystem.deleteTask(id);
        updateTasks(); // Refresh the list after deleting
    }
    
    
    
}
