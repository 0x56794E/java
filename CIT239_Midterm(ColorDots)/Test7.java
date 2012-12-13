/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bhcc.VyThuyNguyen;

import net.topherc.colorchase.*;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Point;
import java.util.Random;

/**
 * Test 7 - Test RainbowBot Class
 * @author Vy Thuy Nguyen
 */
public class Test7 {

    /**
     * This method asks user for number of bots of different colors
     * then creates dots of different colors at random position
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Arena arena = new Arena();
        Random ran = new Random();
        int num;

        num = getNum("red");
        for (int n = 0; n < num; n++)
            new RainBowBot(arena, Color.RED, new Point(ran.nextInt(50),ran.nextInt(25)));

        num = getNum("orange");
        for (int n = 0; n < num; n++)
            new RainBowBot(arena, Color.ORANGE, new Point(ran.nextInt(50),ran.nextInt(25)));

        num = getNum("yellow");
        for (int n = 0; n < num; n++)
                new RainBowBot(arena, Color.YELLOW, new Point(ran.nextInt(50),ran.nextInt(25)));

        num = getNum("green");
        for (int n = 0; n < num; n++)
            new RainBowBot(arena, Color.GREEN, new Point(ran.nextInt(50),ran.nextInt(25)));

        num = getNum("blue");
        for (int n = 0; n < num; n++)
            new RainBowBot(arena, Color.BLUE, new Point(ran.nextInt(50),ran.nextInt(25)));

        num = getNum("cyan");
        for (int n = 0; n < num; n++)
            new RainBowBot(arena, Color.CYAN, new Point(ran.nextInt(50),ran.nextInt(25)));

        num = getNum("magenta");
        for (int n = 0; n < num; n++)
            new RainBowBot(arena, Color.MAGENTA, new Point(ran.nextInt(50),ran.nextInt(20)));
       
        arena.start();

    }

    //return a number
    private static int getNum(String color)
    {
        String preNum;
        char[] numArr;
        int n;

        do
        {
            preNum = JOptionPane.showInputDialog("How many " + color + " dots do you want?");
            numArr = preNum.toCharArray();
            for (n = 0; n < preNum.length(); n++)
                if (numArr[n] < '0' ||  numArr[n] > '9' || preNum.length() < 1)
                {
                    JOptionPane.showMessageDialog(null,"Enter a POSITVE INTEGER!", "INVALID INPUT", JOptionPane.ERROR_MESSAGE);
                    break;
                }
        }
        while (n < preNum.length() || preNum.length() < 1);

        return Integer.parseInt(preNum);
    }
}
