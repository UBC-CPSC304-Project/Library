package ca.cs304.client;

import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LibraryClerkView extends JPanel{
	
	Connection connection;
	
	public LibraryClerkView(Connection connection) {
		
		this.connection = connection;
		setLayout(new GridLayout(2, 2));	// 2 x 2 layout
		addButtons();
	}

	private void addButtons() {
		
		JButton addBorrowerButton = new JButton("Add Borrower");
		addBorrowerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				showAddBorrowerDialog();
			}
		});
		
		JButton checkOutItemsButton = new JButton("Check Out Items");
		checkOutItemsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				showCheckOutItemsDialog();
			}
		});
		
		JButton returnButton = new JButton("Process a Return");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				showProcessReturnsDialog();
			}
		});
		
		JButton overDueButton = new JButton("Check Overdue Items");
		overDueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				showCheckOverdueDialog();
			}
		});
		
		add(addBorrowerButton);
		add(checkOutItemsButton);
		add(returnButton);
		add(overDueButton);
	}

	private void showAddBorrowerDialog() {
		final JDialog addBorrowerDialog = new JDialog();
		final JPanel borrowerInputPanel = new JPanel();
		final JLabel finishLabel = new JLabel("Borrower added to the database");
		final JLabel passwordLabel = new JLabel("Please enter a password: ");
		final JLabel nameLabel = new JLabel("Please enter a name: ");
		final JLabel addressLabel = new JLabel("Please enter an address: ");
		final JLabel phoneLabel = new JLabel("Please enter a phone number: ");
		final JLabel emailLabel = new JLabel("Please enter an email address: ");
		final JLabel sinOrStNoLabel = new JLabel("Please enter a student or social insurance number: ");
		final JLabel expiryDateLabel = new JLabel("Please enter an expiry date: ");
		final JLabel typeLabel = new JLabel("Please enter a borrower type: ");
		final JTextField borrowerPasswordField = new JTextField(10);
		final JTextField borrowerNameField = new JTextField(20);
		final JTextField borrowerAddressField = new JTextField(20);
		final JTextField borrowerPhoneField = new JTextField(10);
		final JTextField borrowerEmailField = new JTextField(20);
		final JTextField borrowerSinOrStNoField = new JTextField(8);
		final JTextField borrowerExpiryField = new JTextField(10);
		final String[] borrowerTypes ={"Student", "Faculty", "Librarian"};
		final JComboBox borrowerTypeBox = new JComboBox(borrowerTypes);

		final JButton borrowerButton = new JButton("Add Borrower");
		

		borrowerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				List<String> params = new ArrayList<String>();
				params.add(borrowerPasswordField.getText());
				params.add(borrowerNameField.getText());
				params.add(borrowerAddressField.getText());
				params.add(borrowerPhoneField.getText());
				params.add(borrowerEmailField.getText());
				params.add(borrowerSinOrStNoField.getText());
				params.add(borrowerExpiryField.getText());
				params.add(borrowerTypeBox.getSelectedItem().toString().toLowerCase());
				
				AddBorrower addBorrowerTransaction = new AddBorrower(connection);
				addBorrowerTransaction.execute(params);
				
				addBorrowerDialog.dispose();
			}
		});
		borrowerInputPanel.setLayout(new BoxLayout(borrowerInputPanel, BoxLayout.X_AXIS));

		borrowerInputPanel.setLayout(new GridLayout(9, 2, 5, 5));
		borrowerInputPanel.add(passwordLabel);
		borrowerInputPanel.add(borrowerPasswordField);
		borrowerInputPanel.add(nameLabel);
		borrowerInputPanel.add(borrowerNameField);
		borrowerInputPanel.add(addressLabel);
		borrowerInputPanel.add(borrowerAddressField);
		borrowerInputPanel.add(phoneLabel);
		borrowerInputPanel.add(borrowerPhoneField);
		borrowerInputPanel.add(emailLabel);
		borrowerInputPanel.add(borrowerEmailField);
		borrowerInputPanel.add(sinOrStNoLabel);
		borrowerInputPanel.add(borrowerSinOrStNoField);
		borrowerInputPanel.add(expiryDateLabel);
		borrowerInputPanel.add(borrowerExpiryField);
		borrowerInputPanel.add(typeLabel);
		borrowerInputPanel.add(borrowerTypeBox);
		borrowerInputPanel.add(new JLabel(""));
		borrowerInputPanel.add(borrowerButton);
		
		addBorrowerDialog.setLayout(new BoxLayout(addBorrowerDialog.getContentPane(), BoxLayout.Y_AXIS));
		addBorrowerDialog.add(borrowerInputPanel);

		addBorrowerDialog.setModalityType(ModalityType.APPLICATION_MODAL);		// Disables input in MainVew
		addBorrowerDialog.setTitle("Add Borrower");
		addBorrowerDialog.setLocationRelativeTo(null);
		addBorrowerDialog.pack();
		addBorrowerDialog.setVisible(true);
		
		
		ResultSetDialog rs = new ResultSetDialog("Add Borrower", null);
		rs.add(finishLabel);

		
	}
	private void showCheckOutItemsDialog() {
		final JDialog checkOutDialog = new JDialog();
		final JLabel checkOutLabel = new JLabel("Check out items for a borrower");
		final JPanel checkOutInputPanel = new JPanel();
		final JLabel bidLabel = new JLabel(" Please enter a bid: ");
		final JLabel callNumberLabel = new JLabel(" Please enter a call number: ");
		final JTextField checkOutBidField = new JTextField(10);
		final JTextField checkOutCallNumberField = new JTextField(20);

		final JButton checkOutButton = new JButton("Check Out Items");
		

		checkOutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String bid = checkOutBidField.getText();
				String callNumber = checkOutCallNumberField.getText();
				
				// Check if borrower exists
				Borrower borrowerTable = new Borrower(connection);
				if (!borrowerTable.isBorrowerExist(bid)) {
					checkOutLabel.setText("Borrower does not exist");
					return;
				}
				
				// Check fines
				Fine fineTable = new Fine(connection);
				if (fineTable.checkFines(bid).size() > 0) {
					checkOutLabel.setText("This borrower has unpaid fines");
					return;
				}
				
				// Check if call number exists
				Book bookTable = new Book(connection);
				if (!bookTable.findBook(callNumber)) {
					checkOutLabel.setText("Unknown call number");
					return;
				}
				
				// Check if there are any availible copies
				BookCopy bookCopyTable = new BookCopy(connection);
				if (bookCopyTable.availibleCopies(callNumber) <= 0) {
					checkOutLabel.setText("No availible copies for this book");
					return;
				}
				
				checkOutItems(bid, callNumber);
				checkOutDialog.dispose();
			}

		});
		checkOutInputPanel.setLayout(new BoxLayout(checkOutInputPanel, BoxLayout.X_AXIS));

		checkOutInputPanel.setLayout(new GridLayout(0, 2, 5, 5));
		checkOutInputPanel.add(bidLabel);
		checkOutInputPanel.add(checkOutBidField);
		checkOutInputPanel.add(callNumberLabel);
		checkOutInputPanel.add(checkOutCallNumberField);
		checkOutInputPanel.add(new JLabel(""));
		checkOutInputPanel.add(checkOutButton);
		
		checkOutLabel.setAlignmentX(0.5f);
		
		checkOutDialog.setLayout(new BoxLayout(checkOutDialog.getContentPane(), BoxLayout.Y_AXIS));
		checkOutDialog.add(checkOutLabel);
		checkOutDialog.add(checkOutInputPanel);

		checkOutDialog.setModalityType(ModalityType.APPLICATION_MODAL);		// Disables input in MainVew
		checkOutDialog.setTitle("Check Out Items");
		//searchDialog.setDefaultCloseOperation(EXIT_ON_CLOSE);
		checkOutDialog.setLocationRelativeTo(null);
		checkOutDialog.pack();
		checkOutDialog.setVisible(true);
		
	}
	
	private void showProcessReturnsDialog() {
		final JDialog processReturnsDialog = new JDialog();
		final JLabel processReturnsLabel = new JLabel("Process a return for a book");
		final JPanel processReturnsInputPanel = new JPanel();   
		final JLabel callNumberLabel = new JLabel(" Please enter a call number: ");
		final JTextField processReturnsCallNumberField = new JTextField(10);
		final JLabel copyNoLabel = new JLabel(" Please enter a copy number: ");
		final JTextField processReturnsCopyNoField = new JTextField(10);
	
		final JButton processReturnsButton = new JButton("Process Returns");
		

		processReturnsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				BookCopy bookCopyTable = new BookCopy(connection);
				Borrowing borrowingTable = new Borrowing(connection);
				Fine fineTable = new Fine(connection);
				
				String callNumber = processReturnsCallNumberField.getText();
				String copyNo = processReturnsCopyNoField.getText();
				
				// Check if BookCopy exists
				if (!bookCopyTable.isExist(callNumber, copyNo)) {
					processReturnsLabel.setText("Book copy does not exist");
					return;
				}
				
				// Check if Book is Overdue
				if (borrowingTable.isOverdue(callNumber, copyNo)) {
					processReturnsLabel.setText("Returned book is overdue");
					List<String> borrowingParameters = borrowingTable.getSelectedParameters();
					
					// Make a fine
					GregorianCalendar calendar = new GregorianCalendar();
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					
					String borid = borrowingParameters.get(0);
					String amount = "10.0";
					String today = dateFormat.format(calendar.getTime());
					
					List<String> fineParameters = new ArrayList<String>();
					fineParameters.add(amount);
					fineParameters.add(today);
					fineParameters.add(null);
					fineParameters.add(borid);
					
					fineTable.insert(fineParameters);
				}
				
				// Return the book and make hold request if necessary
				Transaction returns = new Returns(connection);
				List<String> returnsParameters = new ArrayList<String>();

				returnsParameters.add(callNumber);
				returnsParameters.add(copyNo);
				
				returns.execute(returnsParameters);

				//processReturnsDialog.dispose();
			}
		});
		processReturnsInputPanel.setLayout(new BoxLayout(processReturnsInputPanel, BoxLayout.X_AXIS));

		processReturnsInputPanel.setLayout(new GridLayout(0, 2, 5, 5));
		processReturnsInputPanel.add(callNumberLabel);
		processReturnsInputPanel.add(processReturnsCallNumberField);
		processReturnsInputPanel.add(copyNoLabel);
		processReturnsInputPanel.add(processReturnsCopyNoField);
		processReturnsInputPanel.add(new JLabel(""));
		processReturnsInputPanel.add(processReturnsButton);
		
		processReturnsDialog.setLayout(new BoxLayout(processReturnsDialog.getContentPane(), BoxLayout.Y_AXIS));
		processReturnsDialog.add(processReturnsLabel);
		processReturnsDialog.add(processReturnsInputPanel);
		
		processReturnsLabel.setAlignmentX(0.5f);

		processReturnsDialog.setModalityType(ModalityType.APPLICATION_MODAL);		// Disables input in MainVew
		processReturnsDialog.setTitle("Process Return");
		//searchDialog.setDefaultCloseOperation(EXIT_ON_CLOSE);
		processReturnsDialog.setLocationRelativeTo(null);
		processReturnsDialog.pack();
		processReturnsDialog.setVisible(true);
		
		
		
	}
	
	private void showCheckOverdueDialog() {
		
		Overdue overdueTransaction = new Overdue(connection);
		ResultSet resultSet = overdueTransaction.execute(null);
		
		CheckOverdueItemsDialog overdueDialog = new CheckOverdueItemsDialog("Overdue Items", resultSet);
		overdueDialog.setVisible(true);
	}
	
	private void checkOutItems(String bid, String callNumber) {
		
		CheckOutItems checkOutItemsTransaction = new CheckOutItems(connection);
		
		List<String> params = new ArrayList<String>();
		params.add(bid);
		params.add(callNumber);
		ResultSet resultSet = checkOutItemsTransaction.execute(params);
		ResultSetDialog resultSetDialog = new ResultSetDialog("Borrower Receipt", resultSet);
		resultSetDialog.setVisible(true);
	}
}

