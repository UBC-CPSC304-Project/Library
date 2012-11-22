package ca.cs304.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class BookCopy extends Table{

	public BookCopy(Connection connection) {
		super(connection);
	}
	
	/*
	 * insert BookCopy
	 */
	public void insert(List<String> parameters){
		String callNumber = parameters.get(0);
		String copyNo = parameters.get(1);
		String status = parameters.get(2);
		
		PreparedStatement ps;
		
		try {
			ps = connection.prepareStatement("INSERT INTO BookCopy VALUES (?,?,?)");
			
			ps.setString(1, callNumber);
			System.out.print("\n CallNumber: " + callNumber);

			
			ps.setString(2, copyNo);
			System.out.print("\n CopyNo: " + copyNo);

			
			ps.setString(3, status);
			System.out.print("\n Status: " + status);

			
			ps.executeUpdate();
			
			connection.commit();
			
			ps.close();
		
		}
		catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			
			try {
				connection.rollback();
			}
			catch (SQLException ex2) {
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}
	
	/*
	 * Delete BookCopy
	 */
	public void delete(List<String> parameters){
		
		String	callNumber = parameters.get(0);
		PreparedStatement ps;
		
		try{
			ps = connection.prepareStatement("DELETE FROM BookCopy WHERE callNumber = ?");
			
			System.out.print("\n callNumber: ");
			ps.setString(1, callNumber);
			
			int rowCount = ps.executeUpdate();
			
			if(rowCount == 0){
				System.out.println("\n callNumber " + callNumber + "does not exist!");
			}
			
			connection.commit();
			
			ps.close();
		}
		catch (SQLException ex){
				System.out.println("Message: " + ex.getMessage());
				
				try
				{
					connection.rollback();
				}
				catch (SQLException ex2){
					System.out.println("Message: " + ex2.getMessage());
					System.exit(-1);
				}
		}

	}
	
	public void display() {
		String callNumber;
		String copyNo;
		String status;
		Statement stmt;
		ResultSet rs;
		
		try{
			stmt = connection.createStatement();
			
			rs = stmt.executeQuery("SELECT * FROM bookCopy");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int numCols = rsmd.getColumnCount();
			
			System.out.println(" ");
			
			for (int i=0; i<numCols; i++){
				System.out.printf("%-20s", rsmd.getColumnName(i+1));
			}
			
			System.out.println(" ");
			
			while(rs.next()){
				callNumber = rs.getString("callNumber");
				System.out.printf("%-20.20s", callNumber);
				
				copyNo = rs.getString("copyNo");
				System.out.printf("%-20.20s", copyNo);
				
				status = rs.getString("status");
				System.out.printf("%-20.20s\n", status);
			}
		
			stmt.close();
		}
		catch(SQLException ex){
			System.out.println("Message: " + ex.getMessage());
		}
	}
	

}
