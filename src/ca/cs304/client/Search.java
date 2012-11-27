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
		String titleSearch = parameters.get(0);
		String authorSearch = parameters.get(1);
		String subjectSearch = parameters.get(2);

		try {
			String hasSubjectSubquery = "SELECT callNumber, title " +
					"FROM Book B2 NATURAL INNER JOIN HasSubject HS " +
					"WHERE (subject LIKE ?) " +
					"AND (B.callNumber = callNumber)) ";
			
			String hasAuthorSubquery = "SELECT callNumber, title " +
					"FROM Book B3 NATURAL INNER JOIN HasAuthor HA " +
					"WHERE (name LIKE ?) " +
					"AND (B.callNumber = callNumber)";
			
//			String emptyKeywordSubquery = "SELECT callNumber FROM Book";
//			
//			if (authorSearch == "") {
//				hasAuthorSubquery = emptyKeywordSubquery;
//			}
//			
//			if (subjectSearch == "") {
//				hasSubjectSubquery = emptyKeywordSubquery;
//			}
			
			ps = connection.prepareStatement("SELECT callNumber, title " +
					"FROM Book B " +
					"WHERE (title LIKE ?)  " +
					"AND EXISTS ( " +
					hasSubjectSubquery +
					"AND ((mainAuthor LIKE ?) " +
					"OR EXISTS ( " +
					hasAuthorSubquery + "))");
			
			String title = ("%" + titleSearch + "%");
			String author = ("%" + authorSearch + "%");
			String subject = ("%" + subjectSearch + "%");
			
			ps.setString(1, title);
			ps.setString(2, subject);	//in subquery
			ps.setString(3, author);
			ps.setString(4, author);	//in subquery
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
