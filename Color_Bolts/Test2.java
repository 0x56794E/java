package bhcc.VyThuyNguyen;
import net.topherc.colorchase.*;
import java.awt.Color;
import java.awt.Point;
/**
 * Test 2 - Test for AttractBot Class
 * @author Vy Thuy Nguyen
 */
public class Test2 {

    /**
     * This method puts a RedBotToOrange bot in the upper left corner of the arena,
     * an OrangeBotToGreen bot in the upper right corner of th arena,
     * a GreenBotToBlue bot in the lower right corner, and a BlueBotToRed bot in the lower left corner.
     * (Using AttractBot class
     * instead of four classes: RedBotToOrange, OrangeBotToGreen, GreenBotToBlue, BlueBotToRed.)
     * @param args the command line arguments
     */

    public static void main(String[] args)
    {
        Arena arena = new Arena();

       new AttractBot(arena, Color.RED, Color.ORANGE, new Point(0,0));
       new AttractBot(arena, Color.ORANGE, Color.GREEN, new Point(49,0));
       new AttractBot(arena, Color.GREEN, Color.BLUE, new Point(49,49));
       new AttractBot(arena, Color.BLUE, Color.RED, new Point(0,49));

        arena.start();
    }


}
