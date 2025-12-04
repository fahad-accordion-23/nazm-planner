package nazmplanner.ui.components;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import nazmplanner.application.tasks.TasksMessageBroker;
import nazmplanner.ui.MainMessageBroker;
import nazmplanner.ui.calendars.CalendarsPanel;
import nazmplanner.ui.messages.ViewSwitchMessage;
import nazmplanner.ui.tasks.TasksPanel;

public class ViewPanel extends JPanel
{
    private final MainMessageBroker mainMessageBroker;
    private final TasksMessageBroker tasksMessageBroker;
    private TasksPanel tasksPanel;
    private CalendarsPanel eventsPanel;
    
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
        eventsPanel = new CalendarsPanel();
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
        case ViewType.EVENTS -> showPanel(eventsPanel);
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
