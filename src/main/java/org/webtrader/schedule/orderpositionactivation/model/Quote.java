package org.webtrader.schedule.orderpositionactivation.model;

@SuppressWarnings("ClassCanBeRecord")
public class Quote
{
    private final String symbol;
    private final double bid;
    private final double ask;

    public Quote( String symbol, double bid, double ask )
    {
        this.symbol = symbol;
        this.bid = bid;
        this.ask = ask;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public double getBid()
    {
        return bid;
    }

    public double getAsk()
    {
        return ask;
    }

    @Override
    public String toString()
    {
        String timestamp = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return String.format(
                "{ \"symbol\":\"%s\", \"bid\":%.4f, \"ask\":%.4f, \"timestamp\":\"%s\" }",
                symbol, bid, ask, timestamp
        );
    }
}
