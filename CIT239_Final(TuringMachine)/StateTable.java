/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turingmachine;

/**
 * This class represents the Turning machine's state table.
 * @author Vy Thuy Nguyen
 */
public class StateTable {

    // Implementation suggestion: represent the state table as:
    //     Action[stateCount][symbolCount]
    // i.e. as an array of Action objects where the rows correspond
    // to the states and the columns to the symbols.
    // Creates a state table with stateCount non-terminal states (terminal
    // states do not appear in the state table) and symbolCount symbols.
    // The initial Action for all entries should be:
    //     Action{newSymbol=addressSymbol, newState=-1, movement=0}.

    Action action[][];
  
    public StateTable(int stateCount, int symbolCount) {
        action = new Action[stateCount][symbolCount];
    }


    /**
     * 
     * @param state: int
     * @param sym: int (The index of the symbol in symbol map)
     * @param newAction: Action
     */
    public void setAction(int state, int sym, Action newAction) {
        action[state][sym] = newAction;
    }

     // Gets the Action at [state][sym].
    public Action getAction(int state, int sym) {

        return action[state][sym];
    }
}
