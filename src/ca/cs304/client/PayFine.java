package ca.cs304.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

public class PayFine extends Transaction {

	public PayFine(Connection connection) {
		super(connection);
	}

	@Override
	public ResultSet execute(List<String> parameters) {
		
		//get today's date
		Calendar calendar = Calendar.getInstance(); 	
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String sDate = dateFormat.format(calendar.getTime());
		
		String fid = parameters.get(0);
		String paidDate = sDate;
		
		try {
			ps = connection.prepareStatement("UPDATE Fine SET paidDate = ? WHERE fid = ?");
			
			ps.setString(1, paidDate);
			ps.setString(2, fid);
			
			int numOfRows = ps.executeUpdate();
			
			if (numOfRows > 0) {
				System.out.println("Fine " + fid + " has been paid.");
			}
			else {
				System.out.println("Fine " + fid + " not found!");
			}
			
			connection.commit();
			ps.close();
			
		}
		catch (SQLException ex)
		{
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
