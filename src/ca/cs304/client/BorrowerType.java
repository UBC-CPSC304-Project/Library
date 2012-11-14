package ca.cs304.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BorrowerType extends Table{

	public BorrowerType(Connection connection) {
		super(connection);
	}

	@Override
	public void delete(ArrayList<String> parameters) {
		String type = parameters.get(0);
		PreparedStatement ps;

		try
		{
			ps = connection.prepareStatement("DELETE FROM BorrowerType WHERE type = ?");
			ps.setString(1, type);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0) {
				System.out.println("\nBorrowerType " + type + " does not exist!");
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
		String type = parameters.get(0);
		String bookTimeLimit = parameters.get(1);
		
		PreparedStatement ps;

		try {
			ps = connection.prepareStatement("INSERT INTO Borrower VALUES (?, ?");
			
			ps.setString(1, type);
			ps.setString(2, bookTimeLimit);
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
		String type;
		String bookTimeLimit;
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
				type = resultSet.getString("type");
				System.out.printf("%-10.10s", type);
				
				bookTimeLimit = resultSet.getString("bookTimeLimit");
				System.out.printf("%-10.10s", bookTimeLimit);
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
