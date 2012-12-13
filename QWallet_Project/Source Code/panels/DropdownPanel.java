/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package panels;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import frames.MainFrame;

/**
 * QWallet Project - Glasgow Caledonian University  Summer Internship
 * DropdownPanel
 * @Date: August 10th, 2011
 * @author Vy Thuy Nguyen
 */
  public class DropdownPanel extends JPanel
    {
        MainFrame mainFr;
        JButton addBt = new JButton("Add Question");
        JButton submitBt = new JButton("Submit");
        JButton cancelBt = new JButton("Cancel");
        JTextField currentQ;
        java.util.ArrayList<SubQPanel> subQArray = new java.util.ArrayList();
        JPanel cPn = new JPanel();
        DropdownHandler handler = new DropdownHandler();

        public DropdownPanel(MainFrame m)
        {
            setLayout(new BorderLayout());
            mainFr = m;
            //North Panel
            JPanel nPn = new JPanel();
            mainFr.qComboBox.addItem("Select a Question...");
            mainFr.loadQList();

            //check qList
            if (mainFr.createDisplayQPn.storedQ.getItemCount() > 1)
            {
                for (int storedIndex = 1; storedIndex < mainFr.createDisplayQPn.storedQ.getItemCount(); storedIndex++)
                {
                    for (int qListIndex = 1; qListIndex < mainFr.qComboBox.getItemCount(); qListIndex++)
                    {
                        if (mainFr.createDisplayQPn.storedQ.getItemAt(storedIndex).toString().compareToIgnoreCase(mainFr.qComboBox.getItemAt(qListIndex).toString()) == 0)
                        {
                            mainFr.qComboBox.removeItemAt(qListIndex);
                        }
                    }
                }
            }


            nPn.add(mainFr.qComboBox);
            nPn.add(addBt);
            add(nPn, BorderLayout.NORTH);

            //Center
            cPn.setLayout(new GridLayout(1, 1, 1, 10));
            subQArray.add(new SubQPanel(subQArray.size() + 1));
            cPn.add(subQArray.get(subQArray.size() - 1));
            add(cPn, BorderLayout.CENTER);


            //South
            JPanel sPn = new JPanel();
            sPn.add(submitBt);
            sPn.add(cancelBt);
            add(sPn, BorderLayout.SOUTH);

            //handler
            mainFr.qComboBox.addActionListener(handler);
            addBt.addActionListener(handler);
            submitBt.addActionListener(handler);
            cancelBt.addActionListener(handler);

        }

        private class DropdownHandler implements ActionListener
        {

            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() == mainFr.qComboBox)
                {
                    subQArray.get(subQArray.size() - 1).str = mainFr.qComboBox.getSelectedItem().toString();
                    subQArray.get(subQArray.size() - 1).q.setText(subQArray.get(subQArray.size() - 1).str);
                    addBt.setEnabled(true);

                }
                else if (e.getSource() == addBt)
                {
                    mainFr.qComboBox.removeActionListener(handler);
                    mainFr.qComboBox.removeItem(mainFr.qComboBox.getSelectedItem());
                    cPn.setLayout(new GridLayout(mainFr.dropdownPn.subQArray.size() + 1, 1, 1, 10));
                    subQArray.add(new SubQPanel(subQArray.size() + 1));
                    cPn.add(subQArray.get(subQArray.size() - 1));
                    repaint();
                    mainFr.validate();
                    mainFr.qComboBox.addActionListener(handler);
                    addBt.setEnabled(false);
                }
                else if (e.getSource() == submitBt)
                {
                    for (int i = 0; i < subQArray.size(); i++)
                    {
                        //check for duplicate questions on frame
                        for (int k = i + 1; k < subQArray.size(); k++)
                        {
                            if (mainFr.duplicateQ(subQArray.get(i).q.getText().toString().trim(),
                                    subQArray.get(k).q.getText().toString().trim())
                                    && !subQArray.get(i).q.getText().toString().trim().isEmpty()
                                    && !subQArray.get(k).q.getText().toString().trim().isEmpty())
                            {
                                JOptionPane.showMessageDialog(null, "Duplicate Question.\n"
                                        + "You Have Selected ONE Question TWICE!\n"
                                        + "Each Question Can ONLY Be Used ONCE!", "ERRROR!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                    }



                    for (int n = 0; n < subQArray.size(); n++)
                    {
                        // check for duplicate questions in database
                        if (mainFr.mysql.duplicateQ(mainFr.currentU, subQArray.get(n).q.getText().toString()))
                        {
                            JOptionPane.showMessageDialog(null, "Duplicate Question.\n"
                                    + "You Have Selected ONE Question TWICE!\n"
                                    + "Each Question Can ONLY Be Used ONCE!", "ERRROR!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        //check for empty fields
                        if (!subQArray.get(n).a.getText().toString().isEmpty()
                                && !subQArray.get(n).a.getText().toString().isEmpty())
                        {
                            mainFr.mysql.saveQ(mainFr.currentU,
                                    subQArray.get(n).q.getText().trim() + (subQArray.get(n).q.getText().trim().contains("?") ? "" : "?"),
                                    subQArray.get(n).a.getText().trim());
                        }
                        else
                        {
                            if (subQArray.get(n).q.getText().toString().isEmpty())
                            {
                                if (JOptionPane.showConfirmDialog(null, "Question Number " + (n + 1) + " Is Empty.\n"
                                        + "Do you want to add question to that field?", "Empty Field Detected!", JOptionPane.YES_NO_OPTION)
                                        == JOptionPane.YES_OPTION)
                                {
                                    return;
                                }
                            }

                            if (subQArray.get(n).a.getText().toString().isEmpty()
                                    && !subQArray.get(n).q.getText().toString().isEmpty())
                            {
                                JOptionPane.showMessageDialog(null, "You Did Not Provide an Answer to Question " + (n + 1), "ERROR!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                    }
                    mainFr.remove(mainFr.scrollPane);
                    mainFr.createDisplayQPn = new CreateDisplayQPanel(mainFr);
                    mainFr.add(mainFr.createDisplayQPn);
                    mainFr.repaint();
                    mainFr.validate();
                }
                else if (e.getSource() == cancelBt)
                {
                    mainFr.remove(mainFr.scrollPane);
                    mainFr.add(mainFr.createDisplayQPn);
                    mainFr.repaint();
                    mainFr.validate();

                }
            }
        }

        private class SubQPanel extends JPanel
        {

            JTextField q = new JTextField(15);
            JTextField a = new JTextField(15);
            String str = new String();

            public SubQPanel(int count)
            {
                setLayout(new GridLayout(2, 1));
                setBorder(new TitledBorder("Question " + count));

                //q
                JPanel sub1 = new JPanel();
                sub1.add(new JLabel("Question"));
                sub1.add(q);
                add(sub1);

                //a
                JPanel sub2 = new JPanel();
                sub2.add(new JLabel("Answer "));
                sub2.add(a);
                add(sub2);

            }
        }
    }

