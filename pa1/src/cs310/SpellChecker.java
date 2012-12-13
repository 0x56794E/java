/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cs310;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.TreeSet;

/**
 *
 * Date Created: Feb 10, 2012
 * Last Modified: Feb 17, 2012
 * @author Vy Thuy Nguyen
 */
public class SpellChecker
{
    private Dictionary dictionary;
    private int lineNumber;
    String path1;
    String path2;
    public SpellChecker(String p1, String p2)
    {
        path1 = p1;
        path2 = p2;
        lineNumber = 0;
        dictionary = new Dictionary(path1, path2);
    }

    public static void main(String args[])
    {
        if (args.length != 3)
        {
            System.out.println("Invalid Number of Arguments!\nRequired: 3;\nFound: " + args.length);
            System.exit(1);
        }

        SpellChecker sp = new SpellChecker(args[1], args[2]);
        System.out.println("Hello there! Welcome to SpellChecker!");
        String inputFile = args[0];
        BufferedReader input;
        String line;
        String words[];
        TreeSet<String> suggestion = new TreeSet<String>();
        System.out.println("Prepare to read input");
        try
        {
            input = new BufferedReader(new FileReader(inputFile));
            while (input.ready())
            {
                line = input.readLine();

                if (line.length() > 0)
                {
                    words = line.split("[^\\w']+");
                    sp.lineNumber++;
                }
                else continue;

                for (int n = 0; n < words.length; n++)
                {
                    if (!sp.dictionary.contains(words[n]))
                    {
                        suggestion.clear();
                        System.out.println("Line #" + sp.lineNumber);
                        System.out.println("The following word was mispelled: " + words[n]);
                        System.out.print("Suggestion(s): ");

                        suggestion.addAll(sp.addOneChar(words[n]));
                        suggestion.addAll(sp.removeOneChar(words[n]));
                        suggestion.addAll(sp.exchangeChar(words[n]));
                        if (suggestion.isEmpty())
                            System.out.print("<No Suggestion Available>");
                        else
                        {
                            Iterator iter = suggestion.iterator();
                            while (iter.hasNext())
                                System.out.print(iter.next() + "   ");
                        }
                        System.out.println("\n=============");
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error while reading input from file at the following path:\n" + inputFile);
            e.printStackTrace();
        }
    }

    public TreeSet<String> addOneChar(String word)
    {
        TreeSet<String> output = new TreeSet<String>();
        StringBuilder temp;

        for (int i = 0; i <= word.length(); i++)
            for (int j = 'a'; j < 'z'; j++)
            {
                temp = new StringBuilder(word);
                temp.insert(i, (char)j);
                if (dictionary.contains(temp.toString()))
                    output.add(temp.toString());
            }
        return output;
    }

    public TreeSet<String> removeOneChar(String word)
    {
        TreeSet<String> output = new TreeSet<String> ();
        StringBuilder temp;

        for (int i = 0; i < word.length(); i++)
        {
            temp = new StringBuilder(word);
            temp.deleteCharAt(i);
            if (dictionary.contains(temp.toString()))
                output.add(temp.toString());
        }

        return output;
    }

    public TreeSet<String> exchangeChar(String word)
    {
        TreeSet<String> output = new TreeSet<String>();
        StringBuilder temp;
        char ch;

        for (int i = 0; i < word.length() - 1; i++)
        {
            temp = new StringBuilder(word);
            ch = temp.charAt(i);
            temp.deleteCharAt(i);
            temp.insert(i + 1, ch);
            if (dictionary.contains(temp.toString()))
                output.add(temp.toString());
        }
        return output;
    }
}
