package nazmplanner;

import java.awt.EventQueue;

import nazmplanner.domain.tasks.*;
import nazmplanner.infrastructure.persistence.tasks.DatabaseManager;
import nazmplanner.application.tasks.*;
import nazmplanner.ui.MainFrame;

/**
 * <h2>PlannerApp</h2>
 * 
 * <p>Entry point for the app.</p>
 * 
 * @author Fahad Hassan
 * @version 22/11/2025
 */
public class PlannerApp
{
   
    public static void main(String[] args)
    {
        EventQueue.invokeLater(() -> 
        {
        	/* Database */
        	DatabaseManager.initializeDatabase();
        	            
            /* Domain */
            TasksSystem taskSystem = new TasksSystem();
            
            /* Application */
            TasksMessageBroker tasksMessageBroker = new TasksMessageBroker();
            TasksController taskController = new TasksController(taskSystem, tasksMessageBroker);
            
            /* UI */
            MainFrame mainFrame = new MainFrame(tasksMessageBroker);
            taskController.updateTasks();
            
            mainFrame.setVisible(true);
        });
    }

}
