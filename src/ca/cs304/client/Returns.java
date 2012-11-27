package ca.cs304.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 
 * @author Rabiyah
 * @param callnumber
 */
public class Returns extends Transaction{

	public Returns(Connection connection) {
		super(connection);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ResultSet execute(List<String> parameters) {

		// today's date
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String sDate = dateFormat.format(calendar.getTime());

		String callNo = parameters.get(0);
		String copyNo = parameters.get(1);
		ResultSet rs = null;
		PreparedStatement ps = null;

		//        // Get borrower id and name of everyone who borrowed book in descending order according to date
		//        // Giving us info about latest borrower to borrow book
		//        try{
		//        	ps = connection.prepareStatement("SELECT b.bid, b.name, bk.outDate, bk.inDate" +
		//        										"FROM Borrower b, Borrowing bor, BookCopy bk" +
		//        										"WHERE b.bid = bor.bid" +
		//        											"AND bor.callNumber = bk.callNumber AND bk.callNumber = ?" +
		//        											"AND bk.copyNo =?" +
		//        											"HAVING (SELECT MAX(bor.borid) FROM bookCopy bk" +
		//        											"WHERE  bor.callNumber = bk.callNumber AND bk.callNumber =? AND bk.copyNo = ?)");
		//        	ps.setString(1, callNo);
		//        	ps.setString(2, copyNo);
		//
		//        	rs = ps.executeQuery();
		//        	
		//        } catch(SQLException ex){
		//        	System.out.println("Message: " + ex.getMessage());
		//        }

		try{

			// Reset Book Copy status to 'in'
			ps = connection.prepareStatement("UPDATE BookCopy " +
					"SET status= 'in' WHERE callNumber = ? and copyNo = ?");
			ps.setString(1, callNo);
			ps.setString(2, copyNo);

			int rowCount = ps.executeUpdate();

			if (rowCount > 0) {
				System.out.println("Book Copy Returned: " + callNo + ", " + copyNo);
			}
			else {
				System.out.println("Book Copy cannot be returned");
			}

		} catch(SQLException e){
			System.out.println("Message: " + e.getMessage());
		}

		/*Check if book is reserved*/
		try{
			ps = connection.prepareStatement("SELECT hid, bid FROM holdRequest h WHERE h.callNumber = ?");

			ps.setString(1, callNo);

			rs = ps.executeQuery();

			if (rs.next()) {
				String bid = rs.getString("bid");
				System.out.println("This book is on hold for: " + bid);

				try {
					//update bookcopy to set it to on-hold
					ps = connection.prepareStatement("UPDATE bookcopy SET status = 'on-hold' " +
							"WHERE callNumber = ? AND copyNo = ?");

					ps.setString(1, callNo);
					ps.setString(2, copyNo);

					int rowCount = ps.executeUpdate();

					if (rowCount > 0) {
						System.out.println("Book Copy Held: " + callNo + ", " + copyNo);
					}
					else {
						System.out.println("Book Copy cannot be held");
					}

					connection.commit();
					ps.close();

					// email borrower who made the hold request
					ps = connection.prepareStatement("SELECT emailAddress " +
							"FROM Borrower Bor " +
							"WHERE bid = ?");

					ps.setString(1, bid);

					rs = ps.executeQuery();
					if (rs.next()) {
						String email = rs.getString("emailAddress");
						System.out.println("Sent an email to: " + email);
					}
				}
				catch (SQLException ex) {
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
			}
			else {
				System.out.println("There are no hold requests for this book");
			}

		}
		catch(SQLException e){
			e.printStackTrace();
		}

		return rs;
	}
}
