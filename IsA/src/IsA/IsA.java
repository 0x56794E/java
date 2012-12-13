package IsA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * CS480: Hw 3 (Intro to Graph Theory)
 * Is It or Isn't It?
-- -- -- ----- ---

The `Is A' relation follows some very simple rules.  For
example, if

	x is a Y
	Y is a Z
then
	x is a Z

Here x is a proper noun, like `Jill' or `Jack', denoting
a particular object, and Y and Z are generic nouns, like
`mammal' or `animal', denoting properties.  So the above
is like

	Jack is a mammal
	mammal is a animal

therefore

	Jack is a animal

all of which, of course, needs further editing to be
good English, but is good enough for the internal
thoughts of a computer.

You are given some data consisting of nothing but `is a'
relations in which all objects are named by single lower
case letters and all properties are named by single
upper case letters.  You are then asked questions like

	x is a Z?

which according to the above data has the answer `true'.
However, if you cannot deduce that something is true,
then it is not necessarily false, so given the above
data if you are asked

	x is a W?

the answer should be `unknown', and NOT `false'.


Input
-----

A sequence of test cases.

Each test begins with a sequence of data lines, each of
the form

	x is a Y

or the form

	X is a Y

where x can be replaced by any lower case letter and
X and Y can be replaced by any upper case letters.

Following the data lines is a sequence of question
lines, each of the form

	x is a Y?

or the form

	X is a Y?

where x can be replaced by any lower case letter and
X and Y can be replaced by any upper case letters.

The question lines are followed by a single end line
containing just `.', which ends the test case

The input terminates with an end of file.

To make input easy, each data line is exactly 8 charac-
ters, each question line exactly 9 characters, charac-
ters 2 through 7 of each line are ` is a ', characters
1 and 8 of each line are letters, and character 9 of
each question line is `?'.  Character 8 must be upper
case, while character 1 may be lower or upper case.

Also to make the algorithm easier, each test case will
be such that if you can deduce that `X is a Y' is true,
then you CANNOT also deduce that `Y is a X' is true.
Thus there are no `loops' in the deductions.


Example Input
------- -----

x is a P
P is a Q
Q is a R
R is a S
P is a M
M is a N
x is a P?
x is a S?
R is a P?
Q is a N?
.
B is a C
B is a D
B is a E
x is a D
C is a E?
E is a B?
x is a B?
.

Output
------

For each test case, a sequence of answer lines that
correspond to the test case question lines, followed by
a single end line containing nothing but `.'.

An answer line contains nothing but `true' or `unknown',
according to whether or not the statement in the corres-
ponding question line can be deduced from the data or
not.


Example Output
------- ------

true
true
unknown
unknown
.
unknown
unknown
unknown
.
 * 
 * @author      Vy Thuy Nguyen
 * @version     1.0 Feb 20, 2012 
 * Last modified: Feb 20, 2012
 */
public class IsA
{
    private class Graph
    {
        //Successors are stored in row N
        //Predessors are stored in column N
        boolean graph[][] = new boolean[52][52];

        public void addEdge(char ch1, char ch2) //ch1 IS-A ch2
        {
            graph[map(ch1)][map(ch2)] = true;
        }

        /**
         *
         * @param ch
         * @return an integer equivalent of the char according the following
         * mapping A => 0; ...; Z => 25; a => 26; ...; z => 51
         */
        int map(char ch)
        {
            if (ch > 'z' || ch < 'A' || ch > 'Z' && ch < 'a')
            {
                throw (new UnsupportedOperationException());
            }
            return (ch - (ch > 'Z' ? 'a' - 26 : 'A'));
        }

        char deMap(int num)
        {
            if (num < 0 || num > 52)
            {
                throw (new UnsupportedOperationException());
            }

            return (char) (num >= 26 ? num - 26 + 'a' : num + 'A');
        }

        /**
         *
         * @param ch1
         * @param ch2
         * @return true if ch1 IS-A ch2
         */
        public boolean isA(char ch1, char ch2)
        {
            boolean isa = false;
            if (graph[map(ch1)][map(ch2)])
            {
                return true;
            }
            else
            {
                for (int suc = 0; suc < 52; suc++)
                {
                    if (graph[map(ch1)][suc])
                    {
                        isa = isA(deMap(suc), ch2);
                        if (isa)
                        {
                            return isa;
                        }
                    }
                }
                return isa;
            }
        }

        /**
         * Test method
         */
        public void print()
        {
            int count = 0;
            for (int i = 0; i < 52; i++)
            {
                for (int j = 0; j < 52; j++)
                {
                    if (graph[i][j])
                    {
                        count++;
                        System.out.println(deMap(i) + " Is-A " + deMap(j));
                    }
                }
            }

            System.out.println("Total number of rules: " + count);
        }
    }
    private ArrayList<Entry> entries = new ArrayList<Entry>();

    private class Entry
    {

        Graph rule;
        ArrayList<String> question;

        public Entry()
        {
            rule = new Graph();
            question = new ArrayList<String>();
        }

        public void addRule(char ch1, char ch2)// throws ArrayIndexOutOfBoundsException
        {
            rule.addEdge(ch1, ch2);
        }

        public void addQuestion(char ch1, char ch2)
        {
            question.add(new String(ch1 + "" + ch2));
        }
    }

    public void loadInput()
    {
        Scanner sc = new Scanner(System.in);
        String line;
        entries.add(new Entry());

        while (sc.hasNextLine())
        {
            line = sc.nextLine();
            if (line.length() == 1 && sc.hasNext())
            {
                entries.add(new Entry());
                continue;
            }

            if (line.length() == 8) //a rule
            {
                entries.get(entries.size() - 1).addRule(line.charAt(0), line.charAt(7));
            }
            else if (line.charAt(line.length() - 1) == '?') //a question
            {
                entries.get(entries.size() - 1).addQuestion(line.charAt(0), line.charAt(7));
            }
        }
    }

    public void loadInput(String filePath)
    {
        try
        {
            BufferedReader input = new BufferedReader(new FileReader(filePath));
            entries.add(new Entry());
            while (input.ready())
            {
                String line = new String(input.readLine());
                if (line.length() == 1)
                {
                    entries.add(new Entry());
                    continue;
                }

                if (line.length() == 8) //a rule
                {
                    entries.get(entries.size() - 1).addRule(line.charAt(0), line.charAt(7));
                }
                else if (line.charAt(line.length() - 1) == '?') //a question
                {
                    entries.get(entries.size() - 1).addQuestion(line.charAt(0), line.charAt(7));
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("ERROR! Error while reading from input file.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        IsA isaObj = new IsA();
        isaObj.loadInput();

        //Print output
        for (int i = 0; i < isaObj.entries.size(); i++)
        {
            for (int j = 0; j < isaObj.entries.get(i).question.size(); j++)
            {
                System.out.println(
                        (isaObj.entries.get(i).rule.isA(
                        isaObj.entries.get(i).question.get(j).charAt(0),
                        isaObj.entries.get(i).question.get(j).charAt(1)) ? "true" : "unknown"));
            }

            System.out.println(".");
        }
    }
}