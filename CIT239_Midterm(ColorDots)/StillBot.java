
package bhcc.VyThuyNguyen;

import java.awt.Color;
import java.awt.Point;
import net.topherc.colorchase.*;

/**
 *
 * @author Vy Thuy Nguyen
 */
public class StillBot extends ColorBot{
        protected final Color MY_COLOR;
        
   public StillBot(Arena arena, Color myColor, Point iniPosition)
   {
       super(arena, iniPosition);
       this.MY_COLOR = myColor;

       super.desiredMovement = Movement.Stay;
       super.readyToMove = false;
   }


     @Override
    public Color getColor() {
        return MY_COLOR;
    }

}
