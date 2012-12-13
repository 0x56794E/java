package bhcc.VyThuyNguyen;
import net.topherc.colorchase.*;
import java.awt.Point;

/**
 * Test 1 - Test for RedBotToOrange, OrangeBotToGreen, GreenBotToBlue and BlueBotToRed classes
 * @author Vy Thuy Nguyen
 */
public class Test1
{

    /**
     * main method
     * This method puts a RedBotToOrange bot in the upper left corner of the arena,
     * an OrangeBotToGreen bot in the upper right corner of th arena,
     * a GreenBotToBlue bot in the lower right corner, and a BlueBotToRed bot in the lower left corner.
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Arena arena = new Arena();

        new RedBotToOrange(arena, new Point(0,0));
        new OrangeBotToGreen(arena, new Point(49,0));
        new GreenBotToBlue(arena, new Point(49,49));
        new BlueBotToRed(arena, new Point(0,49));

        arena.start();
    }


}