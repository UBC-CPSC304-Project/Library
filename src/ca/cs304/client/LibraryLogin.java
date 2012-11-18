package ca.cs304.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;


/**
 * Login code taken entirely from branch.java tutorial example
 * with adjustments, and tailored to project requirements
 * 
 * Constructs login window and loads JDBC driver
 * 
 */ 

public class LibraryLogin implements ActionListener{
    static LibraryLogin mainLogin;
     private Connection con;
    // user is allowed 3 login attempts
    private int loginAttempts = 0;

    // components of the login window
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JFrame mainFrame;
    private JPanel contentPane;
    private JLabel errorLabel;

    public LibraryLogin(){
        mainLogin = this;
        mainFrame = new JFrame("AMS Login");
        
        JLabel welcomeLabel = new JLabel("<html><center>Welcome to Allegro Music Store Database!<br/>Please login to continue.</center></html>");
        errorLabel = new JLabel();
        JLabel usernameLabel = new JLabel("Enter username: ");
        JLabel passwordLabel = new JLabel("Enter password: ");

        errorLabel.setForeground(Color.red);
        errorLabel.setFont(new Font(null, Font.ITALIC, 10));
        usernameField = new JTextField(10);
        passwordField = new JPasswordField(10);
        passwordField.setEchoChar('*');

        JButton loginButton = new JButton("Log In");
        
        contentPane = new JPanel();
        mainFrame.setContentPane(contentPane);
        
        contentPane.setBackground(Color.white);

        // layout components using the GridBag layout manager

        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        c.gridwidth = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets (0, 10, 10, 0);
        gb.setConstraints(welcomeLabel, c);
        contentPane.add(welcomeLabel);
        
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets (0, 5, 5, 0);
        gb.setConstraints(errorLabel , c);
        contentPane.add(errorLabel);
        errorLabel.setVisible(false);
        
        // place the username label 
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(usernameLabel, c);
        contentPane.add(usernameLabel);

        // place the text field for the username 
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(usernameField, c);
        contentPane.add(usernameField);

        // place password label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(0, 10, 10, 0);
        gb.setConstraints(passwordLabel, c);
        contentPane.add(passwordLabel);

        // place the password field 
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 0, 10, 10);
        gb.setConstraints(passwordField, c);
        contentPane.add(passwordField);

        // place the login button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(loginButton, c);
        contentPane.add(loginButton);

        // register password field and OK button with action event handler
        passwordField.addActionListener(this);
        loginButton.addActionListener(this);

        // anonymous inner class for closing the window
        mainFrame.addWindowListener(new WindowAdapter() 
        {
            public void windowClosing(WindowEvent e) 
            { 
                System.exit(0); 
            }
        });

        // size the window to obtain a best fit for the components
        mainFrame.pack();

        // center the frame
        Dimension d = mainFrame.getToolkit().getScreenSize();
        Rectangle r = mainFrame.getBounds();
        mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

        // make the window visible
        mainFrame.setVisible(true);

        // place the cursor in the text field for the username
        usernameField.requestFocus();

        try 
        {
            // Load the Oracle JDBC driver
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
            System.exit(-1);
        }
    }


    /*
     * connects to Oracle database named ug using user supplied username and password
     */ 
    private boolean connect(String username, String password)
    {
        String connectURL = "jdbc:oracle:thin:@localhost:1522:ug"; 

        try 
        {
            setCon(DriverManager.getConnection(connectURL,username,password));
            return true;
        }
        catch (SQLException ex)
        {
            errorLabel.setText("Please enter login information.");
            errorLabel.setVisible(true);
            mainFrame.pack();
            return false;
        }
    }


    /*
     * event handler for login window
     */ 
    public void actionPerformed(ActionEvent e) 
    {
        if ( connect(usernameField.getText(), String.valueOf(passwordField.getPassword())) )
        {
            // if the username and password are valid, 
            // remove the login window and display a text menu 
            mainFrame.dispose();
            showInterface();     
        }
        else
        {
            loginAttempts++;

            if (loginAttempts >= 3)
            {
                mainFrame.dispose();
                System.exit(-1);
            }
            else
            {
                errorLabel.setText("Login information was incorrect. Please try again.");
                errorLabel.setVisible(true);
                mainFrame.pack();
                // clear the password
                passwordField.setText("");
            }
        }             

    }
    
    private void showInterface() {
        LibraryMainView mainView = new LibraryMainView();
        mainView.pack();
        // center window on screen
        Dimension d = mainView.getToolkit().getScreenSize();
        Rectangle r = mainView.getBounds();
        mainView.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
        // Initially, the JFrame is invisible. This line
        // makes the window visible.
        mainView.setVisible(true);
    }


    public static void main(String args[])
    {
        new LibraryLogin();
    }
    
    public static LibraryLogin getLogin(){
        return mainLogin;
    }


    public void setCon(Connection con) {
        this.con = con;
    }


    public Connection getCon() {
        return con;
    }
}