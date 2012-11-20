package ca.cs304.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class PopularItemsList extends Transaction {

	public PopularItemsList(Connection connection) {
		super(connection);
	}

	@Override
	public ResultSet execute(List<String> parameters) {
		
		int year = Integer.parseInt(parameters.get(0));
		
		try {
			ps = connection.prepareStatement("SELECT COUNT(DISTINCT B.borid)" +
											 "FROM Book B, Borrowing Bor" +
											 "WHERE (B.callNumber = Bor.callNumber) " +
											 	"AND (SUBSTR(B.outDate, 7, 4)) = ?" +
											 "GROUP BY B.callNumber" +
											 "ORDER BY COUNT(DISTINCT borid)");
										
			ps.setInt(1, year);
			
			rs = ps.executeQuery();				
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
		return rs;	
	}
}
