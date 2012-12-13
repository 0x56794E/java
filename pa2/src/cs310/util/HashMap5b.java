/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cs310.util;

import java.util.*;

/**
 * This class implements Hash map 
 * CS-310: PA2 
 * Due date: 03/07/2012
 * @author Vy Thuy Nguyen
 */
public class HashMap5b<K, V> extends AbstractMap<K, V>
        implements Map<K, V>
{

    /**
     * Construct an empty HashMap1.
     */
    public HashMap5b()
    {
        allocateArray(DEFAULT_TABLE_SIZE);
        clear();
    }

    /**
     * Adds the key and value to the map
     *
     * returns the old value associated with the key, or null if the key was not
     * present prior to the call. Note: this is different from HashSet's add
     * because we have to go on when the incoming key is there already, and
     * update the corresp. value
     */
    @Override
    @SuppressWarnings("unchecked")
    public V put(K key, V value)
    {
        int currentPos = findPos(key);
        V oldValue = null;
        if (isActive(array, currentPos))
        {
            oldValue = (V) array[currentPos].value;  // cast with warning
        } else
        {
            currentSize++;  // adding another assoc.
        }
        if (array[currentPos] != null)
        {
            array[currentPos].value = value;  // overwrite active or inactive val
            array[currentPos].isActive = true;
        } else
        {
            occupied++;
            array[currentPos] = new HashMap5b.HashEntry(key, value, true);
        }

        modCount++;

        if (occupied > array.length / 2)
        {
            rehash();
        }

        return oldValue;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet()
    {
        return new HashMap5b.EntrySetClass();
    }

    /**
     * Removes all mappings from this map.
     */
    @Override
    public void clear()
    {
        currentSize = occupied = 0;
        ++modCount;
        allocateArray(DEFAULT_TABLE_SIZE);
    }

    /**
     * @param key
     * @return true if this map contains a mapping for the specified key.
     */
    @Override
    public boolean containsKey(Object key)
    {
        return isActive(array, findPos(key));
    }

    /**
     * @param val
     * @return true if this map contains a mapping for the specified value.
     */
    @Override
    public boolean containsValue(Object val)
    {
        HashEntry entry;
        for (int n = 0; n < array.length; ++n)                
            if ((entry = array[n]) != null && entry.value.equals(val))
                return true;
        return false;
    }

    /**
     * 
     * @return true  if there's no mapping in the map
     */
    @Override
    public boolean isEmpty()
    {
        return (currentSize == 0 ? true : false);
    }
    
    /**
     * 
     * @return the size of the map 
     */
    @Override
    public int size()
    {
        return currentSize;
    }
    
    /**
     * 
     * @param key
     * @return the old object; null if not in the map
     */
    @Override
    public V remove(Object key)
    {
        int index = findPos(key);
        if (!isActive(array, index))
            return null;
        else
        {
            array[index].isActive = false;
            --currentSize;
            ++modCount;
            return (V)array[index].value;
        }
    }
    
    /**
     * 
     * @param key
     * @return value assoc. with the specified key; null if not exist
     */
    @Override
    public V get(Object key)
    {
        if (!containsKey(key))
            return null;
        int index = findPos(key);
        return (V) array[index].value;
    }

    //*********** Part 5b ********************
    @Override
    public Set<K> keySet()
    {     
        return new KeySet();        
    }
    
    @Override
    public Collection<V> values ()
    {
        return new Values();
    }
    
    private static interface Operations<M>
    {
        boolean contains(Object o);
        boolean remove(Object o);
        M get(HashEntry entry);
    }
    
    private class ViewClass<T> extends AbstractCollection<T>
    {
        private Operations<T> operations;
        ViewClass (Operations<T> op)
        {
            this.operations = op;
        }
                    
        @Override
        public int size()
        {
            return currentSize;
        }   
        
        @Override
        public boolean contains (Object o)
        {
            return operations.contains(o);
        }
        
        @Override
        public boolean containsAll(Collection<?> c)
        {
            for (Object obj : c)
                if (!operations.contains(obj)) return false;
            return true;
        }
        
        @Override
        public boolean isEmpty()
        {
            return currentSize == 0;
        }
        
        @Override
        public void clear()
        {
            HashMap5b.this.clear();
        }
        
        @Override
        public boolean add (T o)
        {
            throw new UnsupportedOperationException("Illegal Operation on Value/Key Collection");
        }
         
        @Override
        public boolean addAll(Collection<? extends T> c)
        {
            throw new UnsupportedOperationException("Illegal Operation on Value/Key Collection");
        }
        
        @Override
        public boolean remove (Object o)
        {
            return operations.remove(o);
        }
        
        @Override
        public boolean removeAll(Collection<?> c)
        {
            boolean all = true;
            for (Object o : c)
                 all &= operations.remove(o);            
            return all;            
        } 
        
        @Override
        public boolean retainAll (Collection<?> c)
        {
            boolean changed = false;
            for (Object o : c)
            {
                if (currentSize == 0) return changed;
                changed |= operations.remove(o);
            }
            return changed;
        }
        


         
        @Override
        public Iterator<T> iterator()
        {
            return new Iterator<T>()
            {
                private int localModCount = modCount;
                private int currentPos = -1;
                private int visited = 0;
                
                @Override
                public boolean hasNext()
                {
                    if (localModCount != modCount)
                        throw new ConcurrentModificationException();
                    return visited < currentSize;
                }

                @Override
                public T next()
                {
                    if (!hasNext())
                        throw new NoSuchElementException();
                    HashEntry entry = null;
                    do
                        ++currentPos;
                    while (currentPos < array.length && !((entry = array[currentPos]) != null && entry.isActive));
                    ++visited;
                    return operations.get(entry);
                }
 
                @Override
                public void remove()
                {
                    if (localModCount != modCount)
                        throw new ConcurrentModificationException();
                    if (currentPos == -1 || !isActive(array, currentPos))
                        throw new IllegalStateException();

                    array[ currentPos].isActive = false;
                    --currentSize;
                    --visited;
                    ++modCount;
                    ++localModCount;
                }               
            };
        }
    }
            
    private class KeySet  extends ViewClass<K> implements Set<K>
    {        
        KeySet ()
        {
            super(new Operations<K>()
            {
                @Override
                public boolean contains (Object o)
                {
                    return HashMap5b.this.containsKey((K)o);
                }
                
                @Override
                public boolean remove (Object o)
                {
                    return HashMap5b.this.remove((K)o) != null;
                }

                @SuppressWarnings("unchecked")
                @Override
                public K get(HashEntry entry)
                {
                    return (K)entry.key;
                }



            });
        }         
                            
    }
    
    private class Values extends ViewClass<V> implements Collection<V>
    {
        Values ()
        {
            super (new Operations<V>()
            {
               @Override
               public boolean contains (Object o)
               {
                   return HashMap5b.this.containsValue((V)o);
               }
               
               @Override
               public boolean remove (Object o)
               {
                   return HashMap5b.this.removeValue(o);
               }

                @SuppressWarnings("unchecked")
                @Override
                public V get(HashEntry entry)
                {
                    return (V)entry.value;
                }


            });
        }         
    }
    
    //***********************************    
    
     /**
     * Removes all values that equal to this val
     * @param val
     * @return true if the map has been modified after the function returns
     */
    private boolean removeValue (Object val)
    {
        int count = 0, oldSize = currentSize;
        HashMap5b.HashEntry entry;
        for (int n = 0; n < array.length && count < oldSize; ++n)
            if ((entry = array[n]) != null && entry.isActive)
            {
                if (entry.value.equals(val))
                {
                    entry.isActive = false;
                    --currentSize;                                        
                }
                ++count;
            }
        if (oldSize != currentSize)
        {
            ++modCount;
            return true;
        }
        return false;
    }
    
    private class EntrySetClass extends AbstractSet<Map.Entry<K, V>>
    {

        @Override
        public int size()
        {
            return currentSize;
        }

        @Override
        public boolean remove(Object key)
        {
            int currentPos = findPos(key);
            if (!isActive(array, currentPos))
            {
                return false;
            }

            array[currentPos].isActive = false;
            currentSize--;
            modCount++;

            if (currentSize < array.length / 8)
            {
                rehash();
            }
            return true;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator()
        {
            return new HashMap5b.EntrySetIterator();
        }
    }

    // This iterator needs to go through the hash table, just
    // as HashSetIterator does
    private class EntrySetIterator implements Iterator<Map.Entry<K, V>>
    {

        private int expectedModCount = modCount;
        private int currentPos = -1;
        private int visited = 0;

        @Override
        public boolean hasNext()
        {
            if (expectedModCount != modCount)
            {
                throw new ConcurrentModificationException();
            }

            return visited != currentSize;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Map.Entry<K, V> next()
        {
            return (Map.Entry<K, V>) nextEntry();
        }

        public HashMap5b.HashEntry nextEntry()
        {
            if (!hasNext())
            {
                throw new NoSuchElementException();
            }
            do
            {
                currentPos++;
            } while (currentPos < array.length && !isActive(array, currentPos));

            visited++;
            return array[ currentPos];

        }

        @Override
        public void remove()
        {
            if (expectedModCount != modCount)
            {
                throw new ConcurrentModificationException();
            }
            if (currentPos == -1 || !isActive(array, currentPos))
            {
                throw new IllegalStateException();
            }

            array[ currentPos].isActive = false;
            currentSize--;
            visited--;
            modCount++;
            expectedModCount++;
        }
    }

    /**
     * Tests if item in pos is active.
     *
     * @param pos a position in the hash table.
     * @param arr the HashEntry array (can be oldArray during rehash).
     * @return true if this position is active.
     */
    private static boolean isActive(HashMap5b.HashEntry[] arr, int pos)
    {
        return arr[ pos] != null && arr[ pos].isActive;
    }

    /**
     * Private routine to perform rehashing. Can be called by both add and
     * remove.
     */
    @SuppressWarnings("unchecked")
    private void rehash()
    {
        HashMap5b.HashEntry[] oldArray = array;

        // Create a new, empty table
        allocateArray(nextPrime(4 * size()));
        currentSize = 0;
        occupied = 0;

        // Copy table over
        for (int i = 0; i < oldArray.length; i++)
        {
            if (isActive(oldArray, i))
            {
                put((K) oldArray[ i].key, (V) oldArray[i].value);
            }
        }
    }

    // We need this "implements", to be able to return these objects
    // as Map.Entry objects in the EntrySetIterator
    // and have it work at runtime, when the erased types are checked
    @SuppressWarnings("rawtypes")
    private static class HashEntry implements Map.Entry
    {

        public Object key;   // the element key
        public Object value;// value associated with the element key
        public boolean isActive;  // false if marked deleted

        public HashEntry(Object e, Object v, boolean i)
        {
            key = e;
            value = v;
            isActive = i;
        }

        @Override
        public Object getKey()
        {
            return key;
        }

        @Override
        public Object getValue()
        {
            return value;
        }

        @Override
        public Object setValue(Object value)
        {
            return null;
        }
    }

    /**
     * Method that performs quadratic probing resolution.
     *
     * @param x the item to search for.
     * @return the position where the search terminates.
     */
    private int findPos(Object x)
    {
        int offset = 1;
        int currentPos = (x == null) ? 0 : Math.abs(x.hashCode() % array.length);

        while (array[ currentPos] != null)
        {

            if (x == null)
            {
                if (array[ currentPos].key == null)
                {
                    break;
                }
            } else if (x.equals(array[ currentPos].key))
            {
                break;
            }

            currentPos += offset;                  // Compute ith probe
            offset += 2;
            if (currentPos >= array.length)       // Implement the mod
            {
                currentPos -= array.length;
            }
        }

        return currentPos;
    }

    /**
     * Internal method to allocate array.
     *
     * @param arraySize the size of the array.
     */
    private void allocateArray(int arraySize)
    {
        array = new HashMap5b.HashEntry[nextPrime(arraySize)];
    }

    /**
     * Internal method to find a prime number at least as large as n.
     *
     * @param n the starting number (must be positive).
     * @return a prime number larger than or equal to n.
     */
    private static int nextPrime(int n)
    {
        if (n % 2 == 0)
        {
            n++;
        }

        for (; !isPrime(n); n += 2)
            ;

        return n;
    }

    /**
     * Internal method to test if a number is prime. Not an efficient algorithm.
     *
     * @param n the number to test.
     * @return the result of the test.
     */
    private static boolean isPrime(int n)
    {
        if (n == 2 || n == 3)
        {
            return true;
        }

        if (n == 1 || n % 2 == 0)
        {
            return false;
        }

        for (int i = 3; i * i <= n; i += 2)
        {
            if (n % i == 0)
            {
                return false;
            }
        }

        return true;
    }
    private static final int DEFAULT_TABLE_SIZE = 101;
    private int currentSize = 0;
    private int occupied = 0;
    private int modCount = 0;
    private HashMap5b.HashEntry[] array;
}
