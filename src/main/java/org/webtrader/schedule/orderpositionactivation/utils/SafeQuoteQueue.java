package org.webtrader.schedule.orderpositionactivation.utils;

import org.webtrader.schedule.orderpositionactivation.model.Quote;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SafeQuoteQueue
{
    private final BlockingQueue<Quote> queue;

    public SafeQuoteQueue( int maxSize )
    {
        this.queue = new LinkedBlockingQueue<>( maxSize );
    }

    public void offer( Quote quote )
    {
        boolean added = queue.offer( quote );
        if ( ! added )
        {
            queue.clear(); // Очистить всю очередь, если она переполнена
            queue.offer( quote ); // Добавить последнюю котировку
            System.err.println( "[Warning] Quote queue full — queue cleared and latest quote added." );
        }
    }

    public BlockingQueue<Quote> getQueue()
    {
        return queue;
    }
}
