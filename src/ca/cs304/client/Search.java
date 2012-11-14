package ca.cs304.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class Search extends Transaction {
	
	public Search(Connection connection) {
		super(connection);
	}
/*
 * @params (String title, String author, String subject)
 * 
 */
		@Override
			public ResultSet execute(List<String> parameters) {
			PreparedStatement ps;
			ResultSet rs = null;
			
			if(parameters.get(1) == null && parameters.get(2) == null) {
			String query1 = "SELECT * FROM book WHERE title like ?";
			String title = parameters.get(0);
			try {
			ps = connection.prepareStatement(query1);
			ps.setString(1, title + "%");
			rs = ps.executeQuery(query1);
			while (rs.next()) {
				String callNumber = rs.getString("book_callNumber");
				String isbn = rs.getString("book_isbn");
				title = rs.getString("book_title");
				String mainAuthor = rs.getString("book_mainAuthor");
				String publisher = rs.getString("book_publisher");
				String year = rs.getString("book_year");
				Integer copyNo = rs.getInt("bookcopy_copyno");
				String status = rs.getString("bookcopy_status");
				System.out.println(callNumber + "\t" + isbn + "\t" + title + "\t" + mainAuthor + 
						"\t" + publisher + "\t" + year + "\t" + copyNo + "\t" + status);
			}
			ps.close();
			rs.close();
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
			return rs;
			}
			
			
			if(parameters.get(0) == null && parameters.get(2) == null) {
			String query2 = "SELECT * FROM HasAuthor WHERE name like ?";
			String author = parameters.get(1);
			try {
			ps = connection.prepareStatement(query2);
			ps.setString(1, author + "%");
			rs = ps.executeQuery(query2);
			while (rs.next()) {
				String callNumber = rs.getString("book_callNumber");
				String isbn = rs.getString("book_isbn");
				String title = rs.getString("book_title");
				author = rs.getString("book_author");
				String publisher = rs.getString("book_publisher");
				String year = rs.getString("book_year");
				Integer copyNo = rs.getInt("bookcopy_copyno");
				String status = rs.getString("bookcopy_status");
				System.out.println(callNumber + "\t" + isbn +
						"\t" + title + "\t" + author + "\t" + publisher + "\t" + year  + "\t" + copyNo + "\t" + status);
			}
			ps.close();
			rs.close();
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
			return rs;
		}
		
			if(parameters.get(0) == null && parameters.get(1) == null) {
			String query3 = "SELECT * FROM HasSubject WHERE subject like ?";
			String subject = parameters.get(1);
			try {
				ps = connection.prepareStatement(query3);
				ps.setString(1, subject + "%");
				rs = ps.executeQuery(query3);
				while (rs.next()) {
					String callNumber = rs.getString("book_callNumber");
					String isbn = rs.getString("book_isbn");
					String title = rs.getString("book_title");
					String mainAuthor = rs.getString("book_mainAuthor");
					String publisher = rs.getString("book_publisher");
					String year = rs.getString("book_year");
					subject = rs.getString("hassubject_subject");
					Integer copyNo = rs.getInt("bookcopy_copyno");
					String status = rs.getString("bookcopy_status");
					System.out.println(callNumber + "\t" + isbn +
							"\t" + title + "\t" + mainAuthor +
							"\t" + publisher + "\t" + year + "\t" + subject
							+ "\t" + copyNo + "\t" + status);
				}
				ps.close();
				rs.close();
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
			return rs;
			}
}

