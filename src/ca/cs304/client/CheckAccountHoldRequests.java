package ca.cs304.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CheckAccountHoldRequests extends Transaction {

	public CheckAccountHoldRequests(Connection connection) {
		super(connection);
	}

	@Override
	public ResultSet execute(List<String> parameters) {
		
		String bid = parameters.get(0);
		PreparedStatement ps;
		ResultSet resultSet = null;
		
		try { 
			ps = connection.prepareStatement("SELECT h.hid, h.callNumber, h.issuedDate " +
											"FROM HoldRequest h " +
											"WHERE h.bid = ? ");
			
			ps.setString(1, bid);
			resultSet = ps.executeQuery();			

		}
		catch (SQLException ex) {
		    System.out.println("Message: " + ex.getMessage());
		}
		
		return resultSet;
	}
}
