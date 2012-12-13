

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.*;
import javax.swing.BorderFactory;

/**
 *
 * @author Vy Thuy Nguyen
 */
public class BetAmountPanel extends JPanel
{
    private JTextField text;

    public BetAmountPanel ()
    {
        super();

        Border compound = BorderFactory.createCompoundBorder(
			  BorderFactory.createEtchedBorder(EtchedBorder.RAISED),BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        this.setBorder(compound);

        // create a text field
        text = new JTextField(10);
        text.setBorder(compound);


        //
        this.add( new JLabel ("How much do you want to bet? "));
        this.add(text);


    }

    public void setActive ( boolean active)
    {
        text.setEnabled(active);
    }

    public int getBet()
    {
        return Integer.parseInt(text.getText());

    }

    public void setBet ( String bet)
    {
        text.setText(bet);
    }

    public boolean isLegit()
    {
        if (text.getText().isEmpty()) return false;

        String t = text.getText();
        for ( int n = 0; n < text.getText().length(); ++n)
            if (Character.toUpperCase(t.charAt(n)) > '9' || Character.toUpperCase(t.charAt(n)) < '0')
                return false;

        return Integer.parseInt(t) > 0;
    }
}
