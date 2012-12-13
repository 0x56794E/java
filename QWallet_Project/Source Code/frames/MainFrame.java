package frames;

import panels.CreateDisplayQPanel;
import panels.CompletionPanel;
import panels.DropdownPanel;
import handlers.BoxListener;
import handlers.TextFieldListener;
import java.io.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import qwallet.MySQL;

/**
 * QWallet Project - Glasgow Caledonian University  Summer Internship
 * MainFrame
 * @Date: August 10th, 2011
 * @author Vy Thuy Nguyen
 */
public class MainFrame extends JFrame
{
    public String currentU = "";
    public String currentP = "";
    MainFrame mainFr = this;
    public MySQL mysql = new MySQL(this);
    public LoginPanel loginPn = new LoginPanel();
    public CreateAccountPanel createAccPn = new CreateAccountPanel();
    public CreateDisplayQPanel createDisplayQPn;
    public DropdownPanel dropdownPn;
    public CompletionPanel completionPn;
    public DragDropJFrame dragdropFr;
    JMenuBar mnb = new JMenuBar();
    Icon helpicon = new ImageIcon("C:\\QWallet\\helpicon.gif");
    JMenu helpMenu = new JMenu("Help");
    JMenuItem helpItem = new JMenuItem("Help Contents", helpicon);
    JMenuItem aboutItem = new JMenuItem("About");
    public JScrollPane scrollPane;
    public JComboBox qComboBox = new JComboBox();
    public java.util.ArrayList<String> qArray = new java.util.ArrayList();

    public MainFrame()
    {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setTitle("Welcome to My QWallet");
        setSize(700, 200); //for main login screen
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);

        //Adding Menu
        helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        helpMenu.add(helpItem);
        helpMenu.addSeparator();
        helpMenu.add(aboutItem);
        mnb.add(helpMenu);
        this.setJMenuBar(mnb);

        //Adding login panel
        add(loginPn);
        loginPn.userField.setFocusable(true);
        boolean testhasup = mysql.hasSavedUP();
        if (testhasup)
        {
            loginPn.rememberID.setSelected(true);
            String temp[] = mysql.getSavedUP();
            loginPn.userField.setText(temp[0]);
            loginPn.passField.setText(temp[1]);
            if (mysql.autoSigin())
            {
                loginPn.autoSignin.setSelected(true);
                loginPn.signinBt.doClick();
            }
        }

        //Load questions from file
        loadQList();

        this.addWindowListener(new LoginHandler());

        mainFr.validate();

        LoginHandler handler = new LoginHandler();
        helpItem.addActionListener(handler);
        aboutItem.addActionListener(handler);

    }

    /*
     * Login Panel
     * This panel displays interface for user to either log in or choose to create a new account
     */
    protected class LoginPanel extends JPanel
    {

        JLabel userlb = new JLabel("User");
        JLabel passlb = new JLabel("Password");
        JTextField userField = new JTextField(20);
        JPasswordField passField = new JPasswordField(20);
        JCheckBox rememberID = new JCheckBox("Remember my User and Password");
        JCheckBox autoSignin = new JCheckBox("Sign in automatically");
        JButton signinBt = new JButton("Sign In");
        JButton createBt = new JButton("Create a New Account");

        public LoginPanel()
        {

            //*****************Logo*****************
            ImageIcon img = new ImageIcon("C:\\QWallet\\qwalletlogo.png");
            JLabel imglb = new JLabel(img);
            add(imglb);

            //************************User and Pass part**********
            JPanel upPn = new JPanel();
            upPn.setBorder(new BevelBorder(BevelBorder.RAISED));
            upPn.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.anchor = GridBagConstraints.EAST;
            c.weighty = 3;

            //Row 1
            c.gridx = 3;
            c.gridy = 3;
            c.gridwidth = 3;
            c.gridheight = 1;
            c.insets = new Insets(2, 2, 1, 2);
            upPn.add(userlb, c);

            c.anchor = GridBagConstraints.WEST;
            c.gridx = 6;
            c.gridwidth = 3;
            c.insets = new Insets(2, 5, 1, 1);
            upPn.add(userField, c);

            //Row 2
            c.anchor = GridBagConstraints.EAST;
            c.gridx = 3;
            c.gridy = 7;
            c.gridwidth = 3;
            c.insets = new Insets(0, 2, 1, 2);
            upPn.add(passlb, c);

            c.anchor = GridBagConstraints.WEST;
            c.gridx = 6;
            c.gridwidth = 3;
            c.insets = new Insets(0, 5, 1, 1);
            upPn.add(passField, c);

            //Row 3
            c.gridx = 6;
            c.gridy = 10;
            c.gridwidth = 5;
            c.insets = new Insets(0, 3, 1, 3);
            upPn.add(rememberID, c);

            //Row 4
            c.gridy = 11;
            c.insets = new Insets(0, 3, 1, 3);
            upPn.add(autoSignin, c);

            add(upPn);

            //*********Button***************
            JPanel btPn = new JPanel();
            btPn.add(signinBt);
            btPn.add(createBt);
            add(btPn);


            //handler
            LoginHandler handler = new LoginHandler();
            signinBt.addActionListener(handler);
            createBt.addActionListener(handler);

            LoginHandler kHandler = new LoginHandler();
            userField.addKeyListener(kHandler);
            passField.addKeyListener(kHandler);
            userField.addMouseListener(new TextFieldListener(userField));
            passField.addMouseListener(new TextFieldListener(passField));

        }

        public void clearForm()
        {
            loginPn.userField.setText("");
            loginPn.passField.setText("");
        }
    }


    public class LoginHandler implements ActionListener, WindowListener, KeyListener
    {

        public void keyPressed(KeyEvent e)
        {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
            {
                if (createAccPn.isShowing())
                {
                    createAccPn.createBt.doClick();
                }
                else
                {
                    if (loginPn.isShowing())
                    {
                        loginPn.signinBt.doClick();
                    }
                }
            }
        }

        public void keyReleased(KeyEvent e)
        {
        }

        public void keyTyped(KeyEvent e)
        {
        }

        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == loginPn.signinBt)
            {
                if (loginPn.userField.getText().isEmpty() || loginPn.passField.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Required Info Is Missing.\n"
                            + "If You Don't Have an Account, Please Click 'Create a New Account' to Create a New One.",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                    if (loginPn.userField.getText().isEmpty())
                    {
                        loginPn.userField.setBackground(Color.YELLOW);
                    }
                    if (loginPn.passField.getText().isEmpty())
                    {
                        loginPn.passField.setBackground(Color.YELLOW);
                    }
                    return;
                }


                //Check user and password
                currentU = loginPn.userField.getText().trim();
                currentP = loginPn.passField.getText().trim();
                if (!mysql.correctUP(currentU, currentP))
                {
                    JOptionPane.showMessageDialog(null, "Record Not Found!\nPlease check your User or Password\n"
                            + "If You Don't Have an Account, Please Click 'Create a New Account' to Create a New One",
                            "ERROR!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //Signin procedure here
                if (loginPn.rememberID.isSelected() || loginPn.autoSignin.isSelected())
                {
                    mysql.saveUP(currentU, currentP, (loginPn.autoSignin.isSelected() ? true : false));
                }
                else
                {
                    loginPn.clearForm();
                    mysql.deleteSavedUP();
                }
                mainFr.setTitle("Hello, " + mysql.getName(currentU) + " - Welcome to Your QWallet!");
                mainFr.remove(loginPn);
                mainFr.setSize(700, 300);
                createDisplayQPn = new CreateDisplayQPanel(mainFr);
                mainFr.add(createDisplayQPn);
                mainFr.repaint();
                mainFr.validate();

            }
            else if (e.getSource() == loginPn.createBt)
            {
                loginPn.clearForm();
                mainFr.remove(loginPn);
                mainFr.setSize(1000, 300);
                mainFr.setLocationRelativeTo(null);
                mainFr.add(createAccPn);
                mainFr.validate();


            }
            else if (e.getSource() == createAccPn.cancelBt)
            {
                createAccPn.clearForm();
                mainFr.remove(createAccPn);
                mainFr.setSize(700, 200);
                mainFr.setLocationRelativeTo(null);
                //loginPn = new LoginPanel();
                mainFr.add(loginPn);
                mainFr.validate();

            }
            else if (e.getSource() == createAccPn.clearBt)
            {
                createAccPn.clearForm();
            }
            else if (e.getSource() == createAccPn.createBt)
            {
                //Check required fields
                if (createAccPn.nameField.getText().isEmpty()
                        || createAccPn.idField.getText().isEmpty()
                        || createAccPn.month.getSelectedIndex() == 0
                        || createAccPn.day.getSelectedIndex() == 0
                        || createAccPn.year.getSelectedIndex() == 0
                        || createAccPn.userField.getText().isEmpty()
                        || createAccPn.passField.getText().isEmpty()
                        || createAccPn.cPassField.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Required Info Is Missing", "ERROR!", JOptionPane.ERROR_MESSAGE);
                    if (createAccPn.nameField.getText().isEmpty())
                    {
                        createAccPn.nameField.setBackground(Color.YELLOW);
                    }
                    if (createAccPn.idField.getText().isEmpty())
                    {
                        createAccPn.idField.setBackground(Color.YELLOW);
                    }
                    if (createAccPn.month.getSelectedIndex() == 0)
                    {
                        createAccPn.month.setBackground(Color.yellow);
                    }
                    if (createAccPn.day.getSelectedIndex() == 0)
                    {
                        createAccPn.day.setBackground(Color.yellow);
                    }
                    if (createAccPn.year.getSelectedIndex() == 0)
                    {
                        createAccPn.year.setBackground(Color.yellow);
                    }
                    if (createAccPn.userField.getText().isEmpty())
                    {
                        createAccPn.userField.setBackground(Color.yellow);
                    }
                    if (createAccPn.passField.getText().isEmpty())
                    {
                        createAccPn.passField.setBackground(Color.yellow);
                    }
                    if (createAccPn.cPassField.getText().isEmpty())
                    {
                        createAccPn.cPassField.setBackground(Color.yellow);
                    }

                    return;

                }

                if (createAccPn.passField.getText().compareTo(createAccPn.cPassField.getText()) != 0)
                {
                    JOptionPane.showMessageDialog(null, "Passwords Do Not Match!", "ERROR!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                StringBuilder dob = new StringBuilder();
                dob.append((createAccPn.month.getSelectedIndex() < 10 ? '0' : ""));
                dob.append(createAccPn.month.getSelectedIndex());
                dob.append((createAccPn.day.getSelectedIndex() < 10 ? '0' : ""));
                dob.append(createAccPn.day.getSelectedIndex());
                dob.append(createAccPn.year.getSelectedItem());

                if (!mysql.addNewUser(createAccPn.userField.getText(), createAccPn.passField.getText(),
                        createAccPn.nameField.getText(), createAccPn.idField.getText(), dob.toString()))
                {
                    return;
                }
                JOptionPane.showMessageDialog(null, "Account Created Successfully", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
                createAccPn.cancelBt.doClick();
            }
            else if (e.getSource() == helpItem)
            {
                new HelpFrame();
            }
            else if (e.getSource() == aboutItem)
            {
                new AboutFrame();
            }
            

        }

        public void windowClosed(WindowEvent e)
        {
        }

        public void windowClosing(WindowEvent e)
        {
            if (JOptionPane.showConfirmDialog(null, "Are You Sure?", "Quitting",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            {
                if (loginPn.rememberID.isSelected())
                {
                    mysql.saveUP(currentU, currentP, (loginPn.autoSignin.isSelected() ? true : false));
                }
                else
                    ;
                System.exit(0);
            }
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

    /**
     * This class displays a panel for user to register
     */
    protected class CreateAccountPanel extends JPanel
    {

        JButton createBt = new JButton("Create Account");
        JButton clearBt = new JButton("Clear Form");
        JButton cancelBt = new JButton("Cancel");
        JTextField nameField = new JTextField(10);
        JTextField idField = new JTextField(10);
        JTextField userField = new JTextField(15);
        JPasswordField passField = new JPasswordField(15);
        JPasswordField cPassField = new JPasswordField(15);
        JComboBox month = new JComboBox();
        JComboBox day = new JComboBox();
        JComboBox year = new JComboBox();
        PasswordStrengthBar passwordStrength = new PasswordStrengthBar();
        public CreateAccountPanel()
        {
            //Month
            month.addItem("Month");
            month.addItem("January");
            month.addItem("February");
            month.addItem("March");
            month.addItem("April");
            month.addItem("May");
            month.addItem("June");
            month.addItem("July");
            month.addItem("August");
            month.addItem("September");
            month.addItem("October");
            month.addItem("November");
            month.addItem("December");

            //Day
            day.addItem("Day");
            year.addItem("Year");
            for (int n = 1; n <= 31; n++)
            {
                day.addItem(n);
                year.addItem((1930 + n));
            }

            //Year
            for (int i = 32; i < 81; i++)
            {
                year.addItem(1930 + i);
            }


            //****** Personal Info ***********
            JPanel infoPn = new JPanel();
            infoPn.setBorder(new TitledBorder("Personal Info"));
            infoPn.setLayout(new BorderLayout());

            //Name & ID
            JPanel idPn = new JPanel();
            idPn.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.anchor = GridBagConstraints.EAST;
            c.weighty = 3;

            //Row 1
            c.gridx = 1;
            c.gridy = 1;
            c.gridwidth = 3;
            c.gridheight = 1;
            idPn.add(new JLabel("Name"), c);

            c.gridx = 5;
            idPn.add(nameField, c);

            //Row 2
            c.gridx = 1;
            c.gridy = 5;
            idPn.add(new JLabel("Passport No"), c);

            c.gridx = 5;
            idPn.add(idField, c);

            infoPn.add(idPn, BorderLayout.CENTER);

            //DOB
            JPanel dobPn = new JPanel();
            dobPn.add(month);
            dobPn.add(day);
            dobPn.add(year);

            infoPn.add(dobPn, BorderLayout.SOUTH);

            infoPn.setPreferredSize(new Dimension(280, 101));
            add(infoPn);


            //************************User and Pass part**********
            JPanel upPn = new JPanel();
            upPn.setBorder(new TitledBorder("User & Password"));
            upPn.setLayout(new GridBagLayout());

            GridBagConstraints c2 = new GridBagConstraints();
            c2.anchor = GridBagConstraints.EAST;
            c2.weighty = 3;
            //Row 1
            c2.gridx = 3;
            c2.gridy = 3;
            c2.gridwidth = 3;
            c2.gridheight = 1;
            c2.insets = new Insets(2, 2, 1, 2);
            upPn.add(new JLabel("User"), c2);

            c2.anchor = GridBagConstraints.WEST;
            c2.gridx = 6;
            c2.insets = new Insets(2, 5, 1, 1);
            upPn.add(userField, c2);

            //Row 2
            c2.anchor = GridBagConstraints.EAST;
            c2.gridx = 3;
            c2.gridy = 7;
            c2.insets = new Insets(0, 2, 1, 2);
            upPn.add(new JLabel("Password"), c2);

            c2.anchor = GridBagConstraints.WEST;
            c2.gridx = 6;
            c2.insets = new Insets(0, 5, 1, 1);
            upPn.add(passField, c2);

            //Row 3 c.anchor = GridBagConstraints.EAST;
            c2.gridx = 3;
            c2.gridy = 10;
            c2.insets = new Insets(0, 2, 1, 2);
            upPn.add(new JLabel("Confirm Password"), c2);

            c2.gridx = 6;
            c2.gridy = 10;
            c2.insets = new Insets(0, 5, 1, 1);
            upPn.add(cPassField, c2);

            upPn.setPreferredSize(new Dimension(320, 101));
            add(upPn);

            //Password Strength
            JPanel passPn = new JPanel();
            passPn.setLayout(new BorderLayout());
            passPn.add(passwordStrength, BorderLayout.NORTH);
            
            JTextArea tx = new JTextArea();
            //tx.setPreferredSize(new Dimension(100, 50));
            tx.setText("- Very Strong Password: "
                    + "\n   1) Has at least 6 characters."
                    + "\n   2) Has uppercase letter(s) AND number(s) AND special character(s)"
          + "\n- Strong Password: "
                    + "\n   1) Has at least 6 characters. "
                    + "\n   2a) Has uppercase letter(s) AND number(s)."
          + "\n   2b) OR has uppercase letter(s) AND special character(s)."
          + "\n   2c) OR has number(s) AND special character(s)."
          + "\n- Fair Password: "
                    + "\n   1) Has at least 6 characters. "
                    + "\n   2) Has upper case letter(s) OR number(s) OR special character(s)"
          + "\n- Weak Password: <the rest>");
            tx.setEditable(false);
            JScrollPane pane = new JScrollPane(tx, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            pane.setPreferredSize(new Dimension(200, 150));
            passPn.add(pane, BorderLayout.CENTER); 
            
            add(passPn);
            
            
            //Button Panel
            JPanel btPn = new JPanel();
            btPn.add(createBt);
            btPn.add(clearBt);
            btPn.add(cancelBt);

            add(btPn);

            //Handler
            LoginHandler handler = new LoginHandler();
            createBt.addActionListener(handler);
            clearBt.addActionListener(handler);
            cancelBt.addActionListener(handler);
            nameField.addKeyListener(handler);
            idField.addKeyListener(handler);
            month.addKeyListener(handler);
            day.addKeyListener(handler);
            year.addKeyListener(handler);
            userField.addKeyListener(handler);
            passField.addKeyListener(handler);
            cPassField.addKeyListener(handler);
            nameField.addMouseListener(new TextFieldListener(nameField));
            idField.addMouseListener(new TextFieldListener(idField));
            month.addMouseListener(new BoxListener(month));
            day.addMouseListener(new BoxListener(day));
            year.addMouseListener(new BoxListener(year));
            userField.addMouseListener(new TextFieldListener(userField));
            passField.addMouseListener(new TextFieldListener(passField));
            cPassField.addMouseListener(new TextFieldListener(cPassField));
            passField.addCaretListener(new PasswordListener());

        }

        /**
         * 
         */
        public void clearForm()
        {
            createAccPn.nameField.setText("");
            createAccPn.idField.setText("");
            createAccPn.month.setSelectedIndex(0);
            createAccPn.day.setSelectedIndex(0);
            createAccPn.year.setSelectedIndex(0);
            createAccPn.userField.setText("");
            createAccPn.passField.setText("");
            createAccPn.cPassField.setText("");
        }

        /**
         * - Very Strong Password: 1) Has at least 6 characters. 2) Has uppercase letter(s) AND number(s) AND special character(s)
         * - Strong Password: 1) Has at least 6 characters. 2a) Has uppercase letter(s) AND number(s).
         * 2b) Has uppercase letter(s) AND special character(s).
         * 2c) Has number(s) AND special character(s).
         * - Fair Password: 1) Has at least 6 characters. 2) Has upper case letter(s) OR number(s)
         * - Weak Password: <the rest>
         */
        private class PasswordListener implements CaretListener
        {
            public void caretUpdate(CaretEvent e)
            {
                switch (passField.getText().length())
                {
                    case 0:
                        passwordStrength.pb.setValue(0);
                        return;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        passwordStrength.pb.setString("Weak");
                        passwordStrength.pb.setForeground(Color.red);
                        passwordStrength.pb.setValue(1);
                        return;
                    default:
                        if ((hasUpper(passField.getText().trim()) 
                                || hasDigit(passField.getText().trim())
                                || hasSpecialChar(passField.getText().trim())) && hasChar(passField.getText().trim()))
                        {
                            passwordStrength.pb.setString("Fair");
                            passwordStrength.pb.setForeground(new Color(255, 153, 0)); //Orange
                            passwordStrength.pb.setValue(2);
                        }
                        if ((hasUpper(passField.getText().trim()) && hasDigit(passField.getText().trim())
                                || hasUpper(passField.getText().trim()) && hasSpecialChar(passField.getText().trim())
                                ||hasSpecialChar(passField.getText().trim()) && hasDigit(passField.getText().trim()))
                                && hasChar(passField.getText().trim()))
                        {
                            passwordStrength.pb.setString("Strong");
                            passwordStrength.pb.setForeground(new Color(255, 255, 0)); //Yellow
                            passwordStrength.pb.setValue(3);  
                        }        
               
                        if (hasUpper(passField.getText().trim()) && hasDigit(passField.getText().trim()) 
                                && hasChar(passField.getText().trim()) && hasSpecialChar(passField.getText().trim()))
                        {
                            passwordStrength.pb.setString("Very Strong");
                            passwordStrength.pb.setForeground(new Color(71, 255, 10)); //Green
                            passwordStrength.pb.setValue(4);
                        }
                        return;
                }                
            }
        }
        
    public boolean hasUpper(String str)
    {
        boolean hasUpper = false;
        for (int n = 0; n < str.length(); n++)
        {
            if (Character.isUpperCase(str.charAt(n)))
                return true;
        }
        return hasUpper;
    }
    
    public boolean hasDigit(String str)
    {
        boolean hasDigit = false;
        for (int n = 0; n < str.length(); n++)
        {
            if (Character.isDigit(str.charAt(n)))
                return true;
        }
        return hasDigit;
    }
    
    public boolean hasSpecialChar(String str)
    {
        boolean hasSpecial = false;
        for (int n= 0; n < str.length(); n++)
        {
            if (!Character.isLetterOrDigit(str.charAt(n)))
                return true;
        }
        return hasSpecial;
    }
    
    public boolean hasChar(String str)
    {
        boolean hasChar = false;
        for (int n= 0; n < str.length(); n++)
        {
            if (Character.isLetter(str.charAt(n)))
                return true;
        }
        return hasChar;
    }
    protected class PasswordStrengthBar extends JPanel
    {

        JProgressBar pb = new JProgressBar(0, 4);

        public PasswordStrengthBar()
        {
            add(new JLabel("Password Strength"));
            pb.setValue(0);
            add(pb);      
            pb.setStringPainted(true);
            
        }
    }
    }
    
    /**
     * Load questions from text file to qComboBox (used in Dropdown) 
     * and to qArray (used in Completion)
     */
    public void loadQList()
    {
        int count = 1;
        try
        {
            FileInputStream fstream = new FileInputStream("C:\\QWallet\\QuestionList.txt");
            DataInputStream da = new DataInputStream(fstream);
            BufferedReader in = new BufferedReader(new InputStreamReader(da));
            String line;
            while ((line = in.readLine()) != null)
            {
                if (line.contains("?"))
                {
                    qComboBox.addItem(line);
                    qArray.add(line);
                }
            }

        } catch (EOFException eofe)
        {
        } catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "File Not Found");
            e.printStackTrace();

        }

    }

    /**
     * 
     * @param Question 1
     * @param Question 2
     * @return true if the two are the same
     */
    public boolean duplicateQ(String q1, String q2)
    {
        boolean dup = false;

        if (q1.compareToIgnoreCase(q2) == 0)
        {
            return true;
        }
        return dup;
    }


}