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
	 * @param String bid, String callNumber
	 */

	@Override
	public ResultSet execute(List<String> parameters) {
		//get today's date
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String outDate = dateFormat.format(calendar.getTime());

		String bid = parameters.get(0);
		String callNo = parameters.get(1);
		ResultSet rs = null;
		PreparedStatement ps;



		try {

			//check for fines
			ps = connection.prepareStatement("SELECT amount FROM Fine F, Borrowing B "
					+ "WHERE F.paidDate IS NULL AND B.borid=F.borid AND B.bid=?");

			ps.setString(1, bid);
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
			ps = connection.prepareStatement("SELECT COUNT(*) present FROM Book WHERE callNumber=?");
			ps.setString(1, callNo);
			rs = ps.executeQuery();

			if (!rs.next() || 0 >= rs.getInt("present")) {
				System.out.print("Unknown call number");
				return null;
			}
			
			System.out.print("\ncheckpoint 2: book present");


			// Get copy of book
			ps = connection.prepareStatement("SELECT copyNo FROM bookCopy "
					+ "WHERE status='available' AND callNumber=?");
			ps.setString(1, callNo);
			rs = ps.executeQuery();

			String copyNo;
			if (rs.next()) {
				copyNo = rs.getString(1);
			}
			else copyNo = "none";

			if (copyNo == "none") {
				System.out.print("No available copies!");
				return null;
			}
			
			System.out.print("\ncheckpoint 3: copyNo = "+ copyNo);
			
			// get bookTimeLimit to determine duedate
			ps = connection.prepareStatement("SELECT bookTimeLimit FROM Borrower B, BorrowerType BT " +
					"WHERE B.type = BT.type " +
					"AND B.bid=?");
			ps.setString(1, bid);
			rs = ps.executeQuery();
			
			System.out.print("\ncheckpoint 4");
			
			if (!rs.next()) {
				System.out.println("\nCannot get book time limit for borrower, using 2 weeks");
				}
			
			int bookTimeLimit = rs.getInt(1);
			
			System.out.print("\ncheckpoint 5: bookTimeLimit = " + bookTimeLimit);
			
			//generate due date
			calendar.add(calendar.DATE, (bookTimeLimit*7));
			String dueDate = dateFormat.format(calendar.getTime());
			
			
			//update book copy to "borrowed"
			ps = connection.prepareStatement("UPDATE BookCopy "
					+ "SET status = 'borrowed' WHERE callNumber=? AND copyNo=?");

			ps.setString(1, callNo);
			ps.setString(2, copyNo);
			ps.executeUpdate();
			
			System.out.print("\ncheckpoint 6: book copy updated to 'borrowed'");


			// Create borrowing record
			ps = connection.prepareStatement("INSERT into Borrowing "
					+ "values ((boridseq.NEXTVAL),?,?,?,?,?)");


			ps.setString(1, bid);
			ps.setString(2, callNo);
			ps.setString(3, copyNo);
			ps.setString(4, outDate);
			ps.setString(5, dueDate);
			ps.executeUpdate();
			

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


