package bhcc.VyThuyNguyen;
import java.awt.Color;
import java.awt.Point;
import net.topherc.colorchase.Arena;
import net.topherc.colorchase.ColorBot;

/**
 *
 * @author Vy Thuy Nguyen
 */
public class AttractBot extends ColorBot {
   protected final Color MY_COLOR;
   protected final Color ATTRACTING_COLOR;

   public AttractBot(Arena arena, Color myColor, Color attractingColor, Point iniPosition)
   {
       super(arena, iniPosition);
       this.MY_COLOR = myColor;
       this.ATTRACTING_COLOR = attractingColor;
       super.readyToMove = true;
   }

   @Override
    public Color getColor() {
        return MY_COLOR;
    }


    protected void scanOneBot(ColorBot bot)
    {
        if (bot.getColor() == ATTRACTING_COLOR)
            getForceAccumulator().addForce(location, bot.getLocation());
        

    }
}

