package org.webtrader.schedule.orderpositionactivation.orderAccount;

import org.webtrader.schedule.orderpositionactivation.model.Account;
import org.webtrader.schedule.orderpositionactivation.model.Order;

public record OrderAccountEntry( Order order, Account account ) {
}
