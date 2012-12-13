/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Beebumblia;

/**
 * Represents a Point.
 * A point has x-coordinate and y-coordinate.
 * 
 * @author              Vy Thuy Nguyen
 * @version             1.0 Mar 24, 2012
 * Last modified:       Mar 24, 2012
 */
public class Point 
{
    private double x;
    private double y;
        
    /**
     * Constructor
     * 
     * @param newX
     * @param newY 
     */
    public Point(double newX, double newY)
    {
        x = newX;
        y = newY;
    }
        
    /**
     * 
     * @return the x coordinate of the point
     */
    public double getX()
    {
        return x;
    }
       
    /**
     * 
     * @return the y coordinate of the point.
     */
    public double getY()
    {
        return y;
    }
        
    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }    
}
