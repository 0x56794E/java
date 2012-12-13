package cs310;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Last modification: Mar 25, 2012
 * Date created: Mar 22, 2012
 * @author Vy Thuy Nguyen
 */
public class Permute {    
    public static void main(String[] args)
    {
        String str = "";
        try
        {
            str = args[0];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.err.println("Invalid input format.\nCorrect Usage: java cs310.Permute <Your String Here>");
            return;
        }
                
        Set <String> result = permute(str);
       
        System.out.println("Number of Permutations: " + result.size());
        System.out.println(result.toString());
        
    }
    
    public static Set<String> permute (String str)
    {
        Set<String> storage = new HashSet<String>();
        doPermute(storage, "", str);
        return storage;
    }
    

    
    private static void doPermute(Set<String> permutations, String prefix, String suffix)
    {    
        //System.out.println("prefix = " + prefix + "; suffix = " + suffix);
        if (suffix.length() < 2)
        {
            permutations.add(prefix + suffix);
        }
        else
        {
            for (int i = 0; i < suffix.length(); i++)
            {   
                //swapping the first char and any other char in the str
                String newPrefix = prefix + suffix.charAt(i); //add charAt(i) from suffix to prefix
                String newSuffix = suffix.substring(0, i) + suffix.substring(i + 1); //take charAt(i) out from suffix
                
                doPermute(permutations, newPrefix, newSuffix);
            }
        }
        
    }
}
