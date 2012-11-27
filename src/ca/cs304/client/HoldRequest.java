package ca.cs304.client;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HoldRequest extends Table{

	public HoldRequest(Connection connection) {
		super(connection);
	}

	/*
	 * inserts a holdrequest
	 */ 
	public void insert(List<String> parameters) {
		String bid = parameters.get(0);
		String callNumber = parameters.get(1);
		
		//get today's date
		Calendar calendar = Calendar.getInstance(); 	
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String issuedDate = dateFormat.format(calendar.getTime());
		
		PreparedStatement ps;
		try {
		
			ps = connection.prepareStatement("INSERT INTO holdrequest VALUES ((hidseq.NEXTVAL),?,?,?)");

			ps.setString(1, bid);
			System.out.print("\nHold Request BID: " + bid);

			ps.setString(2, callNumber);
			System.out.print("\nHold Request CallNumber: " + callNumber);

			ps.setString(3, issuedDate);
			System.out.print("\nHold Request IssuedDate: " + issuedDate);

			ps.executeUpdate();

			// commit work 
			connection.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			try 
			{
				// undo the insert
				connection.rollback();	
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
	public void delete(List<String> parameters)
	{
		String             hid = parameters.get(0);
		PreparedStatement  ps;

		try
		{
			ps = connection.prepareStatement("DELETE FROM HoldRequest WHERE hid = ?");

			System.out.print("\nHold Request hid: ");
			ps.setString(1, hid);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0)
			{
				System.out.println("\nHoldRequest " + hid + " does not exist!");
			}

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
	}

	/*
	 * display information about holdrequests
	 */ 
	public void display()
	{
		String             hid;
		String             bid;
		String             callNumber;
		String             issuedDate;
		Statement 		   stmt;
		ResultSet          rs;

		try
		{
			stmt = connection.createStatement();

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

				System.out.printf("%-20s", rsmd.getColumnName(i+1));    
			}

			System.out.println(" ");

			while(rs.next())
			{
				// for display purposes get everything from Oracle 
				// as a string

				// simplified output formatting; truncation may occur

				hid = rs.getString("hid");
				System.out.printf("%-20.20s", hid);

				bid = rs.getString("bid");
				System.out.printf("%-20.20s", bid);

				callNumber = rs.getString("callNumber");
				System.out.printf("%-20.20s", callNumber);

				issuedDate = rs.getString("issuedDate");
				System.out.printf("%-20.20s\n", issuedDate);

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

	/**
	 * Checks if there is an existing hold request from the 
	 * specified bid for the specified callNumber
	 * @param bid
	 * @param callNumber
	 * @return boolean True if there is already a request made
	 */
	public boolean isExist(String bid, String callNumber) {
		
		boolean isExist = false;
		PreparedStatement  ps;
		ResultSet resultSet;

		try
		{
			ps = connection.prepareStatement("SELECT FROM HoldRequest WHERE bid = ? AND callNumber = ?");
			
			ps.setString(1, bid);
			ps.setString(2, callNumber);

			resultSet = ps.executeQuery();
			if (resultSet.next()) isExist = true;

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
		return isExist;
	}
}
