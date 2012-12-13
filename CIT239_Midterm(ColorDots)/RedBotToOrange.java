package bhcc.VyThuyNguyen;

import java.awt.Color;
import java.awt.Point;
import net.topherc.colorchase.Arena;
import net.topherc.colorchase.ColorBot;

/**
 *
 * @author Vy Thuy Nguyen
 */
public class RedBotToOrange extends ColorBot {

    private static final Color MY_COLOR = Color.RED;

    public RedBotToOrange(Arena arena, Point iniPosition) {
        super(arena, iniPosition);
    }

    public Color getColor() {
        return MY_COLOR;
    }

    protected void scanOneBot(ColorBot bot) {
        if (bot.getColor() == Color.ORANGE) {
            getForceAccumulator().addForce(location, bot.getLocation());
        }
    }
}
