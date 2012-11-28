package ca.cs304.client;

import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BoxLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class SearchResultDialog extends JDialog {

	private JScrollPane scrollPane;
	private JPanel dialogPanel;
	private ResultSet resultSet;
	private JTable resultSetTable;
	private JButton closeButton;
	private DefaultTableModel dataModel;
	private ListSelectionModel selectionModel;
	
	private Connection connection;
	
	public SearchResultDialog(String name, ResultSet resultSet, Connection connection) {
		super();
		this.resultSet = resultSet;
		this.connection = connection;

		makeButtons();
		makeTable();

		dialogPanel = new JPanel();
		dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
		dialogPanel.add(scrollPane);
		dialogPanel.add(closeButton);

		add(dialogPanel);
		setTitle(name);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setLocationRelativeTo(null);
		pack();
	}
	


	private void makeButtons() {
		
		closeButton = new JButton("Close");

		closeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		closeButton.setAlignmentX(0.5f);
	}

	private void makeTable() {

		dataModel = new DefaultTableModel();
		resultSetTable = new JTable();
		resultSetTable.setModel(dataModel);
		resultSetTable.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(resultSetTable);
		
		try {
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int columnCount = rsmd.getColumnCount();

			String[] columnHeaders = new String[columnCount + 2];

			// create table headers
			for (int i = 0; i < columnCount; i++) {
				columnHeaders[i] = rsmd.getColumnName(i + 1);
			}
			
			// create extra in/out copies columns
			columnHeaders[columnCount] = "COPIES_IN";
			columnHeaders[columnCount + 1] = "COPIES_OUT";
			
			dataModel.setColumnIdentifiers(columnHeaders);

			// populate row data
			while (resultSet.next()) {
				
				String callNumber = resultSet.getString("callNumber");
				BookCopy bookCopyTable = new BookCopy(connection);
				String[] row = new String[columnCount + 2];
				
				for (int i = 0; i < columnCount; i++) {
					row[i] = resultSet.getString(i + 1);
				}
				
				Integer copiesIn = bookCopyTable.numOfCopiesInStatus(callNumber, "in");
				Integer copiesOut = bookCopyTable.numOfCopiesInStatus(callNumber, "out");
				
				row[columnCount] = copiesIn.toString();
				row[columnCount + 1] = copiesOut.toString();
	
				dataModel.addRow(row);
			}
		}
		catch (SQLException ex) {
			System.out.println(ex);
		}
		
		selectionModel = new DefaultListSelectionModel();
		resultSetTable.setSelectionModel(selectionModel);
		resultSetTable.setRowSelectionAllowed(true);
		resultSetTable.setSelectionMode(selectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

}
