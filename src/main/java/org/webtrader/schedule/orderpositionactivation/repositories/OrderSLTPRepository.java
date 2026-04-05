package org.webtrader.schedule.orderpositionactivation.repositories;

import org.webtrader.schedule.orderpositionactivation.log.Log;
import org.webtrader.schedule.orderpositionactivation.databases.DBConnection;
import org.webtrader.schedule.orderpositionactivation.model.Order;
import org.webtrader.schedule.orderpositionactivation.order.constants.OrderStates;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderSLTPRepository
{
    private static final String FETCH_SQL = """
            SELECT
                o.*,
                q.bid_price,
                q.ask_price,
                a.uuid as channel_name
            FROM webtrader.orders AS o
            INNER JOIN webtrader.quote AS q ON q.id = o.quote_id
            INNER JOIN webtrader.accounts AS a ON a.id = o.account_id
            WHERE q.symbol = ?
              AND o.state = ?
              AND (
                    -- OrderKeySLTP.PRICE = 3
                    (
                        o.stop_loss_key = 3
                        AND
                        (
                            (o.type = 0 AND q.bid_price <= o.stop_loss_value)
                            OR
                            (o.type = 1 AND q.ask_price >= o.stop_loss_value)
                        )
                    )
                    OR
                    -- OrderKeySLTP.PROFIT_LOSS = 2
                    (
                        o.stop_loss_key = 2
                        AND
                        (
                            (
                                o.type = 0 AND
                                                        (
                                                            (q.bid_price * o.open_rate -
                                                            o.open_price * o.open_rate) *
                                                            o.lots * o.units - o.commission
                                                        ) <= o.stop_loss_value
                            )
                            OR
                            (
                                o.type = 1 AND
                                                        (
                                                            (o.open_price * o.open_rate -
                                                            q.ask_price * o.open_rate) *
                                                            o.lots * o.units - o.commission
                                                        ) >= o.stop_loss_value
                            )
                        )
                    )
                    OR
                    -- OrderKeySLTP.PRICE = 3
                    (
                        o.take_profit_key = 3
                        AND
                        (
                            (o.type = 0 AND q.bid_price >= o.take_profit_value)
                            OR
                            (o.type = 1 AND q.ask_price <= o.take_profit_value)
                        )
                    )
                    OR
                    -- OrderKeySLTP.PROFIT_LOSS = 2
                    (
                        o.take_profit_key = 2
                        AND
                        (
                            (
                                o.type = 0 AND
                                                        (
                                                            (q.bid_price * o.open_rate -
                                                            o.open_price * o.open_rate) *
                                                            o.lots * o.units - o.commission
                                                        ) >= o.take_profit_value
                            )
                            OR
                            (
                                o.type = 1 AND
                                                        (
                                                            (o.open_price * o.open_rate -
                                                            q.ask_price * o.open_rate) *
                                                            o.lots * o.units - o.commission
                                                        ) <= o.take_profit_value
                            )
                        )
                    )
              )
              AND o.deleted_at is null
              AND a.deleted_at is null
            ;
    """;

    public List<Order> findOrdersWithSLTP( String symbol )
    {
        try
        {
            return this.loadOrdersWithSLTP( symbol );
        }
        catch ( Exception e )
        {
            Log.write( "Failed to extract orders for symbol " + symbol + " with SL/TP: " + e.getMessage() );
            return new ArrayList<>();
        }
    }

    private List<Order> loadOrdersWithSLTP( String symbol ) throws Exception
    {
        List<Order> orders = new ArrayList<>();

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement( FETCH_SQL )
        ) {
            stmt.setString( 1, symbol );
            stmt.setInt( 2, OrderStates.POSITION );

            try ( ResultSet rs = stmt.executeQuery() )
            {
                while ( rs.next() )
                {
                    orders.add( new Order( rs ) );
                }
            }
        }

        return orders;
    }
}
