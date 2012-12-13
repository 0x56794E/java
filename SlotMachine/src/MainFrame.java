



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.io.*;
import javax.sound.sampled.*;

/**
 *
 * @author Vy Thuy Nguyen
 */
public class MainFrame extends JFrame implements ActionListener
{
    private WheelPanel panel1;
    private BetAmountPanel panel3;
    private TotalPointPanel panel4;
    private int y;
    private int x;
    private JButton btn;
    private Clip sure;
    private Clip wish;

    public MainFrame ()
    {
        super("Slotting Machine Game - by Vy Thuy Nguyen");


        // create a bet amount panel
        panel3 = new BetAmountPanel();

        // create a total point panel
        panel4 = new TotalPointPanel();

        // create a wheel panel
        panel1 = new WheelPanel(this, panel3, panel4);

        // create a panel to store a button start/stop
        JPanel panel2 = new JPanel();
        panel2.setLayout( new FlowLayout());

        JPanel a = new JPanel();
        JPanel b = new JPanel();

        btn = new JButton("Spin");
        btn.addActionListener(this);

        JButton resetBtn = new JButton ("Reset");
        resetBtn.addActionListener( new ResetListener(this));

        a.add(btn);
        b.add(resetBtn);
        
        panel2.add(a);
        panel2.add(b);

        JPanel southPanel = new JPanel();
        southPanel.setLayout( new GridLayout (2,1));
        southPanel.add(panel2);
        southPanel.add(panel3);


        // set properties for the frame
        x = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 250;
        y = Toolkit.getDefaultToolkit().getScreenSize().height/ 2 - 250;
        this.setLocation(x, y);     
        this.setSize(500,500);
        this.setResizable(false);
        this.setLayout( new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add two panels to the frame
        this.add(panel1, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);
        this.add(panel4, BorderLayout.NORTH);

      
        //========================

         // create clip
           try
        {
            File soundFile1 = new File("C:\\library\\sure.wav");
            File soundFile2 = new File("C:\\library\\wish.wav");

            if (!soundFile1.exists() || !soundFile2.exists() )
            {
                System.err.println("Wave file(s) not found!");
                return;
            }
            AudioInputStream audioIn1 = AudioSystem.getAudioInputStream(soundFile1);
            AudioInputStream audioIn2 = AudioSystem.getAudioInputStream(soundFile2);

            // Get a sound clip resource.
           sure = AudioSystem.getClip();
           wish = AudioSystem.getClip();

            // Open audio clip and load samples from the audio input stream.
            sure.open(audioIn1);
            wish.open(audioIn2);

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

        this.setVisible(false);
    }

    private class ResetListener implements ActionListener
    {
        MainFrame frame;

        public ResetListener ( MainFrame fr)
        {
            frame = fr;
        }
        public void actionPerformed ( ActionEvent e)
        {
            sure.setFramePosition(0);
            sure.start();
            int rst = JOptionPane.showConfirmDialog(frame, "Are you sure?", "Reset?",JOptionPane.YES_NO_OPTION);

            if ( rst == JOptionPane.NO_OPTION)
            {
               
                return;
            }

            wish.setFramePosition(0);
                wish.start();
            panel1.resetTotalBet();
            panel3.setBet("");
            panel4.setPoint(1000);
            panel3.setActive(true);
            btn.setText("Spin");

            frame.repaint();
      

        }
    }
 

        
 
    public void actionPerformed ( ActionEvent e)
    {
       

        if ( !panel3.isLegit())
        {
            JOptionPane.showMessageDialog(this, "Please enter a positive integer for the amount of points you would like to bet", "Error!",JOptionPane.ERROR_MESSAGE);
            return;
        }
        else if ( panel3.getBet() > panel4.getTotal())
        {
            JOptionPane.showMessageDialog(this, "You cannot bet more than you can afford to lose!", "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (btn.getText().equals("Spin"))
        {
            shake();
            panel1.spin();
            btn.setText("Stop");

        }
        else
        {
            panel1.stopSpin();
            btn.setText("Spin");

        }

    }

    public void btnActivated ( boolean active)
    {
        btn.setEnabled(active);
    }

    public void shake()
    {
          try
        {
            this.setLocation(x +5, y + 5);
            Thread.sleep(80);//sleep for 1000 ms

            this.setLocation(x - 5, y - 5);
            Thread.sleep(80);

            this.setLocation(x +5, y + 5);
            Thread.sleep(80);//sleep for 1000 ms

            this.setLocation(x -5,y -5);
            Thread.sleep(80);

             this.setLocation(x +5, y + 5);
            Thread.sleep(80);//sleep for 1000 ms

            this.setLocation(x -5,y -5);
            Thread.sleep(80);

            this.setLocation(x +5, y + 5);
            Thread.sleep(80);//sleep for 1000 ms

            this.setLocation(x -5,y -5);
            Thread.sleep(80);

             this.setLocation(x +5,y + 5);
            Thread.sleep(80);//sleep for 1000 ms

            this.setLocation(x -5, y -5);
            Thread.sleep(80);


            this.setLocation(x, y);

        }
        catch (Exception ie)
        {
            ie.printStackTrace();
        }
    }

}
