package nazmplanner.ui.components;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import nazmplanner.ui.MainMessageBroker;
import nazmplanner.ui.calendars.CalendarsPanel;
import nazmplanner.ui.messages.ViewSwitchMessage;
import nazmplanner.ui.tasks.TasksPanel;

public class ViewPanel extends JPanel
{
    private final MainMessageBroker mainMediator;    
    private TasksPanel tasksPanel;
    private CalendarsPanel eventsPanel;
    
    public ViewPanel(MainMessageBroker mainMediator)
    {
        this.mainMediator = mainMediator;
        mainMediator.subscribe(ViewSwitchMessage.class, this::onViewSwitch);
        
        initComponents();
        initLayout();
        
        showPanel(tasksPanel);
    }
    
    private void initComponents()
    {
        tasksPanel = new TasksPanel(mainMediator);
        eventsPanel = new CalendarsPanel();
    }
    
    private void initLayout()
    {
        setLayout(new BorderLayout());
    }
    
    public void onViewSwitch(ViewSwitchMessage event)
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
