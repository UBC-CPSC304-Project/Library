package ca.cs304.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AddBorrower extends Transaction{

	public AddBorrower(Connection connection) {
		super(connection);
	}

	@Override
	public ResultSet execute(List<String> parameters) {
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
	            
	            System.out.println(ps.toString());


	            ps.executeUpdate();
	            	            
	            connection.commit();

	            ps.close();
		} catch (SQLException ex) {
		    System.out.println("Message: " + ex.getMessage());
		}
			return null;
		
	}

}
