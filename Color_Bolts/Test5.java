package bhcc.VyThuyNguyen;
import java.util.Random;
import net.topherc.colorchase.*;
import java.awt.Color;
import java.awt.Point;

/**
 * Test 5 - Test for RepelBot Class
 * @author Vy Thuy Nguyen
 */
public class Test5 {

    /**
     * This method creates five of each kind of RepelBot at a random position in the arena.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Arena arena = new Arena();
        Random random = new Random();

        //***********************RED TO ORANGE*********************************
        for (int n = 0; n < 5; n++) {
            new RepelBot(arena, Color.RED, Color.ORANGE, Color.BLUE, new Point(random.nextInt(50), random.nextInt(50)));
        }

        //*********************ORANGE TO GREEN *****************************
        for (int n = 0; n < 5; n++) {
            new RepelBot(arena, Color.ORANGE, Color.GREEN, Color.RED, new Point(random.nextInt(50), random.nextInt(50)));
        }

        //********************GREEN TO BLUE ****************
        for (int n = 0; n < 5; n++) {
            new RepelBot(arena, Color.GREEN, Color.BLUE, Color.ORANGE, new Point(random.nextInt(50), random.nextInt(50)));
        }

        //**********************BLUE TO RED*****************************
        for (int n = 0; n < 5; n++) {
            new RepelBot(arena, Color.BLUE, Color.RED, Color.GREEN, new Point(random.nextInt(50), random.nextInt(50)));
        }

        arena.start();
    }
}
