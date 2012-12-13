/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;
import javax.swing.*;
import java.awt.*;

/**
 * QWallet Project - Glasgow Caledonian University  Summer Internship
 * Help Frame
 * @Date: August 10th, 2011
 * @author Vy Thuy Nguyen
 */
public class HelpFrame extends JFrame
{
    public HelpFrame()
    {       
        setSize(400,270);
        setTitle("How to use My QWallet?");
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(new HelpPanel());
        repaint();
        validate();        
    }
    
    protected class HelpPanel extends JPanel
    {
        protected void paintComponent(Graphics g)
        {
            g.drawString("When a user first uses My QWallet, they will need to", 50, 50);
            g.drawString("create a QWallet account. When they need to generate", 50, 70);
            g.drawString("security questions, they first have to log in to their", 50, 90);
            g.drawString("QWallet account using the user and password they have", 50, 110);
            g.drawString("chosen. The user will then choose one of the methods", 50, 130);
            g.drawString("provided to either create their own questions or select", 50, 150);
            g.drawString("from a wide range of questions.", 50, 170);            
        }
    }
}
