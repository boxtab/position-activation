package org.webtrader.schedule.orderpositionactivation.notification;

import org.webtrader.schedule.orderpositionactivation.config.RecalculatorConfig;
import org.webtrader.schedule.orderpositionactivation.domais.order_notifier.AccountNotificationDto;
import org.webtrader.schedule.orderpositionactivation.http.HttpSender;
import org.webtrader.schedule.orderpositionactivation.message.AccountMessageUtil;
import org.webtrader.schedule.orderpositionactivation.message.OrderMessageUtil;
import org.webtrader.schedule.orderpositionactivation.orderAccount.OrderIdWithUuid;

import java.net.URI;

public class NotificationManager
{
    private final RecalculatorConfig config;

    private final HttpSender sender;

    public NotificationManager( RecalculatorConfig config )
    {
        this.config = config;
        this.sender = new HttpSender();
    }

    public void sendOrderNotification( OrderIdWithUuid data )
    {
        String message = OrderMessageUtil.toJson( data );
        URI uri = buildUri("/api/v1/demon/refresh-orders-list/");
        sender.postJson( uri, message );
    }

    public void sendAccountNotification( AccountNotificationDto accountNotificationDto )
    {
        String message = AccountMessageUtil.toJson( accountNotificationDto );
        URI uri = buildUri( "/api/v1/demon/refresh-balance-panel/" );
        sender.postJson( uri, message );
    }

    private URI buildUri( String endpoint )
    {
        return URI.create( "http://" + config.host() + ":" + config.port() + endpoint + config.token() );
    }
}
