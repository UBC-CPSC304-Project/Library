package ca.cs304.client;

import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LibraryClerkView extends JPanel{
	
	AddBorrower addBorrower;
	CheckOutItems checkOutItems;
	Returns processReturns;
	Overdue overdue;
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
		final JTextField borrowerTypeField = new JTextField(10);

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
				params.add(borrowerTypeField.getText());		
				addBorrower.execute(params);
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
		borrowerInputPanel.add(borrowerTypeField);
		borrowerInputPanel.add(borrowerButton);
		
		addBorrowerDialog.setLayout(new BoxLayout(addBorrowerDialog.getContentPane(), BoxLayout.Y_AXIS));
		addBorrowerDialog.add(borrowerInputPanel);

		addBorrowerDialog.setModalityType(ModalityType.APPLICATION_MODAL);		// Disables input in MainVew
		addBorrowerDialog.setTitle("Add Borrower");
		addBorrowerDialog.setLocationRelativeTo(null);
		addBorrowerDialog.pack();
		addBorrowerDialog.setVisible(true);

		
	}
	private void showCheckOutItemsDialog() {
		final JDialog checkOutDialog = new JDialog();
		final JLabel checkOutLabel = new JLabel("Check out items for a borrower");
		final JPanel checkOutInputPanel = new JPanel();
		final JLabel bidLabel = new JLabel(" Please enter a bid: ");
		final JLabel callNumberLabel = new JLabel(" Please enter a call number: ");
		final JLabel copyNoLabel = new JLabel(" Please enter a copy number:  ");
		final JTextField checkOutBidField = new JTextField(10);
		final JTextField checkOutCallNumberField = new JTextField(20);
		final JTextField checkOutCopyField = new JTextField(20);

		final JButton checkOutButton = new JButton("Check Out Items");
		

		checkOutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String bid = checkOutBidField.getText();
				String callNumber = checkOutCallNumberField.getText();
				String copyNo = checkOutCopyField.getText();
				
				Fine fineTable = new Fine(connection);
				if (fineTable.checkFines(bid).size() > 0) {
					checkOutLabel.setText("This borrower has unpaid fines");
				}
				
				Book bookTable = new Book(connection);
				if (!bookTable.findBook(callNumber)) {
					checkOutLabel.setText("Unknown call number");
				}
				
				
				
				checkOutItems(bid, callNumber, copyNo);
				
				checkOutDialog.dispose();
			}

		});
		checkOutInputPanel.setLayout(new BoxLayout(checkOutInputPanel, BoxLayout.X_AXIS));

		checkOutInputPanel.setLayout(new GridLayout(4, 2, 5, 5));
		checkOutInputPanel.add(bidLabel);
		checkOutInputPanel.add(checkOutBidField);
		checkOutInputPanel.add(callNumberLabel);
		checkOutInputPanel.add(checkOutCallNumberField);
		checkOutInputPanel.add(copyNoLabel);
		checkOutInputPanel.add(checkOutCopyField);
		checkOutInputPanel.add(checkOutButton);
		
		checkOutDialog.setLayout(new BoxLayout(checkOutDialog.getContentPane(), BoxLayout.Y_AXIS));
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
		final JPanel processReturnsInputPanel = new JPanel();   
		final JLabel callNumberLabel = new JLabel(" Please enter a call number: ");
		final JTextField processReturnsCallNumberField = new JTextField(10);
	
		final JButton processReturnsButton = new JButton("Process Returns");
		

		processReturnsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String callNumber = processReturnsCallNumberField.getText();
			
				List<String> params = new ArrayList<String>();
				params.add(callNumber);
				processReturns.execute(params);
				processReturnsDialog.dispose();
			}
		});
		processReturnsInputPanel.setLayout(new BoxLayout(processReturnsInputPanel, BoxLayout.X_AXIS));

		processReturnsInputPanel.setLayout(new GridLayout(2, 2, 5, 5));
		processReturnsInputPanel.add(callNumberLabel);
		processReturnsInputPanel.add(processReturnsCallNumberField);
		processReturnsInputPanel.add(processReturnsButton);
		
		processReturnsDialog.setLayout(new BoxLayout(processReturnsDialog.getContentPane(), BoxLayout.Y_AXIS));
		processReturnsDialog.add(processReturnsInputPanel);

		processReturnsDialog.setModalityType(ModalityType.APPLICATION_MODAL);		// Disables input in MainVew
		processReturnsDialog.setTitle("Process Return");
		//searchDialog.setDefaultCloseOperation(EXIT_ON_CLOSE);
		processReturnsDialog.setLocationRelativeTo(null);
		processReturnsDialog.pack();
		processReturnsDialog.setVisible(true);
		
	}
	
	private void showCheckOverdueDialog() {
		final JDialog checkOverdueDialog = new JDialog();
		final JPanel checkOverdueInputPanel = new JPanel();   

		checkOverdueInputPanel.setLayout(new BoxLayout(checkOverdueInputPanel, BoxLayout.X_AXIS));
		checkOverdueDialog.setLayout(new BoxLayout(checkOverdueDialog.getContentPane(), BoxLayout.Y_AXIS));
	
		overdue.execute(null);
		
		checkOverdueDialog.setModalityType(ModalityType.APPLICATION_MODAL);		// Disables input in MainVew
		checkOverdueDialog.setTitle("Overdue Items");
		//searchDialog.setDefaultCloseOperation(EXIT_ON_CLOSE);
		checkOverdueDialog.setLocationRelativeTo(null);
		checkOverdueDialog.pack();
		checkOverdueDialog.setVisible(true);
	}
	
	private void checkOutItems(String bid, String callNumber,
			String copyNo) {
		List<String> params = new ArrayList<String>();
		params.add(bid);
		params.add(callNumber);
		params.add(copyNo);
		checkOutItems.execute(params);
	}
}

