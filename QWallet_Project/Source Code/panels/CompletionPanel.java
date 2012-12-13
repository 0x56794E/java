/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package panels;
import handlers.TextFieldListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import frames.MainFrame;

/**
 * QWallet Project - Glasgow Caledonian University  Summer Internship
 * CompletionPanel
 * @Date: August 10th, 2011
 * @author Vy Thuy Nguyen
 */
public class CompletionPanel extends JPanel
    {
        MainFrame mainFr;
        SubCompletionPanel subComPn;
        protected java.util.ArrayList<String> data;
        JButton saveBt = new JButton("Save and Continue");
        JButton finishBt = new JButton("Finish");
        JButton cancelBt = new JButton("Cancel");

        public CompletionPanel(java.util.ArrayList<String> source, MainFrame m)
        {
            mainFr = m;
            data = source;
            subComPn = new SubCompletionPanel(data);
            setLayout(new BorderLayout());

            add(subComPn, BorderLayout.CENTER);

            JPanel btpn = new JPanel();
            btpn.add(saveBt);
            btpn.add(finishBt);
            btpn.add(cancelBt);
            add(btpn, BorderLayout.SOUTH);

            //Handler
            CompletionHandler handler = new CompletionHandler();
            saveBt.addActionListener(handler);
            finishBt.addActionListener(handler);
            cancelBt.addActionListener(handler);

        }

        private class CompletionHandler implements ActionListener
        {

            public void actionPerformed(ActionEvent e)
            {

                if (e.getSource() == saveBt)
                {

                    boolean success = saveQ();
                    if (success)
                    {
                        mainFr.mysql.saveQ(mainFr.currentU,
                                mainFr.completionPn.subComPn.qField.getText().toString().trim(),
                                mainFr.completionPn.subComPn.aField.getText().toString().trim());
                        data.remove(subComPn.qField.getText().toString().trim());
                        mainFr.completionPn.remove(subComPn);
                        subComPn = new SubCompletionPanel(data);
                        mainFr.completionPn.add(subComPn, BorderLayout.CENTER);
                        mainFr.completionPn.repaint();
                        mainFr.validate();
                    }
                    else
                    {
                        return;
                    }

                }
                else if (e.getSource() == finishBt)
                {
                    boolean success = saveQ();
                    if (success)
                    {
                        mainFr.mysql.saveQ(mainFr.currentU,
                                mainFr.completionPn.subComPn.qField.getText().toString().trim(),
                                mainFr.completionPn.subComPn.aField.getText().toString().trim());
                        data.remove(subComPn.qField.getText().toString().trim());
                        cancelBt.doClick();
                    }
                    else
                    {
                        return;
                    }
                }
                else if (e.getSource() == cancelBt)
                {
                    mainFr.remove(mainFr.scrollPane);
                    mainFr.setSize(700, 300);
                    mainFr.createDisplayQPn = new CreateDisplayQPanel(mainFr);
                    mainFr.add(mainFr.createDisplayQPn);
                    mainFr.repaint();
                    mainFr.validate();
                }

            }
        }

        protected boolean saveQ()
        {
            boolean success = true;

            if (mainFr.completionPn.subComPn.qField.getText().toString().trim().compareToIgnoreCase("Type Your Question Here...") == 0
                    || mainFr.completionPn.subComPn.aField.getText().toString().trim().compareToIgnoreCase("Type Your Answer Here...") == 0)
            {
                JOptionPane.showMessageDialog(null, "Required Field(s) Is Empty.\n",
                        "ERROR", JOptionPane.ERROR_MESSAGE);

                if (mainFr.completionPn.subComPn.qField.getText().toString().trim().compareToIgnoreCase("Type Your Question Here...") == 0)
                {
                    mainFr.completionPn.subComPn.qField.setBackground(Color.YELLOW);
                }
                if (mainFr.completionPn.subComPn.aField.getText().toString().trim().compareToIgnoreCase("Type Your Answer Here...") == 0)
                {
                    mainFr.completionPn.subComPn.aField.setBackground(Color.YELLOW);
                }
                return false;
            }

            if (mainFr.mysql.duplicateQ(mainFr.currentU, mainFr.completionPn.subComPn.qField.getText().toString().trim()))
            {
                JOptionPane.showMessageDialog(null, "Duplicate Question.\n"
                        + "You Have Selected ONE Question TWICE!\n"
                        + "Each Question Can ONLY Be Used ONCE!", "ERRROR!", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            return success;
        }

        protected class SubCompletionPanel extends JPanel
        {
            //contains all possible entries that may appear on   the suggested list

            protected java.util.ArrayList<String> data;
            // suggested list
            JList suggest;
            JTextField qField;
            JTextField aField;
            // textentered listener
            TextEnteredListener textEnterListener;
            JPanel cpn = new JPanel();

            public SubCompletionPanel(java.util.ArrayList<String> source)
            {
                // create the textField
                qField = new JTextField("Type Your Question Here...");
                qField.setForeground(Color.GRAY);
                qField.setPreferredSize(new Dimension(300, 25));
                textEnterListener = new TextEnteredListener();
                qField.addCaretListener(textEnterListener);
                qField.addFocusListener(new TextFocusListener());

                data = source;

                // create the suggested list
                String[] temp = new String[source.size()];
                suggest = new JList(temp);
                suggest.setPreferredSize(new Dimension(300, 0));
                suggest.enableInputMethods(true);
                suggest.addListSelectionListener(new SuggestListener());

                //setLayout(new GridBagLayout());
                //GridBagConstraints c = new GridBagConstraints();
                setLayout(new BorderLayout());

                //txfld
                //JPanel npn = new JPanel();
                //npn.add(qField);
                //add(npn, BorderLayout.NORTH);

                //sugx
                //cpn.add(suggest);
                cpn.add(suggest);
                add(cpn, BorderLayout.CENTER);

                //ans
                //JPanel spn = new JPanel();
                aField = new JTextField("Type Your Answer Here...");
                aField.setForeground(Color.GRAY);
                aField.setPreferredSize(new Dimension(300, 25));
                aField.addFocusListener(new TextFocusListener());
                //spn.add(aField);
                //add(spn, BorderLayout.SOUTH);

                JPanel sub1 = new JPanel();
                sub1.add(qField);
                JPanel sub2 = new JPanel();
                sub2.add(aField);
                JPanel npn = new JPanel();
                npn.setLayout(new GridLayout(2, 1));
                npn.add(sub1);
                npn.add(sub2);
                add(npn, BorderLayout.NORTH);


                //Textfield handler
                qField.addMouseListener(new TextFieldListener(qField));
                aField.addMouseListener(new TextFieldListener(aField));

            }

            private class TextEnteredListener implements CaretListener
            {

                protected String pre = "";

                public void caretUpdate(CaretEvent e)
                {
                    pre = qField.getText();
                    String[] temp = new String[data.size()];
                    int n = 0;

                    for (String st : data)
                    {
                        if (st.toLowerCase().startsWith(qField.getText().toLowerCase()))
                        {
                            temp[n] = st;
                            n++;
                        }
                    }

                    if (pre.length() >= 1 && n > 0)
                    {
                        suggest.setPreferredSize(new Dimension(300, n * 20)); //25 instead of 15
                    }
                    else
                    {
                        suggest.setPreferredSize(new Dimension(300, 0));
                    }
                    suggest.setListData(temp);
                }
            }

            private class TextFocusListener implements FocusListener
            {

                public void focusGained(FocusEvent e)
                {
                    if (e.getSource() == qField)
                    {
                        if (qField.getForeground().equals(Color.GRAY))
                        {
                            if (!mainFr.completionPn.subComPn.suggest.isShowing())
                            {
                                mainFr.completionPn.subComPn.cpn.add(suggest);
                                mainFr.completionPn.subComPn.cpn.repaint();
                                mainFr.validate();
                            }
                            qField.setText("");
                        }
                        qField.setForeground(Color.BLACK);
                    }
                    else
                    {

                        if (aField.getForeground().equals(Color.GRAY))
                        {
                            aField.setText("");
                        }
                        aField.setForeground(Color.BLACK);
                    }
                }

                public void focusLost(FocusEvent e)
                {
                    if (e.getSource() == qField)
                    {
                        if (qField.getText().length() == 0)
                        {
                            qField.setForeground(Color.GRAY);
                            qField.setText("Type Your Question Here...");
                        }
                    }
                    else
                    {
                        if (aField.getText().length() == 0)
                        {
                            aField.setForeground(Color.GRAY);
                            aField.setText("Type Your Answer Here...");
                        }
                    }
                }
            }

            private class SuggestListener implements ListSelectionListener
            {

                public void valueChanged(ListSelectionEvent e)
                {

                    String sec = "";
                    int n = 0;
                    for (n = 0; n < suggest.getModel().getSize() - 1; n++)
                    {
                        if (suggest.isSelectedIndex(n))
                        {
                            sec = suggest.getSelectedValue().toString();
                            mainFr.completionPn.subComPn.cpn.remove(suggest);
                            mainFr.completionPn.subComPn.cpn.repaint();
                            mainFr.validate();
                            break;
                        }
                    }

                    qField.removeCaretListener(textEnterListener);
                    qField.setForeground(Color.black);
                    qField.setText(sec);
                    qField.addCaretListener(textEnterListener);
                }
            }
        }
    }