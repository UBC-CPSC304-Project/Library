package ca.cs304.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

public class PlaceHoldRequest extends Transaction {

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
		Calendar calendar = Calendar.getInstance(); 	
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String issuedDate = dateFormat.format(calendar.getTime());
		
		String callNumber = parameters.get(0);
		String bid = parameters.get(1);

		
		try {
			ps = connection.prepareStatement("SELECT emailAddress FROM borrower");
			
			ps.setString(2, bid);
			ps.setString(3, callNumber);
			ps.setString(4, issuedDate);
			
		} catch (SQLException ex) {
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
		return null;
	}

}
