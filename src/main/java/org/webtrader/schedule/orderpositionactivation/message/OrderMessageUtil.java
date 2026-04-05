package org.webtrader.schedule.orderpositionactivation.message;

import com.google.gson.Gson;
import org.webtrader.schedule.orderpositionactivation.order.constants.OrderStates;
import org.webtrader.schedule.orderpositionactivation.orderAccount.OrderIdWithUuid;

import java.util.HashMap;
import java.util.Map;

public class OrderMessageUtil
{
    public static String toJson( OrderIdWithUuid data )
    {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put( "channelName", data.uuid() );
        jsonMap.put( "eventName", "eventOrder" );
        jsonMap.put( "programName", "OrderPositionActivation.jar" );
        jsonMap.put( "eventType", "update" );
        jsonMap.put( "id", data.orderId() );

        Map<String, Integer> dataMap = new HashMap<>();
        dataMap.put( "state", OrderStates.POSITION );

        jsonMap.put( "data", dataMap );

        return new Gson().toJson( jsonMap );
    }

}
