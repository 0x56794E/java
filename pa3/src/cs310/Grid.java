package cs310;

import java.util.HashSet;
import java.util.Set;

/**
 * Programming Assigment 3
 * 
 * @author Vy Thuy Nguyen
 * /
public class Grid
{
    private boolean[][] grid = null;

    public Grid(boolean[][] inGrid)
    {
        grid = inGrid;
    }

    /**
     * Represents a spot. 
     * hashCode() and equals() are implemented to use HashSet.
     */
    private static class Spot
    {
        int row;
        int col;

        public Spot(int r, int c)
        {
            row = r;
            col = c;
        }

        @Override
        public int hashCode()
        {
            return row * 10 + col;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj == null)
            {
                return false;
            }
            if (getClass() != obj.getClass())
            {
                return false;
            }
            final Spot other = (Spot) obj;
            if (this.row != other.row)
            {
                return false;
            }
            if (this.col != other.col)
            {
                return false;
            }
            return true;
        }
    }

    // <your methods to do 7.46a(7.31a)>
    private int count(int row, int col)
    {
        if (row < 0 || col < 0 || row >= grid.length || col >= grid.length)
        {
            throw new ArrayIndexOutOfBoundsException("Row and Column must be valid");
        }

        Set<Spot> spots = new HashSet<Spot>();
        findNeighbor(spots, row, col);
        return spots.size();
    }

    private void findNeighbor(Set<Spot> spots, int row, int col)
    {
        if (row < 0 || col < 0 || row >= grid.length || col >= grid.length)
            return;
        
        if (grid[row][col])
        { 
            //Do not search if already done so
            //All the neighbors of spots in the set are already been search.
            if (spots.add(new Spot(row, col)))
            {
                findNeighbor(spots, row + 1, col);
                findNeighbor(spots, row - 1, col);
                findNeighbor(spots, row, col + 1);
                findNeighbor(spots, row, col - 1);
            }
        }
    }
    
    public static void main(String[] args)
    {
        int row = 0, col = 0;
        try
        {
            row = Integer.parseInt(args[0]);
            col = Integer.parseInt(args[1]);
        }
        catch(NumberFormatException ne)
        {
            System.err.println("Row and column must be integers.");
            return;
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.err.println("Invalid input format.\nCorrect Usage: java cs310.Grid <row> <col>");
            return;
        }
        
        boolean[][] gridData = {
            {false, false, false, false, false, false, false, false, false, true},
            {false, false, false, true, true, false, false, false, false, true},
            {false, false, false, false, false, false, false, false, false, false},
            {false, false, false, false, true, false, false, true, false, false},
            {false, false, false, true, false, false, false, true, false, false},
            {false, false, false, false, false, false, false, true, true, false},
            {false, false, false, false, true, true, false, false, false, false},
            {false, false, false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false, false},
        };
        
        Grid mygrid = new Grid(gridData);        
        System.out.println(mygrid.count(row, col));
    }
}
