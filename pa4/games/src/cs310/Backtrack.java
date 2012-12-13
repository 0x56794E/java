package cs310;

// Backtrack.java
//
// Stub version--you fill in real implementation
// Ethan Bolker, February 2003

import edu.umb.cs.game.*;
import edu.umb.cs.io.Terminal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Backtracking ComputerPlayer chooses the best move by recursively exploring
 * the entire Game tree below the current position.
 *
 * @author      Vy Thuy Nguyen
 * @version     4/15/2012
 */
public class Backtrack extends ComputerPlayer
{
    /* Constants used as array index */
    private static final int LOST = 1;
    private static final int WON = 2;
    private static final int DRAW = 0;
    private static final int TOTAL_MOVES = 3;

    private Terminal t = new Terminal();
    private PlayerNumber desiredWinner;
    
    private int lost;
    private int draw;
    private int won;
    
    private Map <Move, double[]> moveMap;
    
    public Backtrack()
    {
        super("Backtracking");
    }

    /**
     * This supposedly smart ComputerPlayer consulting his (interactive) oracle.
     *
     * @param g the current Game position.
     * @return the best Move to make.
     * @throws GameException.
     * 
     */
    @Override
    public Move findbest(Game g) throws GameException
    {
        GameStrings view = g.getGameStrings();
        desiredWinner = g.whoseTurn();
        
        while (true)
        {
            try
            {
                String s = getBest(g);
                return view.parseMove(s);
            }
            catch (GameException e)
            {
                t.println(e.toString());
            }
        }
    }
    
    /**
     * 
     * @param g
     * @return best move 
     */
    private String getBest(Game g) throws IllegalMoveException, GameException
    {
        moveMap = new HashMap<Move, double[]>();
        
        if (!g.isGameOver())
        {        
            Iterator<Move> moves = g.getMoves();    
            Move bestMove = null;
            while (moves.hasNext())
            {                
               Move move = moves.next();
               if (bestMove == null)
                   bestMove = move;
               Game g1 = g.copy();
               g1.make(move);
               
               lost = 0;
               won = 0;
               draw = 0;
               
               explore(g1);
               
               //Calculating chances for current move              
               double total = lost + won + draw;
               double drawChance = draw / total;
               double lostChance = lost / total;
               double winChance = won /total;
               double chances[] = {drawChance, lostChance, winChance, total};
               
               //Save result to map
               moveMap.put(move, chances);
               
               //***Determining best move***
               if (moveMap.containsKey(bestMove))
               {
                   if (chances[LOST] < moveMap.get(bestMove)[LOST]) //If lostChance is smaller
                   {
                       bestMove = move;
                   }                   
                   else if (chances[LOST] == moveMap.get(bestMove)[LOST]) //If lostChance is equal
                   { 
                       //Comparing chances to win
                       if (chances[WON] > moveMap.get(bestMove)[WON]) //If win chance is greater than 
                       {
                           bestMove = move;
                           
                       }
                       else if (chances[WON] == moveMap.get(bestMove)[WON]) //If they are equal
                       {
                            if (chances[TOTAL_MOVES] > moveMap.get(bestMove)[TOTAL_MOVES])
                                bestMove = move;
                       }
                   }
               }
               //***end Determining best move***
               
               //These lines are for debugging purpose
               //System.out.println("next possible move = " + move + " and outcome of this move: Draw=" + moveMap.get(move)[DRAW] + " Lost=" +  moveMap.get(move)[LOST] + " Win=" + moveMap.get(move)[WON] + " Total moves=" + moveMap.get(move)[TOTAL_MOVES]);
               //System.out.println("best move so far = " + bestMove + " and out come of this move: Draw=" + moveMap.get(bestMove)[DRAW]+ " Lost=" + moveMap.get(bestMove)[WON]  + " Win=" + moveMap.get(bestMove)[WON] + " Total moves=" + moveMap.get(bestMove)[TOTAL_MOVES] + "\n\n"); 
            }
            return bestMove.toString();
        }
        
        return "";
    }
    
    /**
     * Recursive routine
     * 
     * @param g
     * @throws GameException 
     */
    private void explore(Game g) throws GameException
    {   
        if (!g.isGameOver())
        {
            Iterator<Move> moves = g.getMoves();
            while (moves.hasNext())
            {
                Move m = moves.next();
                Game g1 = g.copy();
                g1.make(m);
                explore(g1);
            }
        }
        else
        {
            if (g.winner() == desiredWinner)
                won++;
            
            else if (g.winner() == PlayerNumber.DRAW)
                draw++;
            
            else
                lost++;      
        }
    }
}
