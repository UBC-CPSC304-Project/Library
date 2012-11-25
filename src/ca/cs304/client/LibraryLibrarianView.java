package ca.cs304.client;

import java.awt.Dialog.ModalityType;
import java.awt.GridLayout;
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

public class LibraryLibrarianView extends JPanel{

			
	AddBook newBook;
	CheckOutReport outReport;
	PopularItemsList popItems;
	Connection connect;
	
	public LibraryLibrarianView(Connection connection){
		this.connect = connection;
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
		
		JButton outReportButton = new JButton(" Generate Check Out Report");
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
		// TODO Auto-generated method stub
		final JDialog popItemsDialog = new JDialog();
		final JPanel itemsInputPanel = new JPanel();
		final JLabel rowNumLabel = new JLabel("Please enter number of rows:");
		final JLabel yearLabel = new JLabel("Please enter a year:");
		final JTextField rowNumField = new JTextField(10);
		final JTextField yearField = new JTextField(20);
		
		final JButton listButton = new JButton("Search");
		
		listButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				List<String> params = new ArrayList<String>();
				params.add(rowNumField.getText());
				params.add(yearField.getText());
				popItems.execute(params);
				popItemsDialog.dispose();
			}
		});
		
		itemsInputPanel.setLayout(new BoxLayout(itemsInputPanel, BoxLayout.X_AXIS));
		
		itemsInputPanel.setLayout(new GridLayout(5,5,5,5));
		itemsInputPanel.add(rowNumLabel);
		itemsInputPanel.add(rowNumField);
		itemsInputPanel.add(yearLabel);
		itemsInputPanel.add(yearField);
		
		popItemsDialog.setLayout(new BoxLayout(popItemsDialog.getContentPane(), BoxLayout.Y_AXIS));
		popItemsDialog.add(itemsInputPanel);
		
		popItemsDialog.setModalityType(ModalityType.APPLICATION_MODAL);
		popItemsDialog.setTitle("Popular Items Search");
		popItemsDialog.setLocationRelativeTo(null);
		popItemsDialog.pack();
		popItemsDialog.setVisible(true);	
	}

	protected void showOutReportDialog() {
		// TODO Auto-generated method stub
		
	}

	protected void showAddBookDialog() {
		// TODO Auto-generated method stub
		
		final JDialog addbookDialog = new JDialog();
		
	}
}