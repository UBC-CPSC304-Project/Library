package ca.cs304.client;

import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class CheckOutReportDialog extends JDialog {

	private JScrollPane scrollPane;
	private JPanel dialogPanel;
	private JPanel buttonPanel;
	private ResultSet resultSet;
	private JTable resultSetTable;
	private JButton closeButton;
	private JButton sendEmailButton;
	private DefaultTableModel dataModel;
	private ListSelectionModel selectionModel;
	
	private Date today;

	public CheckOutReportDialog(String name, ResultSet resultSet) {
		super();
		this.resultSet = resultSet;

		makeButtons();
		makeTable();

		dialogPanel = new JPanel();
		dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
		dialogPanel.add(scrollPane);
		dialogPanel.add(buttonPanel);

		add(dialogPanel);
		setTitle(name);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setLocationRelativeTo(null);
		pack();
		
		// Get Today's Date
		GregorianCalendar calendar = new GregorianCalendar();
		today = calendar.getTime();
	}
	


	private void makeButtons() {
		
		closeButton = new JButton("Close");
		sendEmailButton = new JButton("Send Email");

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
		
		sendEmailButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Emails sent to selected borrowers.");

			}
		});
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2, 5, 5));
		buttonPanel.add(sendEmailButton);
		buttonPanel.add(closeButton);
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

			String[] columnHeaders = new String[columnCount + 1];

			// create table headers
			for (int i = 0; i < columnCount; i++) {
				columnHeaders[i] = rsmd.getColumnName(i + 1);
			}
			
			// create extra overdue column
			columnHeaders[columnCount] = "OVERDUE";
			
			dataModel.setColumnIdentifiers(columnHeaders);

			// populate row data
			while (resultSet.next()) {
				
				String inDate = resultSet.getString("inDate");
				String[] row = new String[columnCount + 1];
				
				for (int i = 0; i < columnCount; i++) {
					row[i] = resultSet.getString(i + 1);
				}
				
				if (isOverdue(inDate)) {
					row[columnCount] = "YES";
				}
				else {
					row[columnCount] = "NO";
				}
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



	private boolean isOverdue(String inDateString) {
		
		boolean isOverdue = false;
		
		try {
			// today's date
			GregorianCalendar calendar = new GregorianCalendar();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date today = calendar.getTime();
			Date inDate = dateFormat.parse(inDateString);
			
			isOverdue = inDate.before(today);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return isOverdue;
	}
}
