package Buffalo;

/**
 * Represents a Line. A line's equation has the following form: Ax + By + C = 0;
 * Where A is x coefficient; B is y coefficient.
 * 
 * @author              Vy Thuy Nguyen
 * @version             1.0 Mar 24, 2012
 * Last modified:       
 */
public class Line 
{
    private double a;
    private double b;
    private double c;
        
  /**
     * Constructor
     * Create a line when given two points.
     * 
     * @param p1
     * @param p2 
     */
    public Line(Point p1, Point p2)
    {
        if (p1.getX() == p2.getX() && p1.getY() == p2.getY())
        {
            throw new ArithmeticException("Cannot Determine a Line with ONLY ONE Point Given!"
                    + " P1 " + p1 + " = P2 " + p2);
        }
        else if (p1.getX() == p2.getX())
        {
            a = 1;
            b = 0;
            c = -p1.getX();
        }
        else if (p1.getY() == p2.getY())
        {
            a = 0;
            b = 1;
            c = -p1.getY();
        }
        else if (p1.getX() != p2.getX() && p1.getY() != p2.getY())
        {
            /* cast one of the operands to double to avoid integer division
             * since coordinates of p1 and p2 are integers,
             * which may result in truncating the quotient.
             */
            a = (double)(p2.getY() - p1.getY()) / (p2.getX() - p1.getX()); 
            b = -1;
            c = p1.getY() - a * p1.getX();
        }
    }

    /**
     * Constructor
     * Create a line when given the coefficients.
     * 
     * @param newA
     * @param newB
     * @param newC 
     */
    public Line(double newA, double newB, double newC)
    {
        a = newA;
        b = newB;
        c = newC;
    }
        
    /**
     * 
     * @return the equation of the line in form "Ax + By + C = 0"
     */
    public String getLineEqn()
    {
        return a + "X + " + b + "Y " + c + " = 0";
    }
        
    /**
     * 
     * @param p
     * @return value of Ax + By + C where x and y are the coordinates of p.
     */
    private double valueAt(Point p)
    {
        return a * p.getX() + b * p.getY() + c;
    }

    /**
     *
     * @param p1
     * @param p2
     * @return true if p1 and p2 are one the same side relative to the line.
     */
    public boolean isCoplanar(Point p1, Point p2)
    {
        return ( (valueAt(p1) * valueAt(p2) < 0) ? false : true );        
    }  
}
