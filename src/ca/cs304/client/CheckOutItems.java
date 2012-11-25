package ca.cs304.client;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

public class CheckOutItems extends Transaction{
	

    public CheckOutItems(Connection connection) {
        super(connection);
    }


    @Override
    public ResultSet execute(List<String> parameters) {
        //get today's date
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String sDate = dateFormat.format(calendar.getTime());
                
                int bid = Integer.parseInt(parameters.get(0));
                int callNo = Integer.parseInt(parameters.get(1));
                String copyNo = parameters.get(2);
                ResultSet rs = null;
                PreparedStatement ps = null;
                String dueDate = null;
                String borid = null;
               
                
                
                try {
                	
                    
                    //check for fines
                    ps = connection.prepareStatement("SELECT amount FROM Fine F, Borrowing B "
                            + "WHERE F.paidDate IS NULL AND B.borid=F.borid AND B.bid=?");
                    
                    ps.setInt(1, bid);
                    rs = ps.executeQuery();

	/**
	 * (non-Javadoc)
	 * @param String bid, String callNumber
	 */

                    if (!rs.next() || 0 >= rs.getInt("in"))
                        try {
                            throw new Exception("Unknown call number");
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    
                    // Create borrowing record
                    ps = connection.prepareStatement("INSERT into Borrowing(bid, callNumber, copyNo, outDate, inDate) "
                    + "values (?,?,?,?,?,?)");
                    
                    
                    ps.setInt(1, bid);
                    ps.setInt(2, callNo);
                    ps.setString(3, copyNo);
                    ps.setString(4, sDate);
                    ps.setString(5, dueDate);
                    
                    // TODO generate due date
                	// dueDate = checkoutDate + (num of weeks allowed * length of week)
                    // duedate = sDate + (bookTimeLimit * 7);
                    
                    //update book copy to "out"
                    ps = connection.prepareStatement("UPDATE BookCopy "
                    								  + "SET status = 'out' WHERE callNumber=? AND copyNo=?");
                    
                    ps.setInt(1, callNo);
                    ps.setString(2, copyNo);
                    ps.executeUpdate();

                    connection.commit();
                    ps.close();
                }
                catch (SQLException ex)
                {
                    System.out.println("Message: " + ex.getMessage());
                    
                    try 
                    {
                    connection.rollback();    
                    }
                    catch (SQLException ex2)
                    {
                    System.out.println("Message: " + ex2.getMessage());
                    System.exit(-1);
                    }
                }
            return rs;
    }
}

