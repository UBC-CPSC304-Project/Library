package ca.cs304.client;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

public class CheckOutItems extends Transaction{

	private List<String> borrowedItems;

	public CheckOutItems(Connection connection) {
		super(connection);
	}


	/**
	 * (non-Javadoc)
	 * @param String bid, String callNumber
	 */

	@Override
	public ResultSet execute(List<String> parameters) {
		
		borrowedItems = new ArrayList<String>();
		
		//get today's date
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String outDate = dateFormat.format(calendar.getTime());
		
		ResultSet rs = null;
		PreparedStatement ps;

		String bid = parameters.get(0);
		List<String> callNumbers = tokenizeString(parameters.get(1));
		
		int params = callNumbers.size()-1;
		for (int i = 1; i <= params; i++) {
		  String callNo = callNumbers.get(i);
		  checkOutItem(bid, callNo);
		}
		

		try {
			ps = connection.prepareStatement("SELECT callNumber, inDate FROM Borrowing " +
					"WHERE outDate=? " +
					"AND bid=?");
			ps.setString(1, outDate);
			ps.setString(2, bid);
			rs = ps.executeQuery();

			connection.commit();
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

	
	
	public void checkOutItem(String bid, String callNo){
		
		//get today's date
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String outDate = dateFormat.format(calendar.getTime());
		
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
				borrowedItems.add(callNo);
				return;
			}

			// Check if book exists
			ps = connection.prepareStatement("SELECT COUNT(*) present FROM Book WHERE callNumber=?");
			ps.setString(1, callNo);
			rs = ps.executeQuery();

			if (!rs.next() || 0 >= rs.getInt("present")) {
				System.out.print("Unknown call number");
				return;
			}
			
			System.out.print("\ncheckpoint 2: book present");
			
			
			// check if bid has hold request, get hold request if it does
			ps = connection.prepareStatement("SELECT hid FROM HoldRequest HR "
					+ "WHERE HR.bid=? AND HR.callNumber=?");
			ps.setString(1, bid);
			ps.setString(2, callNo);
			rs = ps.executeQuery();
			String hid;
			if (rs.next()){
				hid = rs.getString("hid");
			}
			else
				hid = "none";
			
			// update the copy to status 'in' and deletes hold request
			if (hid != "none") {
			ps = connection.prepareStatement("UPDATE BookCopy SET status='in' WHERE status='on-hold' AND callNumber=?");
			ps.setString(1, callNo);
			ps.executeUpdate();
			
			//delete the hold request
			ps = connection.prepareStatement("DELETE FROM HoldRequest WHERE hid=?");
			ps.setString(1, hid);
			ps.executeUpdate();
			}


			// Get copy of book
			ps = connection.prepareStatement("SELECT copyNo FROM bookCopy "
					+ "WHERE status='in' AND callNumber=?");
			ps.setString(1, callNo);
			rs = ps.executeQuery();

			String copyNo;
			if (rs.next()) {
				copyNo = rs.getString(1);
			}
			else copyNo = "none";

			if (copyNo == "none") {
				System.out.print("No available copies!");
				borrowedItems.add(callNo);
				return;
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
					+ "SET status = 'out' WHERE callNumber=? AND copyNo=?");

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
	}


	public List<String> getBorrowedItems() {
		return borrowedItems;
	}


	public void setBorrowedItems(List<String> borrowedItems) {
		this.borrowedItems = borrowedItems;
	}
	
	/**
	 * Parses paramters in the format: 
	 * "Test, test, love, books"
	 * It converts that into a List<String> (of four elements)
	 * @param String a comma separated list of words
	 * @return List<String> A parsed list of parameters
	 */
	private List<String> tokenizeString(String parameterInput) {
		List<String> parameters = new ArrayList<String>();

		// String Tokenizer to parse line to parameters - Inspired by CPSC 310 Member Chris Thomson
		StringTokenizer parametersTokenizer = new StringTokenizer(parameterInput, ", ");
		while (parametersTokenizer.hasMoreTokens()) {
			parameters.add(parametersTokenizer.nextToken());
		}
		return parameters;
	}
}


