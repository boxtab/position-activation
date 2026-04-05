package org.webtrader.schedule.orderpositionactivation.audit;

import org.webtrader.schedule.orderpositionactivation.orderAccount.OrderAccountEntry;

public interface AuditLogFormatter
{
    String format( OrderAccountEntry entry );
}
