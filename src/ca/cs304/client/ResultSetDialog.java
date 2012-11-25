package ca.cs304.client;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class ResultSetDialog extends JDialog {

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
		dialogPanel.add(resultSetTable);
		dialogPanel.add(closeButton);
		
		add(dialogPanel);
		setTitle(name);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setLocationRelativeTo(null);
		pack();
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
		resultSetTable = new JTable();
		dataModel = new DefaultTableModel();
		resultSetTable.setModel(dataModel);

		try {
			// create table headers
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int columnCount = resultSetMetaData.getColumnCount();
			String[] columnNames = new String[columnCount];
			for (int i = 0; i < columnCount; i++) {
				columnNames[i] = resultSetMetaData.getColumnName(i + 1);
			}
			dataModel.setColumnIdentifiers(columnNames);

			// populate row data
			while (resultSet.next()) {
				String[] rowData = new String[columnCount];
				for (int i = 0; i < columnCount; i++) {
					rowData[i] = resultSet.getString(i + 1);
				}
				dataModel.addRow(rowData);
			}
		}

		catch (SQLException ex) {
			System.out.println(ex);
		}
	}

}