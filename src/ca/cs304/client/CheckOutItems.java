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
		String copyNo = parameters.get(2);
		ResultSet rs = null;
		PreparedStatement ps = null;



		try {

			//check for fines
			ps = connection.prepareStatement("SELECT amount FROM Fine F, Borrowing B "
					+ "WHERE F.paidDate IS NULL AND B.borid=F.borid AND B.bid=?");

			ps.setInt(1, bid);
			rs = ps.executeQuery();

			// Throw fine message if fine exists
			if (rs.next()) {
				int fine = rs.getInt("amount");
				try {
					throw new Exception("Borrower ID " + bid
							+ " currently has a fine of $" + fine
							+ " and is blocked from borrowing.");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// Check if book is available for borrowing
			ps = connection.prepareStatement("SELECT COUNT(*) AS 'present' FROM Book WHERE callNumber=?");
			ps.setInt(1, callNo);
			rs = ps.executeQuery();

			if (!rs.next() || 0 >= rs.getInt("in"))
				try {
					throw new Exception("Unknown call number");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			//generate due date
			ps = connection.prepareStatement("SELECT bookTimeLimit FROM Borrower B, BorrowerType BT" +
					"WHERE B.type = BT.type" +
					"AND B.bid=?");
			ps.setInt(1, bid);
			rs = ps.executeQuery();

			int bookTimeLimit = rs.getInt(1);
			calendar.add(calendar.DAY_OF_MONTH, (bookTimeLimit*7));
			String dueDate = dateFormat.format(calendar.getTime());


			// Create borrowing record
			ps = connection.prepareStatement("INSERT into Borrowing "
					+ "values (?,?,?,?,?,?)");


			ps.setInt(1, bid);
			ps.setInt(2, callNo);
			ps.setString(3, copyNo);
			ps.setString(4, outDate);
			ps.setString(5, dueDate);


			//update book copy to "out"
			ps = connection.prepareStatement("UPDATE BookCopy "
					+ "SET status = 'out' WHERE callNumber=? AND copyNo=?");

			ps.setInt(1, callNo);
			ps.setString(2, copyNo);
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

