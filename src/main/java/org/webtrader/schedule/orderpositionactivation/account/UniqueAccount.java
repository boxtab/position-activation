package org.webtrader.schedule.orderpositionactivation.account;

import org.webtrader.schedule.orderpositionactivation.model.Order;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class UniqueAccount
{
    public static Set<Long> extractAccountIds( List<Order> orders )
    {
        return orders.stream()
                .map( Order::getAccountId )
                .filter( Objects::nonNull )
                .collect( Collectors.toSet() );
    }
}
