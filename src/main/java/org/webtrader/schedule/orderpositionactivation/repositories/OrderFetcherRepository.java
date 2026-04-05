package org.webtrader.schedule.orderpositionactivation.repositories;

import org.webtrader.schedule.orderpositionactivation.databases.DBConnection;
import org.webtrader.schedule.orderpositionactivation.log.Log;
import org.webtrader.schedule.orderpositionactivation.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OrderFetcherRepository
{
    public List<Order> findByIds( List<Long> orderIds )
    {
        if ( orderIds.isEmpty() ) return List.of();

        try
        {
            PreparedStatement stmt = prepareStatement( orderIds );
            return mapOrdersFromStatement( stmt );
        }
        catch ( Exception e )
        {
            Log.write( "OrderFetcherRepository.fetchFromDB error: " + e.getMessage() );
            return List.of();
        }
    }

    private PreparedStatement prepareStatement( List<Long> orderIds ) throws SQLException
    {
        String placeholders = buildSqlPlaceholders( orderIds.size() );
        String sql = "SELECT * FROM orders WHERE id IN (" + placeholders + ")";
        PreparedStatement stmt = DBConnection.getConnection().prepareStatement( sql );

        bindOrderIds( stmt, orderIds );

        return stmt;
    }

    private void bindOrderIds( PreparedStatement stmt, List<Long> orderIds ) throws SQLException
    {
        int parameterIndex = 1;
        for ( Long orderId : orderIds )
        {
            stmt.setLong( parameterIndex++, orderId );
        }
    }

    private String buildSqlPlaceholders( int count )
    {
        return IntStream.range(0, count)
                .mapToObj(i -> "?")
                .collect(Collectors.joining(", "));
    }

    private List<Order> mapOrdersFromStatement( PreparedStatement stmt ) throws SQLException
    {
        try ( ResultSet rs = stmt.executeQuery() )
        {
            List<Order> result = new ArrayList<>();

            while ( rs.next() )
            {
                result.add( new Order( rs ) );
            }

            return result;
        }
    }
}
