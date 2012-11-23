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
				ps = connection.prepareStatement("SELECT * FROM Book b, HasSubject WHERE ((subject like ?) OR (title like ?) OR (mainAuthor like ?))");
				ps.setString(1, "%");
				ps.setString(2, "%");
				ps.setString(3, "%");
				rs = ps.executeQuery();
				while (rs.next()) {
					if (rs.getString("title").equalsIgnoreCase(search) | (rs.getString("mainAuthor").equalsIgnoreCase(search)) | (rs.getString("subject").equalsIgnoreCase(search))) {
					String callNumber = rs.getString("callNumber");
					String isbn = rs.getString("isbn");
					String title = rs.getString("title");
					String mainAuthor = rs.getString("mainAuthor");
					String publisher = rs.getString("publisher");
					String year = rs.getString("year");
					//Integer copyNo = rs.getInt("bookcopy_copyno");
					//String status = rs.getString("bookcopy_status");
					System.out.println(callNumber + "\t" + isbn + "\t" + title + "\t" + mainAuthor + 
							"\t" + publisher + "\t" + year + "\t");
				}
			}
				 connection.commit();
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
