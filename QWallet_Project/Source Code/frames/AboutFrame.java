/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;
import javax.swing.*;
import java.awt.*;

/**
 * QWallet Project - Glasgow Caledonian University  Summer Internship
 * About Frame
 * @Date: August 10th, 2011
 * @author Vy Thuy Nguyen
 */
public class AboutFrame extends JFrame
{
    public AboutFrame()
    {
        setSize(400,400);
        setTitle("About My QWallet");
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(new AboutPanel());
        repaint();
        validate();
    }
    
     protected class AboutPanel extends JPanel
    {
        protected void paintComponent(Graphics g)
        {           
            g.drawString("My QWallet is an application that acts as an electronic", 50 , 50);
            g.drawString("wallet for generating and storing security questions and", 50 , 70);
            g.drawString("answers.", 50 , 90);
            
            g.drawString("My QWallet helps a user to generate a wide variety of", 50, 120);
            g.drawString("questions, and stores the questions and asnwers for the", 50 , 140);
            g.drawString("user so that they can be used later to authenticate at a", 50 , 160); 
            g.drawString("web site.", 50 , 180);
            
            g.drawString("My QWallet helps users in their security question selection", 50 , 210);
            g.drawString("by offering them a set of tools for building a wide variety", 50 , 230);
            g.drawString("of questions. For example, the tool might allow users to ", 50 , 250);
            g.drawString("choose parts of the questions from a dropw-down list", 50 , 270);
   
   
            g.draw3DRect(40, 300, 320, 1, true);
            g.drawString("My QWallet is developed by Vy Thuy Nguyen", 80, 330);
            g.drawString("Copyright © 2011 Glasgow Caledonian University.", 60, 350 );
   
        }
        
    }
    
}
