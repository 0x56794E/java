/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turingmachine;
import java.util.*;
/**
 * This class represents the tape, the state register and the R/W Head
 * @author Vy Thuy Nguyen
 */
public class Tape {

    private int state;
    private int head;
    LinkedList <Character> activeTape = new LinkedList <Character> ();
//************************
    // Implementation suggestion: you can represent the tape as an ArrayList, or
    // more efficiently for adding cells on the left, a LinkedList.  You can use
    // an index to represent the head position with ArrayList.  You can use a
    // ListIterator for the head position in either case.  The easiest would be
    // a StringBuilder and an index.  In that case you would need sb.append(blankChar),
    // sb.insert(0, blankChar), sb.charAt(headPosition) and
    // sb.setCharAt(headPosition, char) and toString().  Note that inserting at the
    // beginning of a StringBuilder is not efficient.    
//************************

    //Constructor
    public Tape(String input)
    {
        
        head = 0;
        state = 0;
        for (int n = 0; n < input.length(); n++)
        {
            activeTape.add(input.charAt(n));
            if (input.charAt(n) == '#')
            {
                head = n;
                activeTape.remove(n);
            }
        }

    }

    //print tape
    public void printTape()
    {
        for (int n = 0; n < activeTape.size(); n++)
        {
            if (n == head)
                System.out.print('#');
            System.out.print(activeTape.get(n));
        }
        System.out.println();
    }


    //Returns state
    public int getState()
    {
        return state;
    }

    public void setState(int newState)
    {
        state = newState;
    }

    //Returns the symbol pointed to by the head
    public char getSymbolChar()
    {
        return activeTape.get(head);
    }

    //Returns the index of the symbol pointed to by the head
    public int getSymbol()
    {
        return activeTape.indexOf(getSymbolChar());
    }


    // Returns true if the action doesn't move, doesn't modify the cell, and
    // doesn't change the state, or if the current symbol is blank, if it does not
    // change its state and it moves out of the active region.  These conditions
    // are some simple cases of infinite loops.
    public boolean Update(Action action)
    {
        return true;
    }

    //Returns the active region of the tape as a string of Symbol chars
    public String getActiveRegion()
    {
        char arr[] = new char[activeTape.size()];
        for (int n = 0; n < activeTape.size(); n++)
            arr[n] = activeTape.get(n);
        return new String(arr);
    }

    //Returns the position of the head relative to the beginning of the active region of the tape
    public int getHeadPosition()
    {        
        return head;
    }

    //Set the index for the head
    public void setHeadPosition(int index)
    {
        head = index;
    }
}
