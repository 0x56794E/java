package bhcc.VyThuyNguyen;
import net.topherc.colorchase.*;
import java.awt.Color;
import java.awt.Point;

/**
 * Test 4 - Test for RepelBot Class
 * @author Vy Thuy Nguyen
 */
public class Test4 {
     /**
      * This method creates four bots in the four corners:
      * Red bot in the upper left corner, Orange bot in the upper right corner
      * Green bot in the lower right corner, Blue bot in the lower left corner
      * Each bot is attracted to the next bot and repelled by the previous bot in clockwise order.
      * @param args the command line arguments
      */
    public static void main(String[] args)
    {
        Arena arena = new Arena();

        new RepelBot(arena, Color.RED, Color.ORANGE, Color.BLUE,new Point(0,0));
        new RepelBot(arena, Color.ORANGE, Color.GREEN, Color.RED,new Point(0,49));
        new RepelBot(arena, Color.GREEN, Color.BLUE, Color.ORANGE,new Point(49,49));
        new RepelBot(arena, Color.BLUE, Color.RED, Color.GREEN,new Point(49,0));

        arena.start();
    }

}
