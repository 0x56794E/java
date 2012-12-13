package bhcc.VyThuyNguyen;
import java.awt.Color;
import java.awt.Point;
import net.topherc.colorchase.Arena;
import net.topherc.colorchase.ColorBot;

/**
 *
 * @author Vy Thuy Nguyen
 */
public class BlueBotToRed extends ColorBot {

    private static final Color MY_COLOR = Color.BLUE;

    public BlueBotToRed(Arena arena, Point iniPosition) {
        super(arena, iniPosition);
    }

    public Color getColor() {
        return MY_COLOR;
    }

    //indirectly changing accum ( therefore, when the arena calls scanEnvir.
    // which calls scanOneBot, the accum will've been changed
    protected void scanOneBot(ColorBot bot) {
        if (bot.getColor() == Color.RED) {
            getForceAccumulator().addForce(location, bot.getLocation());
        }
    }
}

