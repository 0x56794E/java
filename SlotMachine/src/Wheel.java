


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import javax.swing.*;
import java.util.Random;

/**
 *
 * @author Vy Thuy Nguyen
 */
public class Wheel extends JPanel {

    protected final int NUMBER_OF_PICS = 10; // Increase to reduce the chance of wining
    protected ImageIcon images[];
    private int currentImage = 0;
    private int animationDelay;
    private int width = 100;
    private int height = 100;
    private Timer animationTimer;
    private int x0;
    private int y0;
    private int pre;
    private int post;
    private boolean isStopped;
    private Random randomGenerator;

    public Wheel(int x, int y) {
        try {
            images = new ImageIcon[NUMBER_OF_PICS];
            String url = new String("C:\\library\\pic\\");


            for (int i = 0; i < NUMBER_OF_PICS; ++i) {
                images[i] = new ImageIcon(url + (i + 1) + ".gif");
            }

            // set the position of the upper left most corner
            x0 = x;
            y0 = y;

            randomGenerator = new Random();
            currentImage = 0;
            pre = images.length - 1;
            post = 1;

            isStopped = false;
            setRandomSpeed();

            animationTimer = new Timer(animationDelay, new TimeHandler());
            //  animationTimer.start();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private void setImages() {
        pre = currentImage;
        currentImage = post;
        post = randomGenerator.nextInt(images.length);

        while (post == currentImage || post == pre) {
            post = randomGenerator.nextInt(images.length);
        }

    }

    public void setRandomSpeed() {
        animationDelay = randomGenerator.nextInt(100) + 90;

    }

    public void slowDown() {
        animationDelay = 6000;
        animationTimer = new Timer(animationDelay, new TimeHandler());
        animationTimer.start();
        startAnimation();

    }

    public int getCurrent()
    {
        return pre;
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!isStopped) {
            images[pre].paintIcon(this, g, x0 + (100 - width) / 2, y0 + (100 - height) / 2);
            images[currentImage].paintIcon(this, g, x0 + (100 - width) / 2, y0 + (100 - height) / 2 + 100);
            images[post].paintIcon(this, g, x0 + (100 - width) / 2, y0 + (100 - height) / 2 + 200);

            // top
            g.drawLine(x0, y0, x0 + 101, y0);
            g.drawLine(x0, y0 + 1, x0 + 101, y0 + 1);
            g.drawLine(x0, y0 + 2, x0 + 101, y0 + 2);

            // left
            g.drawLine(x0, y0, x0 + 0, y0 + 300);
            g.drawLine(x0 + 1, y0, x0 + 1, y0 + 300);
            g.drawLine(x0 + 2, y0, x0 + 2, y0 + 300);

            //bottom            
            g.drawLine(x0, y0 + 298, x0 + 101, y0 + 298);
            g.drawLine(x0, y0 + 299, x0 + 101, y0 + 299);
            g.drawLine(x0, y0 + 300, x0 + 101, y0 + 300);


            // right
            g.drawLine(x0 + 99, y0, x0 + 99, y0 + 300);
            g.drawLine(x0 + 100, y0, x0 + 100, y0 + 300);
            g.drawLine(x0 + 101, y0, x0 + 101, y0 + 300);
             setImages();


        } else
        {
            ImageIcon bg = new ImageIcon("C:\\library\\pic\\no.gif");

            // Repaint the status of the wheel in the last run including the dividers
            bg.paintIcon(this, g, x0 + (100 - width) / 2, y0 + (100 - height) / 2);
            images[pre].paintIcon(this, g, x0 + (100 - width) / 2, y0 + (100 - height) / 2 + 100);
            bg.paintIcon(this, g, x0 + (100 - width) / 2, y0 + (100 - height) / 2 + 200);

            // top
            g.drawLine(x0, y0, x0 + 101, y0);
            g.drawLine(x0, y0 + 1, x0 + 101, y0 + 1);
            g.drawLine(x0, y0 + 2, x0 + 101, y0 + 2);

            // left
            g.drawLine(x0, y0, x0 + 0, y0 + 300);
            g.drawLine(x0 + 1, y0, x0 + 1, y0 + 300);
            g.drawLine(x0 + 2, y0, x0 + 2, y0 + 300);

            //bottom
            g.drawLine(x0, y0 + 298, x0 + 101, y0 + 298);
            g.drawLine(x0, y0 + 299, x0 + 101, y0 + 299);
            g.drawLine(x0, y0 + 300, x0 + 101, y0 + 300);

            // right
            g.drawLine(x0 + 99, y0, x0 + 99, y0 + 300);
            g.drawLine(x0 + 100, y0, x0 + 100, y0 + 300);
            g.drawLine(x0 + 101, y0, x0 + 101, y0 + 300);

            //=================

          
            ImageIcon div1 = new ImageIcon("C:\\library\\pic\\div.gif");
            ImageIcon div2 = new ImageIcon("C:\\library\\pic\\div.gif");

          //  div.paintIcon(this, g, 0, -10);
          //  div.paintIcon(this, g, 0, 210);
            div1.paintIcon(this, g, 0, 70);
            div2.paintIcon(this, g, 0, 180);
            animationTimer.stop();
        }

     
    }

    public void startAnimation() {

        isStopped = false;
        if (animationTimer == null) {
            setRandomSpeed();
            animationTimer = new Timer(animationDelay, new TimeHandler());
            animationTimer.start();

        } else {
            if (!animationTimer.isRunning()) {
                setRandomSpeed();
                animationTimer.restart();
            }
        }
    }

    public void stopAnimation() {
        animationTimer.stop();


    }

    public void setIsStopped(boolean vl) {
        isStopped = vl;

        if (isStopped == true) {
            animationTimer.start();
        }
    }

    private class TimeHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    }
}
