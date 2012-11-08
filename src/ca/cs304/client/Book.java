package ca.cs304.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Book {

	 private Connection con;

	/*
     * inserts a book
     */ 
    private void insertBook(ArrayList<String> parameters)
    {
	String                 callNumber = parameters.get(0);
	String                 isbn = parameters.get(1);
	String                 title = parameters.get(2);
	String                 mainAuthor = parameters.get(3);
	String			       publisher = parameters.get(4);
	String                 year = parameters.get(5);
	PreparedStatement      ps;
	  
	try
	{
	  ps = con.prepareStatement("INSERT INTO book VALUES (?,?,?,?,?,?)");
	  	
	  System.out.print("\nBook CallNumber: ");
	  ps.setString(1, callNumber);

	  System.out.print("\nBook ISBN: ");
	  ps.setString(2, isbn);

	  System.out.print("\nBook Title: ");
	  ps.setString(3, title);
	 
	  System.out.print("\nBook Main Author: ");
	  ps.setString(4, mainAuthor);

	  System.out.print("\nBook Publisher: ");
	  ps.setString(5, publisher);

	  System.out.print("\nBook Date: ");
	  ps.setString(5, year);
	 
	  ps.executeUpdate();

	  // commit work 
	  con.commit();

	  ps.close();
	}
	catch (SQLException ex)
	{
	    System.out.println("Message: " + ex.getMessage());
	    try 
	    {
		// undo the insert
		con.rollback();	
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
    private void deleteBook(ArrayList<String> parameters)
    {
	String             callNumber = parameters.get(0);
	PreparedStatement  ps;
	  
	try
	{
	  ps = con.prepareStatement("DELETE FROM book WHERE callNumber = ?");
	
	  System.out.print("\nBook CallNumber: ");
	  ps.setString(1, callNumber);

	  int rowCount = ps.executeUpdate();

	  if (rowCount == 0)
	  {
	      System.out.println("\nBook " + callNumber + " does not exist!");
	  }

	  con.commit();

	  ps.close();
	}
	catch (SQLException ex)
	{
	    System.out.println("Message: " + ex.getMessage());

            try 
	    {
		con.rollback();	
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
    private void showBook(ArrayList<String> parameters)
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
	  stmt = con.createStatement();

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

	      System.out.printf("%-15s", rsmd.getColumnName(i+1));    
	  }

	  System.out.println(" ");

	  while(rs.next())
	  {
	      // for display purposes get everything from Oracle 
	      // as a string

	      // simplified output formatting; truncation may occur

	      callNumber = rs.getString("book_callNumber");
	      System.out.printf("%-10.10s", callNumber);

	      isbn = rs.getString("book_isbn");
	      System.out.printf("%-20.20s", isbn);

	      title = rs.getString("book_title");
	      System.out.printf("%-20.20s", title);

	      mainAuthor = rs.getString("book_mainAuthor");
	      System.out.printf("%-15.15s", mainAuthor);

	      publisher = rs.getString("book_publisher");
	      System.out.printf("%-15.15s\n", publisher);
	      
	      year = rs.getString("book_year");
	      System.out.printf("%-15.15s\n", year);
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
    
 
    public static void main(String args[])
    {
      Book b = new Book();
    }
}

