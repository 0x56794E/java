/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qwallet;

import frames.MainFrame;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

/**
 * QWallet Project - Glasgow Caledonian University  Summer Internship
 * MySQL Account info
 * Database name: QWallet
 * User: qwalletu
 * Password: qwalletp
 * @Date: August 10th, 2011
 * @author Vy Thuy Nguyen
 */
public class MySQL
{
    //JDBC database URL, username and password
    MainFrame mainFr;
    
    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/QWallet";
    static final String USER = "qwalletu";
    static final String PASSWORD = "qwalletp";
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    ResultSetMetaData metaData = null;
    
    PreparedStatement insertNewRecord = null;
    
    public MySQL(MainFrame fr)
    {
        mainFr = fr;
        //Connect to database QWallet and querry database
        try
        {
            //Establish connection
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            
            //Create statement for querrying database
            statement = connection.createStatement();
            
           
        } catch (SQLException sqlExc)
        {
            sqlExc.printStackTrace();
        }

    }
    
    
    /**
     * 
     * @param user
     * @return true if user has already been used
     */
    public boolean userUsed(String user)
    {
        boolean exist = false;
        try
        {
            resultSet = statement.executeQuery("SELECT User FROM UserInfo;");
            
            while (resultSet.next())
            {
                if (user.trim().compareToIgnoreCase(resultSet.getObject(1).toString()) == 0)
                        return true;
            }
        }
        catch (SQLException exc)
        {
            exc.printStackTrace();
        }
        return exist;        
    }
    
    public boolean addNewUser(String user, String pass, String name, String passport, String dob)
    {
        boolean success = true;
        
 
        //Check if username has already been taken
        if (userUsed(user))
        {
            JOptionPane.showMessageDialog(null, "Username is Not Available.\nPlease Select Another One",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        
        
        try
        {
            statement.executeUpdate("INSERT INTO UserInfo " +
                    "(User, Password, Name, Passport, DOB) " +
                    "VALUES (\"" + user + "\", \"" + pass + "\", \"" + name + "\", \""
                    + passport + "\", \"" + dob + "\");");
        }
        catch (SQLException exc)
        {
            JOptionPane.showMessageDialog (null, exc.getMessage(), "ERROR!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return success;
    }
    
    /**
     * Save user and password for next login
     * @param user
     * @param pass 
     */
    public void saveUP(String user, String pass, boolean auto)
    {
        try
        {
            statement.executeUpdate("DELETE FROM SavedUandP;");
            statement.executeUpdate("INSERT INTO SavedUandP (User, Password, AutoSignin) VALUES (\"" +
                    user.trim() + "\", \"" + pass.trim() + "\", " + auto + ");");
        }
        catch (SQLException exc)
        {
            exc.printStackTrace();
            return;
        }
        
    }
    
    /**
     * 
     * @return a string array containing User and Password, respectively
     */
    public String[] getSavedUP()
    {
        String arr[] = new String[2];
      
        try
        {
            resultSet = statement.executeQuery("SELECT * FROM SavedUandP;");
            while (resultSet.next())
            {
                arr[0] = resultSet.getObject("User").toString();
                arr[1] = resultSet.getObject("Password").toString();
            }
            
        }
        catch (SQLException exc)
        {
            exc.printStackTrace();
        }
        
        return arr;
    }
    
    /**
     * 
     * @return true if the user previously chose Auto-Signin
     */
    public boolean autoSigin()
    {
        boolean auto = false;
        
        try
        {
            resultSet = statement.executeQuery("SELECT AutoSignin FROM SavedUandP;");
            while (resultSet.next())
                auto = resultSet.getBoolean(1);
        }
        catch (SQLException exc)
        {
            exc.printStackTrace();
        }
        return auto;
    }
    
    /**
     * 
     * @return true if the user previously chose to save User and Password
     */
    public boolean hasSavedUP()
    {
        boolean saved = false;
                try
        {
            resultSet = statement.executeQuery("SELECT * FROM SavedUandP;");
            while (resultSet.next())
                return true;
        }
        catch (SQLException exc)
        {
            exc.printStackTrace();
        }
        return saved;
        
    }
    
    /**
     * Empty table SavedUandP in MySQL Database
     */
    public void deleteSavedUP()
    {
        try
        {
            statement.executeUpdate("DELETE FROM SavedUandP;");
        }
        catch (SQLException exc)
        {
            exc.printStackTrace();
        }
        
    }
    
    /**
     * 
     * @param user
     * @return name of the person whose user is given
     */
    public String getName(String user)
    {
        String name = "";
        try
        {
            resultSet = statement.executeQuery("SELECT Name FROM UserInfo WHERE User = '"+ user.trim() +"';");
            while (resultSet.next())
                name = resultSet.getObject(1).toString();
        }
        catch (SQLException exc)
        {
            exc.printStackTrace();
        }
        return name;
    }
    
    /**
     * 
     * @param user
     * @param pass
     * @return true if an account with provided user and password exists
     */
    public boolean correctUP(String user, String pass)
    {
        boolean correct = false;
                
        try
        {
            resultSet = statement.executeQuery("SELECT User FROM UserInfo WHERE Password = '"+ pass.trim() +"';");
            while (resultSet.next())
            {
                 if (user.trim().compareToIgnoreCase(resultSet.getObject(1).toString()) == 0)
                     return true;
            }
        }
        catch (SQLException exc)
        {
            exc.printStackTrace();
        }
        return correct;
    }
   
    /**
     * 
     * @param user
     * @return true if the user has any stored questions
     */
    public boolean hasQ(String user)
    {
        boolean hasQ = false;
            
        try
        {
            resultSet = statement.executeQuery("SELECT Question FROM QandA WHERE User = '" + user.trim() +"';");
            while (resultSet.next())
                return true;
        }
        catch (SQLException exc)
        {
            return false;
        }
        
        return hasQ;
    }
    
    /**
     * Save the question and answer to database
     * @param user
     * @param question
     * @param answer 
     */
    public void saveQ(String user, String question, String answer)
    {
        try
        {
            statement.executeUpdate("INSERT INTO QandA " +
                    "(User, Question, Answer) " +
                    "VALUES (\"" + user + "\", \"" + question + "\", \"" + answer + "\");");
        }
        catch (SQLException exc)
        {
            JOptionPane.showMessageDialog (null, exc.getMessage(), "ERROR!", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    
    /**
     * 
     * @param user
     * @return an array list containing all stored questions of the user
     */
    public java.util.ArrayList<String> getQ (String user)
    {
        java.util.ArrayList<String> q = new java.util.ArrayList();
        
        try
        {
            resultSet = statement.executeQuery("SELECT Question FROM QandA WHERE User = '"+ user.trim() +"';");
            while (resultSet.next())
            {                                
                q.add(resultSet.getObject(1).toString());
                
            }
        }
        catch (SQLException exc)
        {
            exc.printStackTrace();
        }
          
        return q;
    }
    
    /**
     * Load Questions and Answers of the user to the array list provided
     * @param user
     * @param qa 
     */
    public void getQA(String user, java.util.ArrayList<QandA> qa)
    {       
        try
        {
            resultSet = statement.executeQuery("SELECT Question, Answer FROM QandA WHERE User = '"+ user.trim() +"';");
            while (resultSet.next())
            {                                
                qa.add(new QandA(resultSet.getObject(1).toString(), resultSet.getObject(2).toString()));
                
            }
        }
        catch (SQLException exc)
        {
            exc.printStackTrace();
        }
        
    }
    
    /**
     * 
     * @param user
     * @param q
     * @return true if the question has already been used
     */
    public boolean duplicateQ (String user, String q)
    {
        boolean dup = false;
        java.util.ArrayList<String> questions = getQ(user);
        for (int n = 0; n < questions.size(); n++)
        {
            if (q.trim().compareToIgnoreCase(questions.get(n)) == 0)
            {
                return true;                
            }
        }
        return dup;        
    }
}
