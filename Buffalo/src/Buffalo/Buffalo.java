/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Buffalo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Buffalo Finder ------- ------ Description: Ranchy Flatman is a buffalo
 * rancher who has lived on the Plains for decades. He has a ranch on which he
 * keeps buffalo in a field with three sides. Each side is bounded by a
 * perfectly straight fence which actually extends in both directions well
 * beyond Ranchy's field, and which serves as a boundary between various fields
 * owned by various other people. In all there are 7 fields, Ranchy's triangular
 * field, and 6 neighboring fields.
 *
 * Sometimes Ranchy loses track of a buffalo. Then he sends his trusty Beagle
 * Issy (`I Smell You') out to find the errant buffalo. Issy wears a GPS
 * receiver and a radio, and this sends Issy's position back to Ranchy. When
 * Issy finds the buffalo, she stops, and Ranchy then knows the buffalo's GPS
 * coordinates. If the buffalo has gotten lost in Ranchy's field, Ranchy goes
 * out to find the buffalo, but if the buffalo is in a neighbor's field, Ranchy
 * must call up the neighbor who will go with Ranchy to retrieve the Buffalo and
 * Issy.
 *
 * In order to make this work, Ranchy's daughter has worked out the following
 * naming system for fields, and program- med the family computer to tell Ranchy
 * which field Issy is in. The corners of Ranchy's triangular field are given
 * the names 1, 2, and 3 in clockwise order, and the fences are given names 12,
 * 23, and 31 in clockwise order. A given field can be either to the left or
 * right of a given fence. So we can give a field a name of the form
 *
 * D12 D23 D31
 *
 * where Dxy is `L' if the field is to the left of fence xy and `R' if the field
 * is to the right of fence xy when traveling in the direction from x to y. Thus
 * Ranchy's triangular field is named RRR and if you cross fence 12 from this
 * field you enter field LRR.
 *
 * To find out which field Issy is in, the GPS coordinates of Issy and the
 * corners 1, 2, and 3 are used. The GPS coordinates are treated as integer
 * coordinates of points in a flat plane.
 *
 * Due to an unfortunate accident, Ranchy's daughter's computer program has been
 * lost, and as she is off at college and in the middle of exams, you have been
 * tasked to replace it.
 *
 * Input -----
 *
 * For each test case, one line of the form
 *
 * x1 y1 x2 y2 x3 y3 xi yi
 *
 * where (x1,y1), (x2,y2), (x3,y3) are the coordinates of the corners 1, 2, and
 * 3, respectively, and (xi,yi) are Issy's coordinates. All coordinates are
 * integers. Input ends with an end of file.
 *
 * For simplicity, the input will be such that Issy is never exactly on a fence.
 *
 * Output ------ For each test case one line containing just the name of the
 * field containing Issy.
 *
 * Sample Input ------ ----- -3 -3 0 6 3 -3 0 0 -3 -3 0 6 3 -3 -5 0 -3 -3 0 6 3
 * -3 0 10 -3 -3 0 6 3 -3 5 0 -3 -3 0 6 3 -3 10 -4 -3 -3 0 6 3 -3 0 -4 -3 -3 0 6
 * 3 -3 -10 -4
 *
 * Sample Output ------ ------ RRR LRR LLR RLR RLL RRL LRL
 *
 * @author Vy Thuy Nguyen
 * @version 1.0 Feb 21, 2012 Last modified: Feb 21, 2012
 */
public class Buffalo
{

    ArrayList<Entry> entries;

    public Buffalo()
    {
        entries = new ArrayList<Entry>();
    }

    /**
     * Represents ONE set of input. An entry has: the three points p1, p2 and p3
     * representing three corners, and pi representing Issy's coordinates.
     *
     * @author Vy Thuy Nguyen
     * @version 1.0 Feb 21, 2012 
     * Last modified: Feb 21, 2012
     */
    private class Entry
    {
        private Point p1;
        private Point p2;
        private Point p3;
        private Point pi;
        private char output[];

        public Entry(int coor[])
        {
            this.p1 = new Point(coor[0], coor[1]);
            this.p2 = new Point(coor[2], coor[3]);
            this.p3 = new Point(coor[4], coor[5]);
            this.pi = new Point(coor[6], coor[7]);

            output = new char[3];
            try
            {
                Line line12 = new Line(p1, p2);
                output[0] = (line12.isCoplanar(pi, p3) ? 'R' : 'L');
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            try
            {
                Line line23 = new Line(p2, p3);
                output[1] = (line23.isCoplanar(pi, p1) ? 'R' : 'L');
            }
            catch (Exception e)
            {
                e.printStackTrace();

            }
            try
            {
                Line line31 = new Line(p3, p1);
                output[2] = (line31.isCoplanar(pi, p2) ? 'R' : 'L');
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public String toString()
        {
            return output[0] + "" + output[1] + output[2];
        }
    }

    /**
     * Print output
     */
    public void printOutput()
    {
        for (int i = 0; i < entries.size(); i++)
        {
            System.out.println(entries.get(i));
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

        while (sc.hasNextInt())
        {
            int coor[] = new int[8];
            count = 0;
            line = sc.nextLine();
            Scanner sc2 = new Scanner(line);
            while (sc2.hasNextInt() && count < 8)
            {
                coor[count++] = sc2.nextInt();
            }

            entries.add(new Entry(coor));
        }
    }

    /**
     * Read input from file
     */
    public void readInput(String filePath)
    {
        String line;
        int count = 0;
        try
        {
            BufferedReader input = new BufferedReader(new FileReader(filePath));
            while (input.ready())
            {
                count = 0;
                int coor[] = new int[8];
                line = input.readLine();
                Scanner sc = new Scanner(line);
                while (sc.hasNextInt())
                {
                    coor[count] = sc.nextInt();
                    count++;
                }
                entries.add(new Entry(coor));
            }
        }
        catch (Exception e)
        {
            System.out.println("ERROR! Error while reading from input file.");
            e.printStackTrace();
        }
    }
}
