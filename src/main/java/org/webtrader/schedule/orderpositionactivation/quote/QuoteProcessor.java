package org.webtrader.schedule.orderpositionactivation.quote;

import org.webtrader.schedule.orderpositionactivation.model.Quote;
import org.webtrader.schedule.orderpositionactivation.log.Log;
import org.webtrader.schedule.orderpositionactivation.services.OrderService;

import java.util.concurrent.BlockingQueue;

public class QuoteProcessor implements Runnable
{
    private final BlockingQueue<Quote> queue;

    private final OrderService orderService;

    public QuoteProcessor(
            BlockingQueue<Quote> queue,
            OrderService orderService
    )
    {
        this.queue = queue;
        this.orderService = orderService;
    }

    @Override
    public void run()
    {
        while ( true )
        {
            try
            {
                Quote quote = queue.take();
                orderService.handle( quote );
            }
            catch ( InterruptedException e )
            {
                Log.write( "QuoteProcessor interrupted, exiting..." );
                Thread.currentThread().interrupt();
                break;
            }
            catch ( Exception e )
            {
                Log.write( "Error in QuoteProcessor: " + e.getMessage() );
                // продолжаем цикл — устойчивость
            }
        }
    }
}
