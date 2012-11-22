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
	        String password = parameters.get(0);
	        String name = parameters.get(1);
	        String address = parameters.get(2);
	        String phone = parameters.get(3);
	        String emailAddress = parameters.get(4);
	        String sinOrStNo = parameters.get(5);
	        String expiryDate = parameters.get(6);
	        String type = parameters.get(7);

	        PreparedStatement ps;

	        try {
	            ps = connection.prepareStatement("INSERT INTO Borrower VALUES ((bidseq.NEXTVAL), ?, ?, ?, ?, ?, ?, ?, ?)");

	            ps.setString(1, password);
	            System.out.print("\nPassword: " + password);

	            ps.setString(2, name);
	            System.out.print("\nName: " + name);

	            ps.setString(3, address);
	            System.out.print("\nAddress: " + address);

	            ps.setString(4, phone);
	            System.out.print("\nPhone number: " + phone);

	            ps.setString(5, emailAddress);
	            System.out.print("\nEmail Address: " + emailAddress);

	            ps.setString(6, sinOrStNo);
	            System.out.print("\nSin or Student Number: " + sinOrStNo);
	            
	            ps.setString(7, expiryDate);
	            System.out.print("\nExpiry Date: " + expiryDate);

	            ps.setString(8, type);
	            System.out.print("\nType: " + type);

	            ps.executeUpdate();       
	            connection.commit();

	            ps.close();
		} catch (SQLException ex) {
		    System.out.println("Message: " + ex.getMessage());
		}
			return null;
		
	}

}
