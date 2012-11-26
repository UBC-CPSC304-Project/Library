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

			ps = connection.prepareStatement("SELECT B.borid, B.callNumber, B.outDate, B.inDate " +
					"FROM Borrowing B, BookCopy BC " +
					"WHERE B.bid = ? " +
					"AND (B.callNumber = BC. callNumber) " +
					"AND (B.copyNo = BC.copyNo) " +
					"AND (BC.status = 'out')");

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
