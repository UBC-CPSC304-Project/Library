package ca.cs304.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class AddBook extends Transaction {	
	public AddBook(Connection connection) {
		super(connection);
	}

	/*
	 * @params (String callNumber, String isbn, String title, String mainAuthor, String publisher, String year, String otherAuthors, String subjects)
	 *
	 */
	@Override
	public ResultSet execute(List<String> parameters) {
		Book book = new Book(connection);
		String status = "in";
		String copyNo = "copyNo";
		PreparedStatement ps;
		try
		{
			String callNumber = parameters.get(0);
			
			if (book.findBook(callNumber) == true) {
				ps = connection.prepareStatement("SELECT copyNo FROM BookCopy bc WHERE (copyNo = (Select Max(CopyNo) from BookCopy) AND callNumber = ?)"); 
				ps.setString(1, callNumber);
				rs = ps.executeQuery();
				while (rs.next()) {
					String copy = rs.getString("copyNo");
					int copyInc = Integer.parseInt(copy);
					copyInc += 1;
					String copyN = Integer.toString(copyInc);
					System.out.printf("Book with callNumber already exists.  Adding copyNo: " + copyN);

					ps = connection.prepareStatement("INSERT INTO bookcopy VALUES (?, ?, ?)");
					ps.setString(1, callNumber);
					ps.setString(2, copyN);
					ps.setString(3, status);
				}
				ps.executeUpdate();
				connection.commit();
				ps.close();
				rs.close();

			} else {

				book.insert(parameters);
				ps = connection.prepareStatement("INSERT INTO bookcopy VALUES (?, ?, ?)");

				String bookCopy = "1";
				ps.setString(1, callNumber);
				System.out.printf("CallNumber: " + callNumber);

				ps.setString(2, bookCopy);
				System.out.printf("bookCopy: " + bookCopy);

				ps.setString(3, status);
				System.out.printf("status: " + status);

				ps.executeUpdate();
				connection.commit();
				ps.close();
				
				// Add subjects and authors
				List<String> otherAuthorsList = tokenizeString(parameters.get(6));
				List<String> subjectsList = tokenizeString(parameters.get(7));
				
				for (int i = 0; i < otherAuthorsList.size(); i++) {
					
					List<String> authorParameters = new ArrayList<String>();
					authorParameters.add(callNumber);
					authorParameters.add(otherAuthorsList.get(i));
					
					HasAuthor hasAuthorTable = new HasAuthor(connection);
					hasAuthorTable.insert(authorParameters);
				}
				
				for (int i = 0; i < subjectsList.size(); i++) {
					
					List<String> subjectParameters = new ArrayList<String>();
					subjectParameters.add(callNumber);
					subjectParameters.add(subjectsList.get(i));
					
					HasSubject hasSubjectTable = new HasSubject(connection);
					hasSubjectTable.insert(subjectParameters);
				}
			}
		} catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			try {
				connection.rollback();	
			} catch (SQLException ex2) {
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
		return null;	
	}

	/**
	 * Parses paramters in the format: 
	 * "Test, test, love, books"
	 * It converts that into a List<String> (of four elements)
	 * @param String a comma separated list of words
	 * @return List<String> A parsed list of parameters
	 */
	private List<String> tokenizeString(String parameterInput) {
		List<String> parameters = new ArrayList<String>();

		// String Tokenizer to parse line to parameters - Inspired by CPSC 310 Member Chris Thomson
		StringTokenizer parametersTokenizer = new StringTokenizer(parameterInput, ", ");
		while (parametersTokenizer.hasMoreTokens()) {
			parameters.add(parametersTokenizer.nextToken());
		}
		return parameters;
	}

}
