package nazmplanner.ui.tasks.components;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import nazmplanner.application.tasks.TaskDTO;
import nazmplanner.application.tasks.TasksMessageBroker;
import nazmplanner.application.tasks.messages.TaskMarkedMessage;
import nazmplanner.application.tasks.messages.TaskSelectedMessage;
import nazmplanner.domain.tasks.TaskStatus;
import nazmplanner.ui.core.CardPanel;

/**
 * <h2>TaskCardPanel</h2>
 * 
 * <p>UI panel class representing a single task in card form.</p>
 * 
 * @author Fahad Hassan
 * @version 21/11/2025
 */
public class TaskCardPanel extends CardPanel
{
    
    private final TasksMessageBroker tasksMessageBroker;
    private final TaskDTO task;
    
    private JCheckBox statusBox;
    private JLabel titleLabel;
    private JLabel dueDateLabel;
    
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
    
    public TaskCardPanel(TaskDTO task, TasksMessageBroker tasksMessageBroker)
    {
        this.tasksMessageBroker = tasksMessageBroker;
        this.task = task;

        initComponents();
        initLayout();
        initStyling();
        initEvents();
        
        refresh();
    }
    
    private void initComponents()
    {
        statusBox = new JCheckBox();
        titleLabel = new JLabel();
        dueDateLabel = new JLabel();
    }
    
    private void initLayout()
    {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        add(statusBox);
        add(Box.createHorizontalStrut(10)).setMaximumSize(getPreferredSize());
        add(titleLabel);
        add(Box.createHorizontalGlue());
        add(dueDateLabel);
    }
    
    private void initStyling()
    {
        dueDateLabel.setForeground(new Color(120, 120, 120, 120));
        statusBox.setOpaque(false);
    }
    
    private void initEvents()
    {
        statusBox.addActionListener(e ->
        {
            TaskStatus newStatus = TaskStatus.COMPLETED;
            
            if (statusBox.isSelected())
            {
                newStatus = TaskStatus.COMPLETED;
            }
            else
            {
                newStatus = TaskStatus.TODO;
            }
            
            tasksMessageBroker.publish(new TaskMarkedMessage(task.id(), newStatus));
        });
        
        super.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                tasksMessageBroker.publish(new TaskSelectedMessage(task.id()));
            }
        });
    }
    
    private void refresh()
    {
        statusBox.setSelected(task.status() == TaskStatus.COMPLETED);
        titleLabel.setText(task.title());
        
        String date = task.dueDate().format(DATE_FORMAT);
        dueDateLabel.setText(date);
        
        repaint();
        revalidate();
    }
    
}
