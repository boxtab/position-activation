package org.webtrader.schedule.orderpositionactivation.orderAccount;

public record OrderIdWithUuid
        (
            long orderId,
            String uuid
        ) {}
