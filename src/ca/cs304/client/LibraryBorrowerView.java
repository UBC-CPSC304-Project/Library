package ca.cs304.client;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

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
 * May change this to inherit JPanel instead
 * (so the menu could swap its panel to this)
 */
public class LibraryBorrowerView extends JPanel {
	
	Connection connection;
	
	public LibraryBorrowerView(Connection connection) {
		
		this.connection = connection;
		setLayout(new GridLayout(2, 2));	// 2 x 2 layout with 5px of padding vertically + horizontally
		addButtons();
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
				checkAccount();
			}
		});
		
		JButton placeHoldButton = new JButton("Place Hold Request");
		placeHoldButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				placeHoldRequest();
			}
		});
		
		JButton payFineButton = new JButton("Pay Fines");
		payFineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				payFine();
			}
		});
		
		add(searchBookButton);
		add(checkAccountButton);
		add(placeHoldButton);
		add(payFineButton);
	}
	
	private void showSearchDialog() {
		
		final String[] searchTypes = {"Title", "Author", "Subject"};
		final JDialog searchDialog = new JDialog();
		final JPanel searchInputPanel = new JPanel();
		final JLabel searchLabel = new JLabel("Search by Title, Author, or Subject");
		final JComboBox searchTypeBox = new JComboBox(searchTypes);
		final JTextField searchField = new JTextField(10);
		final JButton searchButton = new JButton("Search Book");
		
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				search((String)searchTypeBox.getSelectedItem(), searchField.getText());
			}
		});

		searchLabel.setAlignmentX(0.5f);		// Centre label
		
		searchTypeBox.setSelectedIndex(0);
		searchTypeBox.setPreferredSize(new Dimension(120, 22));
		searchTypeBox.setMaximumSize(new Dimension(120, 22));
		
		searchInputPanel.setLayout(new BoxLayout(searchInputPanel, BoxLayout.X_AXIS));
		searchInputPanel.add(searchTypeBox);
		searchInputPanel.add(searchField);
		searchInputPanel.add(searchButton);
		
		searchDialog.setLayout(new BoxLayout(searchDialog.getContentPane(), BoxLayout.Y_AXIS));
		searchDialog.add(searchLabel);
		searchDialog.add(searchInputPanel);
		
		searchDialog.setModalityType(ModalityType.APPLICATION_MODAL);
		searchDialog.setTitle("Search Books");
        //searchDialog.setDefaultCloseOperation(EXIT_ON_CLOSE);
		searchDialog.setLocationRelativeTo(null);
		searchDialog.pack();
		searchDialog.setVisible(true);

	}
	
	private void showCheckAccountDialog() {
		
	}
	
	private void showPlaceHoldRequestDialog() {
		
	}
	
	private void showPayFineDialog() {
		
	}
	
	private void search(String type, String keyword) {
		System.out.println("Search Book Pressed");	//TODO
	}
	
	private void checkAccount() {
		System.out.println("Check Account Pressed");	//TODO
	}
	
	private void placeHoldRequest() {
		System.out.println("Place Hold Request Pressed");	//TODO
	}
	
	private void payFine() {
		System.out.println("Pay Fine Pressed");	//TODO	
	}
}
