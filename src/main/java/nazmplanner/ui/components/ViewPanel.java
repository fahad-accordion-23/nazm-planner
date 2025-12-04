package nazmplanner.ui.components;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import nazmplanner.application.tasks.TasksMessageBroker;
import nazmplanner.ui.MainMessageBroker;
import nazmplanner.ui.calendars.CalendarsPanel;
import nazmplanner.ui.messages.ViewSwitchMessage;
import nazmplanner.ui.tasks.TasksPanel;

/**
 * <h2>ViewPanel</h2>
 * 
 * <p>Contains all the views (tasksPanel, calendarsPanel)</p>
 * 
 * @author Fahad Hassan
 * @version 04/12/2025
 */
public class ViewPanel extends JPanel
{
    private final MainMessageBroker mainMessageBroker;
    private final TasksMessageBroker tasksMessageBroker;
    private TasksPanel tasksPanel;
    private CalendarsPanel calendarsPanel;
    
    public ViewPanel(MainMessageBroker mainMessageBroker, TasksMessageBroker tasksMessageBroker)
    {
        this.mainMessageBroker = mainMessageBroker;
        this.tasksMessageBroker = tasksMessageBroker;
        this.mainMessageBroker.subscribe(ViewSwitchMessage.class, this::onEvent);
        
        initComponents();
        initLayout();
        
        showPanel(tasksPanel);
    }
    
    private void initComponents()
    {
        tasksPanel = new TasksPanel(tasksMessageBroker);
        calendarsPanel = new CalendarsPanel();
    }
    
    private void initLayout()
    {
        setLayout(new BorderLayout());
    }
        
    public void onEvent(ViewSwitchMessage event)
    {
        switch(event.viewType())
        {
        case ViewType.TASKS -> showPanel(tasksPanel);
        case ViewType.EVENTS -> showPanel(calendarsPanel);
        }
    }
    
    private void showPanel(JPanel panel)
    {
        removeAll();
        add(panel, BorderLayout.CENTER);
        
        revalidate();
        repaint();
    }
    
}
