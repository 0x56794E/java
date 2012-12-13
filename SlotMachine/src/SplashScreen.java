


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
/**
 *
 * @author Vy Thuy Nguyen
 */



class SplashScreen extends JWindow
{
    private MainFrame frame;
    private Clip wel;
    public SplashScreen(MainFrame frame)
    {
        this.frame = frame;


        JLabel l = new JLabel(new ImageIcon("C:\\library\\bg.jpg"));
       // JLabel n = new JLabel( new ImageIcon("C:\\welc.gif"));



        getContentPane().add(l);
        //getContentPane().add(n);

        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension labelSize = l.getPreferredSize();
        setLocation(screenSize.width/2 - (labelSize.width/2),
                    screenSize.height/2 - (labelSize.height/2));
        this.addMouseListener( new MouseAdapt());
        setVisible(true);

        // create clip
           try
        {
            File soundFile1 = new File("C:\\library\\welcome.wav");

            if (!soundFile1.exists() )
            {
                System.err.println("Wave file not found!");
                return;
            }
            AudioInputStream audioIn1 = AudioSystem.getAudioInputStream(soundFile1);


            // Get a sound clip resource.
           wel = AudioSystem.getClip();

            // Open audio clip and load samples from the audio input stream.
            wel.open(audioIn1);

        }
        catch (javax.sound.sampled.LineUnavailableException e)
        {
            e.printStackTrace();
            return;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return;
        }

    }

    private class MouseAdapt extends MouseAdapter
    {
         public void mousePressed(MouseEvent e)
                {
                    setVisible(false);
                    dispose();
                    wel.start();
                    frame.setVisible(true);
                    frame.shake();
                }
    }
}