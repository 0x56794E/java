package cs310.client;

import cs310.util.HashMap0;
import cs310.util.HashMap1;
import cs310.util.HashMap5b;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Set;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * This class tests HashMap, TreeMap and HashMap0
 * CS-310: PA2
 * Due date: 03/07/2012
 * @author Vy Thuy Nguyen
 */
public class TestMap
{
    Map<String, String> map;
    
    public TestMap(Map m)
    {
        map = m;
    }
    
    // Do some inserts and printing (done in printMap).
    public static void main( String [ ] args )
    {
        TestMap treeMap = new TestMap(new TreeMap<String, String>());
        treeMap.test();
        
        TestMap hashMap = new TestMap(new HashMap<String, String>());
        hashMap.test();
        
        TestMap hashMap0 = new TestMap(new HashMap0<String, String>());
        hashMap0.test();
        
        TestMap hashMap1 = new TestMap(new HashMap1<String, String>());
        hashMap1.test();
        
        TestMap hashMap5b = new TestMap(new HashMap5b<String, String>());
        hashMap5b.test();
    }
    
    public void test()
    {    
        try
        {
            //Get class name
            System.out.println( "The " + map.getClass( ).getName( ) + ": \n" );
            
            //Test adding values
            map.put( "John Doe", "212-555-1212" );
            map.put( "Jane Doe", "312-555-1212" );
            map.put( "Holly Doe", "213-555-1212" ); 
            map.put( "Susan Doe", "617-555-1212" ); 
            map.put( "Jane Doe", "unlisted" ); //Multiple values to one key "Jane Doe"            
            System.out.println( "map.get(\"Jane Doe\"): " + map.get( "Jane Doe" ) + "\n");
                         
            //Test iterator
            try
            {
                Iterator iter = map.keySet().iterator();
                while ( iter.hasNext() )
                    iter.next();
                
                iter.next(); //To trigger No Such Element exception
            }
            catch(NoSuchElementException noEleExc)
            {
                System.out.println("No Such Element.");
                noEleExc.printStackTrace();
            }
            
            System.out.println("\n");
            
            //Test remove            
            try
            {
                System.out.println("Removing \"John Doe\"...The old object is :" +
                                    map.remove("John Doe"));
                
                
                System.out.println("Removing \"bad key\"...The old object is: " +
                map.remove("bad key"));
                
                
                System.out.println("Removing null...The old object is: " +
                map.remove(null)); //Trigger null ptr exception
                
            }
            catch(ConcurrentModificationException e)
            {
                System.out.println("Concurrent Modification Exception");
                e.printStackTrace();
            }
            catch (Exception e)
            {
                System.out.println("Unpredictable Exception");
                e.printStackTrace();
            }
                        
            //Test getting keys
            System.out.println( "\nThe keys are: " );
            Set<String> keys = map.keySet( );
            printCollection( keys );
            
            //Test getting values
            System.out.println( "\nThe values are: " );
            Collection<String> values = map.values( );
            printCollection( values );       
            
            //Removing from views
            //Whatever we do to key and value sets will be reflected in the map
            keys.remove( "John Doe" );
            values.remove( "unlisted" );
            
            System.out.println( "\nAfter \"John Doe\" and \"unlisted\" are removed, the map is" );       
            System.out.println( map );
            
            //Test containsKey
            try
            {
                System.out.println("\nmap.containsKey(\"Holly Doe\")? " + map.containsKey("Holly Doe"));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            
            //Test containsValue
            try
            {
                System.out.println("\nmap.containsValue(\"213-555-1212\")? " + map.containsValue("213-555-1212"));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
            //test printMap method
            System.out.println();
            printMap("Map", map);      
            
            System.out.println("\n==============================");

        }
        catch (Exception e) //Catch any kind of exception if there is any
        {
            e.printStackTrace();
        }
        
        

        

    }
    
    public static <AnyType> void printCollection( Collection<AnyType> c )
    {
        try
        {
            for( AnyType val : c )
                System.out.print( val + "; " );
            System.out.println( );
        }
        catch(ConcurrentModificationException concurrentModExc)
        {
            System.out.println("Concurrent Modification Exception.");
            concurrentModExc.printStackTrace();
        }
    }

    public <KeyType,ValueType> void printMap( String msg, Map<KeyType,ValueType> m )
    {
        System.out.println( msg + ":" );
        Set<Map.Entry<KeyType,ValueType>> entries = m.entrySet( );
        
        for( Map.Entry<KeyType,ValueType> thisPair : entries )
        {
            System.out.print( thisPair.getKey( ) + ": " );
            System.out.println( thisPair.getValue( ) );
        }
    }


}
