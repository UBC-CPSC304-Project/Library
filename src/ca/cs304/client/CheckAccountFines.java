package ca.cs304.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CheckAccountFines extends Transaction {

	public CheckAccountFines(Connection connection) {
		super(connection);
	}

	@Override
	public ResultSet execute(List<String> parameters) {
		
		String bid = parameters.get(0);
		PreparedStatement ps;
		ResultSet resultSet = null;
		List<String> fines = new ArrayList<String>();
		
		try { 
			ps = connection.prepareStatement("SELECT f.fid, f.amount, bor.callNumber " +
											"FROM Fine f, Borrowing bor " +
											"WHERE (f.borid = bor.borid) " +
											"AND bor.bid = ? " +
											"AND paidDate IS NULL");
			
			ps.setString(1, bid);
			resultSet = ps.executeQuery();			

		}
		catch (SQLException ex) {
		    System.out.println("Message: " + ex.getMessage());
		}
		
		return resultSet;
	}

}
