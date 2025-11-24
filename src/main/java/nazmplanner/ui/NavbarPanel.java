package nazmplanner.ui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import nazmplanner.ui.contracts.ViewSwitchEvent;

public class NavbarPanel extends JPanel
{
    private final MainMediator mainMediator;
    private JButton tasksButton;
    private JButton eventsButton;
    
    public NavbarPanel(MainMediator mainMediator)
    {
        this.mainMediator = mainMediator;
        
        initComponents();
        initLayout();
        initEvents();
    }
    
    private void initComponents()
    {
        tasksButton = new JButton("Tasks");
        eventsButton = new JButton("Events");                
    }
    
    private void initLayout()
    {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        
        add(tasksButton);
        add(eventsButton);
    }
    
    private void initEvents()
    {
        tasksButton.addActionListener(e -> mainMediator.publish(new ViewSwitchEvent(ViewType.TASKS)));
        eventsButton.addActionListener(e -> mainMediator.publish(new ViewSwitchEvent(ViewType.EVENTS)));
    }
}
