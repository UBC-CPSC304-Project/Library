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
			String title = parameters.get(0);
			String author = parameters.get(1);
			String subject = parameters.get(2);

			try {
				ps = connection.prepareStatement("SELECT b.callNumber, b.title, b.mainAuthor, s.subject, COUNT(bc.copyNo) FROM Book b INNER JOIN HasSubject s ON b.callNumber = s.callNumber LEFT OUTER JOIN BookCopy bc ON ((bc.callNumber = b.callNumber) AND (bc.status = 'in')) WHERE ((title like ?) OR (mainAuthor like ?) OR (subject like ?))  GROUP BY b.callNumber, b.title, b.mainAuthor, s.subject");
				ps.setString(1, title);
				ps.setString(2, author);
				ps.setString(3, subject);
				rs = ps.executeQuery();
			
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
}
