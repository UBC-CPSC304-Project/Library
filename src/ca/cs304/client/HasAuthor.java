package ca.cs304.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class HasAuthor {

	private Connection connect;
	
	/*
	 * insert Author
	 */
	private void insert(ArrayList<String> parameters){
		String callNumber = parameters.get(0);
		String name = parameters.get(1);
		
		PreparedStatement ps;
		
		try {
			ps = connect.prepareStatement("Insert HasAuthor Values (?,?,?): ");
			
			System.out.print("\n CallNumber: ");
			ps.setString(1, callNumber);
			
			System.out.print("\n Name: ");
			ps.setString(2, name);
			
			
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
	 * Delete Author
	 */
	private void delete(ArrayList<String> parameters){
		
		String	callNumber = parameters.get(0);
		PreparedStatement ps;
		
		try{
			ps = connect.prepareStatement("DELETE FROM HasAuthor WHERE callNumber = ?");
			
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
	
	/*
	 * Show Author 
	 */
	private void show(ArrayList<String> parameters){
		String callNumber;
		String name;
		Statement stmt;
		ResultSet rs;
		
		try{
			stmt = connect.createStatement();
			
			rs = stmt.executeQuery("SELECT * FROM HasAuthor");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int numCols = rsmd.getColumnCount();
			
			System.out.println(" ");
			
			for (int i=0; i<numCols; i++){
				System.out.printf("%-15s", rsmd.getColumnName(i+1));
			}
			
			System.out.println(" ");
			
			while(rs.next()){
				callNumber = rs.getString("hasAuthor_callNumber");
				System.out.printf("%-10.10s", callNumber);
				
				name = rs.getString("hasAuthor_name");
				System.out.printf("%-20.20s", name);

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

		HasAuthor hs = new HasAuthor();

	}

}
