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

		String year = parameters.get(0);

		try {
			ps = connection.prepareStatement("SELECT B.callNumber, COUNT(DISTINCT Bor.borid) Borrows " +
											"FROM Book B, Borrowing Bor " +
											"WHERE (B.callNumber = Bor.callNumber) " +
											"AND (SUBSTR(Bor.outDate, 7, 4)) = ? " +
											"GROUP BY B.callNumber");

			ps.setString(1, year);

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
