/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
/**
 *
 * @author Vy Thuy Nguyen
 */
public class MainFrame extends JFrame
{
    boolean fileExist;
    JMenuBar mb = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenu geMenu = new JMenu("German - English");
    JMenu feMenu = new JMenu("French - English");
    
    //fileMenu
    JMenuItem createList = new JMenuItem("Create the word list");
    JMenuItem search = new JMenuItem("Search for a word");
    JMenuItem quit = new JMenuItem("Quit");
    
    //geMenu
    JMenuItem geNouns = new JMenuItem("German to English Nouns");
    JMenuItem geVerbs = new JMenuItem("German to English Verbs");
    JMenuItem gePreps = new JMenuItem("German to English Prepositions");
    JMenuItem geOther = new JMenuItem("German to English Other");
    JMenuItem geAll = new JMenuItem("German to English All");
    //JMenuItem egNouns = new JMenuItem("English to German Nouns");
    //JMenuItem egVerbs = new JMenuItem("English to German Verbs");
    //JMenuItem egPreps = new JMenuItem("English to German Prepositions");
    //JMenuItem egOther = new JMenuItem("English to German Other");
    //JMenuItem egAll = new JMenuItem("English to German All");
    
    //feMenu
    JMenuItem feNouns = new JMenuItem("French to English Nouns");
    JMenuItem feVerbs = new JMenuItem("French to English Verbs");
    JMenuItem fePreps = new JMenuItem("French to English Prepositions");
    JMenuItem feOther = new JMenuItem("French to English Other");
    JMenuItem feAll = new JMenuItem("French to English All");
    //JMenuItem efNouns = new JMenuItem("English to French Nouns");
    //JMenuItem efVerbs = new JMenuItem("English to French Verbs");
    //JMenuItem efPreps = new JMenuItem("English to French Prepositions");
    //JMenuItem efOther = new JMenuItem("English to French Other");
    //JMenuItem efAll = new JMenuItem("English to French All");
    
    //helpmenu
    JMenu helpMenu = new JMenu("Help");
    JMenuItem chart = new JMenuItem("Key Chart");
    
    MainFrame mainFr;  
    WordList wordList;
    GetDataPanel getDataPn;
    InitPanel initPn = new InitPanel();
    searchPanel searchPn;
    
    public MainFrame()
    {
        setTitle("Trilingual Dictionary - Vy Thuy Nguyen");
        setSize(350,500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        add(initPn);
        this.addWindowListener(new WindowListener(){
        public void windowClosed(WindowEvent e){}      
        public void windowClosing(WindowEvent e)
        {
            if (JOptionPane.showConfirmDialog(null, "Are You Sure?", "Quitting", 
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                System.exit(0);
        } 
        public void windowOpened(WindowEvent e) {} 
        public void windowIconified(WindowEvent e) {} 
        public void windowDeiconified(WindowEvent e) {} 
        public void windowActivated(WindowEvent e) {} 
        public void windowDeactivated(WindowEvent e) {}       
        });

      
        
        //create shortcuts
        fileMenu.setMnemonic('F');
        geMenu.setMnemonic('G');
        feMenu.setMnemonic('E');
        createList.setAccelerator(KeyStroke.getKeyStroke('N', java.awt.event.InputEvent.CTRL_DOWN_MASK));
        search.setAccelerator(KeyStroke.getKeyStroke('F', java.awt.event.InputEvent.CTRL_DOWN_MASK)); 
        quit.setAccelerator(KeyStroke.getKeyStroke('Q', java.awt.event.InputEvent.CTRL_DOWN_MASK));
        
        //Menu
        fileMenu.add(createList);
        fileMenu.add(search);
        fileMenu.addSeparator();
        fileMenu.add(quit);
        mb.add(fileMenu);
     
        /*
        geMenu.add(geNouns);
        geMenu.add(geVerbs);
        geMenu.add(gePreps);
        geMenu.add(geOther);
        geMenu.add(geAll);
        //geMenu.addSeparator();
       // geMenu.add(egNouns);
       // geMenu.add(egVerbs);
       // geMenu.add(egPreps);
       /// geMenu.add(egOther);
        //geMenu.add(egAll);
        mb.add(geMenu);
        
        feMenu.add(feNouns);
        feMenu.add(feVerbs);
        feMenu.add(fePreps);
        feMenu.add(feOther);
        feMenu.add(feAll);
        //feMenu.addSeparator();
        //feMenu.add(efNouns);
        //feMenu.add(efVerbs);
        //feMenu.add(efPreps);
        ///feMenu.add(efOther);
        //feMenu.add(efAll);
        mb.add(feMenu);
         
         */
        
        helpMenu.add(chart);
        mb.add(helpMenu);
        
        this.setJMenuBar(mb);
        
        
        wordList = new WordList(this);
          //Check file existence
        File file = new File("C:\\Users\\Standard\\Desktop\\Vy Thuy Nguyen\\Final\\WordList.txt");
        fileExist = file.exists();
        if (fileExist)
        {
            createList.setEnabled(false);
            wordList.readFromFile();
        }
          
        
        //Event Handler
        MenuHandler handler = new MenuHandler();
        createList.addActionListener(handler);
        search.addActionListener(handler);
        quit.addActionListener(handler);
        geNouns.addActionListener(handler);
        geVerbs.addActionListener(handler);
        gePreps.addActionListener(handler);
        geOther.addActionListener(handler);
        geAll.addActionListener(handler);
        //egNouns.addActionListener(handler);
        //egVerbs.addActionListener(handler);
        //egPreps.addActionListener(handler);
        //egOther.addActionListener(handler);
        //egAll.addActionListener(handler);
        feNouns.addActionListener(handler);
        feVerbs.addActionListener(handler);
        fePreps.addActionListener(handler);
        feOther.addActionListener(handler);
        feAll.addActionListener(handler);
        //efNouns.addActionListener(handler);
        //efVerbs.addActionListener(handler);
        //efPreps.addActionListener(handler);
        //efOther.addActionListener(handler);
        //efAll.addActionListener(handler);
        chart.addActionListener(handler);
        
        mainFr = this;
    }
    
    private class InitPanel extends JPanel
    {
        protected InitPanel()
        {
            ImageIcon icon = new ImageIcon("C:\\Users\\Standard\\Desktop\\Vy Thuy Nguyen\\Final\\books.jpg");
            JLabel lb = new JLabel(icon);
            add(lb);
        }
    }
    private class MenuHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == createList)
            {
                getDataPn = new GetDataPanel(mainFr);
                mainFr.remove(mainFr.initPn);
                mainFr.add(getDataPn);
                mainFr.repaint();
                mainFr.validate();
                
            } 
            else if (e.getSource() == search)
            {
                if (!fileExist)
                {
                    JOptionPane.showMessageDialog(null, "Please Create a Word List First\n"
                            + "To Do So, Click File => Create the word list (CTRL+N)", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                mainFr.remove(mainFr.initPn);
                searchPn = new searchPanel(mainFr);
                mainFr.add(mainFr.searchPn);
                mainFr.repaint();
                mainFr.validate();
            } 
            else if (e.getSource() == quit)
            {
                if (JOptionPane.showConfirmDialog(null, "Are You Sure?", "Quitting", 
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                    System.exit(0);
                    
            } 
            else if (e.getSource() == geNouns)
            {
                if (!fileExist)
                {
                    JOptionPane.showMessageDialog(null, "Please Create a Word List First\n"
                            + "To Do So, Click File => Create the word list (CTRL+N)", "Error!", JOptionPane.ERROR_MESSAGE);
                }
                new ListWindow("GERMAN", "NOUNS").validate();
                
            } 
            else if (e.getSource() == geVerbs)
            {
                if (!fileExist)
                {
                    JOptionPane.showMessageDialog(null, "Please Create a Word List First\n"
                            + "To Do So, Click File => Create the word list (CTRL+N)", "Error!", JOptionPane.ERROR_MESSAGE);
                }                
            } else if (e.getSource() == gePreps)
            {
                if (!fileExist)
                {
                    JOptionPane.showMessageDialog(null, "Please Create a Word List First\n"
                            + "To Do So, Click File => Create the word list (CTRL+N)", "Error!", JOptionPane.ERROR_MESSAGE);
                }                
            } else if (e.getSource() == geOther)
            {
                if (!fileExist)
                {
                    JOptionPane.showMessageDialog(null, "Please Create a Word List First\n"
                            + "To Do So, Click File => Create the word list (CTRL+N)", "Error!", JOptionPane.ERROR_MESSAGE);
                }                
            } else if (e.getSource() == geAll)
            {
                 if (!fileExist)
                {
                    JOptionPane.showMessageDialog(null, "Please Create a Word List First\n"
                            + "To Do So, Click File => Create the word list (CTRL+N)", "Error!", JOptionPane.ERROR_MESSAGE);
                }               
            }  else if (e.getSource() == feNouns)
            {
                if (!fileExist)
                {
                    JOptionPane.showMessageDialog(null, "Please Create a Word List First\n"
                            + "To Do So, Click File => Create the word list (CTRL+N)", "Error!", JOptionPane.ERROR_MESSAGE);
                }                
            } else if (e.getSource() == feVerbs)
            {
                if (!fileExist)
                {
                    JOptionPane.showMessageDialog(null, "Please Create a Word List First\n"
                            + "To Do So, Click File => Create the word list (CTRL+N)", "Error!", JOptionPane.ERROR_MESSAGE);
                }                
            } else if (e.getSource() == fePreps)
            {
                if (!fileExist)
                {
                    JOptionPane.showMessageDialog(null, "Please Create a Word List First\n"
                            + "To Do So, Click File => Create the word list (CTRL+N)", "Error!", JOptionPane.ERROR_MESSAGE);
                }                
            } else if (e.getSource() == feOther)
            {
                if (!fileExist)
                {
                    JOptionPane.showMessageDialog(null, "Please Create a Word List First\n"
                            + "To Do So, Click File => Create the word list (CTRL+N)", "Error!", JOptionPane.ERROR_MESSAGE);
                }                
            } else if (e.getSource() == feAll)
            {
                if (!fileExist)
                {
                    JOptionPane.showMessageDialog(null, "Please Create a Word List First\n"
                            + "To Do So, Click File => Create the word list (CTRL+N)", "Error!", JOptionPane.ERROR_MESSAGE);
                }                
            } 
            else if (e.getSource() == chart)
            {
                new KeyChart();
            }            
        }
    }
    
    
    private class KeyChart extends JFrame
    {
        public KeyChart()
        {
        super("Key Chart");
        this.setSize(300,350);
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(mainFr);
        setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
   

        JPanel pn = new JPanel();
        // JTextfield
        JTextArea txt = new JTextArea();
        txt.setEditable(false);
        txt.setPreferredSize(new Dimension(250,300));
        txt.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));

        txt.setText("F1:  " + (char)231 + "      F2:  " + (char)199 + "      F3:  " + (char)232 + "\n\n" +
                    "F4:  " + (char)200 + "      F5:  " + (char)249 + "      F6:  " + (char)217 + "\n\n" +
                    "F7:  " + (char)224 + "      F8:  " + (char)192 + "      F9:  " + (char)233 + "\n\n" +
                    "F10: " + (char)201 + "      F11: " + (char)234 + "      F12: " + (char)202 + "\n\n==============================\nCtrl + \n" +
                    "F1:  " + (char)228 + "      F2:  " + (char)196 + "      F3:  " + (char)233 + "\n\n" +
                    "F4:  " + (char)201 + "      F5:  " + (char)246 + "      F6:  " + (char)214 + "\n\n" +
                    "F7:  " + (char)252 + "      F8:  " + (char)220);

        pn.add(txt);
        this.add(pn);
        this.setVisible(true);




    }
    }
}
