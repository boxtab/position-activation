package org.webtrader.schedule.orderpositionactivation.repositories;

import org.webtrader.schedule.orderpositionactivation.model.Account;
import org.webtrader.schedule.orderpositionactivation.databases.DBConnection;
import org.webtrader.schedule.orderpositionactivation.log.Log;
import org.webtrader.schedule.orderpositionactivation.model.Order;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class AccountFetcherRepository
{
    public List<Account> fetchAccountsByOrders( List<Order> orders )
    {
        Set<Long> accountIds = extractAccountIds( orders );
        if ( accountIds.isEmpty() ) return Collections.emptyList();

        try
        {
            return this.fetchFromDB( accountIds );
        }
        catch ( Exception e )
        {
            Log.write( "AccountFetcherRepository.fetchFromDB error: " + e.getMessage() );
            return Collections.emptyList();
        }
    }

    private Set<Long> extractAccountIds( List<Order> orders )
    {
        return orders.stream()
                .map(Order::getAccountId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private List<Account> fetchFromDB( Set<Long> accountIds ) throws Exception
    {
        PreparedStatement stmt = getPreparedStatement( accountIds );

        int index = 1;
        for (Long id : accountIds) {
            stmt.setLong(index++, id);
        }

        List<Account> result = new ArrayList<>();
        try ( ResultSet rs = stmt.executeQuery() )
        {
            while ( rs.next() )
            {
                result.add( new Account(rs) );
            }
        }

        return result;
    }

    private PreparedStatement getPreparedStatement( Set<Long> accountIds ) throws Exception
    {
        String placeholders = accountIds.stream()
                .map(id -> "?")
                .collect(Collectors.joining(", "));

        String sql = "SELECT * FROM accounts WHERE id IN (" + placeholders + ")";
        Connection conn = DBConnection.getConnection();
        return conn.prepareStatement(sql);
    }
}
