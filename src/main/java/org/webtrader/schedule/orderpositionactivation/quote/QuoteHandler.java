package org.webtrader.schedule.orderpositionactivation.quote;

import org.webtrader.schedule.orderpositionactivation.model.Quote;

public interface QuoteHandler
{
    void handle( Quote quote );
}
