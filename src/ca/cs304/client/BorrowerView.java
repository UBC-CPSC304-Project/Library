package ca.cs304.client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Renders the Borrower's interface
 * May change this to inherit JPanel instead
 * (so the menu could swap its panel to this)
 */
public class BorrowerView extends JFrame {
	
	Connection connection;
	
	public BorrowerView(Connection connection) {
		
		this.connection = connection;
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);

		panel.setLayout(new GridLayout(2,2));	// Sets the layout of window to a 2 x 2 layout
		
		setTitle("Borrower View");
		setSize(300, 200);
		setLocationRelativeTo(null); 	// Centre frame on the screen
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Window closes when the close button is pressed
		
		addButtons(panel);
	}

	private void addButtons(JPanel panel) {
		
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
		
		panel.add(searchBookButton);
		panel.add(checkAccountButton);
		panel.add(placeHoldButton);
		panel.add(payFineButton);
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
	
}
