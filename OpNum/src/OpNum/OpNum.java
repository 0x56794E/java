package OpNum;

import java.util.HashMap;
import java.util.Scanner;

/**
 * CS480 - Hw1: Pagong Number (Dynamic Programming)
 * -------------
 * Description:
 * Optimum Number Representation
------- ------ --------------
The inhabitants of the ancient tribe of Pagong had in-
credible numerical dexterity.  Their numeral system was
not decimal, but reverse-polish-notation based.  Each
number was represented by a sequence of digits and oper-
ations.  The `digits' they used were surprisingly simi-
lar to those we use today:

   one two three four five six seven eight nine ten

represented our numbers 1 through 10, respectively.  To
build numbers larger than ten, they composed other num-
bers with the operations * or +, respectively, using
reverse polish notation, where the operation follows the
two operands.  Thus, one representation for our number
13 is

   eight five +

Another representation is

   one two + two * two * one +

Because there were so many representations of the same
number, `high' Pagong dictates that the canonical
representation of a number be the shortest possible
representation, and where there were ties in length,
that the earliest lexicographically shall be the canoni-
cal number.  (Their lexicographical ordering was the
same as ASCII, amazingly).  The length includes all
characters, including a single space that follows each
digit or operation but the last.  Thus, the shortest
possible representations of 13 are:

   three ten +
   four nine +
   six seven +
   seven six +
   nine four +
   ten three +

and of these, 

   four nine +

is the earliest and thus canonical representation of 13.

Your task is to translate numbers from our Arabic repre-
sentation to the canonical Pagong representation.  Each
input number will be on a line by itself, and the output
should consist of lines each containing just one canon-
ical Pagong number.  All numbers will be 1000 or less,
and there will be fewer than five hundred numbers in the
input set.  Input ends with an end of file.

Sample input:

5
101
64

Sample output:

five
one ten ten * +
eight eight *
 * 
 * 
 * @author      Vy Thuy Nguyen
 * @version     1.0 Jan 30, 2012 
 * Last modified: Feb 11, 2012
 */
public class OpNum
{
    /* Map containing all results that have already been computed */
    private static final HashMap<Integer, String> ResultMap = new HashMap<Integer, String>();
    static
    {
        /* The map originally holds the pagong digits from 1 through 10 */
        ResultMap.put(0, "No Such Digit in Pagong Numbering System!"); // no such digit
        ResultMap.put(1, "one");
        ResultMap.put(2, "two");
        ResultMap.put(3, "three");
        ResultMap.put(4, "four");
        ResultMap.put(5, "five");
        ResultMap.put(6, "six");
        ResultMap.put(7, "seven");
        ResultMap.put(8, "eight");
        ResultMap.put(9, "nine");
        ResultMap.put(10, "ten");
    }

    /* Current shortest combination */
    static String currentMin = null;

   /**
    *
    * @param str1
    * @param str2
    * @return the min of the two strings (both in terms of length and lexical order)
    */
    private static String min (String str1, String str2)
    {
        str1 = str1.trim();
        str2 = str2.trim();
        if (str1.length() < str2.length())
            return str1;
        else if (str2.length() < str1.length())
            return str2;
        else
            return (str1.compareTo(str2) <= 0 ? str1 : str2);
    }

    /**
     * @param operand1
     * @param operand2
     * @param operator
     * @return the shortest possible combination of two operands
     */
    private static String generateMin (String operand1, String operand2, String operator)
    {
        if (operand1.equals(""))
            return operand2;
        else if (operand2.equals(""))
            return operand1;

        String t1 = operand1 + " " + operand2 + " " + operator;
        String t2 = operand2 + " " + operand1 + " " + operator;
        return min (t1, t2);
    }

    /**
     * This method finds all possible combinations
     * @param num
     * @param divisor
     * @param builder
     * @param addedNum
     * @param oriNum
     * @param oriDivisor
     */
    private static void findCombo (int num, int divisor, StringBuilder builder, String addedNum, int oriNum, int oriDivisor)
    {
        if (num > 10)
        {
            if (num % divisor == 0)
            {
                String tempCur = currentMin;
                currentMin = null;
                findPagongForm(divisor);
                currentMin = tempCur;
                builder = new StringBuilder(generateMin(builder.toString(), ResultMap.get(divisor), "*"));
                String temp = ResultMap.get(num / divisor);
                if (temp != null)
                {
                    String res = generateMin (temp, builder.toString(), "*");
                    String min = generateMin (res, addedNum, "+");
                    currentMin = currentMin == null ? min : min(currentMin, min);
                    if (oriDivisor > 2 && oriNum > 10)
                        findCombo(oriNum, oriDivisor - 1, new StringBuilder(), addedNum, oriNum, oriDivisor - 1);
                }
                else
                    findCombo (num / divisor, divisor, builder, addedNum, oriNum, oriDivisor);
            }
            else
            {
                String temp = ResultMap.get(num);
                if (temp != null)
                {
                    String res = generateMin(temp, builder.toString(), "*");
                    String min = generateMin(res, addedNum, "+");
                    currentMin = currentMin == null ? min : min(currentMin, min);
                }
                else
                {
                    if (divisor > 2)
                        findCombo(num, divisor - 1, builder, addedNum, oriNum, oriDivisor);
                }
            }
        }
        else
        {
            String result = generateMin(ResultMap.get(num), builder.toString(), "*");
            String min = generateMin(result, addedNum, "+");
            currentMin = currentMin == null ? min : min(currentMin, min);

            if (oriDivisor > 2 && oriNum > 10)
                findCombo(oriNum, oriDivisor - 1, new StringBuilder(), addedNum, oriNum, oriDivisor - 1);
        }
    }

    /**
     *
     * @param num
     * @return the Pagong form of the given number
     */
    private static String findPagongForm (int num)
    {
        currentMin = ResultMap.get(num);
        if (currentMin != null) return currentMin;

        for (int n = 10; n > 0; --n)
        {
            String temp = ResultMap.get(num - n);
            if (temp != null)
            {
                String min = generateMin(temp, ResultMap.get(n), "+");
                if (currentMin == null)
                    currentMin = min;
                else
                    currentMin = min(min, currentMin);
            }
            else
                findCombo(num - n, (int)Math.sqrt(num - n), new StringBuilder(), ResultMap.get(n), num - n, (int)Math.sqrt(num - n));
        }
        findCombo (num, (int)Math.sqrt(num), new StringBuilder(), "", num, (int)Math.sqrt(num));

        ResultMap.put(num, currentMin);
        return currentMin;
    }

    /**
     * Compute the Pagong form for all numbers up to limit
     * @param limit
     */
    public static void init(int limit)
    {
        for (int n = 11; n <= limit; ++n)
            findPagongForm(n);
    }

    public static void main (String args[])
    {
        int num;
        Scanner input = new Scanner(System.in);
        while (input.hasNextInt())
        {
            num = input.nextInt();
            init(num - 1);
            System.out.println(findPagongForm(num));
        }
    }
}
