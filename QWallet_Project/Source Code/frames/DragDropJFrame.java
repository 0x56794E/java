package frames;

import panels.CreateDisplayQPanel;
import javax.swing.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;

/**
 * QWallet Project - Glasgow Caledonian University  Summer Internship
 * Drag and Drop Frame
 * @Date: August 10th, 2010
 * @author Vy Thuy Nguyen
 */
public class DragDropJFrame extends JFrame
{

    private static final String PATH = "C:\\QWallet\\words.txt";
    private ArrayList<String> words;
    private ArrayList<JButton> buttonList;
    private ArrayList<Boolean> atDrag;
    private JPanel dragPanel;
    private JPanel dropPanel;
    private JPanel answerPanel;
    private JTextField quest;
    private JTextField ans;
    MainFrame mainFr;
    DragDropJFrame dragDropFr;

    public DragDropJFrame(MainFrame main) throws FileNotFoundException, IOException
    {
        super();
        this.setLayout(new BorderLayout());
        this.setTitle("Drag-and-Drop to Create Your Question!");
        this.setSize(900, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFr = main;
     
        //-------------------- initializes the two panels;
        // Drag panel
        dragPanel = new JPanel();
        dragPanel.setPreferredSize(new Dimension(900, 450));
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        dragPanel.setBorder(border);

        // Drop panel
        dropPanel = new JPanel();
        dropPanel.setLayout(new FlowLayout());
        dropPanel.setPreferredSize(new Dimension(900, 150));
        dropPanel.setBorder(border);

        // Answer Panel
        answerPanel = new JPanel();
        answerPanel.setPreferredSize(new Dimension(900, 100));
        answerPanel.setLayout(new BorderLayout());

        JPanel dummy1 = new JPanel();
        dummy1.setLayout(new FlowLayout());
        dummy1.add(new JLabel("Question: "));
        quest = new JTextField();
        quest.setPreferredSize(new Dimension(300, 30));
        dummy1.add(quest);

        JPanel dummy2 = new JPanel();
        dummy2.setLayout(new FlowLayout());
        dummy2.add(new JLabel("Answer:   "));
        ans = new JTextField();
        ans.setPreferredSize(new Dimension(300, 30));
        dummy2.add(ans);

        // question and answer panel
        JPanel dummy = new JPanel();
        dummy.setLayout(new GridLayout(2, 1, 0, 10));
        dummy.add(dummy1);
        dummy.add(dummy2);
        dummy.setBorder(border);

        // button panel
        JPanel dummy3 = new JPanel();
        dummy3.setLayout(new GridLayout(2, 1, 0, 20));

        JPanel submitPanel = new JPanel();
        JButton submitBtn = new JButton("Submit");
        submitBtn.addActionListener(new SubmitListener());
        submitPanel.add(submitBtn);

        JPanel clearPanel = new JPanel();
        JButton clearBtn = new JButton("Reset");
        clearBtn.addActionListener(new ClearListener(this));
        clearPanel.add(clearBtn);

        dummy3.add(submitPanel);
        dummy3.add(clearPanel);

        // add the two panels to answerPanel
        answerPanel.add(dummy, BorderLayout.CENTER);
        answerPanel.add(dummy3, BorderLayout.EAST);
        answerPanel.setBorder(border);

        //------------------------- populates words list
        atDrag = new ArrayList<Boolean>();
        words = new ArrayList<String>();
        FileInputStream inFile = new FileInputStream(PATH);
        DataInputStream in = new DataInputStream(inFile);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
        String dataLine = buffer.readLine();
        String wd = "";
        Scanner scanner;
        while (dataLine != null)
        {
            scanner = new Scanner(dataLine);
            while (scanner.hasNext())
            {
                wd = scanner.next();
                words.add(wd);
                atDrag.add(true);
            }
            dataLine = buffer.readLine();
        }

        // create buttons
        JButton btn;
        buttonList = new ArrayList<JButton>();
        for (int i = 0; i < words.size(); ++i)
        {
            btn = new JButton(words.get(i));
            btn.addActionListener(new DragButtonListener(i, this));
            buttonList.add(btn);
        }


        for (int i = 0; i < buttonList.size(); ++i)
        {
            dragPanel.add(buttonList.get(i));
        }

        //--------------------add the panels to the frame
        JScrollPane pane = new JScrollPane(dragPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.add(pane, BorderLayout.CENTER);
        this.add(dropPanel, BorderLayout.NORTH);
        this.add(answerPanel, BorderLayout.SOUTH);

        this.setVisible(true);
        this.addWindowListener(new DragDropWindowHandler());
        dragDropFr = this;
    }

    private class DragDropWindowHandler implements WindowListener
    {

        public void windowClosed(WindowEvent e)
        {
        }

        public void windowClosing(WindowEvent e)
        {

            dragDropFr.dispose();
            mainFr.setVisible(true);
            mainFr.validate();
        }

        public void windowOpened(WindowEvent e)
        {
        }

        public void windowIconified(WindowEvent e)
        {
        }

        public void windowDeiconified(WindowEvent e)
        {
        }

        public void windowActivated(WindowEvent e)
        {
        }

        public void windowDeactivated(WindowEvent e)
        {
        }
    }

    private class SubmitListener implements ActionListener
    {

        public void actionPerformed(ActionEvent e)
        {
            //Check for empty field
            if (quest.getText().isEmpty() || ans.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Empty Field(s) Detected", "ERROR!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Check in database
            if (mainFr.mysql.duplicateQ(mainFr.currentU, quest.getText().trim() + "?"))
            {
                JOptionPane.showMessageDialog(null, "Duplicate Question.\n"
                        + "You Have Selected ONE Question TWICE!\n"
                        + "Each Question Can ONLY Be Used ONCE!", "ERRROR!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            mainFr.mysql.saveQ(mainFr.currentU, quest.getText().trim() + "?", ans.getText().trim());
            dragDropFr.dispose();   
            mainFr.remove(mainFr.createDisplayQPn);
            mainFr.createDisplayQPn = new CreateDisplayQPanel(mainFr);
            mainFr.add(mainFr.createDisplayQPn);
            mainFr.setVisible(true);
            mainFr.validate();
        }
    }

    public String getQuestion()
    {
        return quest.getText() + " ?";
    }

    public String getAnswer()
    {
        return ans.getText();
    }

    private class ClearListener implements ActionListener
    {

        private JFrame fr;

        public ClearListener(JFrame fr)
        {
            this.fr = fr;
        }

        public void actionPerformed(ActionEvent e)
        {
            quest.setText("");
            ans.setText("");

            dropPanel.removeAll();

            for (int i = 0; i < buttonList.size(); ++i)
            {
                if (!atDrag.get(i))
                {
                    dragPanel.add(buttonList.get(i));
                    atDrag.set(i, true);
                }
            }

            fr.setSize(fr.getWidth() - 1, fr.getHeight());
            fr.setSize(fr.getWidth() + 1, fr.getHeight());
            fr.repaint();
        }
    }

    private class DropButtonListener implements ActionListener
    {

        private int i;
        private JButton btn;
        private JFrame fr;

        public DropButtonListener(JButton btn, int i, JFrame fr)
        {
            this.i = i;
            this.btn = btn;
            this.fr = fr;
        }

        public void actionPerformed(ActionEvent e)
        {
            dropPanel.remove(btn);
            dragPanel.add(buttonList.get(i));
            atDrag.set(i, true);

            fr.setSize(fr.getWidth() - 1, fr.getHeight());
            fr.setSize(fr.getWidth() + 1, fr.getHeight());

            String txt = quest.getText();
            txt = removeString(txt, words.get(i));
            txt.replaceAll("\\s+", " ");
            quest.setText(txt);
        }
    }

    private class DragButtonListener implements ActionListener
    {

        private int i;
        private JButton btn;
        public JFrame fr;

        public DragButtonListener(int i, JFrame fr)
        {
            this.i = i;
            this.fr = fr;
        }

        public void actionPerformed(ActionEvent e)
        {
            dragPanel.remove(buttonList.get(i));
            btn = new JButton(words.get(i));
            btn.addActionListener(new DropButtonListener(btn, i, fr));
            dropPanel.add(btn);
            atDrag.set(i, false);
            String txt = quest.getText() + " " + words.get(i);
            txt.replaceAll("\\s+", " ");
            quest.setText(txt);

            fr.setSize(fr.getWidth() - 1, fr.getHeight());
            fr.setSize(fr.getWidth() + 1, fr.getHeight());
        }
    }

    private String removeString(String st, String sub)
    {

        if (st == null)
        {
            return null;
        }
        int index = st.indexOf(sub);
        if (index < 0)
        {
            return st;
        }
        int l = st.length();
        char[] ar = new char[l + 1];
        for (int a = 0; a < l; ++a)
        {
            ar[a] = st.charAt(a);
        }
        ar[l] = '\0';

        if (st.endsWith(sub))
        {
            return st.substring(0, st.length() - sub.length());
        }

        for (int n = index; n < index + sub.length(); ++n)
        {
            ar[n] = '\0';
        }
        int m, i;
        for (m = index, i = index + sub.length(); i < l + 1; ++m, ++i)
        {
            ar[m] = ar[i];
            ar[i] = '\0';
        }


        int n;
        for (n = 0; ar[n] != '\0'; ++n);

        String buf = new String(ar).substring(0, n).trim();
        buf.replaceAll("\\s+", " ");

        return buf;
    }
}
