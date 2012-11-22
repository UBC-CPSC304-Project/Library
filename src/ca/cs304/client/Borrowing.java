package ca.cs304.client;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

public class Borrowing extends Table {

	public Borrowing(Connection connection) {
		super(connection);
	}

	@Override
	public void delete(List<String> parameters) {

		String borid = parameters.get(0);
		PreparedStatement ps;

		try
		{
			ps = connection.prepareStatement("DELETE FROM Borrowing WHERE borid = ?");
			ps.setString(1, borid);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0) {
				System.out.println("\nCheck Out Record " + borid + " does not exist!");
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

		//TODO: How to check invalid values?

		String bid = parameters.get(0);
		String callNumber = parameters.get(1);
		String copyNo = parameters.get(2);
		String outDate = parameters.get(3);
		String inDate = parameters.get(4);

		PreparedStatement ps;

		try {
			ps = connection.prepareStatement("INSERT INTO Borrowing VALUES ((boridseq.NEXTVAL), ?, ?, ?, ?, ?)");
//
//			ps.executeQuery("SELECT boridseq.NEXTVAL FROM Borrowing");
//			int upBorid = ps.executeUpdate("SELECT boridseq.NEXTVAL FROM Borrowing");
//			String borid = Integer.toString(upBorid);
//			ps.setString(1, borid);
//			System.out.print("\n borid: " + borid);

				
			ps.setString(1, bid);
			System.out.print("\n bid: " + bid);

			ps.setString(2, callNumber);
			System.out.print("\n callNumber: " + callNumber);

			ps.setString(3, copyNo);
			System.out.print("\n copyNo: " + copyNo);

			ps.setString(4, outDate);
			System.out.print("\n outDate: " + outDate);

			ps.setString(5, inDate);
			System.out.print("\n inDate: " + inDate);

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

		String borid;
		String bid;
		String callNumber;
		String copyNo;
		String outDate;
		String inDate;
		Statement statement;
		ResultSet resultSet;

		try { 
			statement = connection.createStatement();

			resultSet = statement.executeQuery("SELECT * FROM Borrowing");

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
				borid = resultSet.getString("borid");
				System.out.printf("%-10.10s", borid);
				
				bid = resultSet.getString("bid");
				System.out.printf("%-10.10s", bid);
				
				callNumber = resultSet.getString("callNumber");
				System.out.printf("%-20.20s", callNumber);
				
				copyNo = resultSet.getString("copyNo");
				System.out.printf("%-10.10s", copyNo);

				outDate = resultSet.getString("outDate");
				System.out.printf("%-10.10s", outDate);
				
				inDate = resultSet.getString("inDate");
				System.out.printf("%-10.10s", inDate);
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
