package ca.cs304.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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
        String callNo = parameters.get(0);
        String amount = parameters.get(1);
        String isDate = parameters.get(2);
        String borid = parameters.get(3);
        String paidDate =parameters.get(4);
        ResultSet rs = null;
        PreparedStatement ps = null;
        
        try{
        	ps = connection.prepareStatement("UPDATE BookCopy SET status =? WHERE callNumber = ?");
        	
        	ps.setString(1, callNo);
        	ps.setString(3, "in");
        	
        }
        catch(SQLException e){
        	e.printStackTrace();
        }
    	try {
			forid = ps.executeUpdate("SELECT fid.NEXTVAL FROM Fine");
			fid = Integer.toString(forid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
		try{
			
			ps = connection.prepareStatement("UPDATE Fine SET callNumber = ?, isssuedDate = ?, amount = ?, paidDate = ?, borid = ?, WHERE fid = ?");
	
			ps.setString(1, fid);
			ps.setString(2, callNo);
			ps.setString(3, isDate);
			ps.setString(4, amount);
			ps.setString(5, paidDate);
			ps.setString(6, borid);
			
			connection.commit();
			ps.close();
            
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
			
			connection.commit();
			ps.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		try {
			//update bookcopy to set it to on-hold
			ps = connection.prepareStatement("UPDATE bookcopy SET status = ? WHERE callNumber = ?");
			
			ps.setString(1, callNo);
			ps.setString(3, "on-hold");
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
		return null;
	}

	
}
