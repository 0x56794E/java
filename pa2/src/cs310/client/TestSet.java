/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs310.client;
import cs310.util.HashSet;
import cs310.util.HashSet1;
import java.util.Iterator;

/**
 * This class tests HashSet implemented by Weiss
 * CS-310: PA2
 * Due date: 03/07/2012
 * @author Vy Thuy Nguyen
 */
public class TestSet
{
    public static void main( String[] args)
    {
        System.out.println("Testing cs310.util.HashSet");
        HashSet<String> set = new HashSet<String>();
        
        //Adding three strings
        set.add("Hello");
        set.add(", ");
        set.add("world!");
        System.out.println("The size of set is " + set.size() + "\n");
        
        //Print each string on a line
        Iterator iter = set.iterator();
        while (iter.hasNext())
            System.out.println(iter.next());
        
        //Remove all strings
        set.removeAll(set);
        System.out.println("\nThe size of set is now " + set.size());
        
        
        System.out.println("====================\n");
        System.out.println("Testing cs310.util.HashSet1");
        HashSet1<String> hs1 = new HashSet1<String>();
        
        //Adding three strings
        hs1.add("Hello");
        hs1.add(", ");
        hs1.add("world!");
        System.out.println("The size of set is " + hs1.size() + "\n");
        
        //Print each string on a line
        Iterator iter2 = hs1.iterator();
        while (iter2.hasNext())
            System.out.println(iter2.next());
        
        //Remove all strings
        hs1.removeAll(hs1);
        System.out.println("\nThe size of set is now " + hs1.size());
        
    }
}
