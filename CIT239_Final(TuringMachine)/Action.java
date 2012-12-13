/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turingmachine;

/**
 *
 * @author Vy Thuy Nguyen
 */
public class Action {

    private char newSymbol;
    private int movement;
    private int newState;

    private SymbolMap alphabet;

    // Creates a new Action object
    public Action(char newSymbol, int movement, int newState) {

        this.newSymbol = newSymbol;
        this.newState = newState;
        this.movement = movement;
    }

    //Returns the new symbol
    public char getNewSymbol()
    {
        return newSymbol;
    }

    //Returns the new state
    public int getnewState()
    {
        return newState;
    }

    //Returns the movement:
    //      -1: One step left
    //       0: Don't move
    //       1: One step right
    public int getMovement()
    {
        return movement;
    }

}
