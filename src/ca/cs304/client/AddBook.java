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

	@Override
	public ResultSet execute(List<String> parameters) {
		Book book = new Book();
		PreparedStatement ps;
		PreparedStatement ps1;
		PreparedStatement ps2;
		try
		{
			ps = connection.prepareStatement("INSERT INTO book VALUES ?,?,?,?,?,?)");
			String callNumber = parameters.get(0);
			if (book.findBook(callNumber) == true) {
				ps2 = connection.prepareStatement("INSERT INTO bookCopy VALUES ?");
				String copyNo = parameters.get(1);
				int incCopyNumber = Integer.parseInt(copyNo) + 1;
				String updatedCopyNo = String.valueOf(incCopyNumber);
				ps2.setString(2, updatedCopyNo);
				ps2.executeUpdate();

				connection.commit();
				ps2.close();

			}
			ps.setString(1, callNumber);

			String isbn= parameters.get(1);
			ps.setString(2, isbn);

			String title = parameters.get(2);
			ps.setString(3, title);
		
			String mainAuthor = parameters.get(3);
			ps.setString(4, mainAuthor);
			
			String publisher = parameters.get(4);
			ps.setString(5, publisher);
			
			String year = parameters.get(5);
			ps.setString(6, year);
			
			ps.executeUpdate();
			connection.commit();
			ps.close();

			ps1 = connection.prepareStatement("UPDATE book SET callNumber = callNumber, isbn = isbn, title = title, mainAuthor = mainAuthor, publisher = publisher, year = year");
			ps1.setString(1, callNumber);
			ps1.setString(2, isbn);
			ps1.setString(3, title);
			ps1.setString(4, mainAuthor);
			ps1.setString(5, publisher);
			ps1.setString(6,  year);
			ps1.executeUpdate();

			// commit work 
			connection.commit();


			ps1.close();
		}
		catch (SQLException ex)
		{
			//JOptionPane.showMessageDialog(null,"Message: " + ex.getMessage());

			try 
			{
				// undo the insert
				connection.rollback();	

			}
			catch (SQLException ex2)
			{
				JOptionPane.showMessageDialog(null, "Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}

		return null;
	}

}
