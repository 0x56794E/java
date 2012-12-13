/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhcc.VyThuyNguyen;
import java.awt.Color;
import java.awt.Point;
import net.topherc.colorchase.Arena;
import net.topherc.colorchase.ColorBot;
/**
 *
 * @author Vy Thuy Nguyen
 */
public class RepelBot extends AttractBot{

   private final Color REPEL_COLOR;

   public RepelBot(Arena arena, Color myColor, Color attractingColor, Color repelColor, Point iniPosition)
   {
       super(arena, myColor, attractingColor, iniPosition);
       REPEL_COLOR = repelColor;

   }

     @Override
    public Color getColor() {
        return MY_COLOR;
    }

    protected void scanOneBot(ColorBot bot)
    {
        if (bot.getColor() == ATTRACTING_COLOR)
            getForceAccumulator().addForce(location, bot.getLocation());
        if (bot.getColor() == REPEL_COLOR)
            getForceAccumulator().subtractForce(location,bot.getLocation());
    }
}
