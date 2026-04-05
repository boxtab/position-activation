package org.webtrader.schedule.orderpositionactivation.services;

import org.webtrader.schedule.orderpositionactivation.account.UniqueAccount;
import org.webtrader.schedule.orderpositionactivation.config.Config;
import org.webtrader.schedule.orderpositionactivation.config.RecalculatorConfig;
import org.webtrader.schedule.orderpositionactivation.domais.order_notifier.AccountNotificationDto;
import org.webtrader.schedule.orderpositionactivation.domais.order_notifier.AccountUuidMap;
import org.webtrader.schedule.orderpositionactivation.log.LogConsole;
import org.webtrader.schedule.orderpositionactivation.message.AccountMessageUtil;
import org.webtrader.schedule.orderpositionactivation.model.Order;
import org.webtrader.schedule.orderpositionactivation.notification.NotificationManager;
import org.webtrader.schedule.orderpositionactivation.orderAccount.OrderIdWithUuid;

import java.util.List;
import java.util.Set;

public class OrderNotifierService
{
    private final NotificationManager notificationManager;

    public OrderNotifierService()
    {
        Config config = Config.getConfig();

        RecalculatorConfig recalculatorConfig = new RecalculatorConfig(
                config.getProperty( "ACCOUNT_RECALCULATOR_HOST" ),
                config.getProperty( "ACCOUNT_RECALCULATOR_PORT" ),
                config.getProperty( "ACCOUNT_RECALCULATOR_TOKEN" )
        );

        this.notificationManager = new NotificationManager( recalculatorConfig );
    }

    public void notify( List<Order> orders )
    {
        notifyAccounts( orders );
        notifyOrders( orders );
    }

    private void notifyAccounts( List<Order> orders )
    {
        Set<Long> uniqueAccountIDs = UniqueAccount.extractAccountIds( orders );

        AccountNotificationDto accountNotificationDto = new AccountNotificationDto(
                uniqueAccountIDs, "OrderPositionActivation.jar" );

        String message = AccountMessageUtil.toJson( accountNotificationDto );
        LogConsole.print( "Generated JSON: " + message );

        notificationManager.sendAccountNotification( accountNotificationDto );
    }

    private void notifyOrders( List<Order> orders )
    {
        AccountUuidMap mapper = new AccountUuidMap();
        List<OrderIdWithUuid> orderData = mapper.mapToOrderIdWithUuid( orders );

        for ( OrderIdWithUuid data : orderData )
        {
            LogConsole.print("Sending order notification: orderId=" + data.orderId() + ", uuid=" + data.uuid());
            notificationManager.sendOrderNotification( data );
        }
    }
}
