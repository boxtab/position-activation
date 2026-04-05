package org.webtrader.schedule.orderpositionactivation.services;

import org.webtrader.schedule.orderpositionactivation.domais.order_prepare.OrderCloserService;
import org.webtrader.schedule.orderpositionactivation.log.LogConsole;
import org.webtrader.schedule.orderpositionactivation.model.Order;
import org.webtrader.schedule.orderpositionactivation.model.Quote;
import org.webtrader.schedule.orderpositionactivation.repositories.OrderSLTPRepository;

import java.sql.SQLException;
import java.util.List;

public class OrderService
{
    private final OrderSLTPRepository orderSLTPRepository;

    private final OrderCloserService orderCloserService;

    private final OrderNotifierService orderNotifierService;

    private final OrderLoggerService orderLoggerService;

    public OrderService()
    {
        this.orderSLTPRepository = new OrderSLTPRepository();
        this.orderCloserService = new OrderCloserService();
        this.orderNotifierService = new OrderNotifierService();
        this.orderLoggerService = new OrderLoggerService();
    }

    public void handle( Quote quote ) throws SQLException
    {
        List<Order> orders = orderSLTPRepository.findOrdersWithSLTP( quote.getSymbol() );

        if ( orders.isEmpty() ) {
            return;
        }

        for ( Order order : orders ) {
            LogConsole.print( "Order matched: " + order );
        }

        orderCloserService.closeOrders( orders, quote.getBid(), quote.getAsk() );

        orderNotifierService.notify( orders );

        orderLoggerService.log( orders );
    }
}
