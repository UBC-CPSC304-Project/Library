package ca.cs304.client;

import java.awt.Dialog.ModalityType;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class LibraryLibrarianView extends JPanel{


	AddBook addBook;
	CheckOutReport outReport;
	PopularItemsList popItems;
	Connection connection;

	public LibraryLibrarianView(Connection connection){
		this.connection = connection;
		setLayout(new GridLayout(2,2));
		addButtons();
	}

	private void addButtons() {
		// TODO Auto-generated method stub
		JButton addBookButton = new JButton("Add New Book");
		addBookButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				showAddBookDialog();
			}
		});

		JButton outReportButton = new JButton("Generate Check Out Report");
		outReportButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				showOutReportDialog();
			}
		});

		JButton popItemsButton = new JButton("Generate Popular Items List");
		popItemsButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				showPopItemsDialog();
			}
		});

		add(addBookButton);
		add(outReportButton);
		add(popItemsButton);
	}

	protected void showPopItemsDialog() {

		final JDialog popItemsDialog = new JDialog();
		final JPanel itemsInputPanel = new JPanel();
		final JLabel popularItemsLabel = new JLabel("Show a specific number of popular books by year");
		final JLabel rowNumLabel = new JLabel("Please enter number of rows:");
		final JLabel yearLabel = new JLabel("Please enter a year:");
		final JTextField rowNumField = new JTextField(10);
		final JTextField yearField = new JTextField(20);

		final JButton listButton = new JButton("Search");

		listButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
				// Check if the number of rows is valid
				if (Integer.parseInt(rowNumField.getText()) <= 0) {
					popularItemsLabel.setText("Invalid number of rows");
					return;
				}
				viewPopularItems(rowNumField.getText(), yearField.getText());
			}
		});

		itemsInputPanel.setLayout(new GridLayout(0, 2, 5, 5));
		itemsInputPanel.add(rowNumLabel);
		itemsInputPanel.add(rowNumField);
		itemsInputPanel.add(yearLabel);
		itemsInputPanel.add(yearField);
		itemsInputPanel.add(new JLabel(""));
		itemsInputPanel.add(listButton);

		popularItemsLabel.setAlignmentX(0.5f);

		popItemsDialog.setLayout(new BoxLayout(popItemsDialog.getContentPane(), BoxLayout.Y_AXIS));
		popItemsDialog.add(popularItemsLabel);
		popItemsDialog.add(itemsInputPanel);

		popItemsDialog.setModalityType(ModalityType.APPLICATION_MODAL);
		popItemsDialog.setTitle("Popular Items Search");
		popItemsDialog.setLocationRelativeTo(null);
		popItemsDialog.pack();
		popItemsDialog.setVisible(true);	
	}

	protected void showOutReportDialog() {
		// TODO Auto-generated method stub
		final JDialog showOutReportDialog = new JDialog();
		final JPanel outReportInputPanel = new JPanel();
		final JLabel outReportLabel = new JLabel("View all checked out books, or by one subject");
		final ButtonGroup radioButtonGroup = new ButtonGroup();
		final JRadioButton viewBySubjectButton = new JRadioButton("View by subject:");
		final JRadioButton viewAllButton = new JRadioButton("View all");
		final JTextField outReportSubjectField = new JTextField(10);
		final JButton showOutReportButton = new JButton("View");

		showOutReportButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (viewBySubjectButton.isSelected()) {
					viewCheckOutBooks(outReportSubjectField.getText());
				}
				else {
					viewCheckOutBooks(null);
				}
			}
		});

		radioButtonGroup.add(viewBySubjectButton);
		radioButtonGroup.add(viewAllButton);
		viewAllButton.setSelected(true);

		outReportInputPanel.setLayout(new GridLayout(3, 2, 5, 5));
		outReportInputPanel.add(viewBySubjectButton);
		outReportInputPanel.add(outReportSubjectField);
		outReportInputPanel.add(viewAllButton);
		outReportInputPanel.add(new JLabel(""));
		outReportInputPanel.add(new JLabel(""));
		outReportInputPanel.add(showOutReportButton);

		outReportLabel.setAlignmentX(0.5f);

		showOutReportDialog.setLayout(new BoxLayout(showOutReportDialog.getContentPane(), BoxLayout.Y_AXIS));
		showOutReportDialog.add(outReportLabel);
		showOutReportDialog.add(outReportInputPanel);

		showOutReportDialog.setModalityType(ModalityType.APPLICATION_MODAL);
		showOutReportDialog.setTitle("Generate Check Out Report");
		showOutReportDialog.setLocationRelativeTo(null);
		showOutReportDialog.pack();
		showOutReportDialog.setVisible(true);

	}

	protected void showAddBookDialog() {

		final JDialog addBookDialog = new JDialog();
		final JLabel addBookLabel = new JLabel("Add a new book or a new copy");
		final JPanel addBookInputPanel = new JPanel();
		final JLabel callNumberLabel = new JLabel("Call Number:");
		final JLabel isbnLabel = new JLabel("ISBN:");
		final JLabel titleLabel = new JLabel("Title:");
		final JLabel mainAuthorLabel = new JLabel("Main Author:");
		final JLabel publisherLabel = new JLabel("Publisher:");
		final JLabel yearLabel = new JLabel("Year:");
		final JLabel otherAuthorsLabel = new JLabel("Other Authors:");
		final JLabel subjectsLabel = new JLabel("Subjects:");
		final JTextField callNumberField = new JTextField(10);
		final JTextField isbnField = new JTextField(10);
		final JTextField titleField = new JTextField(10);
		final JTextField mainAuthorField = new JTextField(10);
		final JTextField publisherField = new JTextField(10);
		final JTextField yearField = new JTextField(10);
		final JTextField otherAuthorsField = new JTextField(10);
		final JTextField subjectsField = new JTextField(10);

		final JButton addBookButton = new JButton("Add");

		addBookButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String callNumber = callNumberField.getText();
				String isbn = isbnField.getText();
				String title = titleField.getText();
				String mainAuthor = mainAuthorField.getText();
				String publisher = publisherField.getText();
				String year = yearField.getText();
				String otherAuthors = otherAuthorsField.getText();
				String subjects = subjectsField.getText();

				addBook(callNumber, isbn, mainAuthor, title, publisher, year, otherAuthors, subjects);
				addBookDialog.dispose();
			}
		});

		addBookInputPanel.setLayout(new GridLayout(0, 2, 5, 5));
		addBookInputPanel.add(callNumberLabel);
		addBookInputPanel.add(callNumberField);
		addBookInputPanel.add(isbnLabel);
		addBookInputPanel.add(isbnField);
		addBookInputPanel.add(titleLabel);
		addBookInputPanel.add(titleField);
		addBookInputPanel.add(mainAuthorLabel);
		addBookInputPanel.add(mainAuthorField);
		addBookInputPanel.add(publisherLabel);
		addBookInputPanel.add(publisherField);
		addBookInputPanel.add(yearLabel);
		addBookInputPanel.add(yearField);
		addBookInputPanel.add(otherAuthorsLabel);
		addBookInputPanel.add(otherAuthorsField);
		addBookInputPanel.add(subjectsLabel);
		addBookInputPanel.add(subjectsField);
		addBookInputPanel.add(new JLabel(""));	// Creates the empty space on lower left corner
		addBookInputPanel.add(addBookButton);

		addBookLabel.setAlignmentX(0.5f);		// Centre label

		addBookDialog.setLayout(new BoxLayout(addBookDialog.getContentPane(), BoxLayout.Y_AXIS));
		addBookDialog.add(addBookLabel);
		addBookDialog.add(addBookInputPanel);

		addBookDialog.setModalityType(ModalityType.APPLICATION_MODAL);		// Disables input in MainVew
		addBookDialog.setTitle("Add Books");
		addBookDialog.setLocationRelativeTo(null);
		addBookDialog.pack();
		addBookDialog.setVisible(true);
	}

	private void addBook(String callNumber, String isbn, String title, String mainAuthor, 
						 String publisher, String year, String otherAuthors, String subjects) {

		AddBook addBookTransaction = new AddBook(connection);
		List<String> parameters = new ArrayList<String>();

		parameters.add(callNumber);
		parameters.add(isbn);
		parameters.add(title);
		parameters.add(mainAuthor);
		parameters.add(publisher);
		parameters.add(year);
		parameters.add(otherAuthors);
		parameters.add(subjects);

		addBookTransaction.execute(parameters);
	}

	private void viewCheckOutBooks(String subject) {

		CheckOutReport checkOutReportTransaction = new CheckOutReport(connection);
		List<String> parameters = new ArrayList<String>();
		
		parameters.add(subject);
		
		ResultSet resultSet = checkOutReportTransaction.execute(parameters);
		JDialog resultSetDialog = new CheckOutReportDialog("Checked Out Books Report", resultSet);
		resultSetDialog.setVisible(true);
		
	}

	private void viewPopularItems(String number, String year) {
		
		PopularItemsList popularItemsListTransaction = new PopularItemsList(connection);
		List<String> parameters = new ArrayList<String>();
		
		parameters.add(number);
		parameters.add(year);
		
		ResultSet resultSet = popularItemsListTransaction.execute(parameters);
		JDialog resultSetDialog = new ResultSetDialog("Top Books", resultSet);
		resultSetDialog.setVisible(true);
	}

}
