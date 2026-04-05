package org.webtrader.schedule.orderpositionactivation.orderAccount;

import org.webtrader.schedule.orderpositionactivation.model.Account;
import org.webtrader.schedule.orderpositionactivation.model.Order;

import java.util.*;
import java.util.stream.Collectors;

public class OrderAccountMapper
{
    public List<OrderAccountEntry> map( List<Order> orders, List<Account> accounts )
    {
        // Создаём карту аккаунтов по ID
        Map<Long, Account> accountMap = accounts.stream()
                .collect(Collectors.toMap(Account::getId, a -> a));

        // Сопоставляем каждый ордер с аккаунтом
        return orders.stream()
                .map(order -> {
                    Account account = accountMap.get(order.getAccountId());
                    if (account == null) return null; // пропускаем если аккаунт не найден
                    return new OrderAccountEntry(order, account);
                })
                .filter(Objects::nonNull)
                .toList();
    }
}

