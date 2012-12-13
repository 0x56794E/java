/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs310.util;

import java.util.AbstractCollection;
import java.util.Iterator;

/**
 * This class implements Hash set 
 * CS-310: PA2 
 * Due date: 03/07/2012
 * @author Vy Thuy Nguyen
 */
public class HashSet1<T> extends AbstractCollection
{
    HashMap1 hm1;
    
    public HashSet1()
    {
        hm1 = new HashMap1();
        
    }

    @Override
    public boolean add(Object o)
    {
        if (hm1.containsKey(o))
            return false;
        else
        {
            hm1.put(o, null);
            return true;
        }        
    }
         
    @Override
    public boolean contains(Object o)
    {
        return hm1.containsKey(o);
    }
    
    @Override
    public void clear()
    {
        hm1.clear();
    }
    
    @Override
    public boolean isEmpty()
    {
        return hm1.isEmpty();
    }
    
    @Override
    public boolean remove(Object o)
    {
        Object rs = hm1.remove(o);
        if (rs == null)
            return false;
        else
            return true;
    }
    
    @Override
    public Iterator iterator()
    {
        return hm1.keySet().iterator();
    }

    @Override
    public int size()
    {
        return hm1.size();
    }
}
