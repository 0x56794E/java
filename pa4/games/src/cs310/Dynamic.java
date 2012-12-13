/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cs310;

import edu.umb.cs.game.*;
import edu.umb.cs.io.Terminal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/** 
 * Dynamic ComputerPlayer chooses the best move by recursively exploring
 * the entire Game tree below the current position.
 * 
 * @author              Vy Thuy Nguyen
 * @version             1.0 Apr 15, 2012
 * Last modified:       
 */
public class Dynamic extends ComputerPlayer
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
    
    private int value = 0;
    
    private Map <Game, Move> gameStates = new HashMap<Game, Move>();
    
    private Map <Move, double[]> moveMap;
    
    public Dynamic()
    {
        super("Dynamic");
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
         //gameStates = new HashMap<Game, Move>();
        
        if (!g.isGameOver())
        {       
            Move bestMove = null;
            
            if (gameStates.containsKey(g))
            {
                bestMove = gameStates.get(g);
                //System.out.println("OLD state; bestMove = " + bestMove);
            }
            else
            {
                bestMove = getValue(g);
                //System.out.println("NEW state; BestMove =" + bestMove);
                gameStates.put(g.copy(), bestMove);             
            }
                    
            return bestMove.toString();
        }        
        return "";
    }
    
    private Move getValue (Game g) throws GameException
    {
        Iterator<Move> moves = g.getMoves(); 
        Move bestMove = null;
        while (moves.hasNext())
        {                
            //System.out.println("Get here; bestMove = " + bestMove);
            Move move = moves.next();
            if (bestMove == null)
                bestMove = move;
            Game g1 = g.copy();
            g1.make(move);               

            lost = 0;
            won = 0;
            draw = 0;
            explore(g1);
              
            double chances[] = new double[4];
            //Calculating chances for current move              
            chances[TOTAL_MOVES] = lost + won + draw;
            chances[DRAW] = draw / chances[TOTAL_MOVES];
            chances[LOST] = lost / chances[TOTAL_MOVES];
            chances[WON] = won / chances[TOTAL_MOVES];

            //System.out.println("move made: " + move);
            //System.out.println("chances: " + chances[DRAW] + " " + chances[LOST] + " " + chances[WON] + " " + chances[TOTAL_MOVES]);
            
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
        }
        return bestMove.copy();
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
            {
                value++;
                won++;
            }
            
            else if (g.winner() == PlayerNumber.DRAW)
                draw++;
            
            else
            {
                value--;
                lost++;    
            }                  
        }
    }
}