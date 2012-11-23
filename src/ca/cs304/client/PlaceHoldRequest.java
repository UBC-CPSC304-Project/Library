package ca.cs304.client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class PlaceHoldRequest extends Transaction {

	Boolean bookReturned = false;
	
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

		//update the hold request table
		try {
			ps = connection.prepareStatement("INSERT INTO holdrequest VALUES ((hidseq.NEXTVAL),?,?,?)");

			hr.insert(parameters);

			connection.commit();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}

