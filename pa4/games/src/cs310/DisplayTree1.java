/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cs310;

import edu.umb.cs.game.Game;
import edu.umb.cs.game.Move;
import java.util.Iterator;
import edu.umb.cs.game.Easy;

/**
 * DisplayTree1
 * @author              Vy Thuy Nguyen
 * @version             1.0 Apr 3, 2012
 * Last modified:       
 */
public class DisplayTree1 
{
    public static void main (String[] args)
    {
        Game game = new Easy();
      
        try
        {
            game.init();
            explore(game, 0);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    
    private static void explore(Game game, int level) throws Exception
    {
        printSpace(level);
        System.out.println(game + " level(" + level + ")");
        
        if (game.isGameOver())
            return;
        else
        {
            Iterator<Move> moves = game.getMoves();    
            
            while (moves.hasNext())
            {                
                Move move = moves.next();
                Game g1 = game.copy();
                g1.make(move);
                explore(g1, level + 1);
            }
        }
    }
    
    protected static void printSpace(int level)
    {
        for (int n = 0; n < level; ++n)
            System.out.print("   ");
    }
}
