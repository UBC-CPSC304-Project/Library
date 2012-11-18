package ca.cs304.client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Overdue extends Transaction{

	public Overdue(Connection connection) {
		super(connection);
		
	}

	@Override
	public ResultSet execute(List<String> parameters) {

		try {
			ps = connection.prepareStatement("SELECT Bo.name, B.name)" +
												 "FROM BookCopy B, Borrowing Bor, Borrower Bo" +
												 "WHERE (Bo.bid = Bor.bid) " +
												 	"AND (B.callNumber = Bor.callNumber)" +
												 	"AND (Bor.inDate < getDate())" +
												 "GROUP BY Bo.name DESC");
			
			rs=ps.executeQuery();
			
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
		return rs;
	}

}