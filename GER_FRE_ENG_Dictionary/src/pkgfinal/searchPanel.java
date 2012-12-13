/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.border.*;




/**
 *
 * @author Vy Thuy Nguyen
 */
public class searchPanel extends JPanel
{
   MainFrame mainFr;
   JTextField lookupField = new JTextField(10);
   JButton lookupBt = new JButton("Look up");
   
   DefinitionPanel defPn = new DefinitionPanel();
   JList jwordList;
   
   ListListener handler = new ListListener();
   public searchPanel(MainFrame fr)
   {      
       setLayout(new BorderLayout());
       mainFr = fr;
       //Left Pn
       JPanel lPn = new JPanel();
       lPn.setLayout(new BorderLayout());
       JPanel subPn = new JPanel();
       lookupField.setToolTipText("Enter a word and hit 'Enter' or click 'Look up' to see the translation");
       subPn.add(lookupField);
       subPn.add(lookupBt);
       lPn.add(subPn, BorderLayout.NORTH);
       
       String[] arr = mainFr.wordList.getList();
       jwordList = new JList(arr);      
       JScrollPane sb = new JScrollPane(jwordList);
       jwordList.setAutoscrolls(true);
       jwordList.setToolTipText("Select a word and hit 'Enter' or click 'Look up' to see the translation");
       lPn.add(sb, BorderLayout.CENTER);
       
       add(lPn, BorderLayout.NORTH);
       add(defPn, BorderLayout.CENTER);
       
       //event       
       lookupField.addKeyListener(handler);
       jwordList.addListSelectionListener(handler);
       jwordList.addKeyListener(handler);
       lookupBt.addActionListener(handler);
   }
          

   private class ListListener implements KeyListener, ListSelectionListener, ActionListener
   {
       public void actionPerformed(ActionEvent e)
       {
           if (e.getSource() == lookupBt)
           {
               mainFr.wordList.printDef(lookupField.getText().trim());
           }
           
       }
       public void keyTyped(KeyEvent e){}
        
       private void setChar (int code)
        {
            lookupField.setText(lookupField.getText() + (char)code);
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
       
       public void keyReleased(KeyEvent e)
       {          
           if (e.getKeyCode() == KeyEvent.VK_ENTER)
           {
               lookupBt.doClick();
               //JOptionPane.showMessageDialog(null, "Just click look up");
           }
           jwordList.setSelectedValue(new String(lookupField.getText()), true);   
       }
       
       public void valueChanged(ListSelectionEvent e)
       {
           lookupField.setText(jwordList.getSelectedValue().toString());
       }
   }
   protected class DefinitionPanel extends JPanel
   {
       JTextArea txArea =new JTextArea(5,20);
       
       protected DefinitionPanel()
       {
          this.setBorder(new TitledBorder("Definition"));
          
          txArea.setPreferredSize(new Dimension(330,230));
          add(txArea);
           
       }
       

   }
}
