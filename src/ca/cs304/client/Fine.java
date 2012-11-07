package ca.cs304.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Fine extends Table {

	public Fine(Connection connection) {
		super(connection);
	}

	@Override
	public void delete(ArrayList<String> parameters) {

		String fid = parameters.get(0);
		PreparedStatement ps;
		
		try
		{
			ps = connection.prepareStatement("DELETE FROM Fine WHERE fid = ?");
			ps.setString(1, fid);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0) {
				System.out.println("\nFine Record " + fid + " does not exist!");
			}

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
	}

	@Override
	public void insert(ArrayList<String> parameters) {

		String fid = parameters.get(0);
		float amount = Float.parseFloat(parameters.get(1));
		String issuedDate = parameters.get(2);
		String paidDate = parameters.get(3);
		String borid = parameters.get(4);
		
		PreparedStatement ps;
		
		try {
			ps = connection.prepareStatement("INSERT INTO Fine VALUES (?, ?, ?, ?, ?");
			
			ps.setString(1, fid);
			ps.setFloat(2, amount);
			ps.setString(3, issuedDate);
			ps.setString(4, paidDate);
			ps.setString(5, borid);
			
			ps.executeUpdate();

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
	}

	@Override
	public void display() {
		
		String fid;
		float amount;
		String issuedDate;
		String paidDate;
		String borid;
		
		Statement statement;
		ResultSet resultSet;
		
		try { 
			statement = connection.createStatement();

			resultSet = statement.executeQuery("SELECT * FROM branch");

			// get info on ResultSet
			ResultSetMetaData rsmd = resultSet.getMetaData();

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
			
			while(resultSet.next()) {
				fid = resultSet.getString("fid");
				System.out.printf("%-10.10s", fid);
				
				amount = resultSet.getFloat("bid");
				System.out.printf("%-10.10s", amount);
				
				issuedDate = resultSet.getString("callNumber");
				System.out.printf("%-20.20s", issuedDate);
				
				paidDate = resultSet.getString("copyNo");
				System.out.printf("%-10.10s", paidDate);

				borid = resultSet.getString("outDate");
				System.out.printf("%-10.10s", borid);
			}
			
			  // close the statement; 
			  // the ResultSet will also be closed
			  statement.close();

		}
		catch (SQLException ex) {
		    System.out.println("Message: " + ex.getMessage());
		}
	}

}
