


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;

/**
 * @author Vy Thuy Nguyen
 */
public class WheelPanel extends JPanel
{
    private Wheel first;
    private Wheel second;
    private Wheel third;

    Long start;
    private final int  DELAY_TIME = 1000;

    private boolean secWheel;

    private Clip won;
    private Clip lost;
    private Clip slowing;
    private Clip playing;
    
    private Timer timer;

    private MainFrame frame;
    private BetAmountPanel betPanel;
    private TotalPointPanel pointPanel;

    private int total;
    private int bet;
    
    public WheelPanel ( MainFrame fr, BetAmountPanel p1, TotalPointPanel p2)
    {
        super();
        frame = fr;
        betPanel = p1;
        pointPanel = p2;
        total = 1000;
        bet = 0;

        // create three wheels
        first = new Wheel(20,0);
        second = new Wheel(21,0);
        third = new Wheel(22,0);

        secWheel = true;
        
        timer = new Timer(DELAY_TIME, new TimeHandler());
        this.setLayout( new GridLayout(1,3));

        this.add(first);
        this.add(second);
        this.add(third);


        try 
        {
            File soundFile1 = new File("C:\\library\\won.wav");
            File soundFile2 = new File("C:\\library\\lost.wav");
            File soundFile3 = new File ("C:\\library\\play.wav");
            File soundFile4 = new File("C:\\library\\wheel.wav");
            if (!soundFile1.exists() || !soundFile2.exists() || !soundFile3.exists() || !soundFile4.exists())
            {
                System.err.println("Wave file(s) not found!");
                return;
            }
            AudioInputStream audioIn1 = AudioSystem.getAudioInputStream(soundFile1);
            AudioInputStream audioIn2 = AudioSystem.getAudioInputStream(soundFile2);
            AudioInputStream audioIn3 = AudioSystem.getAudioInputStream(soundFile3);
            AudioInputStream audioIn4 = AudioSystem.getAudioInputStream(soundFile4);

            // Get a sound clip resource.
           won = AudioSystem.getClip();
           lost = AudioSystem.getClip();
           playing = AudioSystem.getClip();
           slowing = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            won.open(audioIn1);
            lost.open(audioIn2);
            playing.open(audioIn3);
          
            slowing.open(audioIn4);
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

    public void spin ()
    {
        playing.start();
        bet = betPanel.getBet();
       
        playing.loop(Clip.LOOP_CONTINUOUSLY);
        first.startAnimation();
        second.startAnimation();
        third.startAnimation();
        betPanel.setActive(false);
        start = System.currentTimeMillis();
    }

    public void stopSpin()
    {
        playing.stop();
        playing.setFramePosition(0);
        slowing.start();
        first.stopAnimation();
        frame.btnActivated(false);
        timer.start();  
    }

    public void resetTotalBet()
    {
        total = 1000;
        bet = 0;

    

        this.remove(first);
        this.remove(second);
        this.remove(third);

        // create three wheels
        first = new Wheel(20,0);
        second = new Wheel(21,0);
        third = new Wheel(22,0);
        playing.stop();
        playing.setFramePosition(0);

        secWheel = true;
        timer = new Timer(DELAY_TIME, new TimeHandler());
        this.add(first);
        this.add(second);
        this.add(third);
        this.repaint();
    }


    private class TimeHandler implements ActionListener 
    {
        public void actionPerformed ( ActionEvent e)
        {
            if (secWheel)
            {
                second.stopAnimation();
                secWheel = false;
            }
            else
            {
                third.stopAnimation();

                first.setIsStopped(true);
                second.setIsStopped(true);
                third.setIsStopped(true);

                secWheel = true;
                timer.stop();

                slowing.stop();
                slowing.setFramePosition(0);
                if (first.getCurrent() == second.getCurrent() && first.getCurrent() == third.getCurrent())
                {
                    won.start();
                    won.setFramePosition(0);
                    JOptionPane.showMessageDialog(frame, "Congratulations!!! You won! d(^^)b");
                    total += bet;
                }
                else
                {
                    lost.start();
                    lost.setFramePosition(0);
                    JOptionPane.showMessageDialog(frame, "Sorry, you did not win! x_X");
                    total -= bet;
                }

                pointPanel.setPoint(total);
                frame.btnActivated(true);
                betPanel.setActive(true);
           
                                   
            }    
        }
    }

}
