
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.*;
import javax.swing.BorderFactory;

/**
 * @author Vy Thuy Nguyen
 */
public class TotalPointPanel extends JPanel
{
    private int pt;
    private JLabel tx;

    public TotalPointPanel()
    {
        super();
        pt = 1000;
        this.setSize(400,100);

 

        Border green = BorderFactory.createLineBorder(Color.YELLOW, 6);
        this.setBorder(green);
        tx = new JLabel("Remaining $" + pt);
        tx.setFont( new Font("TimesRoman", Font.BOLD, 25));
        tx.setForeground( Color.GREEN);
        this.add(tx);
    }

    public void setPoint ( int pt )
    {
        this.pt = pt;
        tx.setText("Remaining $" + pt);
        this.repaint();
    }

    public int getTotal ()
    {
        return pt;
    }

}
