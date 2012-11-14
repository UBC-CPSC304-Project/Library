package ca.cs304.client;

import java.sql.Connection;
import java.util.Collection;

public abstract class Transaction {
	
	Connection connection;

	public Transaction(Connection connection) {
		this.connection = connection;
	}
	
/**
 * Executes the transaction, if parameters are not correctly
 * specified, transaction will return null
 * @param parameters Parameters to be inputed, 
 * @return String[] the results retrieved from the transaction
 */
	public abstract Collection<String[]> execute(String[] parameters);
}
