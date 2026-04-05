package org.webtrader.schedule.orderpositionactivation.utils;

public class ExponentialBackoff
{
    private static final int BASE_DELAY = 5_000;

    private static final int MAX_DELAY = 240_000;

    private int attempt = 0;

    private int getNextDelay()
    {
        return Math.min( BASE_DELAY * (int)Math.pow( 2, attempt ), MAX_DELAY );
    }

    public void increaseAttempt()
    {
        attempt++;
    }

    @SuppressWarnings("unused")
    public void reset()
    {
        attempt = 0;
    }

    public void sleep()
    {
        try
        {
            Thread.sleep( getNextDelay() );
        }
        catch ( InterruptedException ignored ) {}
    }
}
