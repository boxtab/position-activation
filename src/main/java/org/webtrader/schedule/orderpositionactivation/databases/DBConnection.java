package org.webtrader.schedule.orderpositionactivation.databases;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.webtrader.schedule.orderpositionactivation.config.Config;
import org.webtrader.schedule.orderpositionactivation.log.Log;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DBConnection
{
    private static final HikariDataSource dataSource;

    static
    {
        try {
            Config config = getConfig();

            HikariConfig hikariConfig = new HikariConfig();

            hikariConfig.setJdbcUrl( createJdbcUrl( config ) );

            hikariConfig.setUsername( config.getProperty( "DATABASE_USERNAME" ) );
            hikariConfig.setPassword( config.getProperty( "DATABASE_PASSWORD" ) );

            hikariConfig.addDataSourceProperty( "cachePrepStmts", "true" );
            hikariConfig.addDataSourceProperty( "prepStmtCacheSize", "250" );
            hikariConfig.addDataSourceProperty( "prepStmtCacheSqlLimit", "2048" );

            dataSource = new HikariDataSource( hikariConfig );
            Log.write( "HikariCP connection pool initialized" );

        }
        catch ( Exception e )
        {
            Log.write( "Error initializing HikariCP: " + e.getMessage() );
            throw new RuntimeException( e );
        }
    }

    public static Connection getConnection() throws SQLException
    {
//        Log.write("DBConnection.getConnection() called");

        return dataSource.getConnection(); // Hikari сам управляет соединениями
    }

    private static String createJdbcUrl( Config config )
    {
        return "jdbc:mariadb://" +
                config.getProperty( "DATABASE_HOST" ) + ":" +
                config.getProperty( "DATABASE_PORT" ) + "/" +
                config.getProperty( "DATABASE_NAME" );
    }

    private static Config getConfig() throws Exception
    {
        try
        {
            return Config.getConfig();
        }
        catch ( Exception e )
        {
            throw new Exception( "Unable to get Config: ", e );
        }
    }
}
