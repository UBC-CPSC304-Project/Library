package ca.cs304.client;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;

public class CheckOutItems{

	private Connection con;


	public String execute(int bid, int callNo) throws Exception {
		
		//get today's date
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String sDate = dateFormat.format(calendar.getTime());
		
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String duedate = null;
		
		
		try {
			
			//check for fines
			ps = con.prepareStatement("SELECT amount FROM Fine F, Borrowing B "
					+ "WHERE F.paidDate IS NULL AND B.borid=F.borid AND B.bid=?");
			
			ps.setInt(1, bid);
			rs = ps.executeQuery();

			// Throw fine message if fine exists
			if (rs.next()) {
				int fine = rs.getInt("amount");
				throw new Exception("Borrower ID " + bid
					+ " currently has a fine of $" + fine
					+ " and is blocked from borrowing.");
				}
			
			// Check if book is available for borrowing
			ps = con.prepareStatement("SELECT COUNT(*) AS 'present' FROM Book WHERE callNumber=?");
			ps.setInt(1, callNo);
			rs = ps.executeQuery();

			if (!rs.next() || 0 >= rs.getInt("in"))
				throw new Exception("Unknown call number");
			
			// Create borrowing record
			ps = con.prepareStatement("INSERT into Borrowing(bid, callNumber, copyNo, outDate) "
			+ "values (?,?,?,?)");
			
			ps.setInt(1, bid);
			ps.setInt(2, callNo);
			ps.setString(3, sDate);
			duedate = sDate;


		
		} finally {
			con.commit();
			if (con != null)
			con.close();
			}

			return duedate;

}
}
