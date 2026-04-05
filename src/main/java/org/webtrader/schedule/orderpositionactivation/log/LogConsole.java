package org.webtrader.schedule.orderpositionactivation.log;

import org.webtrader.schedule.orderpositionactivation.config.Config;

public class LogConsole
{
    private static final LogConsole INSTANCE = new LogConsole();

    private final String appMode;

    private LogConsole()
    {
        this.appMode = Config.getConfig().getProperty( "APP_MODE" );
    }

    public static void print( String message )
    {
        INSTANCE.printInternal( message );
    }

    private void printInternal( String message )
    {
        if ( "DEV".equalsIgnoreCase( appMode ) )
        {
            System.out.println( message );
        }
    }
}
