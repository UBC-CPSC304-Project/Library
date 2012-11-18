package ca.cs304.client;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class LibraryCustomerView {
    private Connection con = LibraryLogin.getLogin().getCon();
    JPanel mainPanel = new JPanel();
    JButton onlineButton = new JButton("Make a New Online Purchase");
    JButton registerButton = new JButton("Customer Registration");

    JPanel onlinePanel = new JPanel();
    JPanel registerPanel = new JPanel();

    public LibraryCustomerView(){

    }
    public JPanel loadCustomer() {
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        mainPanel.setLayout(gb);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        onlineButton.setBackground(Color.white);
        registerButton.setBackground(Color.white);


        onlineButton.addMouseListener(new CustomerViewButtonListener());
        registerButton.addMouseListener(new CustomerViewButtonListener());

        c.ipady = 20;
        c.ipadx = 50;
        c.anchor = GridBagConstraints.CENTER;
        c.gridy=0;
        c.gridx=0;

        //c.gridwidth = GridBagConstraints.RELATIVE;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 0, 0, 5);
        gb.setConstraints(onlineButton, c);
        mainPanel.add(onlineButton);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx=1;
        gb.setConstraints(registerButton, c);
        mainPanel.add(registerButton);
        c.gridx=2;



        c.gridy=1;
        c.gridx=0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.NONE;

        mainPanel.add(onlinePanel);
        mainPanel.add(registerPanel);
        return mainPanel;

    }

    class CustomerViewButtonListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e){
            JButton source = (JButton) e.getSource();
            if (source == onlineButton){
                showOnlinePurchase();
                onlinePanel.setVisible(true);
                registerPanel.setVisible(false);
                registerPanel.removeAll();
            }
            else if (source == registerButton){
                showRegistration();
                registerPanel.setVisible(true);
                onlinePanel.setVisible(false);
                onlinePanel.removeAll();

            }

        }

    }

    
    public void showRegistration() {
        JLabel welcomeLabel = new JLabel("Welcome. New customers must register to make online purchases.");
        JLabel nameLabel = new JLabel("Name: ");
        JLabel addressLabel = new JLabel("Address: ");
        JLabel phoneLabel = new JLabel("Phone Number: ");
        JLabel idLabel = new JLabel("Choose a User ID (20 characters max): ");
        JLabel passLabel = new JLabel("Password: ");
        final JTextField nameField = new JTextField(20);
        final JTextField addressField = new JTextField(20);
        final JTextField phoneField = new JTextField(20);
        final JTextField idField = new JTextField(20);
        final JTextField passField = new JTextField(20);

        JButton addButton = new JButton("Register");
        addButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    if (processRegistration(nameField.getText().trim(), addressField.getText().trim(),
                            phoneField.getText().trim(), idField.getText().trim(), 
                            passField.getText().trim())){
                        JOptionPane.showMessageDialog(null, "Registration successful. Thank you for registering");
                        nameField.setText("");
                        addressField.setText("");
                        phoneField.setText("");
                        idField.setText("");
                        passField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "User ID is already in use by another customer. Please choose another one.");
                        idField.setText("");
                    }
                } catch (HeadlessException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    e.printStackTrace();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    e.printStackTrace();
                }
            }

        });


        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        registerPanel.setLayout(gb);
        registerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 0;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(welcomeLabel, c);
        registerPanel.add(welcomeLabel);
        
        c.anchor = GridBagConstraints.WEST;
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(nameLabel, c);
        registerPanel.add(nameLabel);

        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 0;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(nameField, c);
        registerPanel.add(nameField);

        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(addressLabel, c);
        registerPanel.add(addressLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 0;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(addressField, c);
        registerPanel.add(addressField);
        
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(phoneLabel, c);
        registerPanel.add(phoneLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 0;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(phoneField, c);
        registerPanel.add(phoneField);
        
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(idLabel, c);
        registerPanel.add(idLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 0;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(idField, c);
        registerPanel.add(idField);
        
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(passLabel, c);
        registerPanel.add(passLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 0;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(passField, c);
        registerPanel.add(passField);

        c.fill = GridBagConstraints.HORIZONTAL;
        gb.setConstraints(addButton, c);
        registerPanel.add(addButton);


    }
    public boolean processRegistration(String name, String address, String phoneNumber, String id, String password ) throws SQLException{
        Statement stmt = con.createStatement();

        PreparedStatement rc;
        ResultSet cursor = stmt.executeQuery("SELECT cid FROM customer WHERE cid = \'" + id + "\'");
        //if id is already registered
        if(cursor.next()){
            //alert customer to choose new id in gui
            return false;
        }
        else{
            rc = con.prepareStatement("INSERT INTO CUSTOMER VALUES(?,?,?,?,?)");
            rc.setString(1,id);
            rc.setString(2, password);
            rc.setString(3,name);
            rc.setString(4, address);
            rc.setString(5,phoneNumber);
            rc.execute();
            return true;
        }

    }
    
    public void showOnlinePurchase() {
        // TODO Auto-generated method stub
    }
    
    public void processOnlinePurchase(){
    }
}