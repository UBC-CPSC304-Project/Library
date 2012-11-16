package ca.cs304.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class HasAuthor extends Table {

	public HasAuthor(Connection connection) {
		super(connection);
		// TODO Auto-generated constructor stub
	}

	private Connection connect;
	
	/*
	 * insert Author
	 */
	public void insert(List<String> parameters){
		String callNumber = parameters.get(0);
		String name = parameters.get(1);
		
		PreparedStatement ps;
		
		try {
			ps = connect.prepareStatement("INSERT INTO HasAuthor VALUES (?,?,?): ");
			
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
	public void delete(List<String> parameters){
		
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
	public void display() {
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
				callNumber = rs.getString("callNumber");
				System.out.printf("%-10.10s", callNumber);
				
				name = rs.getString("name");
				System.out.printf("%-20.20s", name);

			}
		
			stmt.close();
		}
		catch(SQLException ex){
			System.out.println("Message: " + ex.getMessage());
		}
	}


}
