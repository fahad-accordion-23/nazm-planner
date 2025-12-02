package nazmplanner.ui.events.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import nazmplanner.ui.util.GBC;

/**
 * <h2>CalendarGridPanel</h2>
 * * <p>Dumb UI component displaying a 7x6 calendar grid for a month view.</p>
 */
public class CalendarGridPanel extends JPanel
{
    private static final String[] DAY_NAMES = {
        DayOfWeek.SUNDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        DayOfWeek.MONDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        DayOfWeek.TUESDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        DayOfWeek.WEDNESDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        DayOfWeek.THURSDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        DayOfWeek.FRIDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        DayOfWeek.SATURDAY.getDisplayName(TextStyle.SHORT, Locale.getDefault())
    };

    public CalendarGridPanel()
    {
        initStyling();
        initLayout();
    }
    
    private void initStyling()
    {
        // Set background to contrast the Header/Sidebar color
        setBackground(Color.WHITE); 
    }
    
    private void initLayout()
    {
        setLayout(new GridBagLayout());
        
        // 1. Day of the week headers (Row 0)
        for (int i = 0; i < 7; i++)
        {
            JLabel header = new JLabel(DAY_NAMES[i], SwingConstants.CENTER);
            header.setFont(header.getFont().deriveFont(Font.BOLD));
            header.setBackground(Color.LIGHT_GRAY.brighter());
            header.setOpaque(true);
            header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.GRAY));
            
            // Add to Grid
            super.add(header, new GBC(i, 0)
                    .setWeight(1.0, 0.0) // Equal horizontal weight, no vertical growth
                    .setFill(GridBagConstraints.BOTH));
        }
        
        // 2. Calendar Days (Rows 1-6) - Dumb display for a typical month (31 days)
        int dayCounter = 1;
        for (int row = 1; row <= 6; row++)
        {
            for (int col = 0; col < 7; col++)
            {
                JPanel dayCell = createDayCell(dayCounter);
                
                // Add cell to Grid
                super.add(dayCell, new GBC(col, row)
                        .setWeight(1.0, 1.0) // Equal horizontal and vertical weight
                        .setFill(GridBagConstraints.BOTH));
                
                dayCounter++;
                if (dayCounter > 31) // Stop filling after a full month
                    break;
            }
            if (dayCounter > 31)
                break;
        }
    }
    
    /** Helper to create a visual cell for a day */
    private JPanel createDayCell(int dayNumber)
    {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(String.valueOf(dayNumber));
        
        // Apply simple styling for the cell
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panel.setBackground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));
        label.setFont(label.getFont().deriveFont(Font.PLAIN));

        // Highlight a sample day for visual interest
        if (dayNumber == 25)
        {
            panel.setBackground(new Color(230, 240, 255)); 
            label.setFont(label.getFont().deriveFont(Font.BOLD));
        }
        
        panel.add(label, BorderLayout.NORTH);
        return panel;
    }
}