package bhcc.VyThuyNguyen;

import java.awt.Color;
import java.awt.Point;
import net.topherc.colorchase.Arena;
import net.topherc.colorchase.ColorBot;

/**
 *
 * @author Vy Thuy Nguyen
 */
public class OrangeBotToGreen extends ColorBot {

    private static final Color MY_COLOR = Color.ORANGE;

    public OrangeBotToGreen(Arena arena, Point iniPosition) {
        super(arena, iniPosition);
    }

    @Override
    public Color getColor() {
        return MY_COLOR;
    }

    protected void scanOneBot(ColorBot bot) {
        if (bot.getColor() == Color.GREEN) {
            getForceAccumulator().addForce(location, bot.getLocation());
        }

    }
}

