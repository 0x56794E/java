/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bhcc.VyThuyNguyen;

import net.topherc.colorchase.*;
import java.awt.Color;
import java.awt.Point;

/**
 * Test 6 - Test for RepelBot Class
 * @author Vy Thuy Nguyen
 */
public class Test6 {

    /**
     * This method creates four bots in the four corners:
     * Red bot in the upper left corner, Orange bot in the upper right corner
     * Green bot in the lower right corner, Blue bot in the lower left corner.
     * Each bot is attracted to the next bot in clockwise order and
     * is repelled by the bot in the opposite corner from it.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Arena arena = new Arena();

        new RepelBot(arena, Color.RED, Color.ORANGE, Color.GREEN, new Point(0, 0));
        new RepelBot(arena, Color.ORANGE, Color.GREEN, Color.BLUE, new Point(0, 49));
        new RepelBot(arena, Color.GREEN, Color.BLUE, Color.RED, new Point(49, 49));
        new RepelBot(arena, Color.BLUE, Color.RED, Color.ORANGE, new Point(49, 0));

        arena.start();
    }
}
