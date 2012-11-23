package ca.cs304.client;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

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
		setLayout(new GridLayout(2, 2));	// 2 x 2 layout with 5px of padding vertically + horizontally
		addButtons();
	}

	private void addButtons() {
		
		JButton addBorrowerButton = new JButton("Add Borrower");
		addBorrowerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				addBorrower();
			}
		});
		
		JButton checkOutItemsButton = new JButton("Check Out Items");
		checkOutItemsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				checkOutItems();
			}
		});
		
		JButton returnButton = new JButton("Process a Return");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				processReturn();
			}
		});
		
		JButton overDueButton = new JButton("Check Overdue Items");
		overDueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				overDue();
			}
		});
		
		add(addBorrowerButton);
		add(checkOutItemsButton);
		add(returnButton);
		add(overDueButton);
	}

	private void showAddBorrowers() {
		
	}
	private void showCheckOutItemsDialog() {
		
	}
	
	private void showProcessReturnsDialog() {
		
	}
	
	private void showCheckOverdueDialog() {
		
	}
	
	private void addBorrower() {
		System.out.println("Add Borrower Pressed");	//TODO
	}
	
	private void checkOutItems() {
		System.out.println("Check Account Pressed");	//TODO
	}
	
	private void processReturn() {
		System.out.println("Process Return Pressed");	//TODO
	}
	
	private void overDue() {
		System.out.println("Overdue Pressed");	//TODO	
	}
}

