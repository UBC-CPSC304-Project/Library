package ca.cs304.client;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Renders the Borrower's interface
 */
public class LibraryBorrowerView extends JPanel {

	Connection connection;
	String bid;

	public LibraryBorrowerView(Connection connection) {

		this.connection = connection;
		setLayout(new GridLayout(2, 2));	// 2 x 2 layout with 5px of padding vertically + horizontally
		addButtons();
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	private void addButtons() {

		JButton searchBookButton = new JButton("Search Books");
		searchBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				showSearchDialog();
			}
		});

		JButton checkAccountButton = new JButton("Check Account");
		checkAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				showCheckAccountDialog();
			}
		});

		JButton placeHoldButton = new JButton("Place Hold Request");
		placeHoldButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				showPlaceHoldRequestDialog();
			}
		});

		JButton payFineButton = new JButton("Pay Fines");
		payFineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				showPayFineDialog();
			}
		});

		add(searchBookButton);
		add(checkAccountButton);
		add(placeHoldButton);
		add(payFineButton);
	}

	private void showSearchDialog() {

		final JDialog searchDialog = new JDialog();
		final JLabel searchLabel = new JLabel("Search by Title, Author, and Subject");
		final JPanel searchInputPanel = new JPanel();
		final JLabel titleLabel = new JLabel("Title:");
		final JLabel authorLabel = new JLabel("Author:");
		final JLabel subjectLabel = new JLabel("Subject:");
		final JTextField searchTitleField = new JTextField(10);
		final JTextField searchAuthorField = new JTextField(10);
		final JTextField searchSubjectField = new JTextField(10);

		final JButton searchButton = new JButton("Search");

		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String title = searchTitleField.getText();
				String author = searchAuthorField.getText();
				String subject = searchSubjectField.getText();
				search(title, author, subject);
				searchDialog.dispose();
			}
		});

		searchInputPanel.setLayout(new GridLayout(0, 2, 5, 5));
		searchInputPanel.add(titleLabel);
		searchInputPanel.add(searchTitleField);
		searchInputPanel.add(authorLabel);
		searchInputPanel.add(searchAuthorField);
		searchInputPanel.add(subjectLabel);
		searchInputPanel.add(searchSubjectField);
		searchInputPanel.add(new JLabel(""));	// Creates the empty space on lower left corner
		searchInputPanel.add(searchButton);

		searchLabel.setAlignmentX(0.5f);		// Centre label

		searchDialog.setLayout(new BoxLayout(searchDialog.getContentPane(), BoxLayout.Y_AXIS));
		searchDialog.add(searchLabel);
		searchDialog.add(searchInputPanel);

		searchDialog.setModalityType(ModalityType.APPLICATION_MODAL);		// Disables input in MainVew
		searchDialog.setTitle("Search Books");
		//searchDialog.setDefaultCloseOperation(EXIT_ON_CLOSE);
		searchDialog.setLocationRelativeTo(null);
		searchDialog.pack();
		searchDialog.setVisible(true);

	}

	private void showCheckAccountDialog() {
		checkAccount();
	}


	private void showPlaceHoldRequestDialog() {

		final JDialog holdRequestDialog = new JDialog();
		final JLabel holdRequestLabel = new JLabel("Please enter a book callnumber");
		final JPanel holdRequestInputPanel = new JPanel();
		final JTextField holdRequestInputField = new JTextField(10);
		final JButton holdRequestButton = new JButton("Place Hold Request");

		holdRequestButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				Book bookTable = new Book(connection);
				if (bookTable.findBook(holdRequestInputField.getText())) {
					placeHoldRequest(holdRequestInputField.getText());
					holdRequestDialog.dispose();
				}
				else {
					holdRequestLabel.setText("Callnumber does not exist!");
				}
			}
		});

		holdRequestInputPanel.setLayout(new BoxLayout(holdRequestInputPanel, BoxLayout.X_AXIS));
		holdRequestInputPanel.add(holdRequestInputField);
		holdRequestInputPanel.add(holdRequestButton);

		holdRequestLabel.setAlignmentX(0.5f);

		holdRequestDialog.setLayout(new BoxLayout(holdRequestDialog.getContentPane(), BoxLayout.Y_AXIS));
		holdRequestDialog.add(holdRequestLabel);
		holdRequestDialog.add(holdRequestInputPanel);

		holdRequestDialog.setModalityType(ModalityType.APPLICATION_MODAL);
		holdRequestDialog.setTitle("Search Books");
		//checkAccountDialog.setDefaultCloseOperation(EXIT_ON_CLOSE);
		holdRequestDialog.setLocationRelativeTo(null);
		holdRequestDialog.pack();
		holdRequestDialog.setVisible(true);
	}

	private void showPayFineDialog() {

		final JDialog payFineDialog = new JDialog();
		final JLabel payFineLabel = new JLabel("Pay a fine");
		final JPanel payFineInputPanel = new JPanel();
		final JButton fineButton = new JButton("Pay Fine");

		Fine fineTable = new Fine(connection);
		List<String> fines = fineTable.checkFines(bid);

		payFineLabel.setAlignmentX(0.5f);

		if (fines.size() <= 0) {

			// No fines, prompt uesr to leave the dialog
			payFineLabel.setText("You have no fines to pay :(");
			fineButton.setText("OK");
			fineButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					payFineDialog.dispose();
				}
			});

			payFineInputPanel.setLayout(new BoxLayout(payFineInputPanel, BoxLayout.X_AXIS));
			payFineInputPanel.add(fineButton);
		}

		else {

			// There are fines: Make a fine selction box to pay fines
			final JComboBox searchTypeBox = new JComboBox(fines.toArray());

			fineButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					payFine(searchTypeBox.getSelectedItem().toString());
					payFineDialog.dispose();
				}
			});	

			payFineInputPanel.setLayout(new BoxLayout(payFineInputPanel, BoxLayout.X_AXIS));
			payFineInputPanel.add(searchTypeBox);
			payFineInputPanel.add(fineButton);
		}

		payFineDialog.setLayout(new BoxLayout(payFineDialog.getContentPane(), BoxLayout.Y_AXIS));
		payFineDialog.add(payFineLabel);
		payFineDialog.add(payFineInputPanel);

		payFineDialog.setModalityType(ModalityType.APPLICATION_MODAL);
		payFineDialog.setTitle("Pay Fines");
		//checkAccountDialog.setDefaultCloseOperation(EXIT_ON_CLOSE);
		payFineDialog.setLocationRelativeTo(null);
		payFineDialog.pack();
		payFineDialog.setVisible(true);
	}

	private void search(String title, String author, String subject) {
		System.out.println("Search Book Pressed: " + title + ", " + author + ", " + subject);	//TODO
		
		Search searchTransaction = new Search(connection);
		List<String> parameters = new ArrayList<String>();

		parameters.add(title);
		parameters.add(author);
		parameters.add(subject);
		
		ResultSet resultSet = searchTransaction.execute(parameters);
		if (resultSet != null) {
			ResultSetDialog transactionDialog = new ResultSetDialog("Search Books", resultSet);
			transactionDialog.setVisible(true);
		}
		else {
			System.out.println("Error in retrieving result set!");
		}
	}

	private void checkAccount() {
		System.out.println("Check Account Pressed: " + bid);	//TODO

		CheckAccount checkAccountTransaction = new CheckAccount(connection);
		List<String> parameters = new ArrayList<String>();

		parameters.add(bid);

		ResultSet resultSet = checkAccountTransaction.execute(parameters);
		if (resultSet != null) {
			ResultSetDialog transactionDialog = new ResultSetDialog("Check Account", resultSet);
			transactionDialog.setVisible(true);
		}
		else {
			System.out.println("Error in retrieving result set!");
		}
	}

	private void placeHoldRequest(String callNumber) {
		System.out.println("Place Hold Request Pressed: " + callNumber + " holding for " + bid);	//TODO

		PlaceHoldRequest placeHoldRequestTransaction = new PlaceHoldRequest(connection);
		List<String> parameters = new ArrayList<String>();

		parameters.add(bid);
		parameters.add(callNumber);

		placeHoldRequestTransaction.execute(parameters);
		placeHoldRequestTransaction.closeStatement();
	}

	private void payFine(String fid) {
		System.out.println("Pay Fine Pressed: " + fid);	

		PayFine payFineTransaction = new PayFine(connection);
		List<String> parameters = new ArrayList<String>();

		parameters.add(fid);

		payFineTransaction.execute(parameters);
		payFineTransaction.closeStatement();
	}
}
