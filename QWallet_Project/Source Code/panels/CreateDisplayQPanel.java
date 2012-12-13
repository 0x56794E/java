/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package panels;
import handlers.BoxListener;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;

import java.io.IOException;
import frames.DragDropJFrame;
import frames.MainFrame;
import qwallet.QandA;

/**
 * QWallet Project - Glasgow Caledonian University  Summer Internship
 * CreateDisplayQPanel
 * @Date: August 10th, 2011
 * @author Vy Thuy Nguyen
 */
    public class CreateDisplayQPanel extends JPanel
    {
        MainFrame mainFr;
        JButton signoutBt = new JButton("Sign out");
        JButton closeBt = new JButton("Close");
        JButton goBt = new JButton("Go");
        JButton viewABt = new JButton("View Answer");
        JComboBox storedQ = new JComboBox();
        JComboBox creatingMethod = new JComboBox();
        java.util.ArrayList<QandA> qaList = new java.util.ArrayList();

        public CreateDisplayQPanel(MainFrame fr)
        {
            mainFr = fr;
            mainFr.validate();
            setLayout(new GridLayout(4, 1));
            //Greeting
            JPanel pn = new JPanel();
            JLabel lb = new JLabel("Welcome, " + mainFr.mysql.getName(mainFr.currentU) + "!");
            lb.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
            lb.setForeground(new Color(46, 171, 255));
            pn.add(lb);
            add(pn);
            
            //View stored questions
            JPanel viewQPn = new JPanel();
            viewQPn.setBorder(new TitledBorder("View Your Stored Questions"));
            if (!mainFr.mysql.hasQ(mainFr.currentU))
            {
                viewQPn.add(new JLabel("You currently haven't stored any questions"));
            }
            else
            {
                storedQ.addItem("Select a Question...");
                getStoredQA();
                viewQPn.add(storedQ);
                viewQPn.add(viewABt);
            }

            add(viewQPn);

            //Create new questions
            JPanel createQPn = new JPanel();
            createQPn.setBorder(new TitledBorder("Create New Questions"));
            creatingMethod.addItem("Select a method...");
            creatingMethod.addItem("Dropdown List");
            creatingMethod.addItem("Completion");
            creatingMethod.addItem("Fridge Magnet");
            createQPn.add(creatingMethod);
            createQPn.add(goBt);
            add(createQPn);

            //Button panel
            JPanel btPn = new JPanel();
            btPn.add(signoutBt);
            add(btPn);


            //Handler
            CreateDisplayHanler handler2 = new CreateDisplayHanler ();
            goBt.addActionListener(handler2);
            signoutBt.addActionListener(handler2);
            viewABt.addActionListener(handler2);
            creatingMethod.addMouseListener(new BoxListener(creatingMethod));
            storedQ.addMouseListener(new BoxListener(storedQ));
        }

        private class CreateDisplayHanler implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() == goBt)
            {

                switch (creatingMethod.getSelectedIndex())
                {
                    case 1: //dropdown
                        mainFr.remove(mainFr.createDisplayQPn);
                        mainFr.setSize(700, 300);
                        mainFr.dropdownPn = new DropdownPanel(mainFr);
                        mainFr.scrollPane = new JScrollPane(mainFr.dropdownPn, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                        mainFr.add(mainFr.scrollPane);
                        mainFr.validate();
                        return;
                    case 2: //completion
                        mainFr.remove(mainFr.createDisplayQPn);
                        mainFr.setSize(400, 400);
                        mainFr.completionPn = new CompletionPanel(mainFr.qArray, mainFr);
                        mainFr.scrollPane = new JScrollPane(mainFr.completionPn, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                        mainFr.add(mainFr.scrollPane);
                        mainFr.validate();
                        return;
                    case 3: //fridge magnet (drag and drop)
                        mainFr.setVisible(false);
                        try
                        {
                            mainFr.dragdropFr = new DragDropJFrame(mainFr);
                        }
                        catch (IOException exception)
                        {
                            exception.printStackTrace();
                        }
                        return;
                    default:
                        JOptionPane.showMessageDialog(null, "Please Select a Method", "ERROR!", JOptionPane.ERROR_MESSAGE);
                        creatingMethod.setBackground(Color.yellow);
                        return;
                }
            }
            else if (e.getSource() == signoutBt)
            {
                if (JOptionPane.showConfirmDialog(null, "Are You Sure?", "Signing Out", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
                {
                    return;
                }
                mainFr.remove(mainFr.createDisplayQPn);
                mainFr.setSize(700, 200);
                //loginPn = new LoginPanel();
                mainFr.add(mainFr.loginPn);
                mainFr.validate();
            }
            else if (e.getSource() == viewABt)
            {
                if (storedQ.getSelectedIndex() == 0)
                {
                    JOptionPane.showMessageDialog(null, "Please Select a Question", "ERROR", JOptionPane.ERROR_MESSAGE);
                    storedQ.setBackground(Color.yellow);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Your Answer is:\n"
                            + qaList.get(storedQ.getSelectedIndex() - 1).a);
                }

            }
            }
        }
        protected void getStoredQA()
        {
            mainFr.mysql.getQA(mainFr.currentU, qaList);
            for (int n = 0; n < qaList.size(); n++)
            {
                storedQ.addItem(qaList.get(n).q);
            }
        }
    }
