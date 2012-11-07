package ca.cs304.client;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;

public abstract class Table {
	
	protected Connection connection;
	
	public Table(Connection connection) {
		this.connection = connection;
	}

	public abstract void delete(ArrayList<String> parameters);
	
	public abstract void insert(ArrayList<String> parameters);
	
	public abstract void display();
	
}
