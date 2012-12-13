/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bhcc.VyThuyNguyen;

import net.topherc.colorchase.*;
import java.awt.Color;
import java.awt.Point;
import java.util.*;

/**
 *
 * @author Vy Thuy Nguyen
 */
public class RainBowBot extends ColorBot {

    protected static final int HEIGHT = 30;
    protected Point desPoint;
    protected Point iniPosition;
    protected static boolean[][] notAvailable = new boolean[HEIGHT][50];
    protected static int red = 0;
    protected static int orange = 0;
    protected static int yellow = 0;
    protected static int green = 0;
    protected static int blue = 0;
    protected static int cyan = 0;
    protected static int magenta = 0;
    protected Color myColor;
    private boolean notDone = true;
    protected static int numAtDestination = 0;
    protected static int numAtOrigin = 0;
    private boolean notBack = false;

    public RainBowBot(Arena arena, Color myColor, Point iniPosition) {
        super(arena, iniPosition);
        this.iniPosition = iniPosition;
        numAtOrigin++;
        for (int y = 0; y < HEIGHT; y++)
        {
            for (int x = 0; x < 50; x += 7)
                notAvailable[y][x] = true;
        }

        this.myColor = myColor;
        this.setDestination();
        if (desPoint.y != 0)
            notAvailable[48 - desPoint.y][desPoint.x] = true;
    }

    @Override
    public Color getColor() {
        return myColor;
    }

    protected void setColor(Color newColor) {
        myColor = newColor;
    }

    protected void setDestination()
    {
        Random random = new Random();

        int x = 0, y = 0;

        if (myColor == Color.RED)
        {
            red++;
            while (notAvailable[y][x])
            {
                x = random.nextInt(6) + 1;
                y = (red % 6 == 0) ? (red / 6 - 1) : (red / 6);
            }
        }
        else if (myColor == Color.ORANGE)
        {
            orange++;
            while (notAvailable[y][x])
            {
                x = random.nextInt(6) + 8;
                y = (orange % 6 == 0) ? (orange / 6 - 1) : (orange / 6);
            }
        }
        else if (myColor == Color.YELLOW)
        {
            yellow++;
            while (notAvailable[y][x])
            {
                x = random.nextInt(6) + 15;
                y = (yellow % 6 == 0) ? (yellow / 6 - 1) : (yellow / 6);
            }
        }
        else if (myColor == Color.GREEN)
        {
            green++;
            while (notAvailable[y][x])
            {
                x = random.nextInt(6) + 22;
                y = (green % 6 == 0) ? (green / 6 - 1) : (green / 6);
            }
        }
        else if (myColor == Color.BLUE)
        {
            blue++;
            while (notAvailable[y][x])
            {
                x = random.nextInt(6) + 29;
                y = (blue % 6 == 0) ? (blue / 6 - 1) : (blue / 6);
            }
        }
        else if (myColor == Color.CYAN)
        {
            cyan++;
            while (notAvailable[y][x])
            {
                x = random.nextInt(6) + 36;
                y = (cyan % 6 == 0) ? (cyan / 6 - 1) : (cyan / 6);
            }
        }
        else if (myColor == Color.MAGENTA)
        {
            magenta++;
            while (notAvailable[y][x])
            {
                x = random.nextInt(6) + 43;
                y = (magenta % 6 == 0) ? (magenta / 6 - 1) : (magenta / 6);
            }
        }
        else
        {
            this.setColor(Color.WHITE);
            desPoint = new Point(0,0);
            return;
        }
        desPoint = new Point(x, 48 - y);
    }

    protected void scanOneBot(ColorBot bot) {    
        if (notDone)
        {
            if (numAtOrigin == (red + orange + yellow + green + blue + cyan + magenta))
                getForceAccumulator().addForce(location, desPoint);
            if (location.x == desPoint.x && location.y == desPoint.y)
            {
                numAtDestination++;
                notDone = false;
                notBack = true;
            }

            if (numAtDestination == (red + orange + yellow + green + blue + cyan + magenta))
            {
                numAtDestination = 0;
                numAtOrigin = 0;
            }
        }

        else
        {
            if (notBack)
            {
                if (numAtOrigin == 0)
                    getForceAccumulator().addForce(location, iniPosition);

                if (location.x == iniPosition.x && location.y == iniPosition.y)
                {
                    numAtDestination++;
                    notBack = false;
                    notDone = true;
                }
            }

            if (numAtDestination == (red + orange + yellow + green + blue + cyan + magenta))
            {
                numAtOrigin = numAtDestination;
                numAtDestination = 0;
            }
        }
    }
}
