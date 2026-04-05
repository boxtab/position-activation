package org.webtrader.schedule.orderpositionactivation.message;

import com.google.gson.Gson;
import org.webtrader.schedule.orderpositionactivation.domais.order_notifier.AccountNotificationDto;

public class AccountMessageUtil
{
    public static String toJson( AccountNotificationDto dto )
    {
        return new Gson().toJson( dto );
    }
}
