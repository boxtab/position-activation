package org.webtrader.schedule.orderpositionactivation.domais.order_prepare;

import java.sql.Timestamp;

public class OrderCloseContext
{
    private final double bid;
    private final double ask;
    private final Timestamp timestamp;

    public OrderCloseContext( double bid, double ask )
    {
        this.bid = bid;
        this.ask = ask;
        this.timestamp = new Timestamp( System.currentTimeMillis() );
    }

    public double getBid()
    {
        return bid;
    }

    public double getAsk()
    {
        return ask;
    }

    public Timestamp getCurrentTimestamp()
    {
        return timestamp;
    }
}
