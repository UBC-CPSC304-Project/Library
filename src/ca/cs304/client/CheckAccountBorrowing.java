package ca.cs304.client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/*
 * Check his/her account.
 * The system will display the items the borrower has currently borrowed
 * and not yet returned, any outstanding fines and the hold requests that
 * have been placed by the borrower.
 */

public class CheckAccountBorrowing extends Transaction{

	public CheckAccountBorrowing(Connection connection) {
		super(connection);
	}

	@Override
	public ResultSet execute(List<String> parameters) {
		try {

			String bid = parameters.get(0);

			ps = connection.prepareStatement("SELECT borid, callNumber, outDate, inDate " +
					"FROM Borrowing Bor, " +
					"(SELECT MAX(Bor2.borid) latest_borid " +
					"FROM Borrowing Bor2 NATURAL INNER JOIN BookCopy BC " +
					"WHERE BC.status = 'out' " +
					"GROUP BY callNumber, copyNo) " +
					"WHERE Bor.borid = latest_borid " +
					"AND bid = ?");

			ps.setString(1, bid);  

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
