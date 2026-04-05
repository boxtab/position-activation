package org.webtrader.schedule.orderpositionactivation.domais.order_prepare;

import org.webtrader.schedule.orderpositionactivation.model.Order;

import java.util.List;

public class OrderCloserService
{
    private final OrderDatabaseUpdater updater;

    public OrderCloserService()
    {
        this.updater = new OrderDatabaseUpdater();
    }

    public void closeOrders( List<Order> orders, double bid, double ask )
    {
        OrderCloseContext context = new OrderCloseContext( bid, ask );

        OrderPositionActivator activator = new OrderPositionActivator( context );

        orders.forEach(order ->
        {
            activator.closeOrder( order );
            updater.updateOrder( order );
        });
    }
}
