package ca.cs304.client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Renders the Borrower's interface
 * May change this to inherit JPanel instead
 * (so the menu could swap its panel to this)
 */
public class BorrowerView extends JPanel {
	
	Connection connection;
	
	public BorrowerView() {
		
		setLayout(new GridLayout(2, 2, 5, 5));	// 2 x 2 layout with 5px of padding vertically + horizontally
		addButtons();
	}

	private void addButtons() {
		
		JButton searchBookButton = new JButton("Search Books");
		searchBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				search();
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
		
	}
	
	private void showCheckAccountDialog() {
		
	}
	
	private void showPlaceHoldRequestDialog() {
		
	}
	
	private void showPayFineDialog() {
		
	}
	
	private void search() {
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
	
	public static void main(String[] args) {
		
		JPanel borrowerView = new BorrowerView();
		JFrame frame = new JFrame();
		
		borrowerView.setOpaque(true);
		frame.setContentPane(borrowerView);
		
		frame.pack();
		frame.setVisible(true);
	}
}
