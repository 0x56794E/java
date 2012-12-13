package bhcc.VyThuyNguyen;

import java.awt.Color;
import java.awt.Point;
import net.topherc.colorchase.*;
import java.util.*;

/**
 *
 * @author Vy Thuy Nguyen
 */
public class LightLikeBot extends ColorBot {
   protected final Color MY_COLOR;
   protected StillBot attratingPoint;
   protected final Arena MY_ARENA;

   public LightLikeBot(Arena arena, Color myColor, Point iniPosition)
   {
       super(arena, iniPosition);
       MY_COLOR = myColor;
       MY_ARENA = arena;
  
       new StillBot(MY_ARENA, MY_COLOR, new Point(25,25));
       new StillBot(MY_ARENA, MY_COLOR, new Point(49,49));
        
       super.readyToMove = true;
   }

    @Override
    public Color getColor() {
        return MY_COLOR;
    }

    public void addBot()
    {
        while (readyToMove)
            new StillBot(MY_ARENA, Color.WHITE, this.location);
    }
     protected void scanOneBot(ColorBot bot)
    {
            getForceAccumulator().addForce(location, bot.getLocation());

      

    }
}

