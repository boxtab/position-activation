package org.webtrader.schedule.orderpositionactivation.domais.order_notifier;

import org.webtrader.schedule.orderpositionactivation.model.Order;

public record OrderWithUuid(
        Order order,
        String uuid
) {}
