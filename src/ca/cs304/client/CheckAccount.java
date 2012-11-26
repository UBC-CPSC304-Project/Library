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
 */

public class CheckAccount extends Transaction{

    public CheckAccount(Connection connection) {
        super(connection);
    }

    @Override
    public ResultSet execute(List<String> parameters) {
        try {
            
            //int bid = Integer.parseInt(parameters.get(0));
        	String bid = parameters.get(0);
            

//            ps = connection.prepareStatement("SELECT Bor.callNumber, F.amount, H.callNumber " +
//                                             "FROM Borrower B, Borrowing Bor, Fine F, HoldRequest H " +
//                                             "WHERE Bor.inDate IS NULL " +
//                                                     "AND F.paidDate IS NULL " +
//                                                     "AND Bor.bid=B.bid " +
//                                                     "AND H.bid=B.bid " +
//                                                     "AND F.borid=Bor.borid "+
//                                                     "AND B.bid =?");
            
            
            ps = connection.prepareStatement("SELECT B.callNumber " +
            		"FROM Borrowing B JOIN Borrower Bor ON B.bid = Bor.bid " +
            		"WHERE Bor.bid = ? ");
//            		"UNION SELECT to_char(F.amount) " +
//            		"FROM Fine F JOIN Borrowing B ON B.borid = F.borid JOIN Borrower Bor ON B.bid = Bor.bid " +
//            		"WHERE Bor.bid = ? " +
//            		"AND F.paidDate IS NULL " +
//            		"UNION SELECT H.callNumber " +
//            		"FROM HoldRequest H JOIN Borrower Bor ON Bor.bid = H.bid " +
//            		"WHERE Bor.bid = ? ");
                                        
            ps.setString(1, bid);
            //ps.setString(2, bid);
            //ps.setString(3, bid);
            
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
