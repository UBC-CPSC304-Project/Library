package ca.cs304.client;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

public class CheckOutItems extends Transaction{


	public CheckOutItems(Connection connection) {
		super(connection);
	}


	/**
	 * (non-Javadoc)
	 * @param String bid, String callNumber, String copyNo
	 */

	@Override
	public ResultSet execute(List<String> parameters) {
		//get today's date
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String outDate = dateFormat.format(calendar.getTime());

		int bid = Integer.parseInt(parameters.get(0));
		int callNo = Integer.parseInt(parameters.get(1));
		ResultSet rs = null;
		PreparedStatement ps;



		try {

			//check for fines
			ps = connection.prepareStatement("SELECT amount FROM Fine F, Borrowing B "
					+ "WHERE F.paidDate IS NULL AND B.borid=F.borid AND B.bid=?");

			ps.setInt(1, bid);
			rs = ps.executeQuery();
			
			System.out.print("checkpoint 1");

			// Throw fine message if fine exists
			if (rs.next()) {
				int fine = rs.getInt(1);
				System.out.print("Borrower ID " + bid
						+ " currently has a fine of $" + fine
						+ " and is blocked from borrowing.");
				return null;
			}

			// Check if book exists
			ps = connection.prepareStatement("SELECT COUNT(*) AS 'present' "+
					"FROM Book WHERE callNumber=?");
			ps.setInt(1, callNo);
			rs = ps.executeQuery();

			if (!rs.next() || 0 >= rs.getInt("present")) {
				System.out.print("Unknown call number");
			}
			
			System.out.print("checkpoint 2");


			// Get copy of book
			ps = connection.prepareStatement("SELECT copyNo FROM bookCopy"
					+ "WHERE status='in' AND callNumber=?");
			ps.setInt(1, callNo);
			rs = ps.executeQuery();

			int copyNo;
			if (rs.next()) {
				copyNo = rs.getInt(1);
			}
			else copyNo = -1;

			if (copyNo == -1) {
				System.out.print("No available copies!");
				return null;
			}
			
			System.out.print("checkpoint 3");

			//generate due date
			ps = connection.prepareStatement("SELECT bookTimeLimit FROM Borrower B, BorrowerType BT" +
					"WHERE B.type = BT.type" +
					"AND B.bid=?");
			ps.setInt(1, bid);
			rs = ps.executeQuery();
			
			System.out.print("checkpoint 4");

			int bookTimeLimit = rs.getInt(1);
			calendar.add(calendar.DAY_OF_MONTH, (bookTimeLimit*7));
			String dueDate = dateFormat.format(calendar.getTime());


			// Create borrowing record
			ps = connection.prepareStatement("INSERT into Borrowing "
					+ "values (?,?,?,?,?,?)");


			ps.setInt(1, bid);
			ps.setInt(2, callNo);
			ps.setInt(3, copyNo);
			ps.setString(4, outDate);
			ps.setString(5, dueDate);
			
			System.out.print("checkpoint 5");


			//update book copy to "out"
			ps = connection.prepareStatement("UPDATE BookCopy "
					+ "SET status = 'out' WHERE callNumber=? AND copyNo=?");

			ps.setInt(1, callNo);
			ps.setInt(2, copyNo);
			ps.executeUpdate();
			
			System.out.print("checkpoint 6");

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
		return rs;
	}
}


