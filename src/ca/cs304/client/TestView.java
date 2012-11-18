package ca.cs304.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TestView {

	private Connection connection; 
	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private List<Table> tables = new ArrayList<Table>();
	private List<Transaction> transactions = new ArrayList<Transaction>();

	public TestView(Connection connection) {
		this.connection = connection;

		// Add tables to test
		Book book = new Book(connection);
		Book borrowerType = new Book(connection); //TODO 
		Book borrower = new Book(connection); //TODO
		HasAuthor hasAuthor = new HasAuthor(connection);
		HasSubject hasSubject = new HasSubject(connection);
		BookCopy bookCopy = new BookCopy(connection);
		HoldRequest holdRequest = new HoldRequest(connection);
		Borrowing borrowing = new Borrowing(connection);
		Fine fine = new Fine(connection);

		tables.add(book);
		tables.add(borrowerType);
		tables.add(borrower);
		tables.add(hasAuthor);
		tables.add(hasSubject);
		tables.add(bookCopy);
		tables.add(holdRequest);
		tables.add(borrowing);
		tables.add(fine);
		
		// TODO: Add transactions to test
	}

	public void showMenu() {
		{
			int actionChoice = 0;
			int tableChoice = 0;
			boolean quit;

			quit = false;

			try 
			{
				// disable auto commit mode
				connection.setAutoCommit(false);

				while (!quit)
				{
					System.out.print("\n\nTest View - Please choose one of the following: \n");
					System.out.print("1.  Test Table Insertion\n");
					System.out.print("2.  Test Table Deletion \n");
					System.out.print("3.  Show Table\n");
					System.out.print("4.  Test Transactions\n");
					System.out.print("5.  Show All Tables\n");
					System.out.print("6.  Return to main menu\n>> ");

					actionChoice = Integer.parseInt(in.readLine());

					System.out.println(" ");

					
					// Menu for selecting a table to insert/delete a row
					if (actionChoice <= 3 && actionChoice > 0) {
						System.out.print("\n\nPlease a table to test: \n");
						System.out.print("1.  Book\n");
						System.out.print("2.  BorrowerType \n");
						System.out.print("3.  Borrower\n");
						System.out.print("4.  HasAuthor\n");
						System.out.print("5.  HasSubject\n");
						System.out.print("6.  BookCopy\n");
						System.out.print("7.  HoldRequest\n");
						System.out.print("8.  Borrowing\n");
						System.out.print("9.  Fine\n");
						tableChoice = Integer.parseInt(in.readLine());
						System.out.println(" ");
					}
					
					// Menu for test a particular transaction
					if (actionChoice == 4) {
						System.out.print("\n\nPlease choose a transaction to execute\n");
						System.out.print("1.  Clerk - Add Borrower\n");
						System.out.print("2.  Clerk - Check out items\n");
						System.out.print("3.  Clerk - Process a return\n");
						System.out.print("4.  Clerk - Check overdue items\n");
						System.out.print("5.  Borrower - Search Books\n");
						System.out.print("6.  Borrower - Check account\n");
						System.out.print("7.  Borrower - Place hold request\n");
						System.out.print("8.  Borrower - Pay a fine\n");
						System.out.print("9.  Librarian - Add a book/copy\n");
						System.out.print("10.  Librarian - Show list of checked out books\n");
						System.out.print("11.  Librarian - Show this year's most popular item\n");
						tableChoice = Integer.parseInt(in.readLine());
						System.out.println(" ");
					}
					
					switch(actionChoice)
					{
					case 1:  testInsert(tableChoice); break;
					case 2:  testDelete(tableChoice); break;
					case 3:  testDisplay(tableChoice); break;
					case 4:	 testTransaction(tableChoice); break;
					case 5:  displayAllTables(); break;
					case 6:  quit = true; break;
					default: quit = true;
					}
				}

			}
			catch (IOException e)
			{
				System.out.println("IOException!");

				try
				{
					connection.close();
					System.exit(-1);
				}
				catch (SQLException ex)
				{
					System.out.println("Message: " + ex.getMessage());
				}
			}
			catch (SQLException ex)
			{
				System.out.println("Message: " + ex.getMessage());
			}
		}
	}

	private void testInsert(int tableChoice) {

		if ((tableChoice < 0) || (tableChoice >= tables.size())) {
			System.out.println("Invalid Table Number");
		}
		
		else {
			List<String> parameters = acceptParameters();
			tables.get(tableChoice).insert(parameters);
		}

	}

	private void testDelete(int tableChoice) {
		
		if ((tableChoice < 0) || (tableChoice >= tables.size())) {
			System.out.println("Invalid Table Number");
		}
		
		else {
			List<String> parameters = acceptParameters();
			tables.get(tableChoice).delete(parameters);
		}
	}

	private void testDisplay(int tableChoice) {
		tables.get(tableChoice - 1).display();
	}

	private void displayAllTables() {

		for (Table table : tables) {
			table.display();
		}
	}
	
	private void testTransaction(int tableChoice) {
		
		if ((tableChoice < 0) || (tableChoice >= transactions.size())) {
			System.out.println("Invalid Transaction Number");
		}
		
	}

	/**
	 * Asks users to input parameters in the format: 
	 * "Test, test, love, books"
	 * It converts that into a List<String> (of four elements)
	 * @return List<String> A parsed list of parameters
	 */
	private List<String> acceptParameters() {
		System.out.println("Please insert the necessary parameters with \", \" between each parameter for the table you selected (Don't enter it wrong!)");
		List<String> parameters = new ArrayList<String>();

		try {
			String parameterInput = in.readLine();
			// String Tokenizer to parse line to parameters - Inspired by CPSC 310 Member Chris Thomson
			StringTokenizer parametersTokenizer = new StringTokenizer(parameterInput, ", ");
			while (parametersTokenizer.hasMoreTokens()) {
				parameters.add(parametersTokenizer.nextToken());
			}
			
		} catch (IOException e) {
			System.out.println("Bad Parameters!");
			e.printStackTrace();
		}
		return parameters;
	}
}