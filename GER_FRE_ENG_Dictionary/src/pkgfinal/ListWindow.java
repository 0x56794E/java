/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Vy Thuy Nguyen 
 */
public class ListWindow extends JFrame
{
    String title; 
    String part;
    
    public ListWindow (String tit, String pt)
    {
        title = tit;
        part = pt;
        setSize(400,400);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(new ListPanel());
    }
    public class ListPanel extends JPanel
    {
        public void paintComponent(Graphics g)
        {
            g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            g.drawString("ENGLISH - " + title + " " + part, 50, 50);
            
        }
    }
    
}
