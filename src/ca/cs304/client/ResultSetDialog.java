package ca.cs304.client;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class ResultSetDialog extends JDialog {

	private JScrollPane scrollPane;
	private JPanel dialogPanel;
	private ResultSet resultSet;
	private JTable resultSetTable;
	private JButton closeButton;
	private DefaultTableModel dataModel;

	public ResultSetDialog(String name, ResultSet resultSet) {
		super();
		this.resultSet = resultSet;

		makeCloseButton();
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
	
	public void highlightRows(String columnName, Callable<Boolean> condition) {
		
	}

	private void makeCloseButton() {
		closeButton = new JButton("Close");
		closeButton.setAlignmentX(0.5f);

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
	}

	private void makeTable() {

		dataModel = new DefaultTableModel();
		resultSetTable = new JTable();
		resultSetTable.setModel(dataModel);
		resultSetTable.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(resultSetTable);
		
		if (resultSet == null) {
			System.out.println("Result Set is NULL");
			return;
		}
		
		try {
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int columnCount = rsmd.getColumnCount();

			String[] columnHeaders = new String[columnCount];

			// create table headers
			for (int i = 0; i < columnCount; i++) {
				columnHeaders[i] = rsmd.getColumnName(i + 1);
			}
			dataModel.setColumnIdentifiers(columnHeaders);

			// populate row data
			while (resultSet.next()) {
				String[] row = new String[columnCount];
				for (int i = 0; i < columnCount; i++) {
					row[i] = resultSet.getString(i + 1);
				}
				dataModel.addRow(row);
			}
		}
		catch (SQLException ex) {
			System.out.println(ex);
		}


	}

}