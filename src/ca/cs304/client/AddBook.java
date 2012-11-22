package ca.cs304.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

public class AddBook extends Transaction {	
	public AddBook(Connection connection) {
		super(connection);
	}

	/*
	 * @params (String callNumber, String isbn, String title, String mainAuthor, String publisher, String year)
	 *
	 */
	@Override
	public ResultSet execute(List<String> parameters) {
		Book book = new Book(connection);
		String status = "in";
		PreparedStatement ps;
		PreparedStatement ps1;
		try
		{
			ps = connection.prepareStatement("INSERT INTO book VALUES (?, ?, ?, ?, ?, ?)");
			String callNumber = parameters.get(0);
			if (book.findBook(callNumber) == true) {
				ps1 = connection.prepareStatement("SELECT MAX(copyNo) FROM bookcopy,book WHERE bookcopy.callNumber = book.callNumber");
				rs = ps1.executeQuery();
				String copy = rs.getString("copyNo");
				int copyInc = Integer.parseInt(copy);
				copyInc += 1;
				String copyNo = Integer.toString(copyInc);
				System.out.printf("Book with callNumber already exists with copyNo: " + copyNo);

				ps1 = connection.prepareStatement("INSERT INTO bookcopy VALUES (?, ?, ?)");
				ps1.setString(1, callNumber);
				ps1.setString(2, copyNo);
				ps1.setString(3, status);

				connection.commit();
				ps1.close();
			
			} else {
				book.insert(parameters);
				ps1 = connection.prepareStatement("INSERT INTO bookcopy VALUES (?, ?, ?)");
				
				String bookCopy = "1";
				ps1.setString(1, callNumber);
				System.out.printf("CallNumber: " + callNumber);
				
				ps.setString(2, bookCopy);
				System.out.printf("bookCopy: " + bookCopy);
				
				ps.setString(3, status);
				System.out.printf("status: " + status);

				ps.executeUpdate();
				connection.commit();
				ps.close();

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

}
