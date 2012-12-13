package turingmachine;

/**
 *
 * @author Vy Thuy Nguyen
 */
public class SymbolMap {

    private String alphabet;
    // Construct a SymbolsMap.  A string containing the characters
    // used to represent the Symbols.  The first character represents
    // the blank symbol.
    public SymbolMap(String symbolCharSeq)
    {
        alphabet = new String(symbolCharSeq);
    }

    public char getBlank()
    {
        return alphabet.charAt(0);
    }

    public boolean isInAlphabet(char sym)
    {
        boolean in = false;
        for (int n = 0; n < alphabet.length(); n++)
            if (sym == alphabet.charAt(n))
                in = true;
        return in;
    }


    public int getSymbolCount()
    {
        return alphabet.length();
    }

    //given a symbol index retrun the corresponding character
    public char getSymbol(int index)
    {
        return alphabet.charAt(index);
        
    }

    //given a symbol char return the corresponding index
    public int getSymbol(char symbolChar)
    {
        return alphabet.indexOf(symbolChar);
        
    }
}
