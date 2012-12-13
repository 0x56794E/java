package Beebumblia;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Bebumblia Communications
 * --------- --------------
 * Description:
 * The perfectly flat planet of beebumblia is occupied by
 * two warrior races, the bees and the beebears.  They are
 * fighting a war, of course.
 *
 * The bees are having trouble with their communications,
 * and need your computational assistance.
 *
 * Bee communications are done solely by bee drones, who
 * only fly in perfectly straight lines between bee army
 * command hives.  Unfortunately, if a bee drone flies over
 * any part of a beebear army, it will be instantly eaten.
 * 
 * You have been asked to compute whether or not a drone
 * can be used to communication between two particular bee
 * command hives.  The good news is that bee command hives
 * are very small, and beebear armies are almost perfect
 * circles, so for computational purposes bee command hives
 * may be taken as points and beebear armies as circles.
 * 
 * Input
 * -----
 * For each of a number of data sets:
 * 
 * Line 1: The number N of beebear armies, followed by N triples of numbers, 
 *      one for each army.  Each triple consists of an X coordinate, a Y coordi-
 * 	nate, and a diameter, in order.  1 <= N <= 10.
 * 
 * Line 2: Two pairs of numbers, one pair for each bee command hive. 
 *      Each pair consists of an X coordinate followed by a Y coordinate.
 * 
 * The input ends with a line containing a single 0.
 *
 * Output:
 * ------
 * One line for each data set.  The line begins with
 * Data Set #:' and ends with either `OK' or `EATEN'.
 * 
 * Sample Input
 * ------ -----
 * 1 0.0 0.0 2.0
 * -3.2 1.1 3.7 -0.5
 * 1 0.0 0.0 2.0
 * -3.2 1.8 3.7 2.9
 * 0
 * 
 * Sample Output
 * ------ ------
 * Data Set 1: EATEN
 * Data Set 2: OK
 *
 * 
 * @author              Vy Thuy Nguyen
 * @version             1.0 Mar 24, 2012
 * Last modified:       Mar 24, 2012
 */
public class Beebumblia 
{
    private ArrayList<Entry> entries;
    
    /**
     * Constructor
     */
    public Beebumblia()
    {
        entries = new ArrayList<Entry>();
    }
    
    /**
     * Represents ONE set of input.
     * An entry has: a list of beebear armies, two bee hives, and the result
     * of the communication process ('EATEN' or 'OK').
     * 
     * @author          Vy Thuy Nguyen
     * @version         1.0 Mar 24, 2012
     * Last modified:   Mar 24, 2012
     */
    private class Entry
    {
        private ArrayList<Circle> beebearArmies;        
        private ArrayList<Point> beeHives;
        private boolean eaten = false;
        
        public Entry (ArrayList<Circle> armies, ArrayList<Point> hives)
        {
            beebearArmies = armies;
            beeHives = hives;
            eaten = isEaten();         
        }
        
        @Override
        public String toString()
        {
            return (eaten ? "EATEN" : "OK");
        }
        
        private boolean isEaten()
        {            
            //pre-check: if any of the hives is on a circle
            //or one is inside and one is outside a circle => EATEN
            for (int i = 0; i < beebearArmies.size(); i++)
            {
                //If any of the hives is ON the circle
                if (beebearArmies.get(i).relativePosition(beeHives.get(0)) == 0 ||
                    beebearArmies.get(i).relativePosition(beeHives.get(1)) == 0)
                {
                    return true;
                }
                //If one is inside and one is outside
                else if (beebearArmies.get(i).relativePosition(beeHives.get(0)) < 0 
                        && beebearArmies.get(i).relativePosition(beeHives.get(1)) > 0
                        || beebearArmies.get(i).relativePosition(beeHives.get(1)) < 0
                        && beebearArmies.get(i).relativePosition(beeHives.get(0)) > 0
                        )
                {
                    return true;
                }
            }
           
            Line line = new Line(beeHives.get(0), beeHives.get(1));
            boolean okSoFar = true;
            
            for (int i = 0; i < beebearArmies.size(); i++)
            {
                //If distance to center > radius (doesn't intersect with the circle)
                if (line.distanceTo(beebearArmies.get(i).getCenter()) > beebearArmies.get(i).getRadius())
                {
                    okSoFar = okSoFar && true;
                }                    
                else
                {
                    Line pLine = line.getPerpendicular(beebearArmies.get(i).getCenter());
                    
                    //If both are inside one circle
                    if (beebearArmies.get(i).relativePosition(beeHives.get(0)) < 0
                        && beebearArmies.get(i).relativePosition(beeHives.get(1)) < 0)
                    {
                        okSoFar = okSoFar && true;
                    }
                    
                    //If distance < radius, but the segment doesn't touch the circle
                    else if (pLine.isCoplanar(beeHives.get(0), beeHives.get(1)))
                    {
                        okSoFar = okSoFar && true;
                    }
                    else
                    {
                        okSoFar = okSoFar && false;
                    }
                }
            }          
            return !okSoFar;
        }      
    }
  
    /**
     * Read input
     */
    public void readInput()
    {
        Scanner sc = new Scanner(System.in);
        String line;
        int count;
        
        while (sc.hasNextLine())
        {
            ArrayList<Circle> armies = new ArrayList<Circle>();
            ArrayList<Point> drones = new ArrayList<Point>();
                        
            //Reading coordinates of each beebear's center and radius
            line = sc.nextLine();
            if (line.length() <= 2) //check for end of input or bad input line
                continue;
            Scanner sc2 = new Scanner(line);
            count = sc2.nextInt();
            for (int i = 0; i < count; i++)
                armies.add(new Circle(sc2.nextDouble(), sc2.nextDouble(), sc2.nextDouble() / 2));
            
            //Reading coor of bee hives
            line = sc.nextLine();
            sc2 = new Scanner(line);
            for (int i = 0; i < 2; i++)
                drones.add(new Point(sc2.nextDouble(), sc2.nextDouble()));
            
            entries.add(new Entry(armies, drones));            
        }        
    }
    
    /**
     * Print output
     */
    public void printOutput()
    {
        for (int i = 0; i < entries.size(); i++)
            System.out.printf("Data Set %d: %s\n", i + 1, entries.get(i));
    }
}
