package ca.cs304.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Borrower extends Table{
    
    public Borrower(Connection connection) {
        super(connection);
    }
    

    @Override
    public void delete(List<String> parameters) {
        String bid = parameters.get(0);
        PreparedStatement ps;

        try
        {
            ps = connection.prepareStatement("DELETE FROM Borrower WHERE bid = ?");
            ps.setString(1, bid);

            int rowCount = ps.executeUpdate();

            if (rowCount == 0) {
                System.out.println("\nBorrower " + bid + " does not exist!");
            }

            connection.commit();

            ps.close();

        }
        catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());

            try 
            {
                connection.rollback();    
            }
            catch (SQLException ex2)
            {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
        
    }

    @Override
    public void insert(List<String> parameters) {
        String bid = parameters.get(0);
        String password = parameters.get(1);
        String name = parameters.get(2);
        String address = parameters.get(3);
        String phone = parameters.get(4);
        String emailAddress = parameters.get(5);
        String sinOrStNo = parameters.get(6);
        String expiryDate = parameters.get(7);
        String type = parameters.get(8);

        PreparedStatement ps;

        try {
            ps = connection.prepareStatement("INSERT INTO Borrower VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            System.out.print("\nBorrower ID: ");
            ps.setString(1, bid);
            
            System.out.print("\nPassword: ");
            ps.setString(2, password);
            
            System.out.print("\nName: ");
            ps.setString(3, name);
            
            System.out.print("\nAddress: ");
            ps.setString(4, address);
            
            System.out.print("\nPhone number: ");
            ps.setString(5, phone);
            
            System.out.print("\nEmail Address: ");
            ps.setString(6, emailAddress);
            
            System.out.print("\nSin or Student Number: ");
            ps.setString(7, sinOrStNo);
            
            System.out.print("\nExpiry Date: ");
            ps.setString(8, expiryDate);
            
            System.out.print("\nType: ");
            ps.setString(9, type);

            ps.executeUpdate();

            connection.commit();

            ps.close();

        }
        catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());

            try 
            {
                connection.rollback();    
            }
            catch (SQLException ex2)
            {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
        
    }

    @Override
    public void display() {
        String bid;
        String password;
        String name;
        String address;
        String phone;
        String emailAddress;
        String sinOrStNo;
        String expiryDate;
        String type;
        Statement statement;
        ResultSet resultSet;
        
        try { 
            statement = connection.createStatement();

            resultSet = statement.executeQuery("SELECT * FROM Borrower");

            // get info on ResultSet
            ResultSetMetaData rsmd = resultSet.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();

            System.out.println(" ");

            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it

                System.out.printf("%-15s", rsmd.getColumnName(i+1));    
            }

            System.out.println(" ");
            
            while(resultSet.next()) {
                bid = resultSet.getString("bid");
                System.out.printf("%-10.10s", bid);
                
                password = resultSet.getString("password");
                System.out.printf("%-10.10s", password);
                
                name = resultSet.getString("name");
                System.out.printf("%-10.10s", name);
                
                address = resultSet.getString("address");
                System.out.printf("%-10.10s", address);

                phone = resultSet.getString("phone");
                System.out.printf("%-10.10s", phone);
                
                emailAddress = resultSet.getString("emailAddress");
                System.out.printf("%-10.10s", emailAddress);
                
                sinOrStNo = resultSet.getString("sinOrStNo");
                System.out.printf("%-10.10s", sinOrStNo);
                
                expiryDate = resultSet.getString("expiryDate");
                System.out.printf("%-10.10s", expiryDate);
                
                type = resultSet.getString("type");
                System.out.printf("%-10.10s", type);
            }
            
              // close the statement; 
              // the ResultSet will also be closed
              statement.close();

        }
        catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        
    }
    
    
}