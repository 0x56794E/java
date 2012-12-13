package Beebumblia;

/**
 * Represents a circle. 
 * A circle has: a center, and the radius of the circle.
 *
 * @author Vy Thuy Nguyen
 * @version 1.0 Mar 24, 2012 Last modified:
 */
public class Circle
{
    Point center;
    double radius;

    /**
     * Constructor
     * Create a circle when given the x-coor. and y-coor. of the center,
     * and the radius.
     * 
     * @param x y coordinate
     * @param y x coordinate
     * @param r radius
     */
    public Circle(double x, double y, double r)
    {
        center = new Point(x, y);
        radius = r;
    }
    
    /**
     * 
     * @return the radius of the circle.
     */
    public double getRadius()
    {
        return radius;
    }

    /**
     * 
     * @return the center of the circle. 
     */
    public Point getCenter()
    {
        return center;
    }

    /**
     * Determines the relative position of a point to the circle
     * 
     * @param p Point p
     * @return 0 if p is on the circle; 
     * a positive number if p is outside; 
     * a negative number if p is inside.
     */
    public double relativePosition(Point p)
    {
        double d = Math.pow(p.getX() - center.getX(), 2) + Math.pow(p.getY() - center.getY(), 2);
        double r = radius * radius;
        return d - r;
    }
}
