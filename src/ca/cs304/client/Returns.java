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
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String sDate = dateFormat.format(calendar.getTime());
        
        
        int forid;
        String fid = new String();
        String copyNo = new String();
        String callNo = parameters.get(0);
        String borid = parameters.get(1);
        ResultSet rs = null;
        PreparedStatement ps = null;
        
        // Get borrower id and name of everyone who borrowed book in descending order according to date
        // Giving us info about latest borrower to borrow book
        try{
        	ps = connection.prepareStatement("SELECT b.bid, b.name, bk.outDate, bk.inDate" +
        										"FROM Borrower b, Borrowing bor, BookCopy bk" +
        										"WHERE b.bid = bor.bid" +
        											"AND bor.callNumber = bk.callNumber AND bk.callNumber = ?" +
        											"AND bk.copyNo =?" +
        											"HAVING (SELECT MAX(bor.borid) FROM bookCopy bk" +
        											"WHERE  bor.callNumber = bk.callNumber AND bk.callNumber =? AND bk.copyNo = ?)");
        	ps.setString(1, callNo);
        	ps.setString(2, copyNo);

        	rs = ps.executeQuery();
        	
        } catch(SQLException ex){
        	System.out.println("Message: " + ex.getMessage());
        }
        
        try{
        	ps = connection.prepareStatement("UPDATE BookCopy" +
        									 "SET status= 'in' WHERE callNumber = ? and copyNo = ?");
        	ps.setString(1, callNo);
        	ps.setString(2, copyNo);
        	
        	rs = ps.executeQuery();
        } catch(SQLException e){
        	System.out.println("Message: " + e.getMessage());
        }
        //Overdue Status
        /*try {
        	
        }*/

    	//next Fid value available to record fine
        try {
			forid = ps.executeUpdate("SELECT fid.NEXTVAL FROM Fine");
			fid = Integer.toString(forid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
    	/*
    	 * New Fine record is created
    	 */
		try{
			
			ps = connection.prepareStatement("INSERT Fine SET callNumber = ?, isssuedDate = ?, borid = ?, WHERE fid = ?");
	
			ps.setString(1, fid); //sets fine id
			ps.setString(2, callNo);
			ps.setString(3, sDate);
			ps.setString(4, borid);
			
			rs = ps.executeQuery();
            
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		/*Check if book is reserved*/
		try{
			ps = connection.prepareStatement("SELECT hid, bid FROM holdRequest h WHERE h.callNumber = ?");
			
			ps.setString(1, callNo);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				int bid = rs.getInt("bid");
				System.out.println("This book is on hold for: " + bid);
			}
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		try {
			//update bookcopy to set it to on-hold
			ps = connection.prepareStatement("UPDATE bookcopy SET status = 'on-hold' WHERE callNumber = ?");
			
			ps.setString(1, callNo);
			
			connection.commit();
			ps.close();
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
		return rs;
	}

	
}
