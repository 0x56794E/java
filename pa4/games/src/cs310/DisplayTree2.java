/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cs310;

import edu.umb.cs.game.Easy;
import edu.umb.cs.game.Game;
import edu.umb.cs.game.GameException;
import edu.umb.cs.game.Move;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * DisplayTree2
 * @author              Vy Thuy Nguyen
 * @version             1.0 Apr 3, 2012
 * Last modified:       
 */
public class DisplayTree2 
{
    private static Map<Game, Integer> states = new HashMap<Game, Integer>();
    private static int currentState = 0;
    
    public static void main(String[] args)
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
    
    private static void explore(Game game, int level) throws GameException
    {
        if (!states.containsKey(game))
        {
            states.put(game, currentState);
            currentState++;
        }
        
        DisplayTree1.printSpace(level);
        System.out.println(game + " level(" + level + ") :G" + states.get(game));
                
        if (!game.isGameOver())
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
}