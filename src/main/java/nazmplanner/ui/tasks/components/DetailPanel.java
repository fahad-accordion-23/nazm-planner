package nazmplanner.ui.tasks.components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import nazmplanner.domain.tasks.Task;
import nazmplanner.domain.tasks.TaskStatus;
import nazmplanner.ui.tasks.TasksMediator;
import nazmplanner.ui.tasks.message.TaskDeletedMessage;
import nazmplanner.ui.tasks.message.TaskDisplayedMessage;
import nazmplanner.ui.tasks.message.TaskEditedMessage;
import nazmplanner.ui.tasks.message.TaskMarkedMessage;
import nazmplanner.ui.tasks.message.TaskUpdatedMessage;
import nazmplanner.ui.util.GBC;

/**
 * <h2>TaskDetailPanel</h2>
 * 
 * <p>Displays detailed information for a selected task and allows editing/deletion.</p>
 * 
 * @author Fahad Hassan
 * @version 22/11/2025
 */
public class DetailPanel extends JPanel
{
    
    private final TasksMediator tasksMediator;
    private Task currentTask;
    
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JLabel creationDateLabel;
    private JLabel dueDateLabel;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton completeButton;
    private JButton closeButton;
    
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

    public DetailPanel(TasksMediator tasksMediator)
    {
        this.tasksMediator = tasksMediator;
        tasksMediator.subscribe(TaskDisplayedMessage.class, this::onTaskDisplayed);
        tasksMediator.subscribe(TaskUpdatedMessage.class, this::onTaskUpdated);
        
        initComponents();
        initStyling();
        initLayout();
        initEvents();
        
        setVisible(false);
        updateView(null);
    }

    private void initComponents()
    {
        titleField = new JTextField();
        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        
        creationDateLabel = new JLabel();
        dueDateLabel = new JLabel();
        saveButton = new JButton("Save Changes");
        deleteButton = new JButton("Delete Task");
        completeButton = new JButton("Mark Complete/Todo");
        closeButton = new JButton("X");
    }
    
    private void initStyling()
    {
        setBackground(Color.WHITE);
    }

    private void initLayout()
    {
        super.setLayout(new GridBagLayout());
                
        super.add(closeButton, 
            new GBC(2, 0)
            .setInsets(5)
            .setAnchor(GridBagConstraints.NORTHEAST));
        
        super.add(new JLabel("Title:"), 
            new GBC(0, 0)
            .setInsets(5, 5, 5, 5)
            .setAnchor(GridBagConstraints.WEST));
        
        super.add(titleField,
            new GBC(1, 0)
            .setInsets(5, 5, 5, 5)
            .setFill(GridBagConstraints.HORIZONTAL)
            .setWeight(1.0, 0.0));

        super.add(new JLabel("Description:"), 
            new GBC(0, 1)
            .setInsets(5, 5, 5, 5)
            .setAnchor(GridBagConstraints.NORTHWEST));
        
        super.add(new JScrollPane(descriptionArea), 
            new GBC(0, 2, 3, 1)
            .setInsets(5, 5, 5, 5)
            .setFill(GridBagConstraints.BOTH)
            .setWeight(1.0, 1.0));

        super.add(new JLabel("Created:"), 
            new GBC(0, 3)
            .setInsets(5)
            .setAnchor(GridBagConstraints.WEST));
        
        super.add(creationDateLabel,
            new GBC(1, 3, 2, 1)
            .setInsets(5)
            .setAnchor(GridBagConstraints.WEST));
        
        super.add(new JLabel("Due Date:"), 
            new GBC(0, 4)
            .setInsets(5)
            .setAnchor(GridBagConstraints.WEST));
        
        super.add(dueDateLabel, 
            new GBC(1, 4, 2, 1)
            .setInsets(5)
            .setAnchor(GridBagConstraints.WEST));

        super.add(completeButton, 
            new GBC(0, 5, 3, 1)
            .setInsets(10, 5, 5, 5)
            .setFill(GridBagConstraints.HORIZONTAL));
        
        super.add(saveButton, 
            new GBC(0, 6, 3, 1)
            .setInsets(5, 5, 5, 5)
            .setFill(GridBagConstraints.HORIZONTAL));
        
        super.add(deleteButton, 
            new GBC(0, 7, 3, 1)
            .setInsets(5, 5, 10, 5)
            .setFill(GridBagConstraints.HORIZONTAL));
        
        super.add(new JPanel(), 
            new GBC(0, 8)
            .setWeight(0.0, 10.0));
    }
    
    private void initEvents()
    {
        deleteButton.addActionListener(e -> 
        {
            if (currentTask != null) 
            {
                tasksMediator.publish(new TaskDeletedMessage(currentTask.getID()));
                // We don't set null here manually, the controller will fire TaskDisplayedEvent(null)
            }
        });
        
        completeButton.addActionListener(e -> 
        {
            if (currentTask != null)
            {
                TaskStatus newStatus = (currentTask.getStatus() == TaskStatus.TODO) 
                                       ? TaskStatus.COMPLETED 
                                       : TaskStatus.TODO;
                                       
                tasksMediator.publish(new TaskMarkedMessage(currentTask.getID(), newStatus));
            }
        });
        
        closeButton.addActionListener(e -> 
        {
            closePanel(); 
        });
        
        saveButton.addActionListener(e -> 
        {
            if (currentTask != null) 
            {
                String newTitle = titleField.getText();
                String newDescription = descriptionArea.getText();
                
                tasksMediator.publish(new TaskEditedMessage(currentTask.getID(), newTitle, newDescription));
            }
        });
    }

    private void onTaskDisplayed(TaskDisplayedMessage event)
    {
        updateView(event.task());
    }
    
    private void onTaskUpdated(TaskUpdatedMessage event)
    {
        if (currentTask != null && event.task().getID().equals(currentTask.getID()))
        {
            updateView(event.task());
        }
    }
    
    private void updateView(Task task)
    {
        this.currentTask = task;
        
        if (currentTask == null)
        {
            titleField.setText("No task selected");
            descriptionArea.setText("");
            creationDateLabel.setText("");
            dueDateLabel.setText("");
            titleField.setEnabled(false);
            descriptionArea.setEnabled(false);
            saveButton.setEnabled(false);
            deleteButton.setEnabled(false);
            completeButton.setEnabled(false);
            
            setVisible(false);
        }
        else
        {
            titleField.setText(currentTask.getTitle());
            descriptionArea.setText(currentTask.getDescription());
            
            creationDateLabel.setText(currentTask.getCreationDate().format(DATE_FORMAT));
            dueDateLabel.setText(currentTask.getDueDate().format(DATE_FORMAT));
            
            titleField.setEnabled(true);
            descriptionArea.setEnabled(true);
            saveButton.setEnabled(true);
            deleteButton.setEnabled(true);
            completeButton.setEnabled(true);
                        
            if (currentTask.getStatus() == TaskStatus.TODO) 
            {
                completeButton.setText("Mark Task Complete");
                completeButton.setBackground(Color.GREEN);
            } 
            else 
            {
                completeButton.setText("Mark Task To Do");
                completeButton.setBackground(Color.YELLOW);
            }
            
            setVisible(true);
        }
        
        revalidate();
        repaint();
    }
    
    public void closePanel()
    {
        updateView(null);
    }
    
}