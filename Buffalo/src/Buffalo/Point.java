package Buffalo;

/**
 * Represents a Point.
 * A point has x-coordinate and y-coordinate.
 * 
 * @author Vy Thuy Nguyen
 * @version 1.0 Mar 24, 2012 Last modified:
 */
public class Point
{

    private int x;
    private int y;

    /**
     * Constructor
     *
     * @param newX
     * @param newY
     */
    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return the x coordinate of the point
     */
    public int getX()
    {
        return x;
    }

    /**
     *
     * @return the y coordinate of the point.
     */
    public int getY()
    {
        return y;
    }

    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
}
