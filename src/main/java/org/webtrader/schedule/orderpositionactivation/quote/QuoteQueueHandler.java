package org.webtrader.schedule.orderpositionactivation.quote;

import org.webtrader.schedule.orderpositionactivation.model.Quote;
import org.webtrader.schedule.orderpositionactivation.utils.SafeQuoteQueue;

public class QuoteQueueHandler implements QuoteHandler
{
    private final SafeQuoteQueue safeQueue;

    public QuoteQueueHandler( SafeQuoteQueue safeQueue )
    {
        this.safeQueue = safeQueue;
    }

    @Override
    public void handle( Quote quote )
    {
        safeQueue.offer( quote );
    }
}
