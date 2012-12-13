/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
/**
 *
 * @author Vy Thuy Nguyen
 */
public class GetDataPanel extends JPanel
{
    MainFrame mainFr;
    JButton submitBt = new JButton("Submit");
    JButton finishBt = new JButton("Finish");
      
    JTextField aField = new JTextField(2);
    JTextField fgField = new JTextField(15);
    JComboBox langBox = new JComboBox();
    JTextField eField = new JTextField(20);
    JRadioButton noun = new JRadioButton("Noun");
    JRadioButton verb = new JRadioButton("Verb");
    JRadioButton prep = new JRadioButton("Preposition");
    JRadioButton other = new JRadioButton("Other");
    ButtonGroup group = new ButtonGroup();
    public GetDataPanel(MainFrame fr)
    {
        mainFr = fr;
        add(new JLabel("PLEASE ENTER WORDS"));
        setLayout(new BorderLayout());
        
        //center pn
        JPanel centerPn = new JPanel();
        centerPn.setLayout(new GridLayout(3,1));
            //pn1 - german
        JPanel pn1 = new JPanel();
        langBox.addItem("French");
        langBox.addItem("German");
        
        aField.setToolTipText("Enter article here (if applicable)");
        fgField.setToolTipText("Enter the French/German word here");
        pn1.add(langBox);
        pn1.add(aField);
        pn1.add(fgField);
        centerPn.add(pn1);
        
      
        
            //pn3 - eng
        JPanel pn3 = new JPanel();
        pn3.add(new JLabel("English"));
        eField.setToolTipText("Enter the English word here");
        pn3.add(eField);
        centerPn.add(pn3);
        
            //pn3 - part of speech
        JPanel pn4 = new JPanel();
        group.add(noun);
        group.add(verb);
        group.add(prep);
        group.add(other);
        pn4.add(noun);
        pn4.add(verb);
        pn4.add(prep);
        pn4.add(other);
        centerPn.add(pn4);
        
        add(centerPn, BorderLayout.CENTER);
        
        //button panel
        JPanel btPn = new JPanel();
        submitBt.setToolTipText("Click 'Submit' to submit a word.");
        finishBt.setToolTipText("Click 'Finish' to finish creating the list");
        btPn.add(submitBt);
        btPn.add(finishBt);
        add(btPn, BorderLayout.SOUTH);
        
        //handler
        GetDataHandler handler = new GetDataHandler();
        submitBt.addActionListener(handler);
        finishBt.addActionListener(handler);
        
        WordKeyListener key = new WordKeyListener();
        fgField.addKeyListener(key);
    }
  
    private class GetDataHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == submitBt)
            {
                String p = "";
                if (noun.isSelected())
                   p = "Noun";
                else if (verb.isSelected())
                   p = "Verb";
                else if (prep.isSelected())
                   p = "Preposition";
                else if (other.isSelected())
                   p = "Other";
                if (langBox.getSelectedIndex() == 0) //French
                    mainFr.wordList.addNode(new Node(new Data(eField.getText(), p,
                            (noun.isSelected() ? aField.getText() : null), fgField.getText(), null, null)));
                else //German
                    mainFr.wordList.addNode(new Node(new Data(eField.getText(), p,
                            null, null, (noun.isSelected() ? aField.getText() : null), fgField.getText())));
              //  mainFr.wordList.addNode(new Node(new Data(eField.getText(), 
                //        p, afField.getText(),fField.getText(),agField.getText(),gField.getText())));
                eField.setText("");
                aField.setText("");
                fgField.setText("");
                
            } else 
            {
                if(mainFr.wordList.isEmpty());
                else
                {
                    mainFr.wordList.writeToFile();
                    mainFr.remove(mainFr.getDataPn);
                    mainFr.add(mainFr.initPn);
                    mainFr.fileExist = true;
                    mainFr.createList.setEnabled(false);
                    mainFr.repaint();
                    mainFr.validate();
                }
                    
            }
        }
    }   
    
    
    
    private class WordKeyListener implements KeyListener
    {
        private void setChar (int code)
        {
            fgField.setText(fgField.getText() + (char)code);
        }

        public void keyPressed (KeyEvent e)
        {
    
            if(e.getModifiers() == KeyEvent.CTRL_MASK)
            {
                switch (e.getKeyCode())
                {
                    case KeyEvent.VK_F1: setChar(228); break;
                    case KeyEvent.VK_F2: setChar(196); break;
                    case KeyEvent.VK_F3: setChar(233); break;
                    case KeyEvent.VK_F4: setChar(201); break;
                    case KeyEvent.VK_F5: setChar(246); break;
                    case KeyEvent.VK_F6: setChar(214); break;
                    case KeyEvent.VK_F7: setChar(252); break;
                    case KeyEvent.VK_F8: setChar(220); break;
                }
            }
           else
                switch (e.getKeyCode())
                {
                    case KeyEvent.VK_F1: setChar(231); break;
                    case KeyEvent.VK_F2: setChar(199); break;
                    case KeyEvent.VK_F3: setChar(232); break;
                    case KeyEvent.VK_F4: setChar(200); break;
                    case KeyEvent.VK_F5: setChar(249); break;
                    case KeyEvent.VK_F6: setChar(217); break;
                    case KeyEvent.VK_F7: setChar(224); break;
                    case KeyEvent.VK_F8: setChar(192); break;
                    case KeyEvent.VK_F9: setChar(233); break;
                    case KeyEvent.VK_F10: setChar(201); break;
                    case KeyEvent.VK_F11: setChar(234); break;
                    case KeyEvent.VK_F12: setChar(202); break;
                }

        }

        public void keyReleased (KeyEvent e)
        {

        }

        public void keyTyped (KeyEvent e)
        {

        }
    }
}
