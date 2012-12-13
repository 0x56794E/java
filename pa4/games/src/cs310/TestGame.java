package cs310;

import edu.umb.cs.game.*;
import java.util.Iterator;

/**
 * Minimal Game app
 *
 */
public class TestGame
{
    public static void main(String[] args)
    {
        Game game = new Easy(); // a concrete Game

        System.out.println("Using game " + game.getName());

        try
        {
            game.init();
            PlayerNumber pn = game.whoseTurn();
            System.out.println("At game start, pn = " + pn);
            System.out.println("At game start, game state: " + game + "\n");

            Iterator<Move> moves = game.getMoves();
            while (moves.hasNext())
            {
                Move m = moves.next();
                System.out.println("Move: " + m);
                Game g1 = game.copy();
                g1.make(m);
                System.out.println("To game state: " + g1);
            }
        }
        catch (GameException e)
        {
            System.out.println(e);
        }
    }
}
