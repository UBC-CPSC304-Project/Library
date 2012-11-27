package ca.cs304.client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class PlaceHoldRequest extends Transaction {

	Boolean bookReturned = false;
	
	public PlaceHoldRequest(Connection connection) {
		super(connection);
	}

/*
 * @params (String callNumber, String bid)
 * 
 */
	@Override
	public ResultSet execute(List<String> parameters) {
		//get today's date
		String callNumber = parameters.get(0);
		
		try {
			ps = connection.prepareStatement("SELECT status FROM BookCopy bc, HoldRequest hr WHERE callNumber = ? AND bc.callNumber = hr.callNumber");
			ps.setString(1, callNumber);
			
			while (rs.next()) {
				if (rs.getString("status").equalsIgnoreCase("in")) {
					System.out.println("Hold Request cannot be processed since bookcopy is ");
					return null;
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// no available copies.  If there are available books,  check out items has similar code.  
		HoldRequest hr = new HoldRequest(connection);
			hr.insert(parameters);

		return null;
	}
	
	@Override
	public void closeStatement() {
		//Do nothing, since no statement is opened in transaction
	}
}

