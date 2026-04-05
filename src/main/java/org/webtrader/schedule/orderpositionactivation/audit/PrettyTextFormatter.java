package org.webtrader.schedule.orderpositionactivation.audit;

import org.webtrader.schedule.orderpositionactivation.json.util.AccountJsonUtil;
import org.webtrader.schedule.orderpositionactivation.json.util.OrderJsonUtil;
import org.webtrader.schedule.orderpositionactivation.orderAccount.OrderAccountEntry;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PrettyTextFormatter implements AuditLogFormatter
{
    @Override
    public String format( OrderAccountEntry entry )
    {
        String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( new Date() );

        return  "\n[ " + datetime + " ]\n" +
                AuditConstants.ACTION_CLOSE + "\n" +
                AuditConstants.SCHEDULER_NAME + " = " + AuditConstants.WHO_IDENTIFY + "\n" +
                AuditConstants.CLASS_FUNCTION + "\n" +
                "Order=\n" + OrderJsonUtil.toPrettyJson( entry.order() ) + "\n" +
                "Account=\n" + AccountJsonUtil.toPrettyJson( entry.account() );
    }
}
