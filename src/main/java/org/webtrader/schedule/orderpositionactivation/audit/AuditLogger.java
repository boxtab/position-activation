package org.webtrader.schedule.orderpositionactivation.audit;

import org.webtrader.schedule.orderpositionactivation.orderAccount.OrderAccountEntry;

public interface AuditLogger
{
    void saveAuditRecord( OrderAccountEntry orderAccountEntry );
}
