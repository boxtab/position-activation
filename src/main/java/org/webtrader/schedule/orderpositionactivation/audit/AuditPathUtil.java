package org.webtrader.schedule.orderpositionactivation.audit;

import org.webtrader.schedule.orderpositionactivation.orderAccount.OrderAccountEntry;

import java.io.File;

public class AuditPathUtil
{
    public static String buildFilePath( String pathToStorage, OrderAccountEntry entry )
    {
        return  pathToStorage +
                entry.account().getId() +
                File.separator +
                "order" +
                File.separator +
                entry.order().getId() +
                ".log";
    }
}
