package cs310.util;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * HashMap0 implementation, with  entrySet, and put implemented
 * --depends on AbstractMap for rest
 * The code comes from Weiss's HashSet
 * 
 */
public class HashMap0<K,V> extends AbstractMap<K,V>
implements Map<K,V>
{

    /**
     * Construct an empty HashMap1.
     */
    public HashMap0( )
    {
        allocateArray( DEFAULT_TABLE_SIZE );
        clear( );
    }
	
	/**
	 * Adds the key and value to the map
	 * 
	 * returns the old value associated with the key, or null
	 * if the key was not present prior to the call.
	 * Note: this is different from HashSet's add
	 * because we have to go on when the incoming key
	 * is there already, and update the corresp. value
	 */
	@Override
	@SuppressWarnings("unchecked")
	public V put(K key, V value) {
		int currentPos = findPos(key);
		V oldValue = null;
		if (isActive(array, currentPos)) {
			oldValue = (V)array[currentPos].value;  // cast with warning
		} else
			currentSize++;  // adding another assoc.
		if (array[currentPos] != null) {
			array[currentPos].value = value;  // overwrite active or inactive val
			array[currentPos].isActive = true;
		} else {
			occupied++;
			array[currentPos] = new HashEntry(key, value, true);
		}

		modCount++;

		if (occupied > array.length / 2)
			rehash();

		return oldValue;
	}
	
    @Override
	public Set<Map.Entry<K, V>> entrySet()
	{
		return new EntrySetClass();
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
				return false;

			array[currentPos].isActive = false;
			currentSize--;
			modCount++;

			if (currentSize < array.length / 8) {
				rehash();
			}
			return true;
		}
	    @Override
		public Iterator<Map.Entry<K, V>> iterator()
		{
			return new EntrySetIterator();
		}
	}
	
	
	// This iterator needs to go through the hash table, just
	// as HashSetIterator does
	private class EntrySetIterator implements Iterator<Map.Entry<K,V>>
	{
		private int expectedModCount = modCount;
		private int currentPos = -1;
		private int visited = 0;       
		@Override
		public boolean hasNext( )
		{
			if( expectedModCount != modCount )
				throw new ConcurrentModificationException( );

			return visited != currentSize;    
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public Map.Entry<K,V> next( )
		{
			return (Map.Entry<K, V>) nextEntry();
		}

		public HashEntry nextEntry()
		{
			if( !hasNext( ) )
				throw new NoSuchElementException( );
			do
			{
				currentPos++;
			} while( currentPos < array.length && !isActive( array, currentPos ) );

			visited++;
			return  array[ currentPos ];    
			
		}
		@Override
		public void remove( )
		{
			if( expectedModCount != modCount )
				throw new ConcurrentModificationException( );              
			if( currentPos == -1 || !isActive( array, currentPos ) )
				throw new IllegalStateException( );

			array[ currentPos ].isActive = false;
			currentSize--;
			visited--;
			modCount++;
			expectedModCount++;
		}
	}
    
    
    /**
     * Tests if item in pos is active.
     * @param pos a position in the hash table.
     * @param arr the HashEntry array (can be oldArray during rehash).
     * @return true if this position is active.
     */
    private static boolean isActive( HashEntry [ ] arr, int pos )
    {
        return arr[ pos ] != null && arr[ pos ].isActive;
    }
    
 
    
    /**
     * Private routine to perform rehashing.
     * Can be called by both add and remove.
     */
    @SuppressWarnings("unchecked")
    private void rehash( )
    {
        HashEntry [ ] oldArray = array;
        
            // Create a new, empty table
        allocateArray( nextPrime( 4 * size( ) ) );
        currentSize = 0;
        occupied = 0;
        
        // Copy table over
        for( int i = 0; i < oldArray.length; i++ )
            if( isActive( oldArray, i ) ) 
                put( (K) oldArray[ i ].key, (V) oldArray[i].value );
    }    

    
    // We need this "implements", to be able to return these objects
    // as Map.Entry objects in the EntrySetIterator
    // and have it work at runtime, when the erased types are checked
	@SuppressWarnings("rawtypes")
	private static class HashEntry implements Map.Entry
    {
        public Object  key;   // the element key
        public Object value;// value associated with the element key
        public boolean isActive;  // false if marked deleted

        public HashEntry( Object e, Object v, boolean i )
        {
            key  = e;
            value = v;
            isActive = i;
        }

		@Override
		public Object getKey() {
			return key;
		}

		@Override
		public Object getValue() {
			return value;
		}

		@Override
		public Object setValue(Object value) {
			return null;
		}
    }
    
    
    /**
     * Method that performs quadratic probing resolution.
     * @param x the item to search for.
     * @return the position where the search terminates.
     */
    private int findPos( Object x )
    {
        int offset = 1;
        int currentPos = ( x == null ) ? 0 : Math.abs( x.hashCode( ) % array.length );

        while( array[ currentPos ] != null )
        {
 
            if( x == null )
            {
                if( array[ currentPos ].key == null )
                    break;
            }
            else if( x.equals( array[ currentPos ].key ) )   
                break; 
            
            currentPos += offset;                  // Compute ith probe
            offset += 2;
            if( currentPos >= array.length )       // Implement the mod
                currentPos -= array.length;
        }

        return currentPos;
    }
    
    
    /**
     * Internal method to allocate array.
     * @param arraySize the size of the array.
     */
    private void allocateArray( int arraySize )
    {
        array = new HashEntry[ nextPrime( arraySize ) ];
    }

    /**
     * Internal method to find a prime number at least as large as n.
     * @param n the starting number (must be positive).
     * @return a prime number larger than or equal to n.
     */
    private static int nextPrime( int n )
    {
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
    }

    /**
     * Internal method to test if a number is prime.
     * Not an efficient algorithm.
     * @param n the number to test.
     * @return the result of the test.
     */
    private static boolean isPrime( int n )
    {
        if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;

        return true;
    }
    
    private static final int DEFAULT_TABLE_SIZE = 101;
    
    private int currentSize = 0;
    private int occupied = 0;
    private int modCount = 0;
    private HashEntry [ ] array;
}
