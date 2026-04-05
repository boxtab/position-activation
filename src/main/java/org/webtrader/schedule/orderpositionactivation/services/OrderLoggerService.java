package org.webtrader.schedule.orderpositionactivation.services;

import org.webtrader.schedule.orderpositionactivation.model.Account;
import org.webtrader.schedule.orderpositionactivation.audit.AuditDatabase;
import org.webtrader.schedule.orderpositionactivation.audit.AuditLogFormatter;
import org.webtrader.schedule.orderpositionactivation.audit.PrettyTextFormatter;
import org.webtrader.schedule.orderpositionactivation.config.Config;
import org.webtrader.schedule.orderpositionactivation.repositories.AccountFetcherRepository;
import org.webtrader.schedule.orderpositionactivation.audit.AuditFile;
import org.webtrader.schedule.orderpositionactivation.model.Order;
import org.webtrader.schedule.orderpositionactivation.orderAccount.OrderAccountEntry;
import org.webtrader.schedule.orderpositionactivation.orderAccount.OrderAccountMapper;
import org.webtrader.schedule.orderpositionactivation.repositories.OrderFetcherRepository;
import java.sql.SQLException;
import java.util.List;

public class OrderLoggerService
{
    public void log( List<Order> orders ) throws SQLException
    {
        List<OrderAccountEntry> entries = fetchOrderAccountEntries( orders );
        auditEntries( entries );
    }

    private List<OrderAccountEntry> fetchOrderAccountEntries( List<Order> originalOrders )
    {
        List<Long> orderIds = extractOrderIds( originalOrders );

        List<Order> refreshedOrders = refetchOrders( orderIds );
        List<Account> accounts = fetchAccounts( refreshedOrders );

        return mapOrderAccountPairs( refreshedOrders, accounts );
    }

    private List<Long> extractOrderIds( List<Order> orders )
    {
        return orders.stream()
                .map( Order::getId )
                .toList();
    }

    private List<Order> refetchOrders( List<Long> orderIds )
    {
        OrderFetcherRepository orderFetcherRepository = new OrderFetcherRepository();
        return orderFetcherRepository.findByIds( orderIds );
    }

    private List<Account> fetchAccounts( List<Order> refreshedOrders )
    {
        AccountFetcherRepository accountFetcherRepository = new AccountFetcherRepository();
        return accountFetcherRepository.fetchAccountsByOrders( refreshedOrders );
    }

    private List<OrderAccountEntry> mapOrderAccountPairs( List<Order> orders, List<Account> accounts )
    {
        OrderAccountMapper mapper = new OrderAccountMapper();
        return mapper.map( orders, accounts );
    }

    private void auditEntries( List<OrderAccountEntry> orderAccountEntries )
    {
        String pathToStorage = Config.getConfig().getProperty( "PATH_TO_STORAGE" );

        AuditLogFormatter formatter = new PrettyTextFormatter();
        AuditFile fileLogger        = new AuditFile( pathToStorage, formatter );
        AuditDatabase dbLogger      = new AuditDatabase( pathToStorage );

        for ( OrderAccountEntry orderAccountEntry : orderAccountEntries )
        {
            fileLogger.saveAuditRecord( orderAccountEntry );
            dbLogger.saveAuditRecord( orderAccountEntry );
        }
    }
}
