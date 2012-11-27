package ca.cs304.client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class PlaceHoldRequest extends Transaction {

	String callNumber = new String();	
	
	public PlaceHoldRequest(Connection connection) {
		super(connection);
	}

/*
 * @params (String callNumber, String bid)
 * 
 */
	@Override
	public ResultSet execute(List<String> parameters) {
		//get today's date

		HoldRequest hr = new HoldRequest(connection);
			hr.insert(parameters);

		return null;
	}
	
	@Override
	public void closeStatement() {
		//Do nothing, since no statement is opened in transaction
	}
}

