package org.webtrader.schedule.orderpositionactivation.account;

import org.webtrader.schedule.orderpositionactivation.log.Log;
import org.webtrader.schedule.orderpositionactivation.databases.DBConnection;
import org.webtrader.schedule.orderpositionactivation.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountFetcher
{
    public Account getAccountById(int accountId)
    {
        try {
            return this.fetchFromDB( accountId );
        } catch ( Exception e ) {
            Log.write("Get account by ID. Fetch from DB error: " + e.getMessage());
            return null;
        }
    }

    private Account fetchFromDB( int accountId ) throws Exception
    {
        PreparedStatement stmt = getPreparedStatement();
        stmt.setInt(1, accountId);

        ResultSet rs = stmt.executeQuery();

        if ( rs.next() ) {
            return new Account(rs);
        } else {
            return null;
        }
    }

    private static PreparedStatement getPreparedStatement() throws Exception
    {
        String sql = """
                SELECT
                    id,
                    user_id,
                    currency_id,
                    balance,
                    credit,
                    leverage,
                    type,
                    equity,
                    margin,
                    free_margin,
                    margin_indicator,
                    margin_level,
                    profit_loss,
                    profit_loss_opened_orders,
                    status,
                    login,
                    uuid,
                    last_login_time,
                    created_at,
                    updated_at,
                    deleted_at
                FROM webtrader.accounts
                WHERE id = ?;
            """;
        Connection conn = DBConnection.getConnection();

        return conn.prepareStatement(sql);
    }
}
