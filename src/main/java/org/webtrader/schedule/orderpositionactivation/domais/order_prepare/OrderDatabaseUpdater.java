package org.webtrader.schedule.orderpositionactivation.domais.order_prepare;

import org.webtrader.schedule.orderpositionactivation.log.Log;
import org.webtrader.schedule.orderpositionactivation.log.LogConsole;
import org.webtrader.schedule.orderpositionactivation.databases.DBConnection;
import org.webtrader.schedule.orderpositionactivation.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class OrderDatabaseUpdater
{
    private static final String UPDATE_SQL = """
            UPDATE webtrader.orders AS o
            INNER JOIN webtrader.accounts AS a ON a.id = o.account_id
            SET
                o.close_price = ?,
                o.profit_loss = ?,
                a.balance = a.balance + ?,
                o.state = ?,
                o.closing_date = ?,
                o.updated_at = ?
            WHERE o.id = ?;
        """;

    public void updateOrder( Order order )
    {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement( UPDATE_SQL ))
        {
            stmt.setBigDecimal( 1, order.getClosePrice() );
            stmt.setBigDecimal( 2, order.getProfitLoss() );
            stmt.setBigDecimal( 3, order.getProfitLoss() );
            stmt.setInt( 4, order.getState() );
            stmt.setTimestamp( 5, order.getClosingDate() );
            stmt.setTimestamp( 6, order.getUpdatedAt() );
            stmt.setLong( 7, order.getId() );

            stmt.executeUpdate();

            LogConsole.print( "OrderDatabaseUpdater: Order with ID " + order.getId() + " updated." );
        }
        catch ( Exception e )
        {
            Log.write( "OrderDatabaseUpdater: Failed to update order ID " + order.getId() +
                        " → " + e.getClass().getSimpleName() + ": " + e.getMessage()
            );
        }
    }
}
