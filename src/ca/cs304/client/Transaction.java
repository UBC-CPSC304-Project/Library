package ca.cs304.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public abstract class Transaction {
	
	Connection connection;
	PreparedStatement ps;
	ResultSet rs;

	public Transaction(Connection connection) {
		this.connection = connection;
	}
	
/**
 * Executes the transaction, if parameters are not correctly
 * specified, transaction will return null
 * @param parameters Parameters to be inputed, 
 * @return ResultSet the results retrieved from the transaction
 * REMEMBER TO CALL close() WHEN DONE WITH THE RESULTSET!
 */
	public abstract ResultSet execute(List<String> parameters);
	
	public void closeStatement(){
		try {
			ps.close();
		} catch (SQLException ex) {
		    System.out.println("Message: " + ex.getMessage());
		}
	}
}
