package org.webtrader.schedule.orderpositionactivation;

import org.webtrader.schedule.orderpositionactivation.app.App;
import org.webtrader.schedule.orderpositionactivation.log.Log;
import org.webtrader.schedule.orderpositionactivation.utils.ExponentialBackoff;

public class Main
{
    private static final ExponentialBackoff backoff = new ExponentialBackoff();

    public static void main( String[] args )
    {
        while ( true )
        {
            try
            {
                App app = new App();
                app.start();
                break;
            }
            catch ( Exception e )
            {
                Log.write( "Startup failure: " + e.getMessage() );
                backoff.increaseAttempt();
                backoff.sleep();
            }
        }
    }
}
