package ca.cs304.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class HasSubject extends Table{

	public HasSubject(Connection connection) {
		super(connection);
	}
	
	/**
	 * insert Subject
	 */
	public void insert(List<String> parameters){
		String callNumber = parameters.get(0);
		String subject = parameters.get(1);
		
		PreparedStatement ps;
		
		try {
			ps = connection.prepareStatement("INSERT INTO HasSubject VALUES (?,?) ");
			
			System.out.print("\n CallNumber: " + callNumber);
			ps.setString(1, callNumber);
			
			System.out.print("\n subject: " + subject);
			ps.setString(2, subject);
			
			
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
	 * Delete Subject
	 */
	public void delete(List<String> parameters){
		
		String	callNumber = parameters.get(0);
		PreparedStatement ps;
		
		try{
			ps = connection.prepareStatement("DELETE FROM HasSubject WHERE callNumber = ?");
			
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
	
	/*
	 * display subjects
	 */
	public void display() {
		String callNumber;
		String subject;
		Statement stmt;
		ResultSet rs;
		
		try{
			stmt = connection.createStatement();
			
			rs = stmt.executeQuery("SELECT * FROM HasSubject");
			
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
				
				subject = rs.getString("subject");
				System.out.printf("%-20.20s", subject);

			}
		
			stmt.close();
		}
		catch(SQLException ex){
			System.out.println("Message: " + ex.getMessage());
		}
	}
}

