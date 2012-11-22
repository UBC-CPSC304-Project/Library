package ca.cs304.client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/*
 * Check his/her account.
 * The system will display the items the borrower has currently borrowed
 * and not yet returned, any outstanding fines and the hold requests that
 * have been placed by the borrower.
 * 
 * @param String bid
 * 
 */

public class CheckAccount extends Transaction{

    public CheckAccount(Connection connection) {
        super(connection);
    }

    @Override
    public ResultSet execute(List<String> parameters) {
        try {
            
            int bid = Integer.parseInt(parameters.get(0));
            
            ps = connection.prepareStatement("SELECT Bor.callNumber, F.amount, HoldRequest.callNumber" +
                                             "FROM Borrower B, Borrowing Bor, Fine F, HoldRequest H" +
                                             "WHERE (Bor.inDate IS NULL" +
                                                      "AND f.paidDate IS NULL" +
                                                     "AND Bor.callNumber=B.callNumber" +
                                                      "AND bid =?)");
                                        
            ps.setInt(1, bid);
            
            rs = ps.executeQuery();                
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
