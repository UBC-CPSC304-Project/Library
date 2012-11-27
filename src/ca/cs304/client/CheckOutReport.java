package ca.cs304.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CheckOutReport extends Transaction {

	/*
	 * CheckOutReport - Generate a report with all the books
	 * that have been checked out. For each book the report
	 * shows the date it was checked out and the due date. 
	 * The system flags the items that are overdue. The items
	 * are ordered by the book call number.  If a subject is 
	 * provided the report lists only books related to that 
	 * subject, otherwise all the books that are out are 
	 * listed by the report.
	 */
	
	 BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	 
	public CheckOutReport(Connection connection) {
		super(connection);
	}

	@Override
	public ResultSet execute(List<String> parameters){
		// TODO Auto-generated method stub
		
			int choice;
			try{
				System.out.println("Would you like to search by subject?");
				System.out.println("1.   Yes.");
				System.out.println("2.   No.");
				
				try{
				choice = Integer.parseInt(in.readLine());
				
				switch(choice){
				case 1: 
					
					String subject = in.readLine();	
					ps = connection.prepareStatement("SELECT B.callNumber, B.outDate, B.inDate" +
														"FROM Borrowing B, BookCopy bk, HasSubject S" +
                            							"WHERE B.callNumber = S.callNumber" +
														"AND bk.callNumber = B.callNumber" +
                            							"AND bk.status='out'" +
														"AND S.subject=?" +
                            							"ORDER B.callNumber ASC");
                    ps.setString(1, subject);
                    rs = ps.executeQuery();						
					break;
					
				case 2:
					ps = connection.prepareStatement("SELECT B.callNumber, B.outDate, B.inDate" +
							"FROM Borrowing B, BookCopy bk" +
							"WHERE bk.callNumber = B.callNumber" +
							"AND bk.status='out'" +
							"ORDER bk.callNumber ASC");
					rs = ps.executeQuery();
					break;
				}
				}
				catch (IOException e)
				{
				    System.out.println("IOException!");
				}
				}
		catch (SQLException ex){
			System.out.println("Message: " + ex.getMessage());
			
			try {
				connection.rollback();
				
			}
			catch (SQLException ex2){
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
		return rs;
	}

}
