package ca.cs304.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Search {
	
		Book book;
		private Connection con;
		
		public void search(String keyword) {
			
			String str1;
			PreparedStatement ps;

		        try {
		        	ResultSet rs;
		            BufferedReader str2 = new BufferedReader(new InputStreamReader(
		                    System.in));
		            System.out.println("Enter your search");
		            str1 = str2.readLine();
		    		
		            String query1 = "SELECT * FROM book WHERE title like ?";
		            ps = con.prepareStatement(query1);
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
		      	     System.out.println(callNumber + "\t" + isbn +
	                        "\t" + title + "\t" + mainAuthor +
	                        "\t" + publisher + "\t" + year
	                        + "\t" + copyNo + "\t" + status);
		            }
		            ps.close();
		            rs.close();
		            
		            String query2 = "SELECT * FROM HasAuthor WHERE name like ?";
		            ps = con.prepareStatement(query2);
		            ps.setString(1, str1 + "%");
		            rs = ps.executeQuery(query1);
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
	                        "\t" + title + "\t" + author +
	                        "\t" + publisher + "\t" + year 
	                        + "\t" + copyNo + "\t" + status);
		            }
		            ps.close();
		            rs.close();
		            
		            String query3 = "SELECT * FROM HasSubject WHERE subject like ?";
		            ps = con.prepareStatement(query3);
		            ps.setString(1, str1 + "%");
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
		            catch (NullPointerException e)

		            {
		                System.out.println(e);
		            } catch (Exception ex) {
		                System.out.println(ex);
		            }
			
		}

	}
