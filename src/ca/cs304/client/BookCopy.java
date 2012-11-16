package ca.cs304.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class BookCopy {

	private Connection connect;
	
	/*
	 * insert BookCopy
	 */
	private void insertBookCopy(ArrayList<String> parameters){
		String callNumber = parameters.get(0);
		String copyNo = parameters.get(1);
		String status = parameters.get(2);
		
		PreparedStatement ps;
		
		try {
			ps = connect.prepareStatement("Insert BookCopy Values (?,?,?): ");
			
			System.out.print("\n CallNumber: ");
			ps.setString(1, callNumber);
			
			System.out.print("\n CopyNo: ");
			ps.setString(2, copyNo);
			
			System.out.print("\n Status: ");
			ps.setString(3, status);
			
			ps.executeUpdate();
			
			connect.commit();
			
			ps.close();
		
		}
		catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			
			try {
				connect.rollback();
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
	private void deleteBookCopy(ArrayList<String> parameters){
		
		String	callNumber = parameters.get(0);
		PreparedStatement ps;
		
		try{
			ps = connect.prepareStatement("DELETE FROM BookCopy WHERE callNumber = ?");
			
			System.out.print("\n callNumber: ");
			ps.setString(1, callNumber);
			
			int rowCount = ps.executeUpdate();
			
			if(rowCount == 0){
				System.out.println("\n callNumber " + callNumber + "does not exist!");
			}
			
			connect.commit();
			
			ps.close();
		}
		catch (SQLException ex){
				System.out.println("Message: " + ex.getMessage());
				
				try
				{
					connect.rollback();
				}
				catch (SQLException ex2){
					System.out.println("Message: " + ex2.getMessage());
					System.exit(-1);
				}
		}

	}
	
	private void showBookCopy(ArrayList<String> parameters){
		String callNumber;
		String copyNo;
		String status;
		Statement stmt;
		ResultSet rs;
		
		try{
			stmt = connect.createStatement();
			
			rs = stmt.executeQuery("SELECT * FROM bookCopy");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int numCols = rsmd.getColumnCount();
			
			System.out.println(" ");
			
			for (int i=0; i<numCols; i++){
				System.out.printf("%-15s", rsmd.getColumnName(i+1));
			}
			
			System.out.println(" ");
			
			while(rs.next()){
				callNumber = rs.getString("bookCopy_callNumber");
				System.out.printf("%-10.10s", callNumber);
				
				copyNo = rs.getString("bookCopy_copyNo");
				System.out.printf("%-20.20s", copyNo);
				
				status = rs.getString("bookCopy_status");
				System.out.printf("%-15.15s", status);
			}
		
			stmt.close();
		}
		catch(SQLException ex){
			System.out.println("Message: " + ex.getMessage());
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		BookCopy b = new BookCopy();

	}

}
