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

		@Override
			public ResultSet execute(List<String> parameters) {
			PreparedStatement ps;
			ResultSet rs;
			
			String query1 = "SELECT * FROM book WHERE title like ?";
			String str1 = parameters.get(2);
			try {
			ps = connection.prepareStatement(query1);
			ps.setString(1, str1 + "%");
			rs = ps.executeQuery(query1);
			while (rs.next()) {
				String callNumber = rs.getString("book_callNumber");
				String isbn = rs.getString("book_isbn");
				String title = rs.getString("book_title");
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
			
			
			
			String query2 = "SELECT * FROM HasAuthor WHERE name like ?";
			String str2 = parameters.get(1);
			try {
			ps = connection.prepareStatement(query2);
			ps.setString(1, str2 + "%");
			rs = ps.executeQuery(query2);
			while (rs.next()) {
				String callNumber = rs.getString("book_callNumber");
				String isbn = rs.getString("book_isbn");
				String title = rs.getString("book_title");
				String author = rs.getString("hasauthor_author");
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
		
			String query3 = "SELECT * FROM HasSubject WHERE subject like ?";
			String str3 = parameters.get(1);
			try {
				ps = connection.prepareStatement(query3);
				ps.setString(1, str3 + "%");
				rs = ps.executeQuery(query1);
				while (rs.next()) {
					String callNumber = rs.getString("book_callNumber");
					String isbn = rs.getString("book_isbn");
					String title = rs.getString("book_title");
					String mainAuthor = rs.getString("book_mainAuthor");
					String publisher = rs.getString("book_publisher");
					String year = rs.getString("book_year");
					String subject = rs.getString("hassubject_subject");
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
			return null;	
		}
}

