package nazmplanner.ui.components;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <h2>HeaderPanel</h2>
 * 
 * <p>Generic header component</p>
 * 
 * @author Fahad Hassan
 * @version 21/11/2025
 */
public class HeaderPanel extends JPanel
{
    private JLabel titleLabel;
    
    public HeaderPanel(String title)
    {
        initStyling();
        initLayout();
        
        titleLabel = new JLabel(title);
        titleLabel.setFont(titleLabel.getFont().deriveFont(java.awt.Font.BOLD, 18f));
        super.add(titleLabel);        
    }
    
    public void setText(String title)
    {
        titleLabel.setText(title);
    }
    
    private void initStyling()
    {
        var paddingBorder = BorderFactory.createEmptyBorder(10, 15, 10, 15);
        var separationBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.Color.LIGHT_GRAY);
        
        setBorder(BorderFactory.createCompoundBorder(separationBorder, paddingBorder));
        setBackground(Color.LIGHT_GRAY);
    }
    
    private void initLayout()
    {
        super.setLayout(new FlowLayout(FlowLayout.LEFT));
    }
    
}
