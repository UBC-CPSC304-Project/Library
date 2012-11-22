package ca.cs304.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Fine extends Table {

	public Fine(Connection connection) {
		super(connection);
	}

	@Override
	public void delete(List<String> parameters) {

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
	public void insert(List<String> parameters) {
		float amount = Float.parseFloat(parameters.get(0));
		String issuedDate = parameters.get(1);
		String paidDate = parameters.get(2);
		String borid = parameters.get(3);
		
		PreparedStatement ps;
		
		try {
			ps = connection.prepareStatement("INSERT INTO Fine VALUES ((fineseq.NEXTVAL), ?, ?, ?, ?)");
		
			ps.setFloat(1, amount);
			System.out.print("\n Amount: " + amount);

			ps.setString(2, issuedDate);
			System.out.print("\n issuedDate: " + issuedDate);

			ps.setString(3, paidDate);
			System.out.print("\n paidDate: " + paidDate);

			ps.setString(4, borid);			
			System.out.print("\n Borid: " + borid);
			
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

			resultSet = statement.executeQuery("SELECT * FROM Fine");

			// get info on ResultSet
			ResultSetMetaData rsmd = resultSet.getMetaData();

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
			
			while(resultSet.next()) {
				fid = resultSet.getString("fid");
				System.out.printf("%-20.20s", fid);
				
				amount = resultSet.getFloat("amount");
				System.out.printf("%-20.20s", amount);
				
				issuedDate = resultSet.getString("issuedDate");
				System.out.printf("%-20.20s", issuedDate);
				
				paidDate = resultSet.getString("paidDate");
				System.out.printf("%-20.20s", paidDate);

				borid = resultSet.getString("borid");
				System.out.printf("%-20.20s\n", borid);
				
				System.out.printf("\n");
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
