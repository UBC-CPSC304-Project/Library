package ca.cs304.client;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HoldRequest {
	
		 private Connection con;

		/*
	     * inserts a holdrequest
	     */ 
	    private void insertHoldRequest(ArrayList<String> parameters)
	    {
		String             hid = parameters.get(0);
		String             bid= parameters.get(1);
		String             callNumber= parameters.get(2);
		String             issuedDate= parameters.get(3);
		PreparedStatement  ps;
		Statement 		   stmt= null;
	;
		  
		try
		{
		  String sql = "INSERT INTO holdrequest VALUES (?,?,?,?)";
		  ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		  System.out.print("\nHold Request HID: ");
		  ps.setString(1, hid);

		  System.out.print("\nHold Request BID: ");
		  ps.setString(2, bid);

		  System.out.print("\nHold Request CallNumber: ");
		  ps.setString(3, callNumber);
		 
		  System.out.print("\nHold Request IssuedDate: ");
		  ps.setString(4, issuedDate);
		
		  ps.executeUpdate();
		  
		   ResultSet rs = stmt.getGeneratedKeys();
		      while (rs.next()) {
		        ResultSetMetaData rsMetaData = rs.getMetaData();
		        int columnCount = rsMetaData.getColumnCount();

		        for (int i = 1; i <= columnCount; i++) {
		          String key = rs.getString(i);
		          System.out.println("key " + i + " is " + key);
		        }
		  	}

		  // commit work 
		  con.commit();

		  ps.close();
		  rs.close();
		}
		catch (SQLException ex)
		{
		    System.out.println("Message: " + ex.getMessage());
		    try 
		    {
			// undo the insert
			con.rollback();	
		    }
		    catch (SQLException ex2)
		    {
			System.out.println("Message: " + ex2.getMessage());
			System.exit(-1);
		    }
		}
	    }


	    /*
	     * deletes a holdRequest
	     */ 
	    private void deleteHoldRequest(ArrayList<String> parameters)
	    {
		String             hid = parameters.get(0);
		PreparedStatement  ps;
		  
		try
		{
		  ps = con.prepareStatement("DELETE FROM HoldRequest WHERE hid = ?");
		
		  System.out.print("\nHold Request hid: ");
		  ps.setString(1, hid);

		  int rowCount = ps.executeUpdate();

		  if (rowCount == 0)
		  {
		      System.out.println("\nHoldRequest " + hid + " does not exist!");
		  }

		  con.commit();

		  ps.close();
		}
		catch (SQLException ex)
		{
		    System.out.println("Message: " + ex.getMessage());

	            try 
		    {
			con.rollback();	
		    }
		    catch (SQLException ex2)
		    {
			System.out.println("Message: " + ex2.getMessage());
			System.exit(-1);
		    }
		}
	    }
	    
	    /*
	     * display information about holdrequests
	     */ 
	    private void showHoldRequest(ArrayList<String> parameters)
	    {
	    	String             hid;
	    	String             bid;
	    	String             callNumber;
	    	String             issuedDate;
	    	Statement 		   stmt;
	    	ResultSet          rs;
		   
		try
		{
		  stmt = con.createStatement();

		  rs = stmt.executeQuery("SELECT * FROM holdrequest");

		  // get info on ResultSet
		  ResultSetMetaData rsmd = rs.getMetaData();

		  // get number of columns
		  int numCols = rsmd.getColumnCount();

		  System.out.println(" ");
		  
		  // display column names;
		  for (int i = 0; i < numCols; i++)
		  {
		      // get column name and print it

		      System.out.printf("%-15s", rsmd.getColumnName(i+1));    
		  }

		  System.out.println(" ");

		  while(rs.next())
		  {
		      // for display purposes get everything from Oracle 
		      // as a string

		      // simplified output formatting; truncation may occur

		      hid = rs.getString("holdrequest_hid");
		      System.out.printf("%-10.10s", hid);

		      bid = rs.getString("holdrequest_bid");
		      System.out.printf("%-20.20s", bid);

		      callNumber = rs.getString("holdrequest_callNumber");
		      System.out.printf("%-20.20s", callNumber);

		      issuedDate = rs.getString("holdRequest_issuedDate");
		      System.out.printf("%-15.15s", issuedDate);
		  
		  }
	 
		  // close the statement; 
		  // the ResultSet will also be closed
		  stmt.close();
		}
		catch (SQLException ex)
		{
		    System.out.println("Message: " + ex.getMessage());
		}	
	    }
	    
	 
	    public static void main(String args[])
	    {
	      HoldRequest h = new HoldRequest();
	}
	}
