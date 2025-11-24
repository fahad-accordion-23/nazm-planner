package nazmplanner.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import nazmplanner.ui.contracts.ViewSwitchEvent;
import nazmplanner.ui.events.EventsPanel;
import nazmplanner.ui.tasks.TasksPanel;

public class ViewPanel extends JPanel
{
    private final MainMediator mainMediator;    
    private TasksPanel tasksPanel;
    private EventsPanel eventsPanel;
    
    public ViewPanel(MainMediator mainMediator)
    {
        this.mainMediator = mainMediator;
        mainMediator.subscribe(ViewSwitchEvent.class, this::onViewSwitch);
        
        initComponents();
        initLayout();
        
        showPanel(tasksPanel);
    }
    
    private void initComponents()
    {
        tasksPanel = new TasksPanel(mainMediator);
        eventsPanel = new EventsPanel();
    }
    
    private void initLayout()
    {
        setLayout(new BorderLayout());
    }
    
    public void onViewSwitch(ViewSwitchEvent event)
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
