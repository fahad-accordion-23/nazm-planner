package nazmplanner.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

import nazmplanner.application.calendars.CalendarsMessageBroker;
import nazmplanner.application.tasks.TasksMessageBroker;
import nazmplanner.ui.components.NavbarPanel;
import nazmplanner.ui.components.ViewPanel;
import nazmplanner.ui.util.GBC;

/**
 * <h2>MainFrame</h2>
 * 
 * <p>Main window/container. It is supposed to have a top navbar in the future.
 * Currently only displays the TasksPanel at the bottom.</p>
 * 
 * @author Fahad Hassan
 * @version 22/11/2025
 */
public class MainFrame extends JFrame
{
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;
    
    private TasksMessageBroker tasksMessageBroker;
    private CalendarsMessageBroker calendarsMessageBroker;
    private MainMessageBroker mainMediator;
    private NavbarPanel navbarPanel;
    private ViewPanel viewPanel;
        
    public MainFrame(TasksMessageBroker tasksMessageBroker, CalendarsMessageBroker calendarsMessageBroker)
    {                
        mainMediator = new MainMessageBroker();
        this.tasksMessageBroker = tasksMessageBroker;
        this.calendarsMessageBroker = calendarsMessageBroker;
        
        initComponents();
        initLayout();
        initStyling();
    }
    
    private void initComponents()
    {
        navbarPanel = new NavbarPanel(mainMediator);
        viewPanel = new ViewPanel(mainMediator, tasksMessageBroker, calendarsMessageBroker);
    }
    
    private void initLayout()
    {
        setLayout(new GridBagLayout());
        add(navbarPanel, 
            new GBC(0, 0)
            .setAnchor(GridBagConstraints.NORTH)
            .setWeight(1.00, 0.00)
            .setFill(GridBagConstraints.HORIZONTAL));
        
        add(viewPanel, 
                new GBC(0, 1)
                .setAnchor(GridBagConstraints.NORTH)
                .setWeight(1.00, 1.00)
                .setFill(GridBagConstraints.BOTH));
            
        pack();
    }
    
    private void initStyling()
    {
        super.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setTitle("Nazm Planner");
    }
    
    public MainMessageBroker getMainMediator()
    {
        return mainMediator;
    }
}
