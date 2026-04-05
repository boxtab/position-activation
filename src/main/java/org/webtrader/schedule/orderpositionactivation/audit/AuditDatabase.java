package org.webtrader.schedule.orderpositionactivation.audit;

import org.webtrader.schedule.orderpositionactivation.json.util.AccountJsonUtil;
import org.webtrader.schedule.orderpositionactivation.json.util.OrderJsonUtil;
import org.webtrader.schedule.orderpositionactivation.log.Log;
import org.webtrader.schedule.orderpositionactivation.orderAccount.OrderAccountEntry;
import org.webtrader.schedule.orderpositionactivation.databases.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuditDatabase implements AuditLogger
{
    private static final String ORDER_RECORDS_SQL =
            """
            INSERT INTO order_records(
                order_id,
                date_time_created,
                action,
                who,
                who_identify,
                class_function,
                file_path,
                `order`,
                account,
                created_at,
                updated_at
            )
            VALUES (?, NOW(), ?, ?, ?, ?, ?, ?, ?, NOW(), NOW());
            """;

    private final String pathToStorage;

    public AuditDatabase( String pathToStorage )
    {
        this.pathToStorage = pathToStorage;
    }

    @Override
    public void saveAuditRecord( OrderAccountEntry entry )
    {
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement( ORDER_RECORDS_SQL )
        ) {
            stmt.setLong( 1, entry.order().getId() );
            stmt.setString(2, AuditConstants.ACTION_CLOSE );
            stmt.setString(3, AuditConstants.SCHEDULER_NAME );
            stmt.setString(4, AuditConstants.WHO_IDENTIFY );
            stmt.setString(5, AuditConstants.CLASS_FUNCTION );
            stmt.setString(6, buildFilePath( entry ) );
            stmt.setString(7, OrderJsonUtil.toPrettyJson( entry.order() ) );
            stmt.setString(8, AccountJsonUtil.toPrettyJson( entry.account() ) );

            stmt.executeUpdate();
        } catch ( SQLException e ) {
            Log.write( "AuditDatabase: SQL error for order ID " + entry.order().getId() + ": " + e.getMessage() );
        } catch ( Exception e ) {
            Log.write( "AuditDatabase: Unexpected error for order ID " + entry.order().getId() + ": " + e.getMessage() );
        }
    }

    private String buildFilePath( OrderAccountEntry entry )
    {
        return AuditPathUtil.buildFilePath( pathToStorage, entry );
    }
}
