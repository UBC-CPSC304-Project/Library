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
			String search = parameters.get(0);
			
			try {

				ps = connection.prepareStatement("SELECT b.callNumber, b.isbn, b.title, b.mainAuthor, b.publisher, b.year, bc.copyNo, bc.status, s.subject FROM Book b JOIN HasSubject s ON b.callNumber = s.callNumber LEFT OUTER JOIN BookCopy bc ON bc.callNumber = b.callNumber WHERE ((title like ?) OR (mainAuthor like ?) OR (subject like ?))");
				ps.setString(1, "%");
				ps.setString(2, "%");
				ps.setString(3, "%");
				rs = ps.executeQuery();
				while(rs.next()) {
					if ((rs.getString("title").equalsIgnoreCase(search)) || (rs.getString("mainAuthor").equalsIgnoreCase(search)) || (rs.getString("subject").equalsIgnoreCase(search))) {		

						String callNumber = rs.getString("callNumber");
						String isbn = rs.getString("isbn");
						String title = rs.getString("title");
						String mainAuthor = rs.getString("mainAuthor");
						String publisher = rs.getString("publisher");
						String year = rs.getString("year");
						String copyNo = rs.getString("copyNo");
						String status = rs.getString("status");
						String subject = rs.getString("subject");

						System.out.println(callNumber + "\t" + isbn + "\t" + title + "\t" + mainAuthor + 
								"\t" + publisher + "\t" + year + "\t" + copyNo + "\t" + status + "\t" + subject);
					} else {
						try {
							throw new Exception("Book does not exist");
						} catch (Exception e) {
						}
					}
				}
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
