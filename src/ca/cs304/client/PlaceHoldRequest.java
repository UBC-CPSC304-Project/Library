package ca.cs304.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class PlaceHoldRequest extends Transaction {

	public PlaceHoldRequest(Connection connection) {
		super(connection);
	}

	@Override
	public ResultSet execute(List<String> parameters) {
		PreparedStatement ps;
		
		//get Borrower email
		try {
			ps = connection.prepareStatement("SELECT emailAddress FROM borrower");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
