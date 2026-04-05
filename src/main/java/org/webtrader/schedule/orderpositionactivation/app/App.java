package org.webtrader.schedule.orderpositionactivation.app;

import org.webtrader.schedule.orderpositionactivation.quote.QuoteClient;
import org.webtrader.schedule.orderpositionactivation.quote.QuoteHandler;
import org.webtrader.schedule.orderpositionactivation.quote.QuoteProcessor;
import org.webtrader.schedule.orderpositionactivation.quote.QuoteQueueHandler;
import org.webtrader.schedule.orderpositionactivation.services.OrderService;
import org.webtrader.schedule.orderpositionactivation.utils.SafeQuoteQueue;

public class App
{
    private static final int MAX_QUEUE_SIZE = 1_000;

    private final SafeQuoteQueue safeQueue;
    private final OrderService orderService;

    public App() throws Exception
    {
        this.safeQueue = new SafeQuoteQueue( MAX_QUEUE_SIZE );
        this.orderService = new OrderService();
    }

    public void start() throws Exception
    {
        startQuoteClient( safeQueue );
        startQuoteProcessor( safeQueue, orderService );
    }

    private static void startQuoteClient( SafeQuoteQueue safeQueue )
    {
        QuoteHandler handler = new QuoteQueueHandler( safeQueue );
        QuoteClient client = new QuoteClient( handler );
        client.connectWithRetry();
    }

    private static void startQuoteProcessor( SafeQuoteQueue safeQueue, OrderService orderService )
    {
        QuoteProcessor processor = new QuoteProcessor( safeQueue.getQueue(), orderService );
        Thread processorThread = new Thread( processor, "QuoteProcessorThread" );
        processorThread.start();
    }
}
