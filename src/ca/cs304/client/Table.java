package ca.cs304.client;

import java.sql.Connection;
import java.util.Collection;
import java.util.List;

public abstract class Table {
	
	protected Connection connection;
	
	public Table(Connection connection) {
		this.connection = connection;
	}

	public abstract void delete(List<String> parameters);
	
	public abstract void insert(List<String> parameters);
	
	public abstract void display();
}
