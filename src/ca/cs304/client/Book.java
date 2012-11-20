package ca.cs304.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Book extends Table {

	 public Book(Connection connection) {
		super(connection);
	}

	/*
     * inserts a book
     */ 
	 public void insert(List<String> parameters)
	 {
		 String                 callNumber = parameters.get(0);
		 String                 isbn = parameters.get(1);
		 String                 title = parameters.get(2);
		 String                 mainAuthor = parameters.get(3);
		 String			        publisher = parameters.get(4);
		 String                 year = parameters.get(5);
		 PreparedStatement      ps;

		 try
		 {
			 ps = connection.prepareStatement("INSERT INTO book VALUES (?,?,?,?,?,?)");

			 ps.setString(1, callNumber);
			 ps.setString(2, isbn);
			 ps.setString(3, title);
			 ps.setString(4, mainAuthor);
			 ps.setString(5, publisher);
			 ps.setString(6, year);
			 
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
	  * deletes a book
	  */ 
	 public void delete(List<String> parameters)
	 {
		 String             callNumber = parameters.get(0);
		 PreparedStatement  ps;

		 try
		 {
			 ps = connection.prepareStatement("DELETE FROM book WHERE callNumber = ?");

			 System.out.print("\nBook CallNumber: ");
			 ps.setString(1, callNumber);

			 int rowCount = ps.executeUpdate();

			 if (rowCount == 0)
			 {
				 System.out.println("\nBook " + callNumber + " does not exist!");
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
	  * display information about books
	  */ 
	 public void display()
	 {
		 String             callNumber;
		 String             isbn;
		 String             title;
		 String             mainAuthor;
		 String			   publisher;
		 String             year;
		 Statement          stmt;
		 ResultSet          rs;

		 try
		 {
			 stmt = connection.createStatement();

			 rs = stmt.executeQuery("SELECT * FROM book");

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

				 callNumber = rs.getString("callNumber");
				 System.out.printf("%-20.20s", callNumber);

				 isbn = rs.getString("isbn");
				 System.out.printf("%-20.20s", isbn);

				 title = rs.getString("title");
				 System.out.printf("%-20.20s", title);

				 mainAuthor = rs.getString("mainAuthor");
				 System.out.printf("%-20.20s", mainAuthor);

				 publisher = rs.getString("publisher");
				 System.out.printf("%-20.20s", publisher);

				 year = rs.getString("year");
				 System.out.printf("%-20.20s\n", year);
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
		public boolean findBook(String callNumber) 
		{
			PreparedStatement ps;
			ResultSet rs;
			try{


				ps = connection.prepareStatement("SELECT * FROM book where callNumber = ?");
				ps.setString(1, callNumber);
				rs = ps.executeQuery();
				if (rs.next())
				{
					ps.close();
					return true;
				}
				else
				{
					ps.close();
					return false;
				}
			}

			catch (SQLException ex)
			{
				JOptionPane.showMessageDialog(null, "Message: " + ex.getMessage());
			}
			return false;	
		}

}

