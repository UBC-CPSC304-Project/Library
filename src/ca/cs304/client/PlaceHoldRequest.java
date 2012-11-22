package ca.cs304.client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class PlaceHoldRequest extends Transaction {

	Boolean bookReturned = false;
	public PlaceHoldRequest(Connection connection) {
		super(connection);
	}

/*
 * @params (String callNumber, String bid)
 * 
 */
	@Override
	public ResultSet execute(List<String> parameters) {
		//get today's date
		Calendar calendar = Calendar.getInstance(); 	
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String issuedDate = dateFormat.format(calendar.getTime());
		
		// get parameter values
		String emailAddress = new String();
		String holdid = new String();
		String callNumber = parameters.get(0);
		String bid = parameters.get(1);
		int hid;
		
		//get next value of HID from sequence generator
		try {
			hid = ps.executeUpdate("SELECT hidseq.NEXTVAL FROM holdrequest");
			holdid = Integer.toString(hid);
		} catch (SQLException e) {
			e.printStackTrace();
		}

			//update the hold request table
			try {
				ps = connection.prepareStatement("UPDATE holdrequest SET bid = ?, callNumber = ?, isssuedDate = ? WHERE hid = ?");
	
			ps.setString(1, holdid);
			ps.setString(2, bid);
			ps.setString(3, callNumber);
			ps.setString(4, issuedDate);
			
			connection.commit();
			ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			//get email address of borrower
			try {
				ps = connection.prepareStatement("SELECT emailAddress FROM borrower b JOIN holdrequest hr WHERE b.bid = h.bid");
			
			rs = ps.executeQuery();
			while (rs.next()) {    
				emailAddress = rs.getString(1);
			}
			connection.commit();
			ps.close();
			rs.close();
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if (bookReturned) {
				//mimic email message with emailAddress
				System.out.println(emailAddress + " Book has been returned");
					
				try {
				//update bookcopy to have it set to on-hold
				ps = connection.prepareStatement("UPDATE bookcopy SET status = ? WHERE callNumber = ?");
				
				ps.setString(1, callNumber);
				ps.setString(3, "on-hold");
				
				connection.commit();
				ps.close();
				
			} catch (SQLException ex) {
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
		return null;
	}

	public int statusTrigger(String callNumber) throws SQLException {
		Boolean bookReturned = false;

		try {
			// Create a prepared statement that accepts binding a number.
			ps = connection.prepareStatement("SELECT   null " +
					"FROM     bookcopy bc JOIN book b " +
					"ON       bc.callNumber = b.callNumber" +
					"WHERE    bc.callNumber? +" +
					"HAVING   bc.status = in");

			// Bind the local variable to the statement placeholder.
			ps.setString(1, callNumber);

			// Execute query and check if status is in.
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				bookReturned = true;

			// Clean up resources.
			rs.close();
			ps.close();
			connection.close();


		} catch (SQLException ex) {
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
		// Return 1 for true when book is returned and 0 for when it is out.
		if (bookReturned)
			return 1;
		else
			return 0; 
	}
}

//CREATE TRIGGER bookcopy
//BEFORE INSERT ON contact
//FOR EACH ROW
//WHEN (NEW.status IS NOT NULL)
//BEGIN
//  IF java_bookcopy (:NEW.status) = 1 THEN
//    RAISE_APPLICATION_ERROR(-20001,'Book has been returned');
//  END IF;
//END;
//
//CREATE FUNCTION java_bookcopy (status in NUMBER) RETURN NUMBER IS
//LANGUAGE JAVA
//NAME 'ThrowAnError.statusTrigger(java.lang.Integer) return int';
