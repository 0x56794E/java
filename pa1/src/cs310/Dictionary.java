/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cs310;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.TreeSet;

/**
 *
 * Date Created: Feb 10, 2012
 * Last Modified: Feb 17, 2012
 * @author Vy Thuy Nguyen
 */
public class Dictionary
{
    //private BinaryTree wordList;
    private TreeSet<String> wordList;
    private BufferedReader input;
    private String file1;
    private String file2;

    public Dictionary(String file1, String file2)
    {
        wordList = new TreeSet<String>();
        this.file1 = file1;
        this.file2 = file2;
        loadData();
    }

    /**
     *
     * @param word
     * @return true if the word is found in the dictionary
     */
    public boolean contains(String word)
    {
        return wordList.contains(word.toLowerCase());
    }

    private void loadData()
    {
        System.out.println("Loading ...");
        try
        {
            String str;
            //Load words from file 1
            input = new BufferedReader(new FileReader(file1));
            while (input.ready())
            {
                str = input.readLine();
                //System.out.println("Current word: " + str.toLowerCase());
                wordList.add(str.toLowerCase());
            }
            input.close();

            //Load words from file 2
            input = new BufferedReader(new FileReader(file2));
            while (input.ready())
            {
                str = input.readLine();
                //System.out.println("Current word: " + str);
                wordList.add(str.toLowerCase());
            }
            input.close();
        }
        catch (Exception e)
        {
            System.out.println("Error while reading loading words to dictionary from input file:\n");
        }

    }

}
