package bhcc.VyThuyNguyen;
import java.util.Random;
import net.topherc.colorchase.*;
import java.awt.Color;
import java.awt.Point;

/**
 * Test 3 - Test for AttractBot Class
 * @author Vy Thuy Nguyen
 */
public class Test3 {

    /**
     * This method creates five of each kind of AttratBot at a random position in the arena.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Arena arena = new Arena();
        Random random = new Random();

        //***********************ORANGE TO GREEN*********************************
        for (int n = 0; n < 5; n++)
            new AttractBot(arena, Color.ORANGE, Color.GREEN, new Point(random.nextInt(50),random.nextInt(50)));

        //***********************GREEN TO BLUE********************************
        for (int n = 0; n < 5; n++)
            new AttractBot(arena, Color.GREEN, Color.BLUE, new Point(random.nextInt(50),random.nextInt(50)));

        //***********************BLUE TO RED*********************************
        for (int n = 0; n < 5; n++)
            new AttractBot(arena, Color.BLUE, Color.RED, new Point(random.nextInt(50),random.nextInt(50)));

        //***********************RED TO ORANGE*********************************
        for (int n = 0; n < 5; n++)
           new AttractBot(arena, Color.RED, Color.ORANGE, new Point(random.nextInt(50),random.nextInt(50)));

        arena.start();
    }
}


