package ca.cs304.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/*
 * Checks overdue items. The system displays a list of the items
 * that are overdue and the borrowers who have checked them out. 
 * The clerk may decide to send an email messages to any of 
 * them (or to all of them).
 */
public class Overdue extends Transaction{

	boolean BookOverdue = false;

	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	 
	public Overdue(Connection connection) {
		super(connection);
		
	}

	@Override
	public ResultSet execute(List<String> parameters) {

		Calendar calendar = Calendar.getInstance(); 	
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String issuedDate = dateFormat.format(calendar.getTime());
		
		String emailAddress = new String();
		String borid = new String();
		
		//ResultSet rs = null;
		
		try {
			ps = connection.prepareStatement("SELECT borid, bid, callNumber, copyNo, inDate, emailAddress " +
												 "FROM Borrowing Bor NATURAL INNER JOIN Borrower B, " +
												 "(SELECT MAX(Bor2.borid) latest_borid " +
												 	"FROM Borrowing Bor2 NATURAL INNER JOIN BookCopy BC " +
												 	"WHERE BC.status = 'out' " +
												 "GROUP BY callNumber, copyNo) " +
												 "WHERE Bor.borid = latest_borid");
			
			rs = ps.executeQuery();
			
//			System.out.println("Would you like to: ");
//			System.out.println("1. Email All Borrowers?");
//			System.out.println("2. Email individual borrower?");
//			
//			try{
//				choice = Integer.parseInt(in.readLine());
//			
//			switch (choice){
//				case 1: 
//					while(rs.next()){
//						emailAddress = rs.getString("emailAddress");
//						System.out.println(emailAddress + "You have overdue items. Please return to library at your earliest convenience.");
//					}
//					break;
//				case 2:
//					rs = ps.executeQuery("SELECT emailAddress FROM Borrower WHERE borid=?");
//					ps.setString(1, borid);
//					System.out.println(emailAddress + "You have overdue items. Please return.");
//					break;
//				}
//			}
//			catch (IOException e){
//				System.out.println("Message: " + e.getMessage());
//			}
			
			
		}
		catch(SQLException ex){
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