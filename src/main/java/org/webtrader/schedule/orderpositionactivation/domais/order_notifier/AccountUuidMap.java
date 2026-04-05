package org.webtrader.schedule.orderpositionactivation.domais.order_notifier;

import org.webtrader.schedule.orderpositionactivation.databases.DBConnection;
import org.webtrader.schedule.orderpositionactivation.log.Log;
import org.webtrader.schedule.orderpositionactivation.model.Order;
import org.webtrader.schedule.orderpositionactivation.orderAccount.OrderIdWithUuid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;

public class AccountUuidMap
{
    public List<OrderIdWithUuid> mapToOrderIdWithUuid( List<Order> orders )
    {
        Set<Long> accountIds        = extractAccountIds( orders );
        Map<Long, String> uuidMap   = loadAccountUuidMap( accountIds );

        return buildOrderIdWithUuidList( orders, uuidMap );
    }

    private Set<Long> extractAccountIds( List<Order> orders )
    {
        return orders.stream()
                .map(Order::getAccountId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private Map<Long, String> loadAccountUuidMap( Set<Long> accountIds )
    {
        if ( accountIds.isEmpty() ) return Collections.emptyMap();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = prepareStatement(conn, accountIds);
             ResultSet rs = stmt.executeQuery())
        {
            return mapResultSetToUuidMap(rs);
        }
        catch ( Exception e )
        {
            Log.write( "AccountUuidMap.loadAccountUuidMap error: " + e.getMessage() );
            return Collections.emptyMap();
        }
    }

    private PreparedStatement prepareStatement( Connection conn, Set<Long> accountIds ) throws Exception
    {
        String placeholders = generatePlaceholders( accountIds.size() );
        String sql = "SELECT id, uuid FROM webtrader.accounts WHERE id IN (" + placeholders + ")";
        PreparedStatement stmt = conn.prepareStatement(sql);

        int index = 1;
        for ( Long id : accountIds ) {
            stmt.setLong( index++, id );
        }

        return stmt;
    }

    private String generatePlaceholders( int count )
    {
        return String.join(", ", Collections.nCopies( count, "?") );
    }

    private Map<Long, String> mapResultSetToUuidMap( ResultSet rs ) throws Exception
    {
        Map<Long, String> map = new HashMap<>();

        while ( rs.next() )
        {
            map.put( rs.getLong("id"), rs.getString("uuid") );
        }
        return map;
    }

    private List<OrderIdWithUuid> buildOrderIdWithUuidList( List<Order> orders, Map<Long, String> uuidMap )
    {
        List<OrderIdWithUuid> result = new ArrayList<>();

        for ( Order order : orders )
        {
            Long accountId = order.getAccountId();
            if ( accountId != null && uuidMap.containsKey( accountId ) )
            {
                result.add( new OrderIdWithUuid( order.getId(), uuidMap.get( accountId ) ) );
            }
        }
        return result;
    }
}
