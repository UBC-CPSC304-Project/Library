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
		String copyNo = "copyNo";
		PreparedStatement ps;
		try
		{
			ps = connection.prepareStatement("INSERT INTO book VALUES (?, ?, ?, ?, ?, ?)");
			String callNumber = parameters.get(0);
			if (book.findBook(callNumber) == true) {
				ps = connection.prepareStatement("SELECT copyNo FROM BookCopy bc WHERE (copyNo = (Select Max(CopyNo) from BookCopy) AND callNumber = ?)"); 
				rs = ps.executeQuery();
				 if (rs.next()) {
				String copy = rs.getString("copyNo");
				int copyInc = Integer.parseInt(copy);
				copyInc += 1;
				String copyN = Integer.toString(copyInc);
				System.out.printf("Book with callNumber already exists with copyNo: " + copyN);
				 
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
