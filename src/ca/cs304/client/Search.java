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
				ps = connection.prepareStatement("SELECT b.callNumber, b.title, COUNT(bc.copyNo) FROM Book b INNER JOIN BookCopy bc ON bc.callNumber = b.callNumber LEFT OUTER JOIN HasSubject s ON b.callNumber = s.callNumber WHERE ((title like ?) OR (mainAuthor like ?) OR (subject like ?))  GROUP BY b.callNumber, b.title");
				ps.setString(1, "%");
				ps.setString(2, "%");
				ps.setString(3, "%");
				rs = ps.executeQuery();
				while (rs.next()) {
					if ((rs.getString("title").equalsIgnoreCase(title)) || (rs.getString("author").contains(author)) || (rs.getString("subject").contains(subject))) {
						System.out.printf(title + author + subject);
					} else {
						System.out.printf("no books");
					}
				}
				System.out.printf("no books");
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
