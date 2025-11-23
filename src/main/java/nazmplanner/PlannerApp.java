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
        	
            /* UI */
            MainFrame mainFrame = new MainFrame();
            
            /* Domain */
            TaskSystem taskSystem = new TaskSystem();
            
            /* Application */
            TaskController taskController = new TaskController(taskSystem, mainFrame.getTasksMediator());
            
            mainFrame.setVisible(true);
        });
    }

}
