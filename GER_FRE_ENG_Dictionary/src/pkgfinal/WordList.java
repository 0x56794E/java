/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal;
import java.io.*;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
/**
 *
 * @author Vy Thuy Nguyen
 */
class Data
{
        String english;
        String part;
        String articleF;        
        String french;
        String articleG;
        String german;
        public Data(String eng, String par, String aF, String fre, String aG, String ger)
        {
            articleG = aG;
            articleF = aF;
            english = eng;
            german = ger;
            french = fre;
            part = par;
        }
}

class Node
{
        Data data;
        Node next;
        
        public Node(Data newData)
        {
            data = newData;
            next = null;
        }
}

public class WordList 
{
    int size = 0;
    Node root = new Node(null);
    MainFrame mainFr;
    
    public WordList(MainFrame fr)
    {
        mainFr = fr;
    }
    public void addNode(Node newNode)
    {
        Node iter = root;
        if (root.next == null)
        {
            root.next = newNode;
            size++;
            return;
        }
            
        while (iter.next != null)
            iter = iter.next;
        
        
        iter.next = newNode;
        size++;
    }
    
    public boolean isEmpty()
    {
        if (root.next == null)
            return true;
        else 
            return false;
    }
    public void sortGerman()
    {
        
    }
    
    public void writeToFile()
    {
        try
        {
            FileWriter outFile = new FileWriter("WordList.txt");
            PrintWriter out = new PrintWriter(outFile);
            
            Node iter = root.next;
            
            while(iter != null)
            {
                if (iter.data.german == null) //french word
                {
                    out.print("F#" + iter.data.english);
                    out.print("#" + iter.data.part);
                    out.print("#" + (iter.data.articleF == null ? " " : iter.data.articleF));
                    out.println("#" + iter.data.french);
                }
                else
                {
                    out.print("G#" + iter.data.english);
                    out.print("#" + iter.data.part);
                    out.print("#" + (iter.data.articleG == null ? " " : iter.data.articleG));
                    out.println("#" + iter.data.german);
                }
                iter = iter.next;
            }
            out.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void readFromFile()
    {
        int count = 1;
        try
        {
            FileInputStream fstream = new FileInputStream("WordList.txt");
            DataInputStream da = new DataInputStream(fstream);
            BufferedReader in = new BufferedReader(new InputStreamReader(da));
            String line;
            while ((line = in.readLine()) != null)
            {
                StringTokenizer st = new StringTokenizer(line, "#");
                String lang = st.nextToken();
                String en = st.nextToken();
                String pt = st.nextToken();
                String a = st.nextToken();
                String fg = st.nextToken();
                if(lang.compareTo("F") == 0) //french
                    addNode(new Node(new Data(en, pt, a, fg, null, null)));
                else //german
                    addNode(new Node(new Data(en, pt, null, null, a, fg)));
            }
            
             
        }
        catch (EOFException eofe)
        {
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public String[] getList()
    {
        java.util.Vector <String> temp = new java.util.Vector();
        
        //get eng
        Node iter = root.next;
        while (iter != null)
        {
            temp.add(iter.data.english);
            iter = iter.next;
        }
        
        //get fre
        iter = root.next;
        while (iter != null)
        {
            //JOptionPane.showConfirmDialog(null, "Start loading Fre");
            if (iter.data.french == null)
            {
                iter = iter.next;
                continue;
            }
            temp.add(iter.data.french);
            iter = iter.next;
            //JOptionPane.showMessageDialog(null, "Fre");
        }
        //get ger
        iter = root.next;
        while (iter != null)
        {
            
            //JOptionPane.showConfirmDialog(null, "Start loading Ger");
            if (iter.data.german == null)
            {
                iter = iter.next;
                continue;
            }
            temp.add(iter.data.german);
            iter = iter.next;
            //JOptionPane.showMessageDialog(null, "Ger");
        }
        
     
        String[] arr = new String[temp.size()];
        temp.toArray(arr);
        
      
        return arr;
    }
    
    public String getEngDefG(String ger)
    {
        String def = "";
        Node iter = root.next;
        while (iter != null)
        {
            if (iter.data.german.compareToIgnoreCase(ger) == 0)
            {
                def = iter.data.english;
                return def;
            }
            iter = iter.next;
        }       
        return def;       
    }
    
    public String getEngDefF(String fre)
    {
        String def = "";
        Node iter = root.next;
        while (iter != null)
        {
            if (iter.data.french.compareToIgnoreCase(fre) == 0)
            {
                def = iter.data.english;
                return def;
            }
            iter = iter.next;
        }       
        return def; 
    }
    
    public String getGerDef(String eng)
    {
        String def = "";
        Node iter = root.next;
        while (iter != null)
        {
            if (iter.data.english.compareToIgnoreCase(eng) == 0)
            {
                def =  (iter.data.articleG == null ? "" : iter.data.articleG + " ") + iter.data.german;
                return def;
            }
            iter = iter.next;
        }       
        return def;         
    }
    
    public String getFreDef(String eng)
    {
        String def = "";
        Node iter = root.next;
        while (iter != null)
        {
            if (iter.data.english.compareToIgnoreCase(eng) == 0)
            {
                def =  (iter.data.articleF == null ? "" : iter.data.articleF + " ") + iter.data.french;
                return def;
            }
            iter = iter.next;
        }       
        return def;         
    }
    
    public String[] getGermanNouns()
    {
        java.util.Vector<String> v = new java.util.Vector();
        Node iter = root.next;
        
        while (iter != null)
        {
            if (iter.data.german == null)
            {
                iter = iter.next;
                continue;
            }
            else if (iter.data.part.compareToIgnoreCase("nous") == 0)
            {
                v.add(iter.data.german);
                iter = iter.next;
            }
        }
        String[] arr = new String[v.size()];
        v.toArray(arr);
        return arr;
    }
    public boolean isEnglish(String word)
    {
        boolean eng = false;
        Node iter = root.next;
        while (iter != null)
        {
            if (iter.data.english == null)
            {
                iter = iter.next;
                continue;
            }
            if (iter.data.english.compareToIgnoreCase(word) == 0)
                return true;
            iter = iter.next;
        }
        return eng;
    }
    
        public boolean isFrench(String word)
    {
        boolean fre = false;
        Node iter = root.next;
        while (iter != null)
        {
            if (iter.data.french == null)
            {
                iter = iter.next;
                continue;
            }
            if (iter.data.french.compareToIgnoreCase(word) == 0)
                return true;
            iter = iter.next;
        }
        return fre;
    }
        
    public boolean isGerman(String word)
    {
        boolean ger = false;
        Node iter = root.next;
        while (iter != null)
        {
            if (iter.data.german == null)
            {
                iter = iter.next;
                continue;
            }
            if (iter.data.german.compareToIgnoreCase(word) == 0)
                return true;
            iter = iter.next;
        }
        return ger;
    }
        
    public void printDef(String word)
    {
        if (isEnglish(word))
        {
            String fre = getFreDef(word);
            String ger = getGerDef(word);
            
            mainFr.searchPn.defPn.txArea.setText("\nEnglish: ");
            mainFr.searchPn.defPn.txArea.append(word);
            mainFr.searchPn.defPn.txArea.append("\n\n\n");
            mainFr.searchPn.defPn.txArea.append((fre.compareToIgnoreCase("null") == 0 ? "German: " + ger : "French: " + fre));           
        }
        else if (isGerman(word))
        {
            JOptionPane.showMessageDialog(null, "make it here");
            mainFr.searchPn.defPn.txArea.setText("\nGerman: ");
            mainFr.searchPn.defPn.txArea.append(word);
            mainFr.searchPn.defPn.txArea.append("\n\n\n");
            
            mainFr.searchPn.defPn.txArea.append("English: ");
            mainFr.searchPn.defPn.txArea.append(getEngDefG(word));
            
        }
        else if (isFrench(word))
        {
            mainFr.searchPn.defPn.txArea.setText("\nFrench: ");
            mainFr.searchPn.defPn.txArea.append(word);
            mainFr.searchPn.defPn.txArea.append("\n\n\n");
            
            mainFr.searchPn.defPn.txArea.append("English: ");
            mainFr.searchPn.defPn.txArea.append(getEngDefF(word));
            
        }
        else 
        {
            JOptionPane.showMessageDialog(null, "Word Not Found!\nCheck your spelling\n"
                    + "or try entering the word WITHOUT the article", "Not Found!", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
   
}
